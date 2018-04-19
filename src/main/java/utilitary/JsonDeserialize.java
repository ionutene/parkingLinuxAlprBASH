package utilitary;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

public class JsonDeserialize {
	private static Logger logger = LogManager.getLogger(JsonDeserialize.class);

	public static HashMap<String, String> deserialize(String groupName) {

		try {

			JsonParser jsonParser = new JsonParser();
			String deserializePath = "src/main/resources/Variables.json";
			JsonElement jsonElement = jsonParser.parse(new FileReader(new File(deserializePath)));
			if (jsonElement.isJsonObject()) {
				String jsonParent = "Variables";
				JsonObject jsonObject = jsonElement.getAsJsonObject().getAsJsonObject(jsonParent);
				JsonObject jsonGroup = jsonObject.getAsJsonObject(groupName);

				HashMap<String, String> result = new ObjectMapper().readValue(String.valueOf(jsonGroup), HashMap.class);
				logger.debug("Deserialized: " + groupName + " as " + jsonParent + " from " + deserializePath);
				return result;
			}

		} catch (Exception e) {
			logger.error("Deserialize problem ", e);
		}

		return null;
	}
}
