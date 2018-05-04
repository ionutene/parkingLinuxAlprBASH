package utilitary;

public class Variables {
	//	Variables variables;

	private static String temporaryPath;
	private static String streamLink;
	private static String linuxHost;
	private static int linuxPort;
	private static String linuxPassword;
	private static String linuxUser;
	private static int platesForResult;
	private static String raspberryUser;
	private static String raspberryPassword;

	private Variables(VariablesBuilder builder) {
		this.temporaryPath = builder.getTemporaryPath();
		this.streamLink = builder.getStreamLink();
		this.linuxHost = builder.getLinuxHost();
		this.linuxPort = builder.getLinuxPort();
		this.linuxPassword = builder.getLinuxPassword();
		this.linuxUser = builder.getLinuxUser();
		this.platesForResult = builder.getPlatesForResult();
		this.raspberryUser = builder.getRaspberryUser();
		this.raspberryPassword = builder.raspberryPassword;
	}

	public static String getTemporaryPath() {
		return temporaryPath;
	}

	public static String getStreamLink() {
		return streamLink;
	}

	public static String getLinuxHost() {
		return linuxHost;
	}

	public static int getLinuxPort() {
		return linuxPort;
	}

	public static String getLinuxPassword() {
		return linuxPassword;
	}

	public static String getLinuxUser() {
		return linuxUser;
	}

	public static int getPlatesForResult() {
		return platesForResult;
	}

	public static class VariablesBuilder {
		private String temporaryPath;
		private String streamLink;
		private String linuxHost;
		private int linuxPort;
		private String linuxPassword;
		private String linuxUser;
		private int platesForResult;
		private String raspberryUser;
		private String raspberryPassword;

		public VariablesBuilder() {
		}

		public Variables build() {
			return new Variables(this);
		}

		public VariablesBuilder platesForResult(int platesForResult) {
			this.platesForResult = platesForResult;
			return this;
		}

		public VariablesBuilder temporaryPath(String temporaryPath) {
			this.temporaryPath = temporaryPath;
			return this;
		}

		public VariablesBuilder streamLink(String streamLink) {
			this.streamLink = streamLink;
			return this;
		}

		public VariablesBuilder linuxHost(String linuxHost) {
			this.linuxHost = linuxHost;
			return this;
		}

		public VariablesBuilder linuxPort(int linuxPort) {
			this.linuxPort = linuxPort;
			return this;
		}

		public VariablesBuilder linuxPassword(String linuxPassword) {
			this.linuxPassword = linuxPassword;
			return this;
		}

		public VariablesBuilder linuxUser(String linuxUser) {
			this.linuxUser = linuxUser;
			return this;
		}

		public VariablesBuilder raspberryUser(String raspberryUser) {
			this.raspberryUser = raspberryUser;
			return this;
		}

		public VariablesBuilder raspberryPassword(String raspberryPassword) {
			this.raspberryPassword = raspberryPassword;
			return this;
		}


		String getTemporaryPath() {
			return temporaryPath;
		}

		String getStreamLink() {
			return streamLink;
		}

		String getLinuxHost() {
			return linuxHost;
		}

		int getLinuxPort() {
			return linuxPort;
		}

		String getLinuxPassword() {
			return linuxPassword;
		}

		String getLinuxUser() {
			return linuxUser;
		}

		String getRaspberryUser() {
			return raspberryUser;
		}

		String getRaspberryPassword() {
			return raspberryPassword;
		}

		int getPlatesForResult() {
			return platesForResult;
		}


		@Override
		public String toString() {
			return "VariablesBuilder{" + "temporaryPath='" + temporaryPath + '\'' + ", streamLink='" + streamLink + '\''
					+ ", linuxHost='" + linuxHost + '\'' + ", linuxPort=" + linuxPort + ", linuxPassword='"
					+ linuxPassword + '\'' + ", linuxUser='" + linuxUser + '\'' + '}';
		}


	}

}
