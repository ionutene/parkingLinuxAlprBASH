public class RunnableParking implements Runnable {
	private Thread t;
	private String threadName;

	RunnableParking( String name) {
		threadName = name;
		System.out.println("Creating " +  threadName );
	}

	@Override
	public void run() {
		Process.process();
	}

	public void start () {
		System.out.println("Starting " +  threadName );
		if (t == null) {
			t = new Thread (this, threadName);
			t.start ();
		}
	}
}
