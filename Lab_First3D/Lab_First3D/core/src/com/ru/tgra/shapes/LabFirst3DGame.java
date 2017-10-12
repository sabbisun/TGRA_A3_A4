package com.ru.tgra.shapes;

//Escape eða space til að loka glugganum /hætta í forriti
//Nota músina sem stýringu, passa að týna ekki bendinum á henni (núllstilla?)
//
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import com.badlogic.gdx.utils.BufferUtils;

public class LabFirst3DGame extends ApplicationAdapter implements InputProcessor {
	
	Shader shader;
	private float angle;
	
	private Camera cam;
	private Camera orthoCam;
	
	private float fov = 60.0f;
	
	private Player playerOne;
	RoomCell tempCell;
	Maze maze;

	//private ModelMatrix modelMatrix;

	@Override
	public void create () {
		
		shader = new Shader();
		
		maze = new Maze(14);
		//maze.setPrison();
		//maze.setFull();
		//maze.setTest1();
		//maze.setMaze();
		maze.setMazeCenterRoom();
		maze.printMaze();
		maze.printSetup();
		
		tempCell = new RoomCell();
		tempCell.fill();
		
		Gdx.input.setInputProcessor(this);

		BoxGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SphereGraphic.create(shader.getVertexPointer(), shader.getNormalPointer());
		SincGraphic.create(shader.getVertexPointer());
		CoordFrameGraphic.create(shader.getVertexPointer());

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		
		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera();
		//cam.perspectiveProjection(fov, 1.0f, 0.4f, 100.0f);
		//cam.look(new Point3D(3.0f, 0, 10.0f), new Point3D(0,0,0), new Vector3D(0,1,0));
		
		orthoCam = new Camera();
		//orthoCam.orthographicProjection(-5, 5, -5, 5, 0.0f, 100);
		orthoCam.orthographicProjection(-10, 10, -10, 10, 3.0f, 100);
		//orthoCam.look(new Point3D(-3f,2f,3f), new Point3D(0,3,0), new Vector3D(0,1,0));
		playerOne = new Player(new Point3D(1.5f, 0, 1.5f), new Vector3D(0, 0, -1.0f), 2, cam, orthoCam, maze, shader);
	
		shader.setGlobalAmibance(0.5f, 0.5f, 0.5f, 1.0f);
		shader.setEyePosition(0, 0, 0);
	}

	private void input()
	{

	}
	
	private void update()
	{
		if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
		{
			System.exit(0);
		}
		
		float deltaTime = Gdx.graphics.getDeltaTime();

		angle += 180.0f * deltaTime;

		if(Gdx.input.isKeyPressed(Input.Keys.A))
		{
			playerOne.strafeLeft(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			playerOne.strafeRight(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			playerOne.forward(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			playerOne.backward(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.R))
		{
			playerOne.playerCam.slide(0,3.0f * deltaTime,0);
			//orthoCam.slide(0,3.0f * deltaTime,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.F))
		{
			playerOne.playerCam.slide(0,-3.0f * deltaTime,0);
			//orthoCam.slide(0,-3.0f * deltaTime,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
		{
			playerOne.lookLeft(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			playerOne.lookRight(deltaTime);
		}	
		if(Gdx.input.isKeyPressed(Input.Keys.UP))
		{
			playerOne.lookUp(deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
		{
			playerOne.lookDown(deltaTime);
		}
	
		if(Gdx.input.isKeyPressed(Input.Keys.T))
		{
			fov -= 30.0f * deltaTime;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.G))
		{
			fov += 30.0f * deltaTime;
		}
	}

	private void drawMaze(Maze maze)
	{
		//ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.pushMatrix();
		ModelMatrix.main.addTranslation(0.5f, 0, 0.5f);

		shader.setLightColor(0, 1.0f, 0.0f, 0.0f, 1.0f);
		shader.setLightPosition(0, 1, 3f, 1, 1.0f);
		
		shader.setLightColor(1, 0.0f, 0.0f, 1.0f, 1.0f);
		shader.setLightPosition(1, 1, 3f, maze.size()-1, 1.0f);
		
		shader.setLightColor(2, 1.0f, 1.0f, 0.0f, 1.0f);
		shader.setLightPosition(2, maze.size()-1, 1f, 3, 1.0f);
		
		shader.setLightColor(3, 0.0f, 1.0f, 1.0f, 1.0f);
		shader.setLightPosition(3, maze.size()-1, 3f, maze.size()-1, 1.0f);
		
		ModelMatrix.main.pushMatrix();
		
		shader.setMaterialAmibance(0.1f, 0.1f, 0.1f, 1.0f);
		shader.setMaterialDiffuse(0.0f, 1.0f, 0.5f, 1.0f);
		shader.setMaterialSpecular(0.2f, 0.2f, 0.2f, 1.0f);
		shader.setMaterialShininess(2);
		
		float centerX = (maze.size()-1)/2 + 1.0f - 0.5f*(maze.size()%2);
		float centerZ = centerX;
		
		ModelMatrix.main.addTranslation(centerX, -0.5f, centerZ);
		ModelMatrix.main.addScale(maze.size()-1, 0.1f, maze.size()-1);
		
		shader.setModelMatrix(ModelMatrix.main.getMatrix());
		
		BoxGraphic.drawSolidCube();
		
		ModelMatrix.main.popMatrix();
		
		// TODO
		for (int z = 0; z < maze.size(); z++)
		{
			int i = 0;
			for (int x = 0; x < maze.size(); x++)
			{
				
				boolean pillar = false;
				boolean[] pillarSides = {false,false,true,true,false,false};
				float pillarShift = 0;
				
				if(maze.cells[x][z].south() && (x < maze.size() - 1 && maze.cells[x+1][z].south()))
				{
					//pillar = true;
					pillarSides[0] = true;
					pillarSides[1] = true;
				}
				if(maze.cells[x][z].east() && (z < maze.size() - 1 && maze.cells[x][z+1].east()))
				{
					//pillar = true;
					pillarSides[4] = true;
					pillarSides[5] = true;
				}
				if(pillar)
				{
					ModelMatrix.main.pushMatrix();
					
					shader.setMaterialAmibance(0.5f, 0.1f, 0.5f, 1.0f);
					shader.setMaterialDiffuse(0.0f, 1.0f, 0.5f, 1.0f);
					
					ModelMatrix.main.addTranslation(x+0.5f, 0.5f, z+0.5f);
					ModelMatrix.main.addScale(0.2f, 1.0f, 0.2f);
					
					shader.setModelMatrix(ModelMatrix.main.getMatrix());
					
					BoxGraphic.drawSolidCube(pillarSides);
					
					ModelMatrix.main.popMatrix();
				}
				
				// Expanding walls to intersect, inbetween piece not good in collision
				// East wall
				if(maze.cells[x][z].east())
				{
					boolean[] sides = {true,true,true,true,true,true};
					
					// Gets rid of unnecessary sides
					if(z > 0 && maze.cells[x][z-1].east())
					{
						sides[0] = false;
					}
					if(z < maze.size() -1 && maze.cells[x][z+1].east())
					{
						sides[1] = false;
					}
					
					ModelMatrix.main.pushMatrix();
					
					shader.setMaterialAmibance(0.5f, 0.0f, 0.0f, 1.0f);
					shader.setMaterialDiffuse(0.0f, 1.0f, 0.5f, 1.0f);
					shader.setMaterialSpecular(0.0f, 1.0f, 0.5f, 1.0f);
					shader.setMaterialShininess(13);
					
					ModelMatrix.main.addTranslation(x+0.5f, 0.0f, z-0.0f);
					ModelMatrix.main.addScale(0.2f, 1.0f, 0.8f+0.2f);
					
					shader.setModelMatrix(ModelMatrix.main.getMatrix());
					
					BoxGraphic.drawSolidCube(sides);
					
					ModelMatrix.main.popMatrix();
				}
				//South wall
				if(maze.cells[x][z].south())
				{
					boolean[] sides = {true,true,true,true,true,true};
					
					// Gets rid of unnecessary sides
					if(x > 0 && maze.cells[x-1][z].south())
					{
						sides[4] = false;
					}
					if(x < maze.size() -1 && maze.cells[x+1][z].south())
					{
						sides[5] = false;
					}
					
					ModelMatrix.main.pushMatrix();
					
					shader.setMaterialSpecular(0.8f, 0.5f, 0.0f, 1.0f);
					shader.setMaterialAmibance(0.0f, 0.5f, 0.0f, 1.0f);
					shader.setMaterialShininess(10);
					shader.setMaterialDiffuse(1.0f, 0.5f, 1.0f, 1.0f);
					ModelMatrix.main.addTranslation(x-0.0f, 0.0f, z+0.5f);
					
					ModelMatrix.main.addScale(0.8f+0.2f, 1.0f, 0.2f);
					shader.setModelMatrix(ModelMatrix.main.getMatrix());
					
					BoxGraphic.drawSolidCube(sides);
					
					ModelMatrix.main.popMatrix();
				}
				
			}
		}
		ModelMatrix.main.popMatrix();
	}
	
	private void display()
	{
		//do all actual drawing and rendering here
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		for(int viewNum = 0; viewNum < 2; viewNum++)
		{
			if(viewNum == 0)
			{
				Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
				playerOne.playerCam.perspectiveProjection(fov, 2.0f, 0.1f, 100.0f);
				shader.setViewMatrix(cam.getViewMatrix());
				shader.setProjectionMatrix(cam.getProjectionMatrix());
			}
			else
			{
				Gdx.gl.glViewport(Gdx.graphics.getWidth()-250, Gdx.graphics.getHeight()-250, 250, 250);
				playerOne.mapLocation.look(new Point3D(cam.eye.x, 20.0f, cam.eye.z), cam.eye, new Vector3D(0, 0, -1));
				shader.setViewMatrix(orthoCam.getViewMatrix());
				shader.setProjectionMatrix(orthoCam.getProjectionMatrix());
			}
			
			ModelMatrix.main.loadIdentityMatrix();
			
			drawMaze(maze);
			
			
			if(viewNum == 1)
			{
				shader.setMaterialDiffuse(1.0f, 0.3f, 0.1f, 1.0f);
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
				ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
				shader.setModelMatrix(ModelMatrix.main.getMatrix());
				BoxGraphic.drawSolidCube();
				ModelMatrix.main.popMatrix();
			}
		}
		

	}

	@Override
	public void render () {
		
		input();
		//put the code inside the update and display methods, depending on the nature of the code
		update();
		display();

	}

	@Override
	public boolean keyDown(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}


}