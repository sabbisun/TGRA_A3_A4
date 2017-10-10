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
		
		System.out.println("corners: ("+xPos+", "+zPos+"), ("+(xPos+1)+", "+zPos+"), ("+(xPos+1)+", "+(zPos+1)+"), ("+xPos+", "+(zPos+1)+")");
		System.out.println("player: ("+playerLocation.x+", "+playerLocation.z+")");
		
		// Check corners
		
		// Check Parallel south wall and Parallel east wall 
		
		// Check north wall
		if(vector.z < 0)
		{
			if(0 < zPos && maze.cells[xPos][zPos-1].south())
			{
				if(playerLocation.z + vector.z - radius < zPos)
				{
					return true;
				}
			}
		}
			
		// Check east wall
		else if(vector.x > 0)
		{
			if(xPos < maze.size() && maze.cells[xPos][zPos].east())
			{
				if(playerLocation.x + vector.x + radius > xPos + 1.0f)
				{
					return true;
				}
			}
		}
		
		// Check south wall
		else if(vector.z > 0)
		{
			if(zPos < maze.size() && maze.cells[xPos][zPos].south())
			{
				if(playerLocation.z + vector.z + radius > zPos + 1.0f)
				{
					return true;
				}
			}
		}
		// Check west wall
		else if(vector.x < 0)
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
