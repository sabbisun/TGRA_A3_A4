package com.ru.tgra.shapes;

public class Obstacle {
	
	Point3D obstacleLocation;
	Point3D obstacleRotation;
	Vector3D obstacleMovement;
	
	float angle = 0;
	float radius;
	float speed;
	float rotateRadius;
	
	public Obstacle(Point3D obstacleRotation, float radius, float rotateRadius, float speed)
	{
		this.obstacleLocation = new Point3D(obstacleRotation.x+rotateRadius, obstacleRotation.y, obstacleRotation.z);
		this.obstacleRotation = obstacleRotation;
		this.radius = radius;
		this.speed = speed;
		this.rotateRadius = rotateRadius;
	}
	
	public void movement(float deltaTime)
	{	
		angle += speed * deltaTime;
		float s = (float)Math.sin(angle*Math.PI/180.0);
		float c = (float)Math.cos(angle*Math.PI/180.0);
		
		obstacleLocation.x = obstacleRotation.x + s*this.rotateRadius;
		obstacleLocation.z = obstacleRotation.z + c*this.rotateRadius;
		
	}

}
