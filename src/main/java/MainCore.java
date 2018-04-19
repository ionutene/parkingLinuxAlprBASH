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


	public static void main(String[] args) {
		Map<String, String> hMap = JsonDeserialize.deserialize("Linux");


		Variables.VariablesBuilder builder = new Variables.VariablesBuilder();

		builder.temporaryPath("TemporaryPath")
				.linuxHost(hMap.get("LinuxHost"))
				.linuxPassword(hMap.get("LinuxPassword"))
				.linuxUser(hMap.get("LinuxUser"))
				.linuxPort(Integer.parseInt(hMap.get("LinuxPort")))
				.streamLink(hMap.get("StreamLink"))
				.platesForResult(Integer.parseInt(hMap.get("PlatesForResult")))
				.build();

		logger.debug(builder.toString());
		Scanner sc=new Scanner(System.in);

		System.out.println("Enter y to continue / others to stop");
		while (sc.next().equals("y")){
			Process.process();
		}

		sc.close();




	}
}
