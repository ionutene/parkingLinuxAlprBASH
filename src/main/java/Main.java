import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main implements Runnable {
	private static Logger logger = LogManager.getLogger(Main.class);
	public static String PATH_TEMPORARY = "src/main/staging/";
	public static String PATH_VISIBILITY = "src/main/visibility/";
	public static String LINK = "http://10.23.14.72:5050/video.mpjpeg";


	@Override
	public void run() {
		Process.process();
	}


	public static void main(String[] args) {

		Process.process();
/*
		RunnableParking tt1 = new RunnableParking ("Thread1");
		RunnableParking tt2 = new RunnableParking ("Thread2");
		Scanner keyboard = new Scanner(System.in);
		boolean value = true;



			while(value){
				tt1.start ();


				try {
					logger.debug("Sleep 5000");
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				tt2.start ();

				System.out.println("enter an integer");
			//	int i = keyboard.nextInt();
				value = (keyboard.nextInt() == 1);
			}*/

	}
}
