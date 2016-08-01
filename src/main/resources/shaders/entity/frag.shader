#version 140

in vec2 pass_textureCoords ;

out vec4 out_Color ;

uniform sampler2D modelTexture ;
uniform float transparencyChannel ;

void main( void )
{
	vec4 color = texture(modelTexture,pass_textureCoords) ;

	if(transparencyChannel > 0.5) {
	    float alpha = color.r ;
	    color.a *= alpha ;
	} else {
	    color.a = 1 ;
	}

	out_Color = color ;
}
