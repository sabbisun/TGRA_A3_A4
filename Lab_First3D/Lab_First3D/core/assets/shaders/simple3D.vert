
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

const int lightNumber = 6;

struct light
{
	vec4 lightPosition;
	vec4 lightColor;
	//vec4 lightDiffuse;
	//vec4 lightSpecular;
};

uniform light lights[lightNumber];

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
	
	vec4 s;
	vec4 v = u_eyePosition - position; // direction to eye
	vec4 h;
	float lambert;
	float phong;
	v_color = u_materialAmbiance*u_globalAmbiance;
	for(int i = 0; i < lightNumber; i++)
	{
		if(lights[i].lightPosition.w == 0.0f) //for directional light
		{
			s = lights[i].lightPosition;
		}
		else
		{
			s = lights[i].lightPosition - position; //for position light
		}
		//how the light hits the object
		lambert = max(0, dot(normal, s) / (length(normal)*length(s))); //normal and direction to the light
		h = v + s;
		phong = max(0, dot(normal, h) / (length(normal)*length(h))); //normal and direction to the light
		phong = pow(phong, u_shininess);
		v_color += lambert*lights[i].lightColor*u_materialDiffuse; //vectors multiplied component wise
		v_color += phong*lights[i].lightColor*u_materialSpecular;
	}
	

	position = u_viewMatrix * position;
	//normal = u_viewMatrix * normal;
	
	//eye coords

	//v_color = max(0, (dot(normal, vec4(0,0,1,0))/ length(normal)))* u_color;
	//v_color = max(0, (dot(normal, normalize(vec4(-position.x, -position.y, -position.z,0)))/ length(normal))) * u_color;

	gl_Position = u_projectionMatrix * position;
}