#version 400 core

in vec3 norm;

out vec4 color;

uniform mat4 model;
uniform mat4 projection;
uniform mat4 view;

void main()
{
    //color = vec4(norm, 1.0);
    color = vec4(0, 0, 0, 1);
} 