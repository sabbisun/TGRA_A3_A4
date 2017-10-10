package com.ru.tgra.shapes;

public class Collision {
	
	public static boolean collide(Point3D playerLocation, Vector3D vector, float radius, Maze maze)
	{	
		int xPos = (int) Math.floor(playerLocation.x);
		int zPos = (int) Math.floor(playerLocation.z);
	
		if(xPos < 0 || xPos >= maze.size() || zPos < 0 || zPos >= maze.size())
		{
			return false;
		}
		
		//System.out.println("corners: ("+xPos+", "+zPos+"), ("+(xPos+1)+", "+zPos+"), ("+(xPos+1)+", "+(zPos+1)+"), ("+xPos+", "+(zPos+1)+")");
		//System.out.println("player: ("+playerLocation.x+", "+playerLocation.z+")");
		
		// Check corners
		
		// Check Parallel south wall and Parallel east wall 
		//System.out.println(vector.string()+ "z > 0: "+ (vector.z>0));
		// Check north wall
		if(vector.z < 0)
		{
			//wSystem.out.println("z < 0");
			if(0 < zPos && maze.cells[xPos][zPos-1].south())
			{
				System.out.println("Cell has northwall");
				if(playerLocation.z + vector.z - radius < zPos)
				{
					return true;
				}
			}
		}
			
		// Check east wall
		if(vector.x > 0)
		{
			// Check if colliding with east wall
			if(xPos < maze.size() && maze.cells[xPos][zPos].east())
			{
				if(playerLocation.x + vector.x + radius > xPos + 1.0f)
				{
					return true;
				}
			}
			
			// Check if colliding with southwall from east
			if(xPos < maze.size() - 1 && maze.cells[xPos+1][zPos].south())
			{
				System.out.println("hitting southwall from north-east");
				System.out.println("Direction: " + vector.string());
				if(zPos + 0.9f < playerLocation.z && playerLocation.z < zPos + 1.0f)
				{
					System.out.println("Within boundry");
					if(playerLocation.x + vector.x + radius - 0.15f > xPos + 1.0f)
					{
						return true;
					}
				}
			}
			
			if(zPos > 0 && xPos< maze.size() - 1 && maze.cells[xPos+1][zPos-1].south())
			{
				System.out.println("hitting southwall from south-east");
				System.out.println("Direction: " + vector.string());
				if(zPos < playerLocation.z && playerLocation.z < zPos + 0.1f)
				{
					System.out.println("Within boundry");
					if(playerLocation.x + vector.x + radius - 0.15f > xPos + 1.0f)
					{
						return true;
					}
				}
			}
			
		}
		
		// Check south wall
		if(vector.z > 0)
		{
			
			//System.out.println("z > 0");
			
			if(zPos < maze.size() && maze.cells[xPos][zPos].south())
			{

				//System.out.println("Cell has southwall");
				if(playerLocation.z + vector.z + radius > zPos + 1.0f)
				{
					return true;
				}
				//System.out.println("Check south: loc " + playerLocation.z + ", vec " + vector.z + " radius " + radius + "zPos + 1 " + (zPos+1));
				
			}
		}
		// Check west wall
		if(vector.x < 0)
		{
			if(0 < xPos && maze.cells[xPos-1][zPos].east())
			{
				if(playerLocation.x + vector.x - radius < xPos)
				{
					return true;
				}			
			}
		}
		
		return false;
	}
}
