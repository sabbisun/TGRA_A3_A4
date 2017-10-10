package com.ru.tgra.shapes;

public class Player {
	
	Camera playerCam;
	Camera mapLocation;
	Point3D playerLocation;
	Vector3D playerMovement;
	
	Vector3D forward;
	Vector3D strafe;
	Maze maze;
	float radius;
	float speed;
	float currAngle;
	float maxAngle;
	float minAngle;
	float pitchMovement;
	float yawMovement;
	
	Player(Point3D location, Vector3D forward, float speed, Camera viewCam, Camera mapLocation, Maze maze){
		this.playerCam = viewCam;
		this.mapLocation = mapLocation;
		this.playerLocation = location;
		this.maze = maze;
		
		this.pitchMovement = 90.0f;
		this.yawMovement = 90.0f;
		this.currAngle = 90.0f;
		this.minAngle = 0;
		this.maxAngle = 180.0f;
		
		this.speed = speed;
		this.radius = 0.15f;
		
		this.forward = forward;
		this.forward.normalize();
		this.forward.scale(speed);
		this.strafe = new Vector3D(-forward.z, 0, forward.x);
		
		
		playerCam.look(playerLocation, playerLocation.movement(forward), new Vector3D(0,1.0f,0));
		
		System.out.println(playerCam.eye.string()+ " == " + playerLocation.string());
	}
	
	public void forward(float deltaTime)
	{	
		if(Collision.collide(playerLocation, forward, radius, maze))
		{
			playerCam.eye.set(playerLocation.x, playerLocation.y, playerLocation.z);
			return;
		}
		
		playerLocation.x += forward.x*deltaTime;
		//playerLocation.y += forward.y*deltaTime;
		playerLocation.z += forward.z*deltaTime;
		playerCam.move(forward.scalar(deltaTime));
	}
	
	public void backward(float deltaTime)
	{
		if(Collision.collide(playerLocation, forward.scalar(-1), radius, maze))
		{
			playerCam.eye.set(playerLocation.x, playerLocation.y, playerLocation.z);
			return;
		}
		
		playerLocation.x += -forward.x*deltaTime;
		//playerLocation.y += -forward.y*deltaTime;
		playerLocation.z += -forward.z*deltaTime;
		playerCam.move(forward.scalar(-deltaTime));
		//playerCam.slide(-forward.x*deltaTime, -forward.y*deltaTime, -forward.z*deltaTime);
	}
	
	public void strafeRight(float deltaTime)
	{
		if(Collision.collide(playerLocation, strafe, radius, maze))
		{
			playerCam.eye.set(playerLocation.x, playerLocation.y, playerLocation.z);
			return;
		}
		
		playerLocation.x += strafe.x*deltaTime;
		//playerLocation.y += strafe.y*deltaTime;
		playerLocation.z += strafe.z*deltaTime;
		playerCam.move(strafe.scalar(deltaTime));
		//playerCam.slide(strafe.x*deltaTime, strafe.y*deltaTime, strafe.z*deltaTime);
	}
	
	public void strafeLeft(float deltaTime)
	{
		if(Collision.collide(playerLocation, strafe.scalar(-1), radius, maze))
		{
			playerCam.eye.set(playerLocation.x, playerLocation.y, playerLocation.z);
			return;
		}
		
		playerLocation.x += -strafe.x*deltaTime;
		//playerLocation.y += -strafe.y*deltaTime;
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
		if(currAngle + angle > maxAngle)
		{
			return; 
		}
		currAngle += angle;
		playerCam.pitch(angle);
	}
	
	public void lookDown(float deltaTime)
	{
		//pitch
		float angle = -pitchMovement*deltaTime;
		if(currAngle+angle < minAngle)
		{
			return; 
		}
		currAngle += angle;
		playerCam.pitch(angle);
	}
	
	private void rotateAroundY(float angle, Vector3D vector)
	{
		float radians = angle * (float)Math.PI/180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);
		float tempX = vector.x;
		float tempY = vector.y;
		float tempZ = vector.z;
		
		vector.set(c*tempX + s*tempZ, tempY, -s*tempX + c*tempZ);
	}
	
	private void rotateAroundY(float c, float s, Vector3D vector)
	{
		float tempX = vector.x;
		float tempY = vector.y;
		float tempZ = vector.z;
		
		vector.set(c*tempX + s*tempZ, tempY, -s*tempX + c*tempZ);
	}
	
	public void lookRight(float deltaTime)
	{
		//yaw
		float angle = -yawMovement*deltaTime;
		
		float radians = angle * (float)Math.PI/180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);

		rotateAroundY(c,s,forward);
		rotateAroundY(c,s,strafe);
		
		rotateAroundY(c,s,playerCam.u);
		rotateAroundY(c,s,playerCam.v);
		rotateAroundY(c,s,playerCam.n);		
	}
	
	public void lookLeft(float deltaTime)
	{
		//yaw
		float angle = yawMovement*deltaTime;
		
		float radians = angle * (float)Math.PI/180.0f;
		float c = (float)Math.cos(radians);
		float s = (float)Math.sin(radians);

		rotateAroundY(c,s,forward);
		rotateAroundY(c,s,strafe);
		
		rotateAroundY(c,s,playerCam.u);
		rotateAroundY(c,s,playerCam.v);
		rotateAroundY(c,s,playerCam.n);
	}
}
