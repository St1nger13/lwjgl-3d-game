#version 140

in vec3 position;
out vec3 textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;

void main(void){
	
	gl_Position = projectionMatrix * viewMatrix * transformationMatrix * vec4(position, 1.0); 
	textureCoords = position;
	
}
