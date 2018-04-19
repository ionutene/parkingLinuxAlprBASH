import org.apache.logging.log4j.LogManager;
import org.opencv.core.Core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestCam {
	private static org.apache.logging.log4j.Logger logger = LogManager.getLogger(TestCam.class);

	public static void main(String[] argv) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

		String webURL = "http://10.23.14.72:5050/video.mpjpeg";
		try {

			readInputStreamIntoMat(getInputStream(webURL));

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.info("dfs");
	}


	public static InputStream getInputStream(String webURL) throws IOException, InterruptedException {

		URL url1 = new URL(webURL);
		URLConnection conn1 = url1.openConnection();
		InputStream is = conn1.getInputStream();

		byte[] buffer = new byte[is.available()];


			Thread.sleep(100);
			is.read(buffer);


		int i = 0;

		for (byte b : buffer) {

			if (i % 10 == 0) {
				System.out.println();
			}

			System.out.printf("%02x ", b);

			i++;
		}


		System.out.println();
		return is;
	}


	private static void readInputStreamIntoMat(InputStream inputStream) throws IOException {
		// Read into byte-array


		byte[] temporaryImageInMemory = readStream(inputStream);

		Files.write(Paths.get(
				"C:\\APPS\\workspaceIntellij\\Hackaton\\parking\\parkingLinuxAlprBASH\\src\\main\\staging\\nou.jpg"),
				temporaryImageInMemory);
		//		Highgui.imdecode(new MatOfByte(temporaryImageInMemory), Highgui.IMREAD_GRAYSCALE);
		// Decode into mat. Use any IMREAD_ option that describes your image appropriately
		//Mat outputImage = Highgui.imdecode(new MatOfByte(temporaryImageInMemory), Highgui.IMREAD_GRAYSCALE);

		//		return outputImage;
	}

	private static byte[] readStream(InputStream stream) throws IOException {
		// Copy content of the image to byte-array
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		int nRead;
		byte[] data = new byte[16384];
		byte[] temporaryImageInMemory = new byte[16384];

		long millisCurrent = System.currentTimeMillis();

		while (System.currentTimeMillis() - millisCurrent > 5000) {
			while ((nRead = stream.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}

			buffer.flush();
			temporaryImageInMemory = buffer.toByteArray();
			buffer.close();
			stream.close();
		}
		return temporaryImageInMemory;
	}


}
