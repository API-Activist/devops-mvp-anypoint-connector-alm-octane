package embrace.devops;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OctaneConnection {

	URLConnection conn = null;
	String Cookie = null;
	String baseURL = null;
	private static final Logger LOGGER = LoggerFactory.getLogger(OctaneConnection.class);

	public OctaneConnection(OctaneConfiguration octaneconfig) {
		conn = createConnection(octaneconfig.getProtocol(), octaneconfig.getHost(), octaneconfig.getPort(), octaneconfig.getClientid(), octaneconfig.getClientsecret());
	}

	private URLConnection createConnection(String protocol, String host, String port, String clientid,
			String clientsecret) {
		
		URLConnection connection = null;
    	String payload = "{\"client_id\": \"" + clientid + "\",\"client_secret\": \"" + clientsecret + "\"}";    	
		String urlProtocol = "https".equals(protocol) ? "https://" : "http://";
		String urlPort = port.isEmpty() ? "" : ":" + port;
		baseURL = urlProtocol + host + urlPort;
		LOGGER.info("Creating connection "+ urlProtocol + host + urlPort + "/authentication/sign_in");

		try {
			connection = new URL(urlProtocol + host + urlPort + "/authentication/sign_in").openConnection();
	    	LOGGER.info("Connection created successfully with"+ urlProtocol + host + urlPort);

		} catch(IOException e) {
	    	LOGGER.error("Error occurred while creating connetion");
	    	e.printStackTrace();
		}
		
		LOGGER.info("Logging into ALM Octane "+ urlProtocol + host + urlPort + "/authentication/sign_in");

		try {
			
	    	if(connection instanceof HttpsURLConnection){
				LOGGER.info("Processing HTTPS request");
		    	HttpsURLConnection https = (HttpsURLConnection) connection;
		    	https.setDoOutput(true);
		    	https.setDoInput(true);
		    	https.setRequestMethod("POST");
		    	https.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	https.setRequestProperty("Accept", "application/json");    	
		    	try(OutputStream os = connection.getOutputStream()){
					byte[] input = payload.getBytes("utf-8");
					os.write(input,0,input.length);
				}
				
				System.out.println(https.getResponseCode());
	    	}
	    	else {
				LOGGER.info("Processing HTTP request");
		    	HttpURLConnection http = (HttpURLConnection) connection;
		    	http.setDoOutput(true);
		    	http.setDoInput(true);
		    	http.setRequestMethod("POST");
		    	http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	http.setRequestProperty("Accept", "application/json");    	
		    	try(OutputStream os = connection.getOutputStream()){
					byte[] input = payload.getBytes("utf-8");
					os.write(input,0,input.length);
				}
				
				System.out.println(http.getResponseCode());

	    	}
	    	
	    	//Extract Cookie 
	    	Cookie = extractCookie(connection);
	    	
	    	LOGGER.info("Cookie extracted: " + Cookie);
	    	
		} catch(IOException e) {
	    	LOGGER.error("Error occurred while logging into ALM Octane");
	    	e.printStackTrace();
		}
		
		
		
		
		return "https".equals(protocol) ? (HttpsURLConnection) connection : (HttpURLConnection) connection;
		//return Cookie;
	}

	
	public URLConnection getConnection() {
		LOGGER.info("Connection Requested in getConnection()");
		return this.conn;
	}
	
	public String getCookie() {
		LOGGER.info("Cookie Requested in getCookie()");
		return this.Cookie;
	}
	
	public String getBaseURL() {
		LOGGER.info("URL Requested in getBaseURL()");
		return this.baseURL;
	}
	
	
	
	public void invalidate() {
		if(this.conn != null) {
			if(conn instanceof HttpsURLConnection) {
				LOGGER.info("Invalidating HTTPS Connection");
				((HttpsURLConnection) conn).disconnect();
				LOGGER.info("Invalidated HTTPS Connection");
			}
			else {
				LOGGER.info("Invalidating HTTP Connection");
				((HttpURLConnection) conn).disconnect();
				LOGGER.info("Invalidated HTTP Connection");
			}
		}
	}

	private String extractCookie(URLConnection connection) {
        Map<String, List<String>> headerFields = connection.getHeaderFields();
        String LWSSO = null;
        String allCookies = "";
        Set<String> headerFieldsSet = headerFields.keySet();
        Iterator<String> hearerFieldsIter = headerFieldsSet.iterator();
         
        while (hearerFieldsIter.hasNext()) {
             
             String headerFieldKey = hearerFieldsIter.next();
              
             if ("Set-Cookie".equalsIgnoreCase(headerFieldKey)) {
                  
                 List<String> headerFieldValue = headerFields.get(headerFieldKey);
                  
                 for (String headerValue : headerFieldValue) {
                      
                    System.out.println("Cookie Found...");
                      
                    String[] fields = headerValue.split(";\\s*");
 
                    String cookieValue = fields[0];
                    String expires = null;
                    String path = null;
                    String domain = null;
                    boolean secure = false;
                     
                    // Parse each field
                    for (int j = 1; j < fields.length; j++) {
                        if ("secure".equalsIgnoreCase(fields[j])) {
                            secure = true;
                        }
                        else if (fields[j].indexOf('=') > 0) {
                            String[] f = fields[j].split("=");
                            if ("expires".equalsIgnoreCase(f[0])) {
                                expires = f[1];
                            }
                            else if ("domain".equalsIgnoreCase(f[0])) {
                                domain = f[1];
                            }
                            else if ("path".equalsIgnoreCase(f[0])) {
                                path = f[1];
                            }
                        }
                         
                    }
                     
                    if (allCookies.isEmpty()) {
                    	allCookies = cookieValue;
                    } else {
                    	allCookies = allCookies + ";" + cookieValue;
                    }
                    
                    System.out.println("cookieValue:" + cookieValue);
                    System.out.println("expires:" + expires);
                    System.out.println("path:" + path);
                    System.out.println("domain:" + domain);
                    System.out.println("secure:" + secure);
                    if (cookieValue.contains("LWSSO_COOKIE_KEY")) {
                    	LWSSO = cookieValue;
                    }
                    System.out.println("*****************************************");
             
    
                 }
                  
             }
             
        }
        
        return LWSSO;

	}

}
