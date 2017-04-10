#version 400 core

layout (location = 0) out vec3 gPosition;
layout (location = 1) out vec3 gNormal;
layout (location = 2) out vec4 gAlbedoSpec;

in vec3 FragPos;
in vec2 TexCoords;
in vec3 Normal;

uniform sampler2D texDiffuse;
uniform sampler2D texSpecular;

void main()
{
    gPosition = FragPos;
    
    gNormal = normalize(Normal);
    
    gAlbedoSpec.rgb = texture(texDiffuse, TexCoords).rgb;
    
    gAlbedoSpec.a = 1;//texture(texSpecular, TexCoords).r;
} 