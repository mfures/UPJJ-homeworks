package hr.fer.zemris.java.raytracer;

import java.util.concurrent.atomic.AtomicBoolean;

import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * Program that draws a scene
 * 
 * @author matfures
 *
 */
public class RayCaster {
	/**
	 * Main method that draws some spheres
	 * 
	 * @param args not used
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * IRayTracerProducer that draws image on the scene
	 * 
	 * @return IRayTracerProducer object
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer, AtomicBoolean cancel) {
				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D vuv = viewUp.normalize();

				Point3D zAxis = view.sub(eye).normalize();
				Point3D yAxis = vuv.sub(zAxis.scalarMultiply(zAxis.scalarProduct(vuv))).normalize();
				Point3D xAxis = zAxis.vectorProduct(yAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();
				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					if (cancel.get())
						break;
					for (int x = 0; x < width; x++) {
						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(x * horizontal / (width - 1))
								.sub(yAxis.scalarMultiply(y * vertical / (height - 1))));
						Ray ray = Ray.fromPoints(eye, screenPoint);
						tracer(scene, ray, rgb);
						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];
						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Ambient value for red color
	 */
	private static final short AMBIENT_RED = 15;

	/**
	 * Ambient value for green color
	 */
	private static final short AMBIENT_GREEN = 15;

	/**
	 * Ambient value for blue color
	 */
	private static final short AMBIENT_BLUE = 15;

	/**
	 * Tracer method that colors pixels
	 * 
	 * @param scene on which objects are
	 * @param ray   of light
	 * @param rgb   colors array
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);

		if (closest == null) {
			return;
		}

		rgb[0] = AMBIENT_RED;
		rgb[1] = AMBIENT_GREEN;
		rgb[2] = AMBIENT_BLUE;

		for (LightSource ls : scene.getLights()) {
			Ray ry = Ray.fromPoints(ls.getPoint(), closest.getPoint());
			RayIntersection S = findClosestIntersection(scene, ry);

			if (S == null) {
				continue;
			}

			if (ls.getPoint().sub(closest.getPoint()).norm() - ls.getPoint().sub(S.getPoint()).norm() > 1e-8) {
				continue;
			}

			double ln = ls.getPoint().sub(S.getPoint()).normalize().scalarProduct(S.getNormal());

			rgb[0] += S.getKdr() * Math.max(ln, 0) * ls.getR();
			rgb[1] += S.getKdg() * Math.max(ln, 0) * ls.getG();
			rgb[2] += S.getKdb() * Math.max(ln, 0) * ls.getB();

			double konst = calculateFactor(S, ls, ray);

			if (konst <= 0) {
				continue;
			}

			konst = Math.pow(konst, S.getKrn());
			rgb[0] += konst * S.getKrr() * ls.getR();
			rgb[1] += konst * S.getKrg() * ls.getG();
			rgb[2] += konst * S.getKrb() * ls.getB();
		}
	}

	/**
	 * Calculates coefficient for light
	 * 
	 * @param S   scene
	 * @param ls  light source
	 * @param ray ray
	 * @return coefficient
	 */
	private static double calculateFactor(RayIntersection S, LightSource ls, Ray ray) {
		Point3D n = S.getNormal();
		Point3D l = ls.getPoint().sub(S.getPoint());
		Point3D r = n.scalarMultiply(l.scalarProduct(n)).add(n.scalarMultiply(l.scalarProduct(n)).sub(l));
		Point3D v = ray.start.sub(S.getPoint());

		return r.normalize().scalarProduct(v.normalize());
	}

	/**
	 * Returns closest intersection between all objects in the scene and current ray
	 * 
	 * @param scene that is drawn
	 * @param ray   of light
	 * @return closest intersection
	 */
	private static RayIntersection findClosestIntersection(Scene scene, Ray ray) {
		RayIntersection intersection = null;
		RayIntersection tmp;

		for (GraphicalObject go : scene.getObjects()) {
			tmp = go.findClosestRayIntersection(ray);

			if (tmp != null) {
				if (intersection == null) {
					intersection = tmp;
				} else if (intersection.getDistance() > tmp.getDistance()) {
					intersection = tmp;
				}
			}
		}

		return intersection;
	}

}
