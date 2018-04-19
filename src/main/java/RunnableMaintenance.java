import utilitary.Utils;

public class RunnableMaintenance implements Runnable {
	private Thread t;
	private String threadName;
	private boolean valid;

	RunnableMaintenance(String name, boolean valid) {
		threadName = name;
		this.valid = valid;
		System.out.println("#############################");
		System.out.println("#   Creating " + threadName + "     #");
	}

	@Override
	public void run() {
		Utils.processFrame(threadName, valid);
	}

	public void start() {

		System.out.println("#   Starting " + threadName + "     #");
		System.out.println("#############################");
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}
