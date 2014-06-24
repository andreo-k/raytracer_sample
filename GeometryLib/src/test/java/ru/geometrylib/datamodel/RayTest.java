package ru.geometrylib.datamodel;

import org.junit.Test;

import junit.framework.TestCase;

//rayId;originX,originY,originZ;directionX,directionY,directionZ
public class RayTest extends TestCase
{
	@Test
	public void testParseCorrect()
	{
		Ray ray = new Ray();
		ray.parse("hello;1.234,5.678,9.101;12.13,14.15,16.17");

		assertEquals("hello", ray.getId());

		assertEquals(1.234, ray.getOrigin().getX(), 0.01);
		assertEquals(5.678, ray.getOrigin().getY(), 0.01);
		assertEquals(9.101, ray.getOrigin().getZ(), 0.01);

		assertEquals(12.13, ray.getDirection().getX());
		assertEquals(14.15, ray.getDirection().getY());
		assertEquals(16.17, ray.getDirection().getZ());
	}

	@Test
	public void testParseIncorrect()
	{
		try
		{
			Ray ray = new Ray();
			ray.parse("hello");
		}
		catch (Throwable e)
		{
			return;
		}
		fail("exception expected");
	}
}
