class Player implements Runnable {

    String m_sName;

    vec2 m_v2Position;
    vec2 m_v2Speed;
    vec2 m_v2Target;
    Thread m_Thread;

    public Player(String sPlayerName, vec2 position) 
    {
        m_sName = sPlayerName;
        m_v2Position = new vec2(position);
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

    public void ChooseATarget()
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
                ChooseATarget();

            try {
                Thread.sleep(300);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

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