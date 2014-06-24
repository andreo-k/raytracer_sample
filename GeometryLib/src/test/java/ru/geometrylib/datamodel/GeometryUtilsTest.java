package ru.geometrylib.datamodel;

import junit.framework.TestCase;

import org.junit.Test;

import ru.geometrylib.GeometryUtils;
import ru.geometrylib.GeometryUtils.MollerTrumboreResult;

public class GeometryUtilsTest extends TestCase
{
	@Test
	public void testOctantNumberForPoint()
	{
		Point center = new Point();

		assertEquals(
				0,
				GeometryUtils.OctantNumberForPoint(center,
						GeometryUtils.getPt(0.0d, 0.0d, 0.0d)));
		assertEquals(
				0,
				GeometryUtils.OctantNumberForPoint(center,
						GeometryUtils.getPt(1.0d, 1.0d, 1.0d)));
		assertEquals(
				1,
				GeometryUtils.OctantNumberForPoint(center,
						GeometryUtils.getPt(-1.0d, 1.0d, 1.0d)));
		assertEquals(
				5,
				GeometryUtils.OctantNumberForPoint(center,
						GeometryUtils.getPt(-1.0d, 1.0d, -1.0d)));
		assertEquals(
				7,
				GeometryUtils.OctantNumberForPoint(center,
						GeometryUtils.getPt(-1.0d, -1.0d, -1.0d)));
	}

	@Test
	public void testOctantNumbersForRay()
	{
		Point center = new Point();

		Ray ray = getRay(1.0d, 1.0d, 1.0d, -1.0d, -1.0d, -1.0d);
		check(GeometryUtils.OctantNumbersForRay(center, ray), 0, 7);

		ray = getRay(0.0d, 0.0d, 0.0d, -1.0d, -1.0d, -1.0d);
		check(GeometryUtils.OctantNumbersForRay(center, ray), 0, 7);

		ray = getRay(-1.0d, -1.0d, -1.0d, -1.0d, -1.0d, -1.0d);
		check(GeometryUtils.OctantNumbersForRay(center, ray), 7);

		ray = getRay(-1.0d, -1.0d, -1.0d, 0.0d, 0.0d, -1.0d);
		check(GeometryUtils.OctantNumbersForRay(center, ray), 7);

		ray = getRay(-2d, 1d, 1d, 1.0d, 1.0d, 0.0d);
		check(GeometryUtils.OctantNumbersForRay(center, ray), 0, 1);

		ray = getRay(-2d, 1d, 1d, 1.0d, 1.0d, -0.0001d);
		check(GeometryUtils.OctantNumbersForRay(center, ray), 0, 1, 4);

	}

	@Test
	public void testMollerTrumbore()
	{
		Point a = GeometryUtils.getPt(0.0d, 0.0d, 0.0d);
		Point b = GeometryUtils.getPt(1.0d, 0.0d, 0.0d);
		Point c = GeometryUtils.getPt(0.0d, 1.0d, 0.0d);

		MollerTrumboreResult mtr = null;
		
		mtr = GeometryUtils.MollerTrumbore(a, b, c,
				getRay(0.2d, 0.2d, 1.0d, 0.0d, 0.0d, -0.5d));
		assertTrue(mtr.isIntersection());
		assertEquals(1.0d, mtr.getDistance(), 0.01d);

		assertFalse(GeometryUtils.MollerTrumbore(a, b, c,
				getRay(0.2d, 0.2d, 1.0d, 0.0d, 0.0d, 1.0d)).isIntersection());

		mtr = GeometryUtils.MollerTrumbore(a, b, c,
				getRay(0.2d, 0.2d, -1.0d, 0.0d, 0.0d, 1.0d));
		assertTrue(mtr.isIntersection());
		assertEquals(1.0d, mtr.getDistance(), 0.01d);
	}

	@Test
	public void testMollerTrumbore2()
	{
		Point a = GeometryUtils.getPt(10.0d, 10.0d, 2.0d);
		Point b = GeometryUtils.getPt(1.0d, 10.0d, 1.0d);
		Point c = GeometryUtils.getPt(1.0d, 1.0d, 3.0d);

		MollerTrumboreResult mtr = null;
		
		mtr = GeometryUtils.MollerTrumbore(a, b, c,
				getRay(5d, 6d, 20d, 1.0d, 1.0d, -10d));
		assertTrue(mtr.isIntersection());
		assertEquals(18.04d, mtr.getDistance(), 0.01d);
		
		mtr = GeometryUtils.MollerTrumbore(a, b, c,
				getRay(5d, 6d, -20d, 0.1d, 0.1d, 20d));
		assertTrue(mtr.isIntersection());
		assertEquals(22.32d, mtr.getDistance(), 0.01d);
		
		assertFalse(GeometryUtils.MollerTrumbore(a, b, c,
				getRay(5d, 6d, -20d, 0.1d, 0.1d, -20d)).isIntersection());	
		
	}		
	
	@Test
	public void testGetCameraOrigin()
	{
		Point viewPortOrigin = GeometryUtils.getPt(0, 0, 0);
		Point viewPortX = GeometryUtils.getPt(20, 0, 0);
		Point viewPortY = GeometryUtils.getPt(0, 10, 0);
		Point cameraOrigin = GeometryUtils.getCameraOrigin(viewPortOrigin, viewPortX, viewPortY, 30.0d);
		assertEquals(10.0d, cameraOrigin.getX(), 0.01d);
		assertEquals(5.0d, cameraOrigin.getY(), 0.01d);
		assertEquals(30.0d, cameraOrigin.getZ(), 0.01d);
	}
	
	private void check(boolean[] b, int... numbers)
	{
		for (int i = 0; i < numbers.length; ++i)
		{
			assertTrue("Expected true in " + Integer.toString(numbers[i]),
					b[numbers[i]]);
			b[numbers[i]] = false;
		}

		for (int i = 0; i < 8; ++i)
		{
			assertFalse("Expected false in " + Integer.toString(i), b[i]);
		}
	}

	private Ray getRay(double x, double y, double z, double dx, double dy,
			double dz)
	{
		Ray result = new Ray();
		result.getOrigin().setX(x);
		result.getOrigin().setY(y);
		result.getOrigin().setZ(z);
		result.getDirection().setX(dx);
		result.getDirection().setY(dy);
		result.getDirection().setZ(dz);
		return result;
	}
}
