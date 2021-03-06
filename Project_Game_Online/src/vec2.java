import java.lang.Math;

public class vec2 {
    public float x, y;

    public vec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public vec2(vec2 srcVec) {
        x = srcVec.x;
        y = srcVec.y;
    }

    public static float getLenght(vec2 v) {
        return (float)Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public static float getLenght(vec2 v1, vec2 v2) {
        vec2 v = new vec2(v1.x - v2.x, v1.y - v2.y);
        return (float)Math.sqrt(v.x * v.x + v.y * v.y);
    }

    public float getLenght() {
        return (float)Math.sqrt(x * x + y * y);
    }

    public String toString()
    {
        return "" + x + " " + y;
    }
}
