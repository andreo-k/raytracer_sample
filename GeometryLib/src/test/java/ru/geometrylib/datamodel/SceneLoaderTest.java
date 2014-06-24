package ru.geometrylib.datamodel;

import junit.framework.TestCase;

import org.junit.Test;

public class SceneLoaderTest extends TestCase
{

	@Test
	public void testLoadSmall() throws Throwable
	{
		Scene scene = SceneLoader.Load(SceneLoaderTest.class
				.getResourceAsStream("example.xml"));
		assertEquals(scene.getPoints().getPoint().size(), 4);
		assertEquals(scene.getFaces().getFace().size(), 4);
		assertEquals(scene.getObjects().getObject().size(), 1);
	}

	@Test
	public void testLoadBig() throws Throwable
	{
		Scene scene = SceneLoader.Load(SceneLoaderTest.class
				.getResourceAsStream("example2.xml"));
		assertEquals(scene.getPoints().getPoint().size(), 608);
		assertEquals(scene.getFaces().getFace().size(), 1024);
		assertEquals(scene.getObjects().getObject().size(), 48);
	}

	@Test
	public void testLoadIncorrect() throws Throwable
	{
		try
		{
			Scene scene = SceneLoader.Load(SceneLoaderTest.class
					.getResourceAsStream("incorrect.xml"));			
		}
		catch (Throwable e)
		{
			return;
		}
		fail("exception expected");
	}

}
