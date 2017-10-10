package com.ru.tgra.shapes;

public class Player {
	
	Camera playerCam;
	Camera mapLocation;
	Point3D playerLocation;
	Vector3D playerMovement;
	
	Vector3D forward;
	Vector3D strafe;
	float speed;
	
	Player(Point3D location, Vector3D forward, float speed, Camera viewCam, Camera mapLocation){
		this.playerCam = viewCam;
		this.mapLocation = mapLocation;
		this.playerLocation = location;
		this.speed = speed;
		this.forward = forward; //new Vector3D(0.0f,0,-3.0f);
		this.forward.normalize();
		this.forward.scale(speed);
		this.strafe = new Vector3D(-forward.z, 0, forward.x); //new Vector3D(3.0f,0,0);
		
		playerCam.look(playerLocation.copy(), playerLocation.movement(forward), new Vector3D(0,1.0f,0));
		
		System.out.println(playerCam.eye.string()+ " == " + playerLocation.string());
	}
	
	public void forward(float deltaTime)
	{
		playerLocation.x += forward.x*deltaTime;
		playerLocation.y += forward.y*deltaTime;
		playerLocation.z += forward.z*deltaTime;
		playerCam.slide(forward.x*deltaTime, forward.y*deltaTime, forward.z*deltaTime);
		System.out.println(playerCam.eye.string()+ " == " + playerLocation.string());
	}
	
	public void backward(float deltaTime)
	{
		
		playerLocation.x += -forward.x*deltaTime;
		playerLocation.y += -forward.y*deltaTime;
		playerLocation.z += -forward.z*deltaTime;
		playerCam.slide(-forward.x*deltaTime, -forward.y*deltaTime, -forward.z*deltaTime);
	}
	
	public void strafeRight(float deltaTime)
	{
		playerLocation.x += strafe.x*deltaTime;
		playerLocation.y += strafe.y*deltaTime;
		playerLocation.z += strafe.z*deltaTime;
		playerCam.slide(strafe.x*deltaTime, strafe.y*deltaTime, strafe.z*deltaTime);
	}
	
	public void strafeLeft(float deltaTime)
	{
		playerLocation.x += -strafe.x*deltaTime;
		playerLocation.y += -strafe.y*deltaTime;
		playerLocation.z += -strafe.z*deltaTime;
		playerCam.slide(-strafe.x*deltaTime, -strafe.y*deltaTime, -strafe.z*deltaTime);
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
