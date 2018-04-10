import java.lang.Math;

class vec2 {
    public int x, y;

    public vec2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public vec2(vec2 srcVec) {
        x = srcVec.x;
        y = srcVec.y;
    }

    public static float getLenght(vec2 v) {
        return sqrt(v.x * v.x + v.y * v.y);
    }

    public static float getLenght(vec2 v1, vec2 v2) {
        vec2 v = new vec2(v1.x - v2.x, v1.y - v2.y);
        return sqrt(v.x * v.x + v.y * v.y);
    }

    public float getLenght() {
        return sqrt(x * x + y * y);
    }
}

class Player implements Runnable {

    vec2 m_v2Position;
    vec2 m_v2Speed;
    vec2 m_v2Target;
    Thread m_Thread;

    Player() {
        m_Thread = new Thread(this);
        m_Thread.start();
    }

    void MoveTowardTarget() {
        if (vec2.getLenght(m_v2Position, m_v2Target) < m_v2Speed.getLenght())
            m_v2Position = m_v2Target;
        else {
            m_v2Position.x += m_v2Speed.x;
            m_v2Position.y += m_v2Speed.y;
        }
    }

    public FindATarget()
    {
        m_v2Target.x = 100;//shoul be random
        m_v2Target.y = 100;//shoul be random
    }

    private boolean IsReachTarget() {
        return m_v2Position.x == m_v2Target.x && m_v2Position.y == m_v2Target.y;
    }

    public void run() {
        while (true) {
            MoveTowardTarget();

            if (IsReachTarget())
                FindATarget();

            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }
}