package ru.geometrylib;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;

import ru.geometrylib.datamodel.Face;
import ru.geometrylib.datamodel.Point;
import ru.geometrylib.datamodel.Scene;

public final class SceneRenderer
{
	private Scene scene;
	private RayTracer rayTracer;
	private int imgWidth;
	private int imgHeight;
	private Point viewPortOrigin;
	private Point viewPortX;
	private Point viewPortY;
	private double cameraDistance;

	private HashMap<Face, Color> face2color = new HashMap<Face, Color>();

	public SceneRenderer(Scene scene)
	{
		this.scene = scene;
		rayTracer = new RayTracer(scene);
		imgWidth = 800;
		imgHeight = 600;

		Random random = new Random();

		for (Face face : scene.getFaces().getFace())
		{
			face2color.put(face, new Color(random.nextInt(256 * 256 * 256)));
		}
	}

	public int getImgWidth()
	{
		return imgWidth;
	}

	public void setImgWidth(int imgWidth)
	{
		this.imgWidth = imgWidth;
	}

	public int getImgHeight()
	{
		return imgHeight;
	}

	public void setImgHeight(int imgHeight)
	{
		this.imgHeight = imgHeight;
	}

	public Point getViewPortOrigin()
	{
		return viewPortOrigin;
	}

	public void setViewPortOrigin(Point viewPortOrigin)
	{
		this.viewPortOrigin = viewPortOrigin;
	}

	public Point getViewPortX()
	{
		return viewPortX;
	}

	public void setViewPortX(Point viewPortX)
	{
		this.viewPortX = viewPortX;
	}

	public Point getViewPortY()
	{
		return viewPortY;
	}

	public void setViewPortY(Point viewPortY)
	{
		this.viewPortY = viewPortY;
	}

	public double getCameraDistance()
	{
		return cameraDistance;
	}

	public void setCameraDistance(double cameraDistance)
	{
		this.cameraDistance = cameraDistance;
	}

	public void renderToPng(String fileName) throws Exception
	{
		Point cameraOrigin = GeometryUtils.getCameraOrigin(viewPortOrigin,
				viewPortX, viewPortY, cameraDistance);

		BufferedImage img = new BufferedImage(imgWidth, imgHeight,
				BufferedImage.TYPE_INT_ARGB);

		Graphics2D g2 = img.createGraphics();
		g2.setBackground(Color.WHITE);

		for (int x = 0; x < imgWidth; ++x)
		{
			Point currentX = GeometryUtils.sumVectors(viewPortOrigin,
					GeometryUtils.mulVector(viewPortX, x / (double) imgWidth));

			for (int y = 0; y < imgHeight; ++y)
			{
				Point currentPoint = GeometryUtils.sumVectors(
						currentX,
						GeometryUtils.mulVector(viewPortY, y
								/ (double) imgHeight));
				Face face = rayTracer.traceRay(cameraOrigin, currentPoint)
						.getFirstFace();
				if (face != null)
				{
					Color color = face2color.get(face);
					img.setRGB(x, y, color.getRGB());
				}
			}
		}

		File outputfile = new File(fileName);
		ImageIO.write(img, "png", outputfile);
	}
}
