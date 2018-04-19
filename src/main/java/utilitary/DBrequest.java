package utilitary;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;


public class DBrequest {
	private static Logger logger = LogManager.getLogger(DBrequest.class);

	public static String existsInDB(String plateRegion, String plateNo, String plateLetters)
			throws ClassNotFoundException {
		try {
			Properties props = new Properties();
			props.setProperty("useSSL", "false");
			props.setProperty("autoReconnect", "true");
			props.setProperty("useUnicode", "true");
			props.setProperty("useJDBCCompliantTimezoneShift", "true");
			props.setProperty("useLegacyDatetimeCode", "false");
			props.setProperty("serverTimezone", "UTC");

			FileInputStream in = new FileInputStream("src/main/resources/db.properties");

			props.load(in);
			in.close();

			String driver = props.getProperty("jdbc.driver");
			if (driver != null) {
				Class.forName("com.mysql.jdbc.Driver");
			}

			try (Connection conn = DriverManager
					.getConnection(props.getProperty("jdbc.url"), props.getProperty("jdbc.username"),
							props.getProperty("jdbc.password")); Statement stmt = conn.createStatement()) {

				ResultSet rs = stmt.executeQuery(
						"SELECT CONCAT('_',plate_region,'_',plate_no,'_',plate_letters) AS result FROM test.plate  WHERE plate_region LIKE '" + plateRegion
								+ "%' AND plate_no LIKE '%" + plateNo + "' AND plate_letters='" + plateLetters + "';");

				if (!rs.next() ) {
					logger.debug("plate not found in DB");
					return null;
				}else{
					//	rs.next();
					return rs.getString("result");
				}

			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Error in DB initial ", e.getMessage());

				throw e;

			}

		} catch (SQLException | IOException e) {
			e.printStackTrace();
			logger.error("Error in DB ", e.getMessage());
			return null;
		}

	}
}
