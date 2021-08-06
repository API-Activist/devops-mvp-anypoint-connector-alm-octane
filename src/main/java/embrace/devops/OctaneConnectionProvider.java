package embrace.devops;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionProvider;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OctaneConnectionProvider implements ConnectionProvider<OctaneConnection> {

	@ParameterGroup(name="Connection")
	OctaneConfiguration customConfig;
	private static final Logger LOGGER = LoggerFactory.getLogger(OctaneConnectionProvider.class);
	@Override
	public OctaneConnection connect() throws ConnectionException {
		// TODO Auto-generated method stub
		LOGGER.info("Initializing connection");
		return new OctaneConnection(customConfig);
	}

	@Override
	public void disconnect(OctaneConnection connection) {
		// TODO Auto-generated method stub
		LOGGER.info("Destrioying the Connection");
		connection.invalidate();
	}

	@Override
	public ConnectionValidationResult validate(OctaneConnection connection) {
		// TODO Auto-generated method stub
		URLConnection con = connection.getConnection();
		ConnectionValidationResult result = null;
		if(con instanceof HttpsURLConnection) {
			LOGGER.info("Invalidating HTTPS Connection");
			try {
				result = (((HttpsURLConnection) con).getResponseCode() == 200) ? ConnectionValidationResult.success() : ConnectionValidationResult.failure("HTTPS Test Failed", new Exception());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.info("Invalidated HTTPS Connection");
		}
		else {
			LOGGER.info("Invalidating HTTP Connection");
			try {
				result = (((HttpURLConnection) con).getResponseCode() == 200) ? ConnectionValidationResult.success() : ConnectionValidationResult.failure("HTTP Test Failed", new Exception());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			LOGGER.info("Invalidated HTTP Connection");
		}
		
		return result;
	}

}
