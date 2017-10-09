package com.ru.tgra.shapes;

public class Player {
	
	Camera playerCam;
	Camera mapLocation;
	Point3D playerLocation;
	Vector3D playerMovement;
	
	Player(Camera viewCam, Camera mapLocation){
		playerCam = viewCam;
		this.mapLocation = mapLocation;
	}

	
	public void movement(Vector3D vector, float deltaTime)
	{
		playerMovement.x += vector.x * deltaTime;
		playerMovement.z += vector.z * deltaTime;
	}
	
	public Point3D newPoint(Vector3D vector)
	{
		return new Point3D(playerMovement.x + vector.x, 0.0f ,playerMovement.z + vector.z);
	}
	
	public Vector3D distanceVector(Point3D to)
	{
		return new Vector3D(to.x - playerLocation.x,0.0f ,to.y - playerLocation.z);
	} 

}
