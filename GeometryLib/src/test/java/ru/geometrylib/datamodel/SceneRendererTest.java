package ru.geometrylib.datamodel;

import junit.framework.TestCase;

import org.junit.Test;

import ru.geometrylib.GeometryUtils;
import ru.geometrylib.SceneRenderer;

public class SceneRendererTest extends TestCase
{
	@Test
	public void testRenderSmall() throws Exception
	{
		Scene scene = SceneLoader.Load(SceneLoaderTest.class
				.getResourceAsStream("example.xml"));
		SceneRenderer renderer = new SceneRenderer(scene);
		renderer.setImgHeight(600);
		renderer.setImgWidth(800);
		renderer.setCameraDistance(5.0d);
		renderer.setViewPortOrigin(GeometryUtils.getPt(-5, -5, 10));
		renderer.setViewPortX(GeometryUtils.getPt(10, 0, 0));
		renderer.setViewPortY(GeometryUtils.getPt(0, 10, 0));
		renderer.renderToPng("example.png");
	}

	@Test
	public void testRenderBig() throws Exception
	{
		Scene scene = SceneLoader.Load(SceneLoaderTest.class
				.getResourceAsStream("example2.xml"));		
		SceneRenderer renderer = new SceneRenderer(scene);
		renderer.setImgHeight(600);
		renderer.setImgWidth(800);
		renderer.setCameraDistance(500.0d);
		renderer.setViewPortOrigin(GeometryUtils.getPt(-1000, -1000, 1000));
		renderer.setViewPortX(GeometryUtils.getPt(1000, 0, 0));
		renderer.setViewPortY(GeometryUtils.getPt(0, 1000, 0));
		renderer.renderToPng("example2.png");
	}

}
