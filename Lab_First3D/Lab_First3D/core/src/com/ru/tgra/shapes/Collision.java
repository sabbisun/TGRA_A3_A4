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
			
			// Check if colliding with eastwall going north, hitting the south side
			/*
			 *   |
			 * __|__
			 * A
			 * |
			 */
			
			if(zPos > 0 && maze.cells[xPos][zPos-1].east())
			{
				if(xPos+0.9f < playerLocation.x && playerLocation.x < xPos + 1.0f)
				{
					if(playerLocation.z + vector.z - radius + 0.15 < zPos)
					{
						return true;
					}
				}
			}
			
			// Check if colliding with eastwall going north, hitting the south side
			/*
			 *   |
			 * __|__
			 *    A
			 *    |
			 */
			
			if(zPos > 0 && xPos > 0 && maze.cells[xPos-1][zPos-1].east())
			{
				if(xPos < playerLocation.x && playerLocation.x < xPos + 0.1f)
				{
					if(playerLocation.z + vector.z - radius + 0.15 < zPos)
					{
						return true;
					}
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
			
			// Check if colliding with southwall going east, hitting the west side
			/*    _____
			 * ->|	
			 *   ------
			 * 	 |_____
			 */
			if(xPos < maze.size() - 1 && maze.cells[xPos+1][zPos].south())
			{
				//System.out.println("hitting southwall going north-east");
				//System.out.println("Direction: " + vector.string());
				if(zPos + 0.9f < playerLocation.z && playerLocation.z < zPos + 1.0f)
				{
					System.out.println("Within boundry");
					if(playerLocation.x + vector.x + radius - 0.15f > xPos + 1.0f)
					{
						return true;
					}
				}
			}
			// Check if colliding with southwall going east, hitting the west side
			/*    _____
			 *   |	
			 *   ------
			 * ->|_____
			 */
			if(zPos > 0 && xPos< maze.size() - 1 && maze.cells[xPos+1][zPos-1].south())
			{
				//System.out.println("hitting southwall going south-east");
				//System.out.println("Direction: " + vector.string());
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
			
			// Check if colliding with eastwall going south, hitting the north side
			/*
			 *  |
			 *  V
			 * __ __
			 *   |
			 *   |
			 */
			
			if(zPos < maze.size() - 1 && maze.cells[xPos][zPos+1].east())
			{
				if(xPos + 0.9f < playerLocation.x && playerLocation.x < xPos + 1.0f)
				{
					if(playerLocation.z + vector.z + radius - 0.15f > zPos + 1.0f)
					{
						return true;	
					}
				}
			}
			// Check if colliding with eastwall going south, hitting the north side
			/*
			 *    |
			 *    V
			 * __ __
			 *   |
			 *   |
			 */
			if(xPos > 0 && zPos < maze.size() - 1 && maze.cells[xPos-1][zPos+1].east())
			{
				if(xPos < playerLocation.x && playerLocation.x < xPos + 0.1f)
				{
					if(playerLocation.z + vector.z + radius - 0.15f > zPos + 1.0f)
					{
						return true;	
					}
				}
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
			
			// Check if colliding with southwall going west, hitting the east side
			/*______
			 * 		| 
			 * ------
			 *______| <- 
			 */
			if(xPos > 0 && zPos > 0 && maze.cells[xPos-1][zPos-1].south())
			{
				if(zPos < playerLocation.z && playerLocation.z < zPos + 0.1f)
				{
					if(playerLocation.x + vector.x - radius + 0.15f < xPos)
					{
						return true;
					}
				}
			}
			
			// Check if colliding with southwall going west, hitting the east side
			/* _____
			 * 		| <-
			 * ------
			 * _____|
			 */
			if(xPos > 0 && maze.cells[xPos-1][zPos].south())
			{
				if(zPos + 0.9f < playerLocation.z && playerLocation.z < zPos + 1.0f)
				{
					if(playerLocation.x + vector.x - radius + 0.15f < xPos)
					{
						return true;
					}
				}
			}
		}
		
		return false;
	}
	
	private static int checkSides(Point3D playerLocation, Vector3D vector, float radius, Maze maze, int xPos, int zPos)
	{
		// Check north wall
		if(vector.z < 0)
		{
			//wSystem.out.println("z < 0");
			if(0 < zPos && maze.cells[xPos][zPos-1].south())
			{
				if(playerLocation.z + vector.z - radius < zPos)
				{
					return 3;
				}
			}
			
			// Check if colliding with eastwall going north, hitting the south side
			/*
			 *   |
			 * __|__
			 * A
			 * |
			 */
			
			if(zPos > 0 && maze.cells[xPos][zPos-1].east())
			{
				if(xPos+0.9f < playerLocation.x && playerLocation.x < xPos + 1.0f)
				{
					if(playerLocation.z + vector.z - radius + 0.15 < zPos)
					{
						return 3;
					}
				}
			}
			
			// Check if colliding with eastwall going north, hitting the south side
			/*
			 *   |
			 * __|__
			 *    A
			 *    |
			 */
			
			if(zPos > 0 && xPos > 0 && maze.cells[xPos-1][zPos-1].east())
			{
				if(xPos < playerLocation.x && playerLocation.x < xPos + 0.1f)
				{
					if(playerLocation.z + vector.z - radius + 0.15 < zPos)
					{
						return 3;
					}
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
					return 1;
				}
			}
			
			// Check if colliding with southwall going east, hitting the west side
			/*    _____
			 * ->|	
			 *   ------
			 * 	 |_____
			 */
			if(xPos < maze.size() - 1 && maze.cells[xPos+1][zPos].south())
			{
				if(zPos + 0.9f < playerLocation.z && playerLocation.z < zPos + 1.0f)
				{
					if(playerLocation.x + vector.x + radius - 0.15f > xPos + 1.0f)
					{
						return 1;
					}
				}
			}
			// Check if colliding with southwall going east, hitting the west side
			/*    _____
			 *   |	
			 *   ------
			 * ->|_____
			 */
			if(zPos > 0 && xPos< maze.size() - 1 && maze.cells[xPos+1][zPos-1].south())
			{
				if(zPos < playerLocation.z && playerLocation.z < zPos + 0.1f)
				{
					if(playerLocation.x + vector.x + radius - 0.15f > xPos + 1.0f)
					{
						return 1;
					}
				}
			}
		}
		
		// Check south wall
		if(vector.z > 0)
		{
			if(zPos < maze.size() && maze.cells[xPos][zPos].south())
			{

				if(playerLocation.z + vector.z + radius > zPos + 1.0f)
				{
					return 3;
				}
			}
			
			// Check if colliding with eastwall going south, hitting the north side
			/*
			 *  |
			 *  V
			 * __ __
			 *   |
			 *   |
			 */
			
			if(zPos < maze.size() - 1 && maze.cells[xPos][zPos+1].east())
			{
				if(xPos + 0.9f < playerLocation.x && playerLocation.x < xPos + 1.0f)
				{
					if(playerLocation.z + vector.z + radius - 0.15f > zPos + 1.0f)
					{
						return 3;	
					}
				}
			}
			// Check if colliding with eastwall going south, hitting the north side
			/*
			 *    |
			 *    V
			 * __ __
			 *   |
			 *   |
			 */
			if(xPos > 0 && zPos < maze.size() - 1 && maze.cells[xPos-1][zPos+1].east())
			{
				if(xPos < playerLocation.x && playerLocation.x < xPos + 0.1f)
				{
					if(playerLocation.z + vector.z + radius - 0.15f > zPos + 1.0f)
					{
						return 3;	
					}
				}
			}
		}
		// Check west wall
		if(vector.x < 0)
		{
			if(0 < xPos && maze.cells[xPos-1][zPos].east())
			{
				if(playerLocation.x + vector.x - radius < xPos)
				{
					return 1;
				}			
			}
			
			// Check if colliding with southwall going west, hitting the east side
			/*______
			 * 		| 
			 * ------
			 *______| <- 
			 */
			if(xPos > 0 && zPos > 0 && maze.cells[xPos-1][zPos-1].south())
			{
				if(zPos < playerLocation.z && playerLocation.z < zPos + 0.1f)
				{
					if(playerLocation.x + vector.x - radius + 0.15f < xPos)
					{
						return 1;
					}
				}
				
			}
			
			// Check if colliding with southwall going west, hitting the east side
			/* _____
			 * 		| <-
			 * ------
			 * _____|
			 */
			if(xPos > 0 && maze.cells[xPos-1][zPos].south())
			{
				if(zPos + 0.9f < playerLocation.z && playerLocation.z < zPos + 1.0f)
				{
					if(playerLocation.x + vector.x - radius + 0.15f < xPos)
					{
						return 1;
					}
				}
			}
		}
		return -1;
	}
	
	private static int checkCorners(Point3D playerLocation, Vector3D vector, float radius, Maze maze, int xPos, int zPos)
	{
		
		// Might collide with south-east corner
		/*
		 * ##
		 * #X
		 */
		
		if(vector.x < 0 || vector.z < 0)
		{
			// Check if colliding with eastwall going north, hitting the south side
			/*
			 *   |
			 * __|__
			 *    A
			 *    |
			 */
			if(zPos > 0 && xPos > 0 && maze.cells[xPos-1][zPos-1].east())
			{
				
			}
			
			// Check if colliding with southwall going west, hitting the east side
			/*______
			 * 		| 
			 * ------
			 *______| <- 
			 */
			if(xPos > 0 && zPos > 0 && maze.cells[xPos-1][zPos-1].south())
			{
				if(xPos > playerLocation.x - radius + 0.15f 
				&& zPos + 0.1f > playerLocation.z - radius + 0.15f)
				{
					return 0;
				}
			}
		}
		
		// Might collide with south-west corner
		/*
		 * ##
		 * X#
		 */
		if(vector.x > 0 || vector.z < 0)
		{
			// Check if colliding with eastwall going north, hitting the south side
			/*
			 *   |
			 * __|__
			 * A
			 * |
			 */
			if(zPos > 0 && maze.cells[xPos][zPos-1].east())
			{
				
			}
			
			// Check if colliding with southwall going east, hitting the west side
			/*    _____
			 *   |	
			 *   ------
			 * ->|_____
			 */
			if(zPos > 0 && xPos< maze.size() - 1 && maze.cells[xPos+1][zPos-1].south())
			{
				if(xPos + 1.0f < playerLocation.x + (radius - 0.15f) 
				&& zPos + 0.1f > playerLocation.z - (radius - 0.15f))
				{
					return 0;
				}
			}
			
			
		}
		// Might collide with north-west corner
		/*
		 * X#
		 * ##
		 */
		if(vector.x > 0 || vector.z > 0)
		{
			// Check if colliding with southwall going east, hitting the west side
			/*    _____
			 * ->|	
			 *   ------
			 * 	 |_____
			 */
			if(xPos < maze.size() - 1 && maze.cells[xPos+1][zPos].south())
			{
				
			}
			
			// Check if colliding with eastwall going south, hitting the north side
			/*
			 *  |
			 *  V
			 * __ __
			 *   |
			 *   |
			 */
			if(zPos < maze.size() - 1 && maze.cells[xPos][zPos+1].east())
			{
			
			}
		}
		
		// Might collide with north-east corner
		/*
		 * #X
		 * ##
		 */
		if(vector.x < 0 || vector.z > 0)
		{
			// Check if colliding with eastwall going south, hitting the north side
			/*
			 *    |
			 *    V
			 * __ __
			 *   |
			 *   |
			 */
			if(xPos > 0 && zPos < maze.size() - 1 && maze.cells[xPos-1][zPos+1].east())
			{

			}
			
			// Check if colliding with southwall going west, hitting the east side
			/* _____
			 * 		| <-
			 * ------
			 * _____|
			 */
			if(xPos > 0 && maze.cells[xPos-1][zPos].south())
			{
				
			}
		}		

		return -1;
	}
	
	public static int collideInt(Point3D playerLocation, Vector3D vector, float radius, Maze maze)
	{	
		int xPos = (int) Math.floor(playerLocation.x);
		int zPos = (int) Math.floor(playerLocation.z);
	
		if(xPos < 0 || xPos >= maze.size() || zPos < 0 || zPos >= maze.size())
		{
			return -1;
		}
		
		// integer 3 represents z axis collision
		// integer 1 represents x axis collision
		// integer 0 represents corner collision
		// integer -1 represents no collision
		
		int result = checkSides(playerLocation, vector, radius, maze, xPos, zPos);
		
		if(result >= 0)
		{
			return result;
		}
		
		result = checkCorners(playerLocation, vector, radius, maze, xPos, zPos);
		
		return result;
	}
	
	Vector3D collideCorner(Point3D playerLocation, Vector3D vector, float radius, Maze maze)
	{
		int xPos = (int) Math.floor(playerLocation.x);
		int zPos = (int) Math.floor(playerLocation.z);
		
		return new Vector3D(0,0,0);
	}
}
