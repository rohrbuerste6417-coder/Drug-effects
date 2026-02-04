#version 150

in vec3 Position;
in vec2 UV0;

uniform mat4 ProjMat;
uniform mat4 ModelViewMat;
uniform vec2 OutSize;

out vec2 texCoord;

void main(){
    vec4 outPos = ProjMat * ModelViewMat * vec4(Position, 1.0);
    gl_Position = outPos;
    texCoord = UV0;
}
