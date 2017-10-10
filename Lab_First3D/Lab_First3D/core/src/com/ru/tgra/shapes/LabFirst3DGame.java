package com.ru.tgra.shapes;

//Escape eða space til að loka glugganum /hætta í forriti
//Nota músina sem stýringu, passa að týna ekki bendinum á henni (núllstilla?)
//
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;

import java.nio.FloatBuffer;

import com.badlogic.gdx.utils.BufferUtils;

public class LabFirst3DGame extends ApplicationAdapter implements InputProcessor {

	private FloatBuffer matrixBuffer;

	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;
	private int normalLoc;

	private int modelMatrixLoc;
	private int viewMatrixLoc;
	private int projectionMatrixLoc;

	private int colorLoc;
	
	private float angle;
	
	private Camera cam;
	private Camera orthoCam;
	
	private float fov = 90.0f;
	
	private Player playerOne;
	RoomCell tempCell;
	Maze maze;

	//private ModelMatrix modelMatrix;

	@Override
	public void create () {
		
		maze = new Maze(5);
		//maze.setPrison();
		//maze.setFull();
		maze.setMaze();
		maze.printMaze();
		maze.printSetup();
		
		tempCell = new RoomCell();
		tempCell.fill();
		
		Gdx.input.setInputProcessor(this);

		String vertexShaderString;
		String fragmentShaderString;

		vertexShaderString = Gdx.files.internal("shaders/simple3D.vert").readString();
		fragmentShaderString =  Gdx.files.internal("shaders/simple3D.frag").readString();

		vertexShaderID = Gdx.gl.glCreateShader(GL20.GL_VERTEX_SHADER);
		fragmentShaderID = Gdx.gl.glCreateShader(GL20.GL_FRAGMENT_SHADER);
	
		Gdx.gl.glShaderSource(vertexShaderID, vertexShaderString);
		Gdx.gl.glShaderSource(fragmentShaderID, fragmentShaderString);
	
		Gdx.gl.glCompileShader(vertexShaderID);
		Gdx.gl.glCompileShader(fragmentShaderID);

		renderingProgramID = Gdx.gl.glCreateProgram();
	
		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
		Gdx.gl.glEnableVertexAttribArray(normalLoc);

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
		projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");

		Gdx.gl.glUseProgram(renderingProgramID);
/*
		float[] mm = new float[16];

		mm[0] = 1.0f; mm[4] = 0.0f; mm[8] = 0.0f; mm[12] = 0.0f;
		mm[1] = 0.0f; mm[5] = 1.0f; mm[9] = 0.0f; mm[13] = 0.0f;
		mm[2] = 0.0f; mm[6] = 0.0f; mm[10] = 1.0f; mm[14] = 0.0f;
		mm[3] = 0.0f; mm[7] = 0.0f; mm[11] = 0.0f; mm[15] = 1.0f;

		modelMatrixBuffer = BufferUtils.newFloatBuffer(16);
		modelMatrixBuffer.put(mm);
		modelMatrixBuffer.rewind();

		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, modelMatrixBuffer);
*/
		//COLOR IS SET HERE
		Gdx.gl.glUniform4f(colorLoc, 0.7f, 0.2f, 0, 1);

		BoxGraphic.create(positionLoc, normalLoc);
		SphereGraphic.create(positionLoc, normalLoc);
		SincGraphic.create(positionLoc);
		CoordFrameGraphic.create(positionLoc);

		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

		ModelMatrix.main = new ModelMatrix();
		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.setShaderMatrix(modelMatrixLoc);

		Gdx.gl.glEnable(GL20.GL_DEPTH_TEST);

		cam = new Camera(viewMatrixLoc, projectionMatrixLoc);
		//cam.perspectiveProjection(fov, 1.0f, 0.4f, 100.0f);
		//cam.look(new Point3D(3.0f, 0, 10.0f), new Point3D(0,0,0), new Vector3D(0,1,0));
		
		orthoCam = new Camera(viewMatrixLoc, projectionMatrixLoc);
		//orthoCam.orthographicProjection(-5, 5, -5, 5, 0.0f, 100);
		orthoCam.orthographicProjection(-10, 10, -10, 10, 3.0f, 100);
		//orthoCam.look(new Point3D(-3f,2f,3f), new Point3D(0,3,0), new Vector3D(0,1,0));
		playerOne = new Player(new Point3D(3.0f, 0, 10.0f), new Vector3D(0, 0, -1.0f), 3, cam, orthoCam);
	
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
			//playerOne.playerCam.slide(-3.0f*deltaTime,0,0);
			//orthoCam.slide(-3.0f * deltaTime,0,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D))
		{
			playerOne.strafeRight(deltaTime);
			//playerOne.playerCam.slide(3.0f*deltaTime,0,0);
			//orthoCam.slide(3.0f * deltaTime,0,0);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.W))
		{
			playerOne.forward(deltaTime);
			//playerOne.playerCam.slide(0,0,-3.0f * deltaTime);
			//orthoCam.slide(0,0,-3.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.S))
		{
			playerOne.backward(deltaTime);
			//playerOne.playerCam.slide(0,0,3.0f * deltaTime);
			//orthoCam.slide(0,0,3.0f * deltaTime);
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
			playerOne.playerCam.yaw(90.0f * deltaTime);
			//orthoCam.yaw(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
		{
			playerOne.playerCam.yaw(-90.0f * deltaTime);
			//orthoCam.yaw(-90.0f * deltaTime);
		}
		boolean fly = true;
		if(fly) //Flight allowed
		{
			if(Gdx.input.isKeyPressed(Input.Keys.UP))
			{
				cam.pitch(90.0f * deltaTime);
				//orthoCam.pitch(90.0f * deltaTime);
			}
			if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
			{
				cam.pitch(-90.0f * deltaTime);
				//orthoCam.pitch(-90.0f * deltaTime);
			}
		}
		if(Gdx.input.isKeyPressed(Input.Keys.Q))
		{
			playerOne.playerCam.roll(90.0f * deltaTime);
			//orthoCam.roll(90.0f * deltaTime);
		}
		if(Gdx.input.isKeyPressed(Input.Keys.E))
		{
			playerOne.playerCam.roll(-90.0f * deltaTime);
			//orthoCam.roll(-90.0f * deltaTime);
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
	
	private void drawCell(RoomCell cell)
	{
		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.pushMatrix();
		if(cell.south() && cell.east())
		{
			ModelMatrix.main.pushMatrix();
			
			Gdx.gl.glUniform4f(colorLoc, 0.2f, 1.0f, 0.2f, 1.0f);
			ModelMatrix.main.addTranslation(-0.5f, 0.0f, -0.5f);
			
			ModelMatrix.main.addScale(0.2f, 1.0f, 0.2f);
			ModelMatrix.main.setShaderMatrix();
			
			BoxGraphic.drawSolidCube();
			
			ModelMatrix.main.popMatrix();
		}
		if(cell.east())
		{
			ModelMatrix.main.pushMatrix();
			
			Gdx.gl.glUniform4f(colorLoc, 0.0f, 1.0f, 0.5f, 1.0f);
			
			ModelMatrix.main.addTranslation(-0.5f, 0.0f, 0.0f);
			ModelMatrix.main.addScale(0.2f, 1.0f, 0.8f);
			
			ModelMatrix.main.setShaderMatrix();
			BoxGraphic.drawSolidCube();
			
			ModelMatrix.main.popMatrix();
		}
		if(cell.south())
		{
			ModelMatrix.main.pushMatrix();
			
			Gdx.gl.glUniform4f(colorLoc, 0.5f, 1.0f, 0.0f, 1.0f);
			ModelMatrix.main.addTranslation(0.0f, 0.0f, -0.5f);
			
			ModelMatrix.main.addScale(0.8f, 1.0f, 0.2f);
			ModelMatrix.main.setShaderMatrix();
			
			BoxGraphic.drawSolidCube();
			
			ModelMatrix.main.popMatrix();
		}
		ModelMatrix.main.popMatrix();
	}
	
	private void drawMaze(Maze maze)
	{
		ModelMatrix.main.loadIdentityMatrix();
		ModelMatrix.main.pushMatrix();
		//ModelMatrix.main.addTranslation(pos.x, pos.y, pos.z);
		for (int z = 0; z < maze.size(); z++)
		{
			for (int x = 0; x < maze.size(); x++)
			{
				//if(x != 0)
				//	continue;
				//drawCell(maze.cells[x][z], new Point3D(x,0,z));
				
				ModelMatrix.main.pushMatrix();
				
				Gdx.gl.glUniform4f(colorLoc, 1.0f, 0.2f, 0.2f, 1.0f);
				ModelMatrix.main.addTranslation(x, 0.0f, z);
				
				ModelMatrix.main.addScale(0.2f, 0.2f, 0.2f);
				ModelMatrix.main.setShaderMatrix();
				
				BoxGraphic.drawSolidCube();
				
				ModelMatrix.main.popMatrix();
				// South east corner
				if(maze.cells[x][z].south() || maze.cells[x][z].east()
						|| (x < maze.size() - 1 && maze.cells[x+1][z].south())
						|| (z < maze.size() - 1 && maze.cells[x][z+1].east()))
				{
					ModelMatrix.main.pushMatrix();
					
					Gdx.gl.glUniform4f(colorLoc, 0.2f, 1.0f, 0.2f, 1.0f);
					ModelMatrix.main.addTranslation(x+0.5f, 0.0f, z+0.5f);
					
					ModelMatrix.main.addScale(0.2f, 1.0f, 0.2f);
					ModelMatrix.main.setShaderMatrix();
					
					BoxGraphic.drawSolidCube();
					
					ModelMatrix.main.popMatrix();
				}
				
				// East wall
				if(maze.cells[x][z].east())
				{
					ModelMatrix.main.pushMatrix();
					
					Gdx.gl.glUniform4f(colorLoc, 0.0f, 1.0f, 0.5f, 1.0f);
					
					ModelMatrix.main.addTranslation(x+0.5f, 0.0f, z-0.0f);
					ModelMatrix.main.addScale(0.2f, 1.0f, 0.8f);
					
					ModelMatrix.main.setShaderMatrix();
					BoxGraphic.drawSolidCube();
					
					ModelMatrix.main.popMatrix();
				}
				//South wall
				if(maze.cells[x][z].south())
				{
					ModelMatrix.main.pushMatrix();
					
					Gdx.gl.glUniform4f(colorLoc, 0.5f, 1.0f, 0.0f, 1.0f);
					ModelMatrix.main.addTranslation(x-0.0f, 0.0f, z+0.5f);
					
					ModelMatrix.main.addScale(0.8f, 1.0f, 0.2f);
					ModelMatrix.main.setShaderMatrix();
					
					BoxGraphic.drawSolidCube();
					
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
				playerOne.playerCam.perspectiveProjection(fov, 1.0f, 1.0f, 100.0f);
				playerOne.playerCam.setShaderMatrices();
			}
			else
			{
				Gdx.gl.glViewport(Gdx.graphics.getWidth()-250, Gdx.graphics.getHeight()-250, 250, 250);
				playerOne.mapLocation.look(new Point3D(cam.eye.x, 20.0f, cam.eye.z), cam.eye, new Vector3D(0, 0, -1));
				//orthoCam.look(new Point3D(7.0f, 40.0f, -7.0f), new Point3D(7.0f, 0.0f, -7.0f), new Vector3D(0, 0, -1));
				playerOne.mapLocation.setShaderMatrices();
			}
		
			//drawCell(tempCell, new Point3D(5.0f,0,0));
			//maze.printMaze();
			drawMaze(maze);
			
			ModelMatrix.main.loadIdentityMatrix();
			
			ModelMatrix.main.pushMatrix();
			
			Gdx.gl.glUniform4f(colorLoc, 1.0f, 0.5f, 0.0f, 1.0f);
			ModelMatrix.main.addTranslation(0.0f, 0.0f, -7.0f);
			ModelMatrix.main.pushMatrix();
			
			ModelMatrix.main.addScale(50, 1, 0.2f);
			ModelMatrix.main.setShaderMatrix();
			BoxGraphic.drawSolidCube();
			
			ModelMatrix.main.popMatrix();
			
			ModelMatrix.main.addTranslation(24.9f, 0, 24.9f);
			
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addScale(0.2f, 1, 50);
			ModelMatrix.main.setShaderMatrix();
			BoxGraphic.drawSolidCube();
			
			ModelMatrix.main.popMatrix();
			
			ModelMatrix.main.addTranslation(-24.9f, 0, 24.9f);
			
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addScale(50, 1, 0.2f);
			ModelMatrix.main.setShaderMatrix();
			BoxGraphic.drawSolidCube();
			
			ModelMatrix.main.popMatrix();
			
			ModelMatrix.main.addTranslation(-24.9f, 0, -24.9f);
			
			ModelMatrix.main.pushMatrix();
			ModelMatrix.main.addScale(0.2f, 1, 50);
			ModelMatrix.main.setShaderMatrix();
			BoxGraphic.drawSolidCube();
			
			ModelMatrix.main.popMatrix();
			
			Gdx.gl.glUniform4f(colorLoc, 0.0f, 1.0f, 0.0f, 1.0f);
			ModelMatrix.main.addTranslation(10.0f, 5.0f, 2.0f);
			ModelMatrix.main.pushMatrix();
			
			/*
			for(int level = 0; level < 10; level++)
			{
				ModelMatrix.main.addTranslation(0.55f, 1.0f, -0.55f);
				
				ModelMatrix.main.pushMatrix();
				for(int i = 0; i < 10 - level; i++)
				{
					ModelMatrix.main.addTranslation(1.1f, 0, 0);
					ModelMatrix.main.pushMatrix();
					for(int j = 0; j < 10 - level; j++)
					{
						ModelMatrix.main.addTranslation(0, 0, -1.1f);
						ModelMatrix.main.pushMatrix();
						ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
						ModelMatrix.main.setShaderMatrix();
						SphereGraphic.drawSolidSphere();
						ModelMatrix.main.popMatrix();
					}
					ModelMatrix.main.popMatrix();
				}
				ModelMatrix.main.popMatrix();
			}
			*/
			ModelMatrix.main.popMatrix();
			
			
			
			
			ModelMatrix.main.popMatrix();// added this to fix first push, needs to be same amount of push and pop
			
			
			
			
			if(viewNum == 1)
			{
				Gdx.gl.glUniform4f(colorLoc, 1.0f, 0.3f, 0.1f, 1.0f);
				ModelMatrix.main.pushMatrix();
				ModelMatrix.main.addTranslation(cam.eye.x, cam.eye.y, cam.eye.z);
				ModelMatrix.main.setShaderMatrix();
				BoxGraphic.drawSolidCube();
				ModelMatrix.main.popMatrix();
			}
		}
		
		
		/*Gdx.gl.glUniform4f(colorLoc, 1.0f, 0.0f, 1.0f, 1.0f);

		cam.setShaderMatrices();
		
		ModelMatrix.main.loadIdentityMatrix();
		
		//ModelMatrix.main.addRotationX(angle);
		
		int maxLevel = 9;
		//ModelMatrix.main.addTranslation(250, 250, 0);
		ModelMatrix.main.pushMatrix();
		for(int level = 0; level < maxLevel; level++)
		{
			ModelMatrix.main.addTranslation(0.55f, 1.0f, -0.55f);
			
			ModelMatrix.main.pushMatrix();
			for(int i = 0; i < maxLevel - level; i++)
			{
				ModelMatrix.main.addTranslation(1.1f, 0, 0);
				ModelMatrix.main.pushMatrix();
				for(int j = 0; j < maxLevel - level; j++)
				{
					ModelMatrix.main.addTranslation(0, 0, -1.1f);
					ModelMatrix.main.pushMatrix();
					ModelMatrix.main.addScale(0.5f, 0.5f, 0.5f);
					ModelMatrix.main.setShaderMatrix();
					SphereGraphic.drawSolidSphere();
					ModelMatrix.main.popMatrix();
					//BoxGraphic.drawSolidCube();
				}
				ModelMatrix.main.popMatrix();	
			}
			ModelMatrix.main.popMatrix();
		}
		ModelMatrix.main.popMatrix();
		
		
		//BoxGraphic.drawOutlineCube();
		
		//SphereGraphic.drawOutlineSphere();
		*/

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