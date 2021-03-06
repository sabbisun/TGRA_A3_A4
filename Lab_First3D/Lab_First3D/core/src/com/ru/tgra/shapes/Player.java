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
	Shader shader;
	
	Player(Point3D location, Vector3D forward, float speed, Camera viewCam, Camera mapLocation, Maze maze, Shader shader){
		this.playerCam = viewCam;
		this.mapLocation = mapLocation;
		this.playerLocation = location;
		this.maze = maze;
		this.shader = shader;
		
		
		this.pitchMovement = 90.0f;
		this.yawMovement = 90.0f;
		this.currAngle = 90.0f;
		this.minAngle = 0;
		this.maxAngle = 180.0f;
		
		this.speed = speed;
		this.radius = 0.3f;
		
		this.forward = forward;
		this.forward.normalize();
		this.forward.scale(speed);
		this.strafe = new Vector3D(-forward.z, 0, forward.x);
		
		
		playerCam.look(playerLocation, playerLocation.movement(forward), new Vector3D(0,1.0f,0));
		shader.setEyePosition(playerCam.eye.x, playerCam.eye.y, playerCam.eye.z);
	}
	
	Point3D getLocation()
	{
		return new Point3D(this.playerLocation.x,this.playerLocation.y,this.playerLocation.z);
	}
	
	void getPushed(Vector3D vector)
	{
		//System.out.println("before" + vector.string());
		
		playerLocation.x += vector.x;
		playerLocation.z += vector.z;
		playerCam.move(vector);
		
		
		shader.setEyePosition(playerCam.eye.x, playerCam.eye.y, playerCam.eye.z);
	}
	
	private void walk(Vector3D vector)
	{
		int counter = 0;
		while(true)
		{
			counter++;
			if (counter > 5)
			{
				System.out.println(vector.string());
				System.out.println("loop exited ");
				System.exit(0);
			}

			int test = Collision.collideInt(playerLocation, vector, radius, maze);
			if(test < 0)
			{
				// No collision break
				break;
			}
			else if(test == 0)
			{
				// Corner collision
				vector.set(0, 0, 0);
			}
			else if (test == 1)
			{
				// Collision on x axis
				vector.set(0, 0, vector.z);
			}
			else if (test == 3)
			{
				// Collision on z axis
				vector.set(vector.x, 0, 0);
			}
		}
		//System.out.println("vector after collision "+vector.string());
		playerLocation.x += vector.x;
		playerLocation.z += vector.z;
		//System.out.println("pos after collision "+playerLocation.string());
		playerCam.move(vector);
		shader.setEyePosition(playerCam.eye.x, playerCam.eye.y, playerCam.eye.z);
	}
	
	public void forward(float deltaTime)
	{	
		Vector3D deltaForward = forward.scalar(deltaTime);
		walk(deltaForward);
	}
	
	public void backward(float deltaTime)
	{
		Vector3D deltaBackward = forward.scalar(-deltaTime);
		walk(deltaBackward);
	}
	
	public void strafeRight(float deltaTime)
	{
		Vector3D deltaRStrafe = strafe.scalar(deltaTime);
		walk(deltaRStrafe);
	}
	
	public void strafeLeft(float deltaTime)
	{
		Vector3D deltaLStrafe = strafe.scalar(-deltaTime);
		walk(deltaLStrafe);
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
