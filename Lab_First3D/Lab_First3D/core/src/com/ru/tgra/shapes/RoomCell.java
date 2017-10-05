package com.ru.tgra.shapes;

public class RoomCell {

	private boolean eastWall;
	private boolean southWall;
	
	public RoomCell()
	{
		this.eastWall = false;
		this.southWall = false;
	}
	
	public RoomCell(boolean eastWall, boolean southWall)
	{
		this.eastWall = eastWall;
		this.southWall = southWall;
	}
	
	public boolean east()
	{
		return eastWall;
	}
	
	public boolean south()
	{
		return southWall;
	}
	
	public void setEast(boolean set)
	{
		this.eastWall = set;
	}
	
	public void setSouth(boolean set)
	{
		this.southWall = set;
	}
}
