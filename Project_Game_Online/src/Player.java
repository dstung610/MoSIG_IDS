import java.lang.Math;

class Player {

    String m_sName;

    vec2 m_v2Position;
    vec2 m_v2Speed;
    vec2 m_v2Target;
    Thread m_Thread;

    public Player(String sPlayerName, vec2 position) 
    {
        m_sName = sPlayerName;
        m_v2Position = new vec2(position);
        ChooseATarget();
        m_Thread = new Thread(this);
        m_Thread.start();
    }

    public void MoveTowardTarget() {
        if (vec2.getLenght(m_v2Position, m_v2Target) < m_v2Speed.getLenght())
            m_v2Position = m_v2Target;
        else {
            m_v2Position.x += m_v2Speed.x;
            m_v2Position.y += m_v2Speed.y;
        }
    }

    public void ChooseARandomTarget()
    {
        m_v2Target = new vec2((int)(Math.random() * (GameUtils.s_SettingMapSize + 1)), 
                               (int)(Math.random() * (GameUtils.s_SettingMapSize + 1)));
        m_v2Speed = new vec2(m_v2Target.x - m_v2Position.x, m_v2Target.y - m_v2Position.y);
        float l = (float)(0.5 * m_v2Speed.getLenght());
        if (l == 0)
            l = 1;
        m_v2Speed.x /= l;
        m_v2Speed.y /= l;

        GameUtils.LOG(m_sName + " goes to target " + m_v2Target.toString() + " in speed " + m_v2Speed.toString());
    }

    public void SetATarget(vec2 target)
    {
        m_v2Target = target;
        m_v2Speed = new vec2(m_v2Target.x - m_v2Position.x, m_v2Target.y - m_v2Position.y);
        float l = (float)(0.5 * m_v2Speed.getLenght());
        if (l == 0)
            l = 1;
        m_v2Speed.x /= l;
        m_v2Speed.y /= l;

        GameUtils.LOG(m_sName + " goes to target " + m_v2Target.toString() + " in speed " + m_v2Speed.toString());
    }


    public boolean IsReachTarget()
    {
        return m_v2Position.x == m_v2Target.x && m_v2Position.y == m_v2Target.y;
    }

    // public void run() {
    //     while (true) {
    //         MoveTowardTarget();

    //         if (IsReachTarget())
    //             ChooseATarget();

    //         try {
    //             Thread.sleep(300);
    //         } catch (Exception e) {
    //             GameUtils.LOG(e.getMessage());
    //         }
    //     }
    // }

    public vec2 getPosition() {
        return m_v2Position;
    }

    public vec2 getSpeed()
    {
        return m_v2Speed;
    }

    public String getName()
    {
        return m_sName;
    }
}