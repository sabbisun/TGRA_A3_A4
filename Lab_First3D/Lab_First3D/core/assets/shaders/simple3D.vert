
#ifdef GL_ES
precision mediump float;
#endif

//variables used and accessed from the outside
//make sure to use the variables declared
//sum up for light count (Idi*pd*lambert+Isips*phongf)+pe+globamb*pa = I final color

attribute vec3 a_position;
attribute vec3 a_normal;

uniform vec4 u_eyePosition;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_lightPosition0;
uniform vec4 u_lightDiffuse0;
uniform vec4 u_lightSpecular0;

uniform vec4 u_lightPosition1;
uniform vec4 u_lightDiffuse1;
uniform vec4 u_lightSpecular1;

uniform vec4 u_lightPosition2;
uniform vec4 u_lightDiffuse2;
uniform vec4 u_lightSpecular2;

uniform vec4 u_materialDiffuse;
uniform vec4 u_materialAmbiance;
uniform vec4 u_materialSpecular;

uniform vec4 u_globalAmbiance;
uniform int u_shininess;

//used inside here and set to the fragment shader
varying vec4 v_color; //flows between shaders

void main()
{
	vec4 position = vec4(a_position.x, a_position.y, a_position.z, 1.0);
	position = u_modelMatrix * position;

	vec4 normal = vec4(a_normal.x, a_normal.y, a_normal.z, 0.0);
	normal = u_modelMatrix * normal;
	
	//global coords
	//Lighting
	
	vec4 s0 = u_lightPosition0;
	vec4 s1 = u_lightPosition1 - position;	
	vec4 s2 = u_lightPosition2 - position;	
	//how the light hits the object
	float lambert0 = max(0, dot(normal, s0) / (length(normal)*length(s0))); //normal and direction to the light
	float lambert1 = max(0, dot(normal, s1) / (length(normal)*length(s1))); //normal and direction to the light
	float lambert2 = max(0, dot(normal, s2) / (length(normal)*length(s2))); //normal and direction to the light
	
	vec4 v = u_eyePosition - position; // direction to eye
	vec4 h0 = v + s0;
	vec4 h1 = v + s1;
	vec4 h2 = v + s2;
	
	float phong0 = max(0, dot(normal, h0) / (length(normal)*length(h0))); //normal and direction to the light
	phong0 = pow(phong0, u_shininess);
	float phong1 = max(0, dot(normal, h1) / (length(normal)*length(h1))); //normal and direction to the light
	phong1 = pow(phong1, u_shininess);
	float phong2 = max(0, dot(normal, h2) / (length(normal)*length(h2))); //normal and direction to the light
	phong2 = pow(phong2, u_shininess);

	v_color = lambert0*u_lightDiffuse0*u_materialDiffuse; //vectors multiplied component wise
	v_color = lambert1*u_lightDiffuse1*u_materialDiffuse; //vectors multiplied component wise
	v_color += lambert2*u_lightDiffuse2*u_materialDiffuse; //vectors multiplied component wise
	v_color += phong0*u_lightSpecular0*u_materialSpecular;
	v_color += phong1*u_lightSpecular1*u_materialSpecular;
	v_color += phong2*u_lightSpecular2*u_materialSpecular;
	v_color += u_materialAmbiance*u_globalAmbiance;

	position = u_viewMatrix * position;
	//normal = u_viewMatrix * normal;
	
	//eye coords

	//v_color = max(0, (dot(normal, vec4(0,0,1,0))/ length(normal)))* u_color;
	//v_color = max(0, (dot(normal, normalize(vec4(-position.x, -position.y, -position.z,0)))/ length(normal))) * u_color;

	gl_Position = u_projectionMatrix * position;
}