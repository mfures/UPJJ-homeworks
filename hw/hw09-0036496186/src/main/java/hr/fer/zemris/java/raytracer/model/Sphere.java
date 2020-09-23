package hr.fer.zemris.java.raytracer.model;

import java.util.Objects;

/**
 * Defines a sphere used in gui
 * 
 * @author matfures
 *
 */
public class Sphere extends GraphicalObject {
	/**
	 * Spheres center
	 */
	Point3D center;

	/**
	 * Spehres radius
	 */
	double radius;

	/**
	 * Coefficients kd* determine the object parameters for diffuse component
	 */
	double kdr, kdg, kdb;

	/**
	 * Coefficients kr* determine the object parameters for reflective components
	 */
	double krr, krg, krb;

	/**
	 * Shininess factor (n)
	 */
	double krn;

	/**
	 * Constructor
	 * 
	 * @param center spheres center
	 * @param radius spheres radius
	 * @param kdr    parameter for diffuse component, red
	 * @param kdg    parameter for diffuse component, green
	 * @param kdb    parameter for diffuse component, blue
	 * @param krr    parameter for reflective component, red
	 * @param krg    parameter for reflective component, green
	 * @param krb    parameter for reflective component, blue
	 * @param krn    Shininess factor (n)
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {

		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {
		Objects.requireNonNull(ray);
		Point3D source = ray.start;
		Point3D direction = ray.direction;

		double discr = Math.pow(direction.scalarProduct(source.sub(center)), 2)
				- (Math.pow((source.sub(center)).norm(), 2) - radius * radius);

		if (discr < 0) {
			return null;
		}

		double closestDistance = valueCloserToZero(-direction.scalarProduct(source.sub(center)) - Math.sqrt(discr),
				-direction.scalarProduct(source.sub(center)) + Math.sqrt(discr));
				
		Point3D intersection = source.add(direction.scalarMultiply(closestDistance));

		return new RayIntersection(intersection, closestDistance, true) {

			@Override
			public Point3D getNormal() {
				return intersection.sub(center).normalize();
			}

			@Override
			public double getKrr() {
				return krr;
			}

			@Override
			public double getKrn() {
				return krn;
			}

			@Override
			public double getKrg() {
				return krg;
			}

			@Override
			public double getKrb() {
				return krb;
			}

			@Override
			public double getKdr() {
				return kdr;
			}

			@Override
			public double getKdg() {
				return kdg;
			}

			@Override
			public double getKdb() {
				return kdb;
			}
		};
	}

	/**
	 * Returns value closer to zero
	 * 
	 * @param x value
	 * @param y value
	 * @return value closer to zero
	 */
	private double valueCloserToZero(double x, double y) {
		if (Math.abs(x) < Math.abs(y)) {
			return x;
		}

		return y;
	}
}