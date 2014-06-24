package ru.geometrylib.datamodel;

public final class Ray
{
	private String id;
	private Point origin = new Point();
	private Point direction = new Point();
	
	public Ray()
	{		
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Point getOrigin()
	{
		return origin;
	}

	public void setOrigin(Point origin)
	{
		this.origin = origin;
	}

	public Point getDirection()
	{
		return direction;
	}

	public void setDirection(Point direction)
	{
		this.direction = direction;
	}
	
	//используются параметрические уравнения луча
	public Point getPointForT(double t)
	{
		Point result = new Point();
		result.setX(origin.getX() + t*direction.getX());
		result.setY(origin.getY() + t*direction.getY());
		result.setZ(origin.getZ() + t*direction.getZ());
		return result;
	}
	
	public static Ray createFromString(String s)
	{
		Ray result = new Ray();
		result.parse(s);
		return result;
	}
	
	public void parse(String s)
	{
		//rayId;originX,originY,originZ;directionX,directionY,directionZ
		String[] idOriginDirection = s.split(";");
		id = idOriginDirection[0];
		
		String[] origin = idOriginDirection[1].split(",");
		this.origin.setX(Double.parseDouble(origin[0]));
		this.origin.setY(Double.parseDouble(origin[1]));
		this.origin.setZ(Double.parseDouble(origin[2]));
		
		String[] direction = idOriginDirection[2].split(",");
		this.direction.setX(Double.parseDouble(direction[0]));
		this.direction.setY(Double.parseDouble(direction[1]));
		this.direction.setZ(Double.parseDouble(direction[2]));
	}
}
