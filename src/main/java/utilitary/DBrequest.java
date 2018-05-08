package utilitary;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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

			/*String driver = props.getProperty("jdbc.driver");
			if (driver != null) {
				Class.forName("com.mysql.cj.jdbc.Drive");
			}*/

			MysqlDataSource dataSource = new MysqlDataSource();
			dataSource.setUser("root");
			dataSource.setPassword("root");
			dataSource.setServerName("192.168.0.101");
			dataSource.setPort(3306);
			dataSource.setDatabaseName("parkingDB");

			try (Connection conn = dataSource.getConnection(); Statement stmt = conn.createStatement()) {

				ResultSet rs = stmt.executeQuery(
						"SELECT CONCAT('_',plate_reg,'_',plate_no,'_',plate_chr) AS result FROM parkingDB.plate  WHERE plate_reg LIKE '" + plateRegion
								+ "%' AND plate_no LIKE '%" + plateNo + "' AND plate_chr='" + plateLetters + "';");

				if (!rs.next() ) {
					logger.debug("plate not found in DB");
					return "NONE";
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
