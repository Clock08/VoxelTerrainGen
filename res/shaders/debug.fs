#version 400 core

out vec4 FragColor;

in vec2 TexCoords;

uniform sampler2D tex;

void main()
{
	vec4 color = texture(tex, TexCoords);
    FragColor = color;
}