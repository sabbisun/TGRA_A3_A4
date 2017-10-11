
#ifdef GL_ES
precision mediump float;
#endif

//variables used and accessed from the outside
//make sure to use the variables declared

attribute vec3 a_position;
attribute vec3 a_normal;

uniform vec4 u_eyePosition;

uniform mat4 u_modelMatrix;
uniform mat4 u_viewMatrix;
uniform mat4 u_projectionMatrix;

uniform vec4 u_lightPosition;
uniform vec4 u_lightDiffuse;
uniform vec4 u_lightSpecular;

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
	
	vec4 s = u_lightPosition - position;	
	//how the light hits the object
	float lambert = max(0, dot(normal, s) / (length(normal)*length(s))); //normal and direction to the light
	
	vec4 v = u_eyePosition - position; // direction to eye
	vec4 h = v + s;
	float phong = max(0, dot(normal, h) / (length(normal)*length(h))); //normal and direction to the light
	phong = pow(phong, u_shininess);

	v_color = lambert*u_lightDiffuse*u_materialDiffuse; //vectors multiplied component wise
	//v_color += phong*u_lightSpecular*u_materialSpecular;
	v_color += u_materialAmbiance*u_globalAmbiance;

	position = u_viewMatrix * position;
	//normal = u_viewMatrix * normal;
	
	//eye coords

	//v_color = max(0, (dot(normal, vec4(0,0,1,0))/ length(normal)))* u_color;
	//v_color = max(0, (dot(normal, normalize(vec4(-position.x, -position.y, -position.z,0)))/ length(normal))) * u_color;

	gl_Position = u_projectionMatrix * position;
}