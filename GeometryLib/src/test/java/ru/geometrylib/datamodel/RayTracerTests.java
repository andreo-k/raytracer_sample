package ru.geometrylib.datamodel;

import junit.framework.TestCase;

import org.junit.Test;

import ru.geometrylib.RayTracer;
import ru.geometrylib.RayTracer.RayTraceResult;

public class RayTracerTests extends TestCase
{
	@Test
	public void testRayTracer() throws Exception
	{
		SceneHolder sh = new SceneHolder(SceneLoader.Load(SceneLoaderTest.class
				.getResourceAsStream("example.xml")));
		RayTracer rt = new RayTracer(sh);

		RayTraceResult rtr = null;

		rtr = rt.traceRay(Ray.createFromString("1;1,-0.5,5;0,0,-1"));
		assertEquals("pyramid", sh.getObjectByFaceId(rtr.getFirstFace().getId()).getName());

		rtr = rt.traceRay(Ray.createFromString("2;1,1,1;1,1,1"));
		assertEquals(null, rtr.getFirstFace());

		rtr = rt.traceRay(Ray.createFromString("3;-1,-0.5,5;0,0,-100"));
		assertEquals("pyramid", sh.getObjectByFaceId(rtr.getFirstFace().getId()).getName());
	}

	@Test
	public void testRayTracerImg() throws Exception
	{

	}
}
