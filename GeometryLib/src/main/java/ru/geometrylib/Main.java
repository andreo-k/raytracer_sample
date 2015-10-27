package ru.geometrylib;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import ru.geometrylib.RayTracer.RayTraceResult;
import ru.geometrylib.datamodel.Ray;
import ru.geometrylib.datamodel.SceneHolder;
import ru.geometrylib.datamodel.SceneLoader;

public class Main
{
	public static void main(String[] args)
	{
		//this comment was added to Hello branch via web editor tool
		try
		{
			if (args.length < 1)
			{
				System.out
						.println("example: java -jar GeometryLib.jar scene.xml");
				return;
			}

			SceneHolder sh = new SceneHolder(
					SceneLoader.Load(new File(args[0])));
			RayTracer rt = new RayTracer(sh);

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					System.in));

			while (true)
			{
				Ray ray = Ray.createFromString(reader.readLine());
				RayTraceResult result = rt.traceRay(ray);
				System.out.print(ray.getId() + ":");
				if (result.getFirstFace()!=null)
				{					
					System.out.println(sh.getObjectByFaceId(result.getFirstFace().getId()).getName());					
				}
				else
					System.out.println("");
			}
		}
		catch (Throwable e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}
}
