package com.ru.tgra.shapes;

import java.nio.FloatBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;

public class Shader {
	
	private int renderingProgramID;
	private int vertexShaderID;
	private int fragmentShaderID;

	private int positionLoc;
	private int normalLoc;

	private int modelMatrixLoc;
	private int viewMatrixLoc;
	private int projectionMatrixLoc;

	//private int colorLoc;
	private int lightPosLoc;
	private int lightDifLoc;
	private int lightSpecLoc;
	private int matDifLoc;
	private int matAmbLoc;
	private int matSpecLoc;
	private int matShinLoc;
	private int globalAmbLoc;
	private int eyePosLoc;
	
	public Shader()
	{
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
		
		//Gdx.gl.glGetError(); google what the errors mean
		//Gdx.gl.glGetInfoLog(); more detailed errors

		renderingProgramID = Gdx.gl.glCreateProgram();
	
		Gdx.gl.glAttachShader(renderingProgramID, vertexShaderID);
		Gdx.gl.glAttachShader(renderingProgramID, fragmentShaderID);
	
		Gdx.gl.glLinkProgram(renderingProgramID);

		positionLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_position");
		Gdx.gl.glEnableVertexAttribArray(positionLoc);

		normalLoc				= Gdx.gl.glGetAttribLocation(renderingProgramID, "a_normal");
		Gdx.gl.glEnableVertexAttribArray(normalLoc); //enable normalLoc to be sent as array

		modelMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_modelMatrix");
		viewMatrixLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_viewMatrix");
		projectionMatrixLoc	= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_projectionMatrix");

		//colorLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_color");
		
		lightPosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightPosition");
		lightDifLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightDiffuse");
		lightSpecLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_lightSpecular");
		
		matDifLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialDiffuse");
		matAmbLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialAmbiance");
		matSpecLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_materialSpecular");
		matShinLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_shininess");
		
		globalAmbLoc			= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_globalAmbiance");
		
		eyePosLoc				= Gdx.gl.glGetUniformLocation(renderingProgramID, "u_eyePosition");
		
		Gdx.gl.glUseProgram(renderingProgramID);
	}
/*	
	public void setColor(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(colorLoc, r, g, b, a);
	}
*/	
	public void setEyePosition(float x, float y, float z)
	{
		Gdx.gl.glUniform4f(eyePosLoc, x, y, z, 1.0f);
	}
	
	public void setLightPosition(float x, float y, float z, float w)
	{
		Gdx.gl.glUniform4f(lightPosLoc, x, y, z, w);
	}
	
	public void setLightDiffuse(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(lightDifLoc, r, g, b, a);
	}
	
	public void setLightSpecular(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(lightSpecLoc, r, g, b, a);
	}
	
	public void setMaterialDiffuse(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(matDifLoc, r, g, b, a);
	}
	
	public void setMaterialAmibance(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(matAmbLoc, r, g, b, a);
	}
	
	public void setMaterialSpecular(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(matSpecLoc, r, g, b, a);
	}
	
	public void setMaterialShininess(int f)
	{
		Gdx.gl.glUniform1i(matShinLoc, f);
	}
	
	public void setGlobalAmibance(float r, float g, float b, float a)
	{
		Gdx.gl.glUniform4f(globalAmbLoc, r, g, b, a);
	}
	
	
	
	
	public int getVertexPointer()
	{
		return positionLoc;
	}
	
	public int getNormalPointer()
	{
		return normalLoc;
	}
	
	public void setModelMatrix(FloatBuffer matrix)
	{
		Gdx.gl.glUniformMatrix4fv(modelMatrixLoc, 1, false, matrix);
	}
	
	public void setViewMatrix(FloatBuffer matrix)
	{
		Gdx.gl.glUniformMatrix4fv(viewMatrixLoc, 1, false, matrix);
	}
	
	public void setProjectionMatrix(FloatBuffer matrix)
	{
		Gdx.gl.glUniformMatrix4fv(projectionMatrixLoc, 1, false, matrix);
	}
}
