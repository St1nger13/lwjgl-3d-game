#version 140

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D guiTexture;
uniform vec4 color ;
uniform float isUseColor ;

void main(void){

	out_Color = texture(guiTexture,textureCoords);
    if(isUseColor > 0)
        out_Color = color ;
}