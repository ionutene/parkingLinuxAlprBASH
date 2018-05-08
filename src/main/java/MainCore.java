import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilitary.JsonDeserialize;
import utilitary.Variables;

import java.util.Map;
import java.util.Scanner;

public class MainCore implements Runnable {
	private static Logger logger = LogManager.getLogger(MainCore.class);

	@Override
	public void run() {
		Process.process();
	}


	public static void main(String[] args) throws Exception {
		Map<String, String> hMap = JsonDeserialize.deserialize("Linux");


		Variables.VariablesBuilder builder = new Variables.VariablesBuilder();

		builder.temporaryPath("TemporaryPath")
				.linuxHost(hMap.get("LinuxHost"))
				.linuxPassword(hMap.get("LinuxPassword"))
				.linuxUser(hMap.get("LinuxUser"))
				.linuxPort(Integer.parseInt(hMap.get("LinuxPort")))
				.raspberryUser(hMap.get("RaspberryUser"))
				.raspberryPassword(hMap.get("RaspberryPassword"))
				.raspberryHost(hMap.get("RaspberryHost"))
				.raspberryPort(Integer.parseInt(hMap.get("RaspberryPort")))
				.streamLink(hMap.get("StreamLink"))
				.platesForResult(Integer.parseInt(hMap.get("PlatesForResult")))

				.build();

		logger.debug(builder.toString());
		Scanner sc=new Scanner(System.in);

		Process.openBarrier();

		/*String videoStream = "cvlc -vvv v4l2:// --sout '#transcode{vcodec=mjpg, vb=2000, width=320,height=240,venc=ffmpeg}:duplicate{dist=standard {access=http,mux=mpjpeg,dst=192.168.0.100:5050/video.mpjpeg}'";
		logger.debug("Starting stream at:" + Variables.getStreamLink());
		Process.startStreaming(videoStream);*/


	//	System.out.println(DBrequest.existsInDB("B","888","BBB"));

			System.out.println("Enter y to continue / others to stop");
		while (/*sc.next().equals("y")*/true){
			logger.debug("Start processing");
			Process.process();
			logger.debug("Another shot");
			//System.out.println("Enter y to continue / others to stop");
		}

		/*while (sc.next().equals("y")){
			logger.info("open barrier");
			Process.openBarrier();

		}*/

		//sc.close();




	}
}
