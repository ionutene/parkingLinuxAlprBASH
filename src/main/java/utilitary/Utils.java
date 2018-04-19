package utilitary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.Java2DFrameConverter;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



//Maybe will not be needed at all
public class Utils {
	private static Logger logger = LogManager.getLogger(Utils.class);


	public static void processFrame(String path, boolean valid) {
		LocalDateTime today = LocalDateTime.now();
		DateTimeFormatter formatToday = DateTimeFormatter.ofPattern("MM_dd_yy_HH;mm");
		path += (valid ? "_GOT" : "_MISSING");

		String pathFileName = today.format(formatToday) + path + ".jpg";
		try {
			//video horror
			String link = Variables.getStreamLink();
			//			String LINK = "http://10.23.14.72:5050/video.mpjpeg";
			FFmpegFrameGrabber frameGrabber = new FFmpegFrameGrabber(link);
			logger.info("getting frame from: " + link);

			frameGrabber.start();

			Frame i = frameGrabber.grab();
			Java2DFrameConverter java2DFrameConverter = new org.bytedeco.javacv.Java2DFrameConverter();

			String PATH_TEMPORARY = "src/main/staging/";
			ImageIO.write(java2DFrameConverter.convert(i), "jpg", new File(PATH_TEMPORARY + pathFileName));
			logger.info("written file : " + PATH_TEMPORARY + pathFileName);

			frameGrabber.stop();

		} catch (IOException | FrameGrabber.Exception e) {
			e.printStackTrace();
		}

	}
}
