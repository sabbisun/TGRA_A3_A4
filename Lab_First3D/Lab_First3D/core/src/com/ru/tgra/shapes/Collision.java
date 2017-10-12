package com.ru.tgra.shapes;

public class Collision {
	
	static boolean record = false;
	
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
				//System.out.println("Cell has northwall");
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
					if(playerLocation.z + vector.z - (radius - 0.15) < zPos)
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
					if(playerLocation.z + vector.z - (radius - 0.15) < zPos)
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
					//System.out.println("Within boundry");
					if(playerLocation.x + vector.x + (radius - 0.15) > xPos + 1.0f)
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
					//System.out.println("Within boundry");
					if(playerLocation.x + vector.x + (radius - 0.15) > xPos + 1.0f)
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
					if(playerLocation.z + vector.z + (radius - 0.15) > zPos + 1.0f)
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
					if(playerLocation.z + vector.z + (radius - 0.15) > zPos + 1.0f)
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
					if(playerLocation.x + vector.x - (radius - 0.15) < xPos)
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
					if(playerLocation.x + vector.x - (radius - 0.15) < xPos)
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
			// Checking cell wall
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
					if(playerLocation.z + vector.z - (radius - 0.15) < zPos)
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
					if(playerLocation.z + vector.z - (radius - 0.15) < zPos)
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
					if(playerLocation.x + vector.x + (radius - 0.15) > xPos + 1.0f)
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
					if(playerLocation.x + vector.x + (radius - 0.15) > xPos + 1.0f)
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
					if(playerLocation.z + vector.z + (radius - 0.15) > zPos + 1.0f)
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
					if(playerLocation.z + vector.z + (radius - 0.15) > zPos + 1.0f)
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
					if(playerLocation.x + vector.x - (radius - 0.15) < xPos)
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
					if(playerLocation.x + vector.x - (radius - 0.15) < xPos)
					{
						return 1;
					}
				}
			}
		}
		return -1;
	}
	
	private static int checkCornersInt(Point3D playerLocation, Vector3D vector, float radius, Maze maze, int xPos, int zPos)
	{
		
		// Might collide with south-east corner
		/*
		 * ##
		 * #X
		 */
		
		if(vector.x < 0 || vector.z < 0)
		{
			if(zPos > 0 && xPos > 0 && maze.cells[xPos-1][zPos-1].east()
					&& xPos > 0 && zPos > 0 && maze.cells[xPos-1][zPos-1].south()
					&& !(maze.cells[xPos-1][zPos].east())
					&& !(maze.cells[xPos][zPos-1].south()))
			{
				if(xPos + 0.1f > playerLocation.x + vector.x - (radius - 0.10)
						&& zPos + 0.1f > playerLocation.z + vector.z - (radius - 0.10))
				{
					if (vector.x < 0 && playerLocation.x - (xPos + 0.1f) > playerLocation.z - (zPos + 0.1f))
					{
						return 1;
					}
					else if(vector.z < 0 && playerLocation.x - (xPos + 0.1f) < playerLocation.z - (zPos + 0.1f))
					{
						return 3;
					}
				}
			}
			else
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
					if(xPos + 0.1f > playerLocation.x + vector.x - (radius - 0.10)
						&& zPos > playerLocation.z + vector.z- (radius - 0.10))
					{
						
						if (vector.x < 0 && playerLocation.x - (xPos + 0.1f) > playerLocation.z - zPos)
						{
							return 1;
						}
						else if(vector.z < 0 && playerLocation.x - (xPos + 0.1f) < playerLocation.z - zPos)
						{
							return 3;
						}
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
					if(xPos > playerLocation.x + vector.x - (radius - 0.1) 
					&& zPos + 0.1f > playerLocation.z + vector.z - (radius - 0.1))
					{
						if (vector.x < 0 && playerLocation.x - xPos > playerLocation.z - (zPos + 0.1f))
						{
							return 1;
						}
						else if(vector.z < 0 && playerLocation.x - xPos < playerLocation.z - (zPos + 0.1f))
						{
							return 3;
						}
					}
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
			//Big corner
			if(zPos > 0 && maze.cells[xPos][zPos-1].east()
					&& zPos > 0 && xPos < maze.size() - 1 && maze.cells[xPos+1][zPos-1].south()
					&& !(maze.cells[xPos][zPos].east())
					&& !(maze.cells[xPos][zPos-1].south()))
			{
				boolean test1 = (xPos + 0.9f) < playerLocation.x + vector.x + (radius - 0.10f);
				boolean test2 = (zPos + 0.1f) > playerLocation.z + vector.z - (radius - 0.10f);
				if(test1 && test2)
				{

					//System.out.println("hitting corner");
					float X = (playerLocation.x - (xPos + 0.9f));
					float Z = (playerLocation.z - (zPos + 0.1f));
					
					if(vector.x > 0 && X/Z> 0 && X/Z > 1)
					{
							return 1;
					}
					else if(vector.z < 0 && X/Z> 0 && X/Z < 1)
					{
							return 3;
					}
					else if (vector.x > 0 && X/Z < -1)
					{
						return 1;
					}
					else if(vector.z < 0 && X/Z > -1)
					{
						return 3;
					}
				}
			}
			else
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
					if(xPos + 0.9f < playerLocation.x + vector.x + (radius - 0.10)
							&& zPos > playerLocation.z + vector.z - (radius - 0.10))
					{
						float X = (playerLocation.x - (xPos + 0.9f));
						float Z = (playerLocation.z - (zPos));
						
						//if(X/Z> 0)
						//{
								// TO DO figure this out, it still works
								// Might be because of vector
						//}
						if(vector.x > 0 && X/Z> 0 && X/Z > 1)
						{
								return 1;
						}
						else if(vector.z < 0 && X/Z> 0 && X/Z < 1)
						{
								return 3;
						}
						else if (vector.x > 0 && X/Z < -1)
						{
							return 1;
						}
						else if(vector.z < 0 && X/Z > -1)
						{
							return 3;
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
					float X = (playerLocation.x - (xPos + 1.0f));
					float Z = (playerLocation.z - (zPos + 0.1f));
					
					if(xPos + 1.0f < playerLocation.x + (radius - 0.1f) 
					&& zPos + 0.1f > playerLocation.z - (radius - 0.1f))
					{
						//if(X/Z> 0)
						//{
								// TODO figure this out, it still works
						//}
						if(vector.x > 0 && X/Z> 0 && X/Z > 1)
						{
								return 1;
						}
						else if(vector.z < 0 && X/Z> 0 && X/Z < 1)
						{
								return 3;
						}
						else if (vector.x > 0 && X/Z < -1)
						{
							return 1;
						}
						else if(vector.z < 0 && X/Z > -1)
						{
							return 3;
						}
						//return 0;
					}
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
			if(xPos < maze.size() - 1 && maze.cells[xPos+1][zPos].south()
					&& zPos < maze.size() - 1 && maze.cells[xPos][zPos+1].east()
					&& !(maze.cells[xPos][zPos].east())
					&& !(maze.cells[xPos][zPos].south()))
			{
				if(xPos + 0.9f < playerLocation.x + vector.x + (radius - 0.10)
						&& zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.10))
				{
					if (vector.x > 0 && playerLocation.x - (xPos + 0.9f) < playerLocation.z - (zPos + 0.9f))
					{
						return 1;
					}
					else if(vector.z > 0 && playerLocation.x - (xPos + 0.9f) > playerLocation.z - (zPos + 0.9))
					{
						return 3;
					}
				}
			}
			else
			{
				// Check if colliding with southwall going east, hitting the west side
				/*    _____
				 * ->|	
				 *   ------
				 * 	 |_____
				 */
				if(xPos < maze.size() - 1 && maze.cells[xPos+1][zPos].south())
				{
					if(xPos + 1.0f < playerLocation.x + vector.x + (radius - 0.1)
						&& zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.1))
					{
						if (vector.x > 0 && playerLocation.x - (xPos + 1.0f) < playerLocation.z - (zPos + 0.9f))
						{
							return 1;
						}
						else if(vector.z > 0 && playerLocation.x - (xPos + 1.0f) > playerLocation.z - (zPos + 0.9f))
						{
							return 3;
						}
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
					if(xPos + 0.9f < playerLocation.x + vector.x + (radius - 0.10)
							&& zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.10))
					{
						if (vector.x > 0 && playerLocation.x - (xPos + 0.9f) < playerLocation.z - (zPos + 0.9f))
						{
							return 1;
						}
						else if(vector.z > 0 && playerLocation.x - (xPos + 0.9f) > playerLocation.z - (zPos + 0.9f))
						{
							return 3;
						}
					}
				}
			}
		}
		
		// Might collide with north-east corner
		/*
		 * #X
		 * ##
		 */
		if(vector.x < 0 || vector.z > 0)
		{
			
			//Big corner
			if(xPos > 0 && zPos < maze.size() - 1 && maze.cells[xPos-1][zPos+1].east()
					&& xPos > 0 && maze.cells[xPos-1][zPos].south()
					&& !(maze.cells[xPos][zPos].south())
					&& !(maze.cells[xPos-1][zPos].east()))
			{
				
				boolean test1 = xPos + 0.1f > playerLocation.x + vector.x - (radius - 0.10);
				boolean test2 = zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.10);
				if(test1 && test2)
				{
					
					//System.out.println("hitting corner");
					float X = (playerLocation.x - (xPos + 0.1f));
					float Z = (playerLocation.z - (zPos + 0.9f));
					
					if(vector.x < 0 && X/Z> 0 && X/Z > 1)
					{
							return 1;
					}
					else if(vector.z > 0 && X/Z> 0 && X/Z < 1)
					{
							return 3;
					}
					else if (vector.x < 0 && X/Z < -1)
					{
						return 1;
					}
					else if(vector.z > 0 && X/Z > -1)
					{
						return 3;
					}
				}
			}
			else
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
					if(xPos + 0.1f > playerLocation.x + vector.x - (radius - 0.10)
							&& zPos + 1.0f < playerLocation.z + vector.z + (radius - 0.10))
					{
						float X = (playerLocation.x - (xPos + 0.1f));
						float Z = (playerLocation.z - (zPos + 1.0f));
						if (vector.x < 0 && X/Z < -1)
						{
							return 1;
						}
						else if(vector.z > 0 && X/Z > -1)
						{
							return 3;
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
					if(xPos > playerLocation.x + vector.x - (radius - 0.10)
						&& zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.10))
					{
						//System.out.println("hitr");
						float X = (playerLocation.x - (xPos));
						float Z = (playerLocation.z - (zPos + 0.9f));
						if(vector.x < 0 && X/Z> 0 && X/Z > 1)
						{
								return 1;
						}
						else if(vector.z > 0 && X/Z> 0 && X/Z < 1)
						{
								return 3;
						}
						else if (vector.x < 0 && X/Z < -1)
						{
							return 1;
						}
						else if(vector.z > 0 && X/Z > -1)
						{
							return 3;
						}
					}
				}
			}
		}		

		return -1;
	}
		
	private static Point3D checkCorners(Point3D playerLocation, Vector3D vector, float radius, Maze maze, int xPos, int zPos)
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
				if(xPos + 0.1f > playerLocation.x + vector.x - (radius - 0.15)
					&& zPos > playerLocation.z + vector.z - (radius - 0.15))
				{
					return new Point3D(xPos + 0.1f, 0, zPos);
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
				if(xPos > playerLocation.x + vector.x - (radius - 0.15) 
				&& zPos + 0.1f > playerLocation.z + vector.z - (radius - 0.15))
				{
					return new Point3D(xPos, 0, zPos + 0.1f);
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
				if(xPos + 0.9f < playerLocation.x + vector.x + (radius - 0.15)
						&& zPos > playerLocation.z + vector.z - (radius - 0.15))
				{
					return new Point3D(xPos + 0.9f, 0, zPos);
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
				if(xPos + 1.0f < playerLocation.x + vector.x + (radius - 0.15f) 
				&& zPos + 0.1f > playerLocation.z + vector.z - (radius - 0.15f))
				{
					return new Point3D(xPos + 1.0f, 0, zPos + 0.1f);
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
				if(xPos < playerLocation.x + vector.x + (radius - 0.15)
					&& zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.15))
				{
					return new Point3D(xPos, 0, zPos + 0.9f);
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
				if(xPos + 0.9f < playerLocation.x + vector.x + (radius - 0.15)
						&& zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.15))
				{
					return new Point3D(xPos + 0.9f, 0, zPos + 0.9f); 
				}
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
				if(xPos + 0.1f > playerLocation.x + vector.x - (radius - 0.15)
						&& zPos + 1.0f < playerLocation.z + vector.z + (radius - 0.15))
				{
					return new Point3D(xPos + 0.1f, 0, zPos + 1.0f);
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
				if(xPos > playerLocation.x + vector.x - (radius - 0.15)
					&& zPos + 0.9f < playerLocation.z + vector.z + (radius - 0.15))
				{
					return new Point3D(xPos, 0, zPos + 0.9f);
				}
			}
		}		

		return new Point3D(-1,-1,-1);
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
		
		result = checkCornersInt(playerLocation, vector, radius, maze, xPos, zPos);
		
		return result;
	}
	
	static void collideCorner(Point3D playerLocation, Point3D cornerLocation, float radius)
	{

		Vector3D adjustment = cornerLocation.to(playerLocation);
		if(adjustment.length() < (radius - 0.15))
		{
			adjustment.normalize();
			adjustment.scale(5);
			playerLocation.movement(adjustment);
		}
	}

	static Vector3D collideObstacle(Player player, Obstacle obstacle)
	{
		
		Vector3D dist = obstacle.obstacleLocation.to(player.playerLocation);
		
		System.out.println(dist.length());
		if (dist.length() < obstacle.radius + player.radius)
		{
			//System.out.println("colo");
			dist.normalize();
			dist.scale(obstacle.radius + player.radius);
			return dist;
			
		}
		return new Vector3D(0,0,0);
	}
}
