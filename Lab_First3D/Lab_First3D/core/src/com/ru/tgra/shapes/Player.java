package com.ru.tgra.shapes;

public class Player {
	
	Camera playerCam;
	Camera mapLocation;
	Point3D playerLocation;
	Vector3D playerMovement;
	
	Vector3D forward;
	Vector3D strafe;
	float speed;
	float currAngle;
	float maxAngle;
	float minAngle;
	float pitchMovement;
	float yawMovement;
	
	Player(Point3D location, Vector3D forward, float speed, Camera viewCam, Camera mapLocation){
		this.playerCam = viewCam;
		this.mapLocation = mapLocation;
		this.playerLocation = location;
		
		this.pitchMovement = 90.0f;
		this.yawMovement = 90.0f;
		this.currAngle = 90.0f;
		this.minAngle = 0;
		this.maxAngle = 180.0f;
		
		this.speed = speed;
		
		this.forward = forward;
		this.forward.normalize();
		this.forward.scale(speed);
		this.strafe = new Vector3D(-forward.z, 0, forward.x);
		
		
		playerCam.look(playerLocation, playerLocation.movement(forward), new Vector3D(0,1.0f,0));
		
		System.out.println(playerCam.eye.string()+ " == " + playerLocation.string());
	}
	
	public void forward(float deltaTime)
	{

		playerLocation.x += forward.x*deltaTime;
		playerLocation.y += forward.y*deltaTime;
		playerLocation.z += forward.z*deltaTime;
		//playerCam.slide(forward.x*deltaTime, forward.y*deltaTime, forward.z*deltaTime);
		playerCam.move(forward.scalar(deltaTime));
		System.out.println(playerCam.eye.string()+ " == " + playerLocation.string());
	}
	
	public void backward(float deltaTime)
	{
		
		playerLocation.x += -forward.x*deltaTime;
		playerLocation.y += -forward.y*deltaTime;
		playerLocation.z += -forward.z*deltaTime;
		playerCam.move(forward.scalar(-deltaTime));
		//playerCam.slide(-forward.x*deltaTime, -forward.y*deltaTime, -forward.z*deltaTime);
	}
	
	public void strafeRight(float deltaTime)
	{
		playerLocation.x += strafe.x*deltaTime;
		playerLocation.y += strafe.y*deltaTime;
		playerLocation.z += strafe.z*deltaTime;
		playerCam.move(strafe.scalar(deltaTime));
		//playerCam.slide(strafe.x*deltaTime, strafe.y*deltaTime, strafe.z*deltaTime);
	}
	
	public void strafeLeft(float deltaTime)
	{
		playerLocation.x += -strafe.x*deltaTime;
		playerLocation.y += -strafe.y*deltaTime;
		playerLocation.z += -strafe.z*deltaTime;
		playerCam.move(strafe.scalar(-deltaTime));
		//playerCam.slide(-strafe.x*deltaTime, -strafe.y*deltaTime, -strafe.z*deltaTime);
	}
	
	public void movement(Vector3D vector, float deltaTime)
	{
		playerMovement.x += vector.x * deltaTime;
		playerMovement.z += vector.z * deltaTime;
	}

	public void lookUp(float deltaTime)
	{
		//pitch
		float angle = pitchMovement*deltaTime;
		playerCam.pitch(angle);
	}
	
	public void lookDown(float deltaTime)
	{
		//pitch
		float angle = -pitchMovement*deltaTime;
		playerCam.pitch(angle);
	}
	
	public void lookRight(float deltaTime)
	{
		//yaw
		float angle = -yawMovement*deltaTime;
		
		float radians = angle * (float)Math.PI/180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		Vector3D t = new Vector3D(forward.x, forward.y, forward.z);
		
		forward.set(t.x * c - strafe.x * s, 0, t.z * c - strafe.z * s);
		strafe.set(t.x * s + strafe.x * c, 0, t.z * s + strafe.z * c);
		
		playerCam.yaw(angle);
		
	}
	
	public void lookLeft(float deltaTime)
	{
		//yaw
		float angle = yawMovement*deltaTime;
		
		float radians = angle * (float)Math.PI/180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		Vector3D t = new Vector3D(forward.x, forward.y, forward.z);
		
		forward.set(t.x * c - strafe.x * s, 0, t.z * c - strafe.z * s);
		strafe.set(t.x * s + strafe.x * c, 0, t.z * s + strafe.z * c);
		
		playerCam.yaw(angle);
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
