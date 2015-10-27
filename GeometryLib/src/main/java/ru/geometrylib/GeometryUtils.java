package ru.geometrylib;

import ru.geometrylib.datamodel.Point;
import ru.geometrylib.datamodel.Ray;

public final class GeometryUtils
{
	public static final double EPS = 0.0000001d;

	// номер октанта для точки point в системе координат с центром center
	public static int OctantNumberForPoint(Point center, Point point)
	{
		int result = 0;
		int bit = 0;
		if (point.getX() < center.getX())
			bit = 1;
		result |= bit;
		bit = 0;
		if (point.getY() < center.getY())
			bit = 1;
		result |= (bit << 1);
		bit = 0;
		if (point.getZ() < center.getZ())
			bit = 1;
		result |= (bit << 2);

		return result;
	}

	// октанты, которые пересекает луч
	public static boolean[] OctantNumbersForRay(Point center, Ray ray)
	{
		boolean result[] = new boolean[] { false, false, false, false, false,
				false, false, false };

		int number = OctantNumberForPoint(center, ray.getOrigin());
		result[number] = true;

		// используем параметрические уравнения луча

		double t = 1.0d;

		if (Math.abs(ray.getDirection().getX()) > EPS)
		{
			if (Math.abs(ray.getOrigin().getX() - center.getX()) > EPS)
			{
				t = (center.getX() - ray.getOrigin().getX())
						/ ray.getDirection().getX();
			}

			if (t > 0.0d)
			{
				t += EPS;
				number = OctantNumberForPoint(center, ray.getPointForT(t));
				result[number] = true;
			}
		}

		t = 1.0d;

		if (Math.abs(ray.getDirection().getY()) > EPS)
		{
			if (Math.abs(ray.getOrigin().getY() - center.getY()) > EPS)
			{
				t = (center.getY() - ray.getOrigin().getY())
						/ ray.getDirection().getY();
			}

			if (t > 0.0d)
			{
				t += EPS;
				number = OctantNumberForPoint(center, ray.getPointForT(t));
				result[number] = true;
			}
		}

		t = 1.0d;

		if (Math.abs(ray.getDirection().getZ()) > EPS)
		{
			if (Math.abs(ray.getOrigin().getZ() - center.getZ()) > EPS)
			{
				t = (center.getZ() - ray.getOrigin().getZ())
						/ ray.getDirection().getZ();
			}

			if (t > 0.0d)
			{
				t += EPS;
				number = OctantNumberForPoint(center, ray.getPointForT(t));
				result[number] = true;
			}
		}

		return result;
	}

	public static Point getPt(double x, double y, double z)
	{
		Point result = new Point();
		result.setX(x);
		result.setY(y);
		result.setZ(z);
		return result;
	}

	public static Point getVec(Point from, Point to)
	{
		return getPt(to.getX() - from.getX(), to.getY() - from.getY(),
				to.getZ() - from.getZ());
	}

	public static double getVectorLength(Point v)
	{
		return Math.sqrt(scalarProduct(v, v));
	}

	public static Point normalizeVector(Point v)
	{
		double len = getVectorLength(v);
		Point result = new Point();
		result.setX(v.getX() / len);
		result.setY(v.getY() / len);
		result.setZ(v.getZ() / len);
		return result;
	}

	public static Point mulVector(Point v, double mul)
	{
		Point result = new Point();
		result.setX(v.getX() * mul);
		result.setY(v.getY() * mul);
		result.setZ(v.getZ() * mul);
		return result;
	}

	public static Point sumVectors(Point v1, Point v2)
	{
		Point result = new Point();
		result.setX(v1.getX() + v2.getX());
		result.setY(v1.getY() + v2.getY());
		result.setZ(v1.getZ() + v2.getZ());
		return result;
	}

	public static Point getCameraOrigin(Point viewPortOrigin, Point viewPortX,
			Point viewPortY, double cameraDistance)
	{
		Point cameraOrigin = crossProduct(viewPortX, viewPortY);
		cameraOrigin = normalizeVector(cameraOrigin);
		cameraOrigin = mulVector(cameraOrigin, cameraDistance);
		cameraOrigin = sumVectors(cameraOrigin, mulVector(viewPortX, 0.5d));
		cameraOrigin = sumVectors(cameraOrigin, mulVector(viewPortY, 0.5d));
		cameraOrigin = sumVectors(viewPortOrigin, cameraOrigin);

		return cameraOrigin;
	}

	public static Point crossProduct(Point v1, Point v2)
	{
		return getPt(v1.getY() * v2.getZ() - v1.getZ() * v2.getY(), v1.getZ()
				* v2.getX() - v1.getX() * v2.getZ(),
				v1.getX() * v2.getY() - v1.getY() * v2.getX());
	}

	public static double scalarProduct(Point v1, Point v2)
	{
		return v1.getX() * v2.getX() + v1.getY() * v2.getY() + v1.getZ()
				* v2.getZ();
	}

	public static final class MollerTrumboreResult
	{
		private boolean intersection;
		private double distance;

		public MollerTrumboreResult()
		{
		}

		public boolean isIntersection()
		{
			return intersection;
		}

		public void setIntersection(boolean intersection)
		{
			this.intersection = intersection;
		}

		public double getDistance()
		{
			return distance;
		}

		public void setDistance(double distance)
		{
			this.distance = distance;
		}
	};

	// пересечение луча и треугольника
	public static MollerTrumboreResult MollerTrumbore(Point a, Point b,
			Point c, Ray ray)
	{		
		MollerTrumboreResult result = new MollerTrumboreResult();

		Point edge1 = getVec(a, b);
		Point edge2 = getVec(a, c);

		Point pVec = crossProduct(ray.getDirection(), edge2);

		double det = scalarProduct(edge1, pVec);

		if (Math.abs(det) < EPS)
			return result;

		Point tVec = getVec(a, ray.getOrigin());

		double u = scalarProduct(tVec, pVec) / det;

		if (u < 0.0d || u > 1.0d)
			return result;

		Point qVec = crossProduct(tVec, edge1);

		double v = scalarProduct(ray.getDirection(), qVec) / det;

		if (v < 0.0d || u + v > 1.0d)
			return result;

		double t = scalarProduct(edge2, qVec) / det;

		if (t < 0.0d)
			return result;

		result.setIntersection(true);
		result.setDistance(t * getVectorLength(ray.getDirection()));

		return result;
	}
}
