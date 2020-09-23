package hr.fer.zemris.java.fractals;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.fractals.viewer.FractalViewer;
import hr.fer.zemris.java.fractals.viewer.IFractalProducer;
import hr.fer.zemris.java.fractals.viewer.IFractalResultObserver;
import hr.fer.zemris.math.Complex;
import hr.fer.zemris.math.ComplexPolynomial;
import hr.fer.zemris.math.ComplexRootedPolynomial;
import hr.fer.zemris.math.util.cparser.ComplexNumberParser;

/**
 * Program for provideing user with representation of Newton fractal
 * 
 * @author matfures
 *
 */
public class Newton {

	public static void main(String[] args) {
		System.out.println("Welcome to Newton-Raphson iteration-based fractal viewer.");
		System.out.println("Please enter at least two roots, one root per line. Enter 'done' when done.");
		ComplexRootedPolynomial rpoly = handleInput();
		System.out.println("Image of fractal will appear shortly. Thank you.");
		FractalViewer.show(new NewtonFractalProducer(rpoly));
	}

	/**
	 * Takes lines from input and builds complex rooted polynomial
	 * 
	 * @return polynomial made from input
	 */
	private static ComplexRootedPolynomial handleInput() {
		Scanner s = new Scanner(System.in);
		List<Complex> list = new ArrayList<>();
		String line;
		int counter = 1;
		ComplexNumberParser parser;

		System.out.print("Root 1> ");
		line = s.nextLine();

		while (!line.equals("done")) {
			try {
				parser = new ComplexNumberParser(line);
				list.add(parser.getValue());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				counter--;
			}

			counter++;
			System.out.print("Root " + counter + "> ");
			line = s.nextLine();
		}

		if (list.size() < 2) {
			System.out.println("Number of inputs was less than 2. Shutting down.");
			System.exit(1);
		}

		s.close();
		return new ComplexRootedPolynomial(Complex.ONE, list);
	}

	/**
	 * Producer used to produce image of fractal
	 * 
	 * @author matfures
	 *
	 */
	private static class NewtonFractalProducer implements IFractalProducer {
		/**
		 * Polynomial in rooted form
		 */
		private ComplexRootedPolynomial rpoly;

		/**
		 * Polynomial in factors form
		 */
		private ComplexPolynomial polynomial;

		/**
		 * Used for managing jobs to threads
		 */
		private ExecutorService pool;

		/**
		 * Constructor. Polynomial can't be null
		 * 
		 * @param rpoly Polynomial
		 * @throws NullPointerException if rpoly is null
		 */
		public NewtonFractalProducer(ComplexRootedPolynomial rpoly) {
			Objects.requireNonNull(rpoly);

			this.rpoly = rpoly;
			polynomial = rpoly.toComplexPolynom();
			pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), x -> {
				Thread thread = new Thread(x);
				thread.setDaemon(true);
				return thread;
			});
		}

		@Override
		public void produce(double reMin, double reMax, double imMin, double imMax, int width, int height,
				long requestNo, IFractalResultObserver observer, AtomicBoolean cancel) {
			System.out.println("Zapocinjem izracun...");
			int m = 16 * 16 * 16;
			short[] data = new short[width * height];
			final int brojTraka = 8 * Runtime.getRuntime().availableProcessors();
			int brojYPoTraci = height / brojTraka;

			List<Future<Void>> rezultati = new ArrayList<>();

			for (int i = 0; i < brojTraka; i++) {
				int yMin = i * brojYPoTraci;
				int yMax = (i + 1) * brojYPoTraci - 1;
				if (i == brojTraka - 1) {
					yMax = height - 1;
				}
				PosaoIzracuna posao = new PosaoIzracuna(reMin, reMax, imMin, imMax, width, height, yMin, yMax, m, data,
						cancel, rpoly);
				rezultati.add(pool.submit(posao));
			}
			for (Future<Void> posao : rezultati) {
				try {
					posao.get();
				} catch (InterruptedException | ExecutionException e) {
				}
			}

			System.out.println("Racunanje gotovo. Idem obavijestiti promatraca tj. GUI!");
			observer.acceptResult(data, (short) (polynomial.order() + 1), requestNo);

		}

	}

	/**
	 * Defines a job for calculating fractals
	 * 
	 * @author matfures
	 *
	 */
	public static class PosaoIzracuna implements Callable<Void> {
		double reMin;
		double reMax;
		double imMin;
		double imMax;
		int width;
		int height;
		int yMin;
		int yMax;
		int m;
		short[] data;
		AtomicBoolean cancel;

		/**
		 * Polynomial in rooted form
		 */
		private ComplexRootedPolynomial rpoly;

		/**
		 * Polynomial in factors form
		 */
		private ComplexPolynomial polynomial;

		/**
		 * Polynomial derived
		 */
		private ComplexPolynomial derived;

		public PosaoIzracuna(double reMin, double reMax, double imMin, double imMax, int width, int height, int yMin,
				int yMax, int m, short[] data, AtomicBoolean cancel, ComplexRootedPolynomial rpoly) {
			super();
			this.reMin = reMin;
			this.reMax = reMax;
			this.imMin = imMin;
			this.imMax = imMax;
			this.width = width;
			this.height = height;
			this.yMin = yMin;
			this.yMax = yMax;
			this.m = m;
			this.data = data;
			this.cancel = cancel;
			this.rpoly = rpoly;
			polynomial = rpoly.toComplexPolynom();
			derived = polynomial.derive();
		}

		@Override
		public Void call() {

			for (int y = yMin; y <= yMax; y++) {
				if (cancel.get())
					break;
				for (int x = 0; x < width; x++) {
					double cre = x / (width - 1.0) * (reMax - reMin) + reMin;
					double cim = (height - 1.0 - y) / (height - 1) * (imMax - imMin) + imMin;
					Complex zn = new Complex(cre, cim);

					double module = 0;
					int iters = 0;
					do {
						Complex numerator = polynomial.apply(zn);
						Complex denominator = derived.apply(zn);
						Complex znold = zn;
						Complex fraction = numerator.divide(denominator);
						zn = zn.sub(fraction);
						module = znold.sub(zn).module();
						iters++;
					} while (iters < m && module > 1e-3);
					data[width * y + x] = (short) (1 + rpoly.indexOfClosestRootFor(zn, 0.002));
				}
			}
			return null;
		}
	}
}
