package com.ru.tgra.shapes;

public class Obstacle {
	
	Point3D obstacleLocation;
	Point3D obstacleRotation;
	Vector3D obstacleMovement;
	
	float angle = 0;
	float radius;
	float speed;
	
	public Obstacle(Point3D obstacleRotation, float radius, float speed)
	{
		this.obstacleLocation = new Point3D(obstacleRotation.x+1, obstacleRotation.y, obstacleRotation.z);
		this.obstacleRotation = obstacleRotation;
		this.radius = radius;
		this.speed = speed;
	}
	
	public void movement(float deltaTime)
	{	
		angle += speed * deltaTime;
		float s = (float)Math.sin(angle*Math.PI/180.0);
		float c = (float)Math.cos(angle*Math.PI/180.0);
		
		obstacleLocation.x = obstacleRotation.x + s*radius;
		obstacleLocation.z = obstacleRotation.z + c*radius;
		
		//System.out.println(this.obstacleLocation.string());
		
		
	}

}
