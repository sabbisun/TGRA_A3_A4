package com.ru.tgra.shapes;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {

	// Upper left corner is 0,0 [x][y]
	public RoomCell[][] cells;
	private int size;
	
	public Maze()
	{
		size = 20;
		cells = new RoomCell[size][size];
		
		for(int i = 0; i < size; i += 1)
		{
			for(int j = 0; j < size; j += 1)
			{
				cells[j][i] = new RoomCell();
			}
		}
	}
	
	public Maze(int size)
	{
		this.size = size;
		cells = new RoomCell[size][size];
		
		for(int i = 0; i < size; i += 1)
		{
			for(int j = 0; j < size; j += 1)
			{
				cells[j][i] = new RoomCell();
			}
		}
	}
	
	public int size()
	{
		return size;
	}
	
	public void setBox()
	{
		for (int i = 0; i < size; i++)
		{
			cells[0][i].setEast(true);
			cells[size-1][i].setEast(true);
			cells[i][0].setSouth(true);
			cells[i][size-1].setSouth(true);
		}
		cells[0][0].setEast(false);
		cells[0][0].setSouth(false);
		
		cells[size-1][0].setEast(false);
		cells[0][size-1].setSouth(false);
	}
	
	public void setPrison()
	{
		for(int y = 0; y < size; y += 1)
		{
			for(int x = 0; x < size; x += 1)
			{
				if(y != 0)
				{
					cells[x][y].setEast(true);
				}
				if(x != 0)
				{
					cells[x][y].setSouth(true);
				}
			}
		}
	}
	

	public void setFull()
	{
		for(int y = 0; y < size; y += 1)
		{
			for(int x = 0; x < size; x += 1)
			{
					cells[x][y].fill();
			}
		}
	}
	
	public void setTest1()
	{
		for(int y = 0; y < size; y += 1)
		{
			for(int x = 0; x < size; x += 1)
			{
					cells[x][y].clear();
			}
		}
		if(size >= 5)
		{
			cells[1][1].fill();
			cells[2][1].setEast(true);
			cells[3][1].setSouth(true);
			
			cells[1][2].setSouth(true);
			cells[3][2].setSouth(true);
			
			cells[1][3].setEast(true);
			cells[2][3].setEast(true);
			
		}
	}
	
	public void setTest2()
	{
		for(int y = 0; y < size; y += 1)
		{
			for(int x = 0; x < size; x += 1)
			{
					cells[x][y].clear();
			}
		}
		if(size >= 5)
		{
			cells[1][1].setSouth(true);
		}
	}
	
	public void setTest3()
	{
		for(int y = 0; y < size; y += 1)
		{
			for(int x = 0; x < size; x += 1)
			{
					cells[x][y].clear();
			}
		}
		if(size >= 5)
		{
			cells[1][1].setEast(true);
		}
	}
	
	public void setMaze()
	{
		// Set up cells
		setPrison();
		
		boolean[][] marked = new boolean[size][size];
		
		// Set up mouse traveling algorithm
		
		//System.err.println("Before setting up marked");
		
		for(int y = 0; y < size; y += 1)
		{
			for(int x = 0; x < size; x += 1)
			{
				if(y == 0 || x == 0)
				{
					marked[x][y] = true;
				}
				else
				{
					marked[x][y] = false;
				}
			}
		}

		//System.err.println("After setting up marked");
		
		Random rnd = new Random();
		
		Stack<Integer> xStack = new Stack<Integer>();
		Stack<Integer> yStack = new Stack<Integer>();
		
		int xCoord = 1 + rnd.nextInt(size - 1);
		int yCoord = 1 + rnd.nextInt(size - 1);
		
		xStack.push(xCoord);
		yStack.push(yCoord);
		

		//System.err.println("Before while loop");
		
		while(!xStack.empty())
		{
			marked[xCoord][yCoord] = true;
			
			ArrayList<Integer> direction = new ArrayList<Integer>(); 
			
			//up,right,down,left
			if(0 < xCoord && !marked[xCoord-1][yCoord])
			{
				// Can go left
				direction.add(3);
			}
			if(xCoord < size - 1 && !marked[xCoord+1][yCoord])
			{
				// Can go right
				direction.add(1);
			}
			if(0 < yCoord && !marked[xCoord][yCoord-1])
			{
				// Can go up
				direction.add(0);
			}
			if(yCoord < size - 1 && !marked[xCoord][yCoord+1])
			{
				// Can go down
				direction.add(2);
			}
			
			if(!direction.isEmpty())
			{
				int go = rnd.nextInt(direction.size());
				go = direction.get(go);
				switch(go)
				{
					case 0: // Go up
						yCoord--;
						cells[xCoord][yCoord].setSouth(false);
						break;
					
					case 1: // Go right
						cells[xCoord][yCoord].setEast(false);
						xCoord++;
						break;
					
					case 2: // Go down
						cells[xCoord][yCoord].setSouth(false);
						yCoord++;
						break;
					
					case 3: // Go left
						xCoord--;
						cells[xCoord][yCoord].setEast(false);
						break;
						
					default:
						break;
				}
				xStack.push(xCoord);
				yStack.push(yCoord);
				
			}
			else
			{
				xCoord = xStack.pop();
				yCoord = yStack.pop();
			}
		}
		//System.err.println("After while loop");
	}
	
	public void setMazeCenterRoom()
	{
		//setMaze();
		setMaze();
		if(size >= 10)
		{
			if(size % 2 == 0)
			{
				// Room is 3x3
				int center = ((size-1)/2);
				// Full clear
				cells[center][center].clear();
				cells[center+1][center].clear();
				cells[center][center+1].clear();
				cells[center+1][center+1].clear();
				// South clear
				cells[center+2][center].setSouth(false);
				cells[center+2][center+1].setSouth(false);
				// East clear
				cells[center][center+2].setEast(false);
				cells[center+1][center+2].setEast(false);
			}
			else
			{
				// Room is 4x4
				int center = (size-1)/2;
				// Full clear
				cells[center-1][center-1].clear();
				cells[center][center-1].clear();
				cells[center+1][center-1].clear();
				cells[center-1][center].clear();
				cells[center-1][center+1].clear();
				
				cells[center][center].clear();
				cells[center+1][center].clear();
				cells[center][center+1].clear();
				cells[center+1][center+1].clear();
				// South clear
				cells[center+2][center-1].setSouth(false);
				cells[center+2][center].setSouth(false);
				cells[center+2][center+1].setSouth(false);
				// East clear
				cells[center-1][center+2].setEast(false);
				cells[center][center+2].setEast(false);
				cells[center+1][center+2].setEast(false);
			}
		}
		
	}
	
	public void printMaze()
	{
		char mazeSymbol[][] = new char[2*size][2*size];		
		
		for(int i = 0; i < 2*size; i += 1)
		{
			for(int j = 0; j < 2*size; j += 1)
			{
				mazeSymbol[j][i] = '.';
			}
		}
		
		for(int i_y = 0; i_y < size; i_y += 1)
		{
			for(int j_x = 0; j_x < size; j_x += 1)
			{
				int x = 2*j_x;
				int y = 2*i_y;
				
				if( (( j_x <size-1) && cells[j_x+1][i_y].south()) || ( i_y<size-1 && cells[j_x][i_y+1].east()))
				{
					mazeSymbol[x+1][y+1] = '#';
				}
				
				if (cells[j_x][i_y].east())
				{
					mazeSymbol[x+1][y] = '#';
					mazeSymbol[x+1][y+1] = '#';
				}
				if (cells[j_x][i_y].south())
				{
					mazeSymbol[x][y+1] = '#';
					mazeSymbol[x+1][y+1] = '#';
				}
			}
		}
		
		for(int y = 0; y < 2*size; y += 1)
		{
			for(int x = 0; x < 2*size; x += 1)
			{
				System.out.print(mazeSymbol[x][y]);
			}
			System.out.println("");
		}
	}
	
	public void printSetup()
	{
		for(int y = 0; y < size; y += 1)
		{
			for(int x = 0; x < size; x += 1)
			{
				if (cells[x][y].east() && cells[x][y].south())
				{
					System.out.print("F");
				}
				else if (cells[x][y].east())
				{
					System.out.print("|");
				}
				else if (cells[x][y].south())
				{
					System.out.print("_");
				}
				else
				{
					System.out.print(".");
				}
			}
			System.out.println("");
		}
	}
}
