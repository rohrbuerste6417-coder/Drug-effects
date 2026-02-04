#version 150

uniform sampler2D Sampler0;
uniform float DrugWobble;
uniform float DrugBlur;
uniform float DrugSaturation;
uniform float Time;

in vec2 texCoord;
out vec4 fragColor;

void main() {
    vec2 uv = texCoord;

    // --- Zoom (Prevent Edge Stretching) ---
    // If we wobble, we might pull pixels from outside the screen.
    // Zoom in slightly to hide the edges.
    float wobble = DrugWobble / 100.0;
    if (wobble > 0.0) {
        // Calculate max possible displacement approximate
        float strength = 0.01 * wobble;
        // Safe Zoom for high levels:
        float zoom = 1.0 / (1.0 + strength); 
        uv = (uv - 0.5) * zoom + 0.5;
        
        // --- Wobble Effect ---
        vec2 offset;
        offset.x = sin(uv.y * 20.0 + Time * 2.0) * strength;
        offset.y = cos(uv.x * 20.0 + Time * 1.5) * strength;
        uv += offset;
    }

    // --- Clamp UVs (Safety) ---
    uv = clamp(uv, 0.001, 0.999);

    // --- Blur / Ghosting ---
    vec4 color = texture(Sampler0, uv);
    // Simple 3-tap
    float blur = DrugBlur / 100.0;
    if (blur > 0.0) {
         float off = 0.005 * blur;
         vec4 l = texture(Sampler0, clamp(uv + vec2(-off, 0), 0.0, 1.0));
         vec4 r = texture(Sampler0, clamp(uv + vec2(off, 0), 0.0, 1.0));
         color = (color + l + r) / 3.0;
    }

    // --- Saturation ---
    // Raw Input: 0 = Default (Off/Gray?), 50 = Normal, 100 = Double.
    // Client sends 0 if inactive.
    // If 0, we want Normal (1.0).
    float rawSat = DrugSaturation;
    float sat = 1.0;
    
    // Heuristic: If rawSat is exactly 0, imply "Not Set" -> Normal (1.0).
    // If rawSat is > 0 but != 50, use it.
    if (rawSat > 0.001) {
        sat = rawSat / 50.0;
    }
    
    if (abs(sat - 1.0) > 0.01) {
        float gray = dot(color.rgb, vec3(0.299, 0.587, 0.114));
        color.rgb = mix(vec3(gray), color.rgb, sat);
    }
    
    fragColor = vec4(color.rgb, 1.0);
}
