package ru.geometrylib;

import ru.geometrylib.datamodel.Face;
import ru.geometrylib.datamodel.Point;
import ru.geometrylib.datamodel.Ray;
import ru.geometrylib.datamodel.Scene;
import ru.geometrylib.datamodel.SceneHolder;

public final class RayTracer
{
	private Scene scene;
	private SceneHolder sceneHolder;
	private SpaceTreeNode<Face> tree = new SpaceTreeNode<Face>(new Point());

	public RayTracer(Scene scene)
	{
		this(new SceneHolder(scene));
	}		
	
	public RayTracer(SceneHolder sceneHolder)
	{
		this.scene = sceneHolder.getScene();
		this.sceneHolder = sceneHolder;
		init();		
	}

	private void insertFaceIntoTree(SpaceTreeNode<Face> tree, Face face,
			Point point1, Point point2, Point point3)
	{
		int octantNumber1 = tree.getOctantNumber(point1);
		int octantNumber2 = tree.getOctantNumber(point2);
		int octantNumber3 = tree.getOctantNumber(point3);

		// если грань пересекает несколько октантов - её приписываем к вершине,
		// иначе к какой-то из дочерних
		if (octantNumber1 == octantNumber2 && octantNumber2 == octantNumber3)
		{
			insertFaceIntoTree(tree.getChildren()[octantNumber1], face, point1,
					point2, point3);
		}
		else
		{
			tree.getData().add(face);
		}
	}

	private void init()
	{
		for (Point point : scene.getPoints().getPoint())
		{
			tree.insert(point);
		}

		for (Face face : scene.getFaces().getFace())
		{
			Point point1 = sceneHolder.getPointById(face.getPointId().get(0));
			Point point2 = sceneHolder.getPointById(face.getPointId().get(1));
			Point point3 = sceneHolder.getPointById(face.getPointId().get(2));

			insertFaceIntoTree(tree, face, point1, point2, point3);
		}
	}

	public static final class RayTraceResult
	{
		private Face firstFace;
		private double distance = Double.POSITIVE_INFINITY;
		private Ray ray;

		public RayTraceResult()
		{
		}

		public Face getFirstFace()
		{
			return firstFace;
		}

		public double getDistance()
		{
			return distance;
		}

		public Ray getRay()
		{
			return ray;
		}
	};

	private void traceRayInternal(SpaceTreeNode<Face> treeNode,
			RayTraceResult ctx)
	{
		// нужно проверить грани, лежащие в нескольких октантах
		for (Face face : treeNode.getData())
		{
			Point a = sceneHolder.getPointById(face.getPointId().get(0));
			Point b = sceneHolder.getPointById(face.getPointId().get(1));
			Point c = sceneHolder.getPointById(face.getPointId().get(2));

			GeometryUtils.MollerTrumboreResult mtr = GeometryUtils
					.MollerTrumbore(a, b, c, ctx.ray);
			if (mtr.isIntersection())
			{
				if (mtr.getDistance() < ctx.distance)
				{
					ctx.distance = mtr.getDistance();
					ctx.firstFace = face;
				}
			}
		}

		// смотрим, какие октанты пересекает луч и проверяем их рекурсивно
		boolean[] octantsForRay = GeometryUtils.OctantNumbersForRay(
				treeNode.getPoint(), ctx.ray);
		for (int i = 0; i < 8; ++i)
		{
			if (octantsForRay[i] && treeNode.getChildren()[i] != null)
				traceRayInternal(treeNode.getChildren()[i], ctx);
		}
	}

	public RayTraceResult traceRay(Ray ray)
	{		
		RayTraceResult ctx = new RayTraceResult();
		ctx.ray = ray;
		traceRayInternal(tree, ctx);

		return ctx;
	}
	
	public RayTraceResult traceRay(Point from, Point to)
	{
		Ray ray = new Ray();
		ray.setOrigin(from);
		ray.setDirection(GeometryUtils.getVec(from, to));
		return traceRay(ray);
	}
}
