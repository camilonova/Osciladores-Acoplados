public class Hilo extends Thread {
	Programa parent;
	public static final int PAUSE = 0;
	public static final int STEP = 1;
	public static final int RUN = 2;
	int msg;
	boolean pause;

	public Hilo(Programa arg0){
		msg = 0;
		pause = true;
		parent = arg0;
	}

	public void run(){
		long time = System.currentTimeMillis();
		do
		{
			int m = getMsg();
			parent.canvas.mover();
			if(m == 1)
				pause = true;
			try
			{
				Thread.sleep(Math.max(30L, time - System.currentTimeMillis()));
			}
			catch(InterruptedException e)
			{
				return;
			}
		} while(true);
	}

	public synchronized int getMsg()
	{
		while(pause) 
			try
			{
				wait(1000L);
			}
			catch(InterruptedException interruptedexception) { }
		return msg;
	}

	public synchronized void putMsg(int m)
	{
		msg = m;
		pause = msg != 2 ? msg != 1 : false;
		notify();
	}
}