import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utilitary.DBrequest;
import utilitary.LinuxCommander;
import utilitary.Utils;

import java.util.List;

public class Process {
	private static Logger logger = LogManager.getLogger(Process.class);

	private static String plateRegion;
	private static String plateNo;
	private static String plateLetters;



	public static void process() {
		//utilitary.LinuxCommander.requestPlates("wget http://plates.openalpr.com/h786poj.jpg");
		float maxConfidence = 0;
		String winningPlate;
		try {
			String command = "alpr -c eu -n 5 http://10.23.14.72:5050/video.mpjpeg";
			//	String command ="ls";
			List<String> plateCandidates = LinuxCommander.requestPlates(command);
			if (plateCandidates != null) {
				for (String plateResult : plateCandidates) {

					float confidence =
							Float.parseFloat(plateResult.substring(plateResult.indexOf("confidence") + 12).trim());
					String plate = plateResult.substring(1, plateResult.indexOf("confidence")).trim();

					logger.debug("Detected |" + plate + "| with |" + confidence + "|");

					processPlate(plate);
					logger.debug("Got : " + plateRegion + plateNo + plateLetters);

					String interogatePlate = DBrequest.existsInDB(plateRegion, plateNo, plateLetters);
					logger.debug("Got interogatePlate : " + interogatePlate);

					if (interogatePlate != null) {

						logger.info("found plate and opening barrier");

						Utils.processFrame( interogatePlate, true);
							return;


					} else if ((maxConfidence < confidence) && plateRegion != null && plateNo != null
							&& plateLetters != null) {
						winningPlate = "_" + plateRegion + "_" + plateNo + "_" + plateLetters;
						maxConfidence = confidence;
						logger.info(String.format("%-15s%-8f\n", winningPlate, confidence));

					}


				}
			//	Utils.processFrame(winningPlate,false);
				if (plateCandidates.size() == 0) {
					logger.info("No plate detected!");
				}


			} else {
				logger.info("No plate detected");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	private static void processPlate(String plate) {
		if (plate.substring(0, 1).equals("B")) {
			logger.debug("Found region as Bucharest ");
			plateRegion = plate.substring(0, 1).toUpperCase();
			noPlate(plate.substring(1));
		} else if (Character.isLetter(plate.charAt(0)) && Character.isLetter(plate.charAt(1))) {
			logger.debug("Found Country plate");
			plateRegion = plate.substring(0, 2).toUpperCase();
			noPlate(plate.substring(2));
		} else if (Character.isDigit(plate.charAt(0))) {
			if (Character.isDigit(plate.charAt(1)) && Character.isDigit(plate.charAt(2))) {
				plateRegion = "B";
				logger.debug("Default region as Bucharest");
				noPlate(plate.substring(2));
			} else {
				noPlate(plate);
			}
		} else {
			plateRegion = "";
		}
	}

	private static void noPlate(String plate) {
		plate = plate.replace("O", "0");
		if (Character.isDigit(plate.charAt(0)) && Character.isDigit(plate.charAt(1)) && Character
				.isDigit(plate.charAt(2))) {
			plateNo = plate.substring(0, 3);
			lastThreeLetters(plate.substring(3));
		} else if (Character.isDigit(plate.charAt(0)) && Character.isDigit(plate.charAt(1))) {
			plateNo = plate.substring(0, 2);
			lastThreeLetters(plate.substring(2));
		} else if (Character.isDigit(plate.charAt(0))) {
			plateNo = plate.substring(0, 1);
			lastThreeLetters(plate.substring(1));
		} else {
			plateNo = "";
			lastThreeLetters(plate);
		}
	}

	private static void lastThreeLetters(String plate) {
		plate = plate.replace("0", "O");
		plate = plate.replace("5", "S");
		if (plate.length() <= 3) {
			plateLetters = plate.toUpperCase();
		} else if (Character.isLetter(plate.charAt(0)) && Character.isLetter(plate.charAt(1)) && Character
				.isLetter(plate.charAt(2))) {
			plateLetters = plate.substring(0, 3).toUpperCase();
		} else
			plateLetters = "";
	}
}