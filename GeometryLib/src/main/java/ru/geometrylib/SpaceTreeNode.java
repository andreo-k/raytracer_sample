package ru.geometrylib;

import java.util.ArrayList;
import java.util.List;

import ru.geometrylib.datamodel.Point;

public final class SpaceTreeNode<T>
{
	private Point point;
	private SpaceTreeNode<T> children[] = new SpaceTreeNode[] { null, null, null, null,
			null, null, null, null };

	private List<T> data = new ArrayList<T>();

	public SpaceTreeNode(Point point)
	{
		this.point = point;
	}

	public Point getPoint()
	{
		return point;
	}

	public List<T> getData()
	{
		return data;
	}
	
	public SpaceTreeNode<T>[] getChildren()
	{
		return children;
	}
		
	public int getOctantNumber(Point point)
	{
		return GeometryUtils.OctantNumberForPoint(this.point, point);
	}
		
	public SpaceTreeNode<T> insert(Point point)
	{
		int childIndex = getOctantNumber(point);

		SpaceTreeNode<T> child = children[childIndex];
		if (child != null)
			return child.insert(point);

		child = new SpaceTreeNode<T>(point);
		children[childIndex] = child;
		return child;
	}

}
