package com.ru.tgra.shapes;

public class Point3D {

	public float x;
	public float y;
	public float z;

	public Point3D()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}

	public Point3D(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public void add(Vector3D v)
	{
		x += v.x;
		y += v.y;
		z += v.z;
	}
	
	public void set(float x, float y, float z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Point3D copy()
	{
		return new Point3D(this.x,this.y,this.z);
	}
	
	public Vector3D to(Point3D P2)
	{
		return new Vector3D(P2.x - this.x, P2.y - this.y, P2.z - this.z);
	}
	
	public Point3D movement(Vector3D vector)
	{
		return new Point3D(this.x+vector.x, this.y+vector.y, this.z+vector.z);
	}
	public String string()
	{
		return "(" + this.x +", " + this.y + ", " + this.z + ")";
	}
}
