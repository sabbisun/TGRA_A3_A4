package com.ru.tgra.shapes;

public class Maze {

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
		
		for (int i = 0; i < size; i++)
		{
			cells[0][i].setEast(true);
			cells[size-1][i].setEast(true);
			cells[i][0].setSouth(true);
			cells[i][size-1].setSouth(true);
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
		
		for(int i = 0; i < size; i += 1)
		{
			for(int j = 0; j < size; j += 1)
			{
				int x = 2*j;
				int y = 2*i;
				
				if (cells[j][i].east())
				{
					mazeSymbol[x+1][y] = '#';
					mazeSymbol[x+1][y+1] = '#';
				}
				if (cells[j][i].south())
				{
					mazeSymbol[x][y+1] = '#';
					mazeSymbol[x+1][y+1] = '#';
				}
			}
		}
		
		for(int i = 0; i < 2*size; i += 1)
		{
			for(int j = 0; j < 2*size; j += 1)
			{
				System.out.print(mazeSymbol[j][i]);
			}
			System.out.println("");
		}
	}
}
