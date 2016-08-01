#version 140

in vec3 textureCoords;
out vec4 out_Color;

uniform samplerCube cubeMapDay;
uniform samplerCube cubeMapNight;
uniform vec3 skyColor ;
uniform float blendFactor ;
uniform int viewMode ;
uniform float colorBlendFactor ;

const float lowerLimit = 0.0 ;
const float upperLimit = 3000.0 ;

void main(void)
{
	vec4 textureDay = texture(cubeMapDay, textureCoords);
	vec4 textureNight = texture(cubeMapNight, textureCoords);
	vec4 finalColor = mix(textureDay, textureNight, blendFactor) ;
	
	float factor = (textureCoords.y - lowerLimit) / (upperLimit - lowerLimit) ; 
	factor = clamp(factor, 0.0, 1.0) ;
	out_Color = mix(vec4(skyColor, 1.0), finalColor, factor) ;
	
	if( viewMode == 1 )
	{
		float gray = 0.299*out_Color.r + 0.587*out_Color.g + 0.114*out_Color.b;
    	vec4 grayColor = vec4(gray, gray, gray, out_Color.a);
    	out_Color = mix( grayColor, out_Color, colorBlendFactor ) ;
	}

    textureDay.a = 0.2 ;
	//
	out_Color = textureDay ;
}
