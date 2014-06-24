package ru.geometrylib.datamodel;

import java.util.HashMap;

public class SceneHolder
{
	private Scene scene;
	private HashMap<Integer, Point> id2point = new HashMap<Integer, Point>();
	private HashMap<Integer, ru.geometrylib.datamodel.Object> faceId2object = new HashMap<Integer, ru.geometrylib.datamodel.Object>();

	public SceneHolder(Scene scene)
	{
		this.scene = scene;
		for (Point point : scene.getPoints().getPoint())
		{		
			id2point.put(point.getId(), point);
		}		
		for (ru.geometrylib.datamodel.Object object : scene.getObjects()
				.getObject())
		{
			for (Integer faceId : object.getFaceId())
			{
				faceId2object.put(faceId, object);
			}
		}		
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	public Point getPointById(Integer id)
	{
		return id2point.get(id);
	}
	
	public ru.geometrylib.datamodel.Object getObjectByFaceId(int id)
	{
		return faceId2object.get(Integer.valueOf(id));
	}
	
}
