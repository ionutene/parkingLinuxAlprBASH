package utilitary;

import com.jcraft.jsch.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class LinuxCommander{

	private static Logger logger = LogManager.getLogger(LinuxCommander.class);

	public static List<String>  requestPlates(String command) {
		try {

			List<String> result = new ArrayList<>();

			JSch jsch = new JSch();

			Session session = jsch.getSession(Variables.getLinuxUser(), Variables.getLinuxHost(), Variables.getLinuxPort());

			// username and password will be given via UserInfo interface.
			UserInfo ui = new MyUserInfo();
			session.setUserInfo(ui);
			session.connect();

			Channel channel = session.openChannel("exec");
			((ChannelExec) channel).setCommand(command);

			channel.setInputStream(null);

			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();

			channel.connect();

			byte[] tmp = new byte[1024];

			int maxNumber=1;
			int start=0;


			while (start<maxNumber) {
				while (start<maxNumber&&in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String tempValue = new String(tmp, 0, i);
						logger.debug(tempValue);
					result.add(tempValue);
					start++;
						logger.debug(start);
				}
				if (channel.isClosed()) {
					logger.info("exit-status: " + channel.getExitStatus());
					break;
				}
				try {
					Thread.sleep(500);
				} catch (Exception ee) {
					ee.printStackTrace();
				}
			}

			channel.disconnect();
			session.disconnect();

			logger.debug(result.size() + " cycles");
			logger.debug(result.get(0) + " cycles");
			return Arrays.stream(result.get(0)
					.split("\\r?\\n"))
					.map(String::trim)
					.filter(c -> c.contains("confidence"))
					.collect(Collectors.toList());

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
		}
		return new ArrayList<>();
	}

	public static class MyUserInfo implements UserInfo {
		public String getPassword() {
			return Variables.getLinuxPassword();
		}

		public boolean promptYesNo(String str) {
			str = "Yes";
			return true;
		}

		public String getPassphrase() {
			return null;
		}

		public boolean promptPassphrase(String message) {
			return true;
		}

		public boolean promptPassword(String message) {
			return true;
		}

		public void showMessage(String message) {

		}

	}
}
