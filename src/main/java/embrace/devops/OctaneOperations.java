package embrace.devops;

import org.mule.runtime.extension.api.annotation.Alias;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.MediaType;
import org.mule.runtime.extension.api.annotation.param.ParameterGroup;

import static org.mule.runtime.extension.api.annotation.param.MediaType.ANY;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import javax.net.ssl.HttpsURLConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OctaneOperations {

	private static final Logger LOGGER = LoggerFactory.getLogger(OctaneOperations.class);
	

	@MediaType(value = ANY, strict = false)
	@Alias("Get-all-defects")
	public String QueryAllDefects(@Config OctaneConfiguration octaneconfig, @Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane) throws IOException, InterruptedException {
    	LOGGER.info("QueryAllDefects entered.");
    	
		String response = null;
		response = SendGetRequest(octaneConn, Octane, "defects");
		
		return response;
	}
				
   
    
	@MediaType(value = ANY, strict = false)
	@Alias("Get-all-workitems")
	public String QueryAllWorkitems(@Config OctaneConfiguration octaneconfig, @Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane) throws IOException, InterruptedException {
    	LOGGER.info("QueryAllWorkitems entered.");
		String response = null;
		response = SendGetRequest(octaneConn, Octane, "work_items");
		
		return response;
	}

	@MediaType(value = ANY, strict = false)
	@Alias("Get-all-tests")
	public String QueryAllTests(@Config OctaneConfiguration octaneconfig, @Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane) throws IOException, InterruptedException {
    	LOGGER.info("QueryAllTests entered.");
		String response = null;
		response = SendGetRequest(octaneConn, Octane, "tests");
		
		return response;
	}


	@MediaType(value = ANY, strict = false)
	@Alias("Get-all-manual-tests")
	public String QueryAllManualTests(@Config OctaneConfiguration octaneconfig, @Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane) throws IOException, InterruptedException {
    	LOGGER.info("QueryAllManualTests entered.");
		String response = null;
		response = SendGetRequest(octaneConn, Octane, "manual_tests");
		
		return response;
	}

	
	
	@MediaType(value = ANY, strict = false)
	@Alias("Get-all-requirements")
	public String QueryAllReqs(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane) throws IOException, InterruptedException {
    	LOGGER.info("QueryAllReqs entered.");
		String response = null;
		response = SendGetRequest(octaneConn, Octane, "requirements");
		
		return response;

	}


	@MediaType(value = ANY, strict = false)
	@Alias("Get-all-tasks")
	public String QueryAllTasks(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane) throws IOException, InterruptedException {
		LOGGER.info("QueryAllTasks entered.");
		String response = null;
		response = SendGetRequest(octaneConn, Octane, "tasks");
		
		return response;

	}
	
	private String SendGetRequest(OctaneConnection octaneConn, OctaneParameters Octane, String OctaneResource) throws IOException, InterruptedException {
    	LOGGER.info("SendGetRequest entered.");
    	
    	String response = null;
    	String sharedspace = Octane.getSharedspace();
    	String workspace = Octane.getWorkspace();
		String baseURL = octaneConn.getBaseURL();
    	String Cookie = octaneConn.getCookie();
    	LOGGER.info("Got Cookie: " + Cookie);
    	
		LOGGER.info("Creating connection "+ baseURL + "/api/shared_spaces/" + sharedspace + "/workspaces/"+ workspace + "/" + OctaneResource);

    	String techPreview = Octane.getTechpreview();
    	String clientType = Octane.getClienttype();

    	
    			
    	URL url = new URL(baseURL + "/api/shared_spaces/" + sharedspace + "/workspaces/"+ workspace + "/" + OctaneResource);
    	URLConnection conn = url.openConnection();

    	LOGGER.info("Using Cookie: " + Cookie);
   	

		try {
			
	    	if(conn instanceof HttpsURLConnection){
				LOGGER.info("Processing HTTPS request");
				HttpsURLConnection https = (HttpsURLConnection) conn;
    	

		    	https.setRequestMethod("GET");
		    	https.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	https.setRequestProperty("Accept", "application/json");    	
		    	https.setRequestProperty("cookie", Cookie);;
		    	https.setRequestProperty("hpeclienttype", clientType);    	
		    	https.setRequestProperty("ALM_OCTANE_TECH_PREVIEW", techPreview);;
		
		    	LOGGER.info("ReturnCode sendGETRequest " + https.getResponseCode());
	    	}
	    	else {
				LOGGER.info("Processing HTTP request");
		    	HttpURLConnection http = (HttpURLConnection) conn;
		    	http.setRequestMethod("GET");
		    	http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	http.setRequestProperty("Accept", "application/json");    	
		    	http.setRequestProperty("cookie", Cookie);;
		    	http.setRequestProperty("hpeclienttype", clientType);    	
		    	http.setRequestProperty("ALM_OCTANE_TECH_PREVIEW", techPreview);;
		
		    	LOGGER.info("ReturnCode sendGETRequest " + http.getResponseCode());

	    	}

			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line+"\n");
	        }
	        br.close();

			response = sb.toString();

		} catch(IOException e) {
	    	LOGGER.error("Error occurred while getting resource: " + OctaneResource);
	    	e.printStackTrace();
	    	
	    	response = "Error occurred while getting resource: " + OctaneResource;
		}
		

		return response;
	}

	
	@MediaType(value = ANY, strict = false)
	@Alias("Add-defects")
	public String SubmitDefects(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("SubmitDefects entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "defects", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);
		return response;

	}
	

	
	@MediaType(value = ANY, strict = false)
	@Alias("Add-user-stories")
	public String SubmitUserStories(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("SubmitUserStories entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "stories", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}
	
	@MediaType(value = ANY, strict = false)
	@Alias("Add-tasks")
	public String Submittasks(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("Submittasks entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "tasks", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}
	
	@MediaType(value = ANY, strict = false)
	@Alias("Add-epics")
	public String SubmitEpics(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("SubmitEpics entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "epics", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}
	
	@MediaType(value = ANY, strict = false)
	@Alias("Add-features")
	public String SubmitFeatures(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("SubmitFeatures entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "features", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}
	
	@MediaType(value = ANY, strict = false)
	@Alias("Add-requirement-documents")
	public String SubmitReqDocs(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("SubmitReqDocs entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "requirement_documents", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}
	
	@MediaType(value = ANY, strict = false)
	@Alias("Add-manual-tests")
	public String SubmitManualTests(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("SubmitManualTests entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "manual_tests", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	@MediaType(value = ANY, strict = false)
	@Alias("Add-test-suites")
	public String SubmitTestssuites(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("SubmitTestssuites entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "test_suites", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	@MediaType(value = ANY, strict = false)
	@Alias("Assign-tests-to-test-suites")
	public String AssignTestsTestssuites(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("AssignTestsTestssuites entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPostRequest(octaneConn, Octane, "test_suite_link_to_tests", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	
	@MediaType(value = ANY, strict = false)
	@Alias("Update-manual-tests")
	public String UpdateManualTests(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("UpdateManualTests entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPutRequest(octaneConn, Octane, "manual_tests", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	@MediaType(value = ANY, strict = false)
	@Alias("Update-defects")
	public String UpdateDefects(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("UpdateDefects entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPutRequest(octaneConn, Octane, "defects", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	@MediaType(value = ANY, strict = false)
	@Alias("Update-work-items")
	public String UpdateWorkitems(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("UpdateWorkitems entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPutRequest(octaneConn, Octane, "work_items", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	
	@MediaType(value = ANY, strict = false)
	@Alias("Update-stories")
	public String UpdateStories(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("UpdateStories entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPutRequest(octaneConn, Octane, "stories", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	@MediaType(value = ANY, strict = false)
	@Alias("Update-features")
	public String UpdateFeatures(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("UpdateFeatures entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPutRequest(octaneConn, Octane, "features", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}

	@MediaType(value = ANY, strict = false)
	@Alias("Update-epics")
	public String UpdateEpics(@Connection OctaneConnection octaneConn, @ParameterGroup(name= "Additional properties") OctaneParameters Octane, @ParameterGroup(name = "Payload") OctanePayload OctanePayload) throws IOException, InterruptedException {
		LOGGER.info("UpdateEpics entered.");
		String response = null;
		LOGGER.info("Payload: " + OctanePayload.getPayload());
		response = SendPutRequest(octaneConn, Octane, "epics", OctanePayload.getPayload());
		LOGGER.info("Response: " + response);		
		return response;

	}
	
	private String SendPostRequest(OctaneConnection octaneConn, OctaneParameters Octane, String OctaneResource, String OctanePayload) throws IOException, InterruptedException {
		URLConnection connection = null;
    	String payload = OctanePayload;    	
    	String response = null;
    	String sharedspace = Octane.getSharedspace();
    	String workspace = Octane.getWorkspace();
		String baseURL = octaneConn.getBaseURL();
    	String Cookie = octaneConn.getCookie();

		LOGGER.info("Creating connection "+ baseURL + "/api/shared_spaces/" + sharedspace + "/workspaces/"+ workspace + "/" + OctaneResource);

    	String techPreview = Octane.getTechpreview();
    	String clientType = Octane.getClienttype();

    	
    			
    	URL url = new URL(baseURL + "/api/shared_spaces/" + sharedspace + "/workspaces/"+ workspace + "/" + OctaneResource);
    	URLConnection conn = url.openConnection();

    	LOGGER.info("Using Cookie: " + Cookie);

    	try {
			
	    	if(conn instanceof HttpsURLConnection){
				LOGGER.info("Processing HTTPS request");
		    	HttpsURLConnection https = (HttpsURLConnection) conn;
		    	https.setDoOutput(true);
		    	https.setDoInput(true);
		    	https.setRequestMethod("POST");
		    	https.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	https.setRequestProperty("Accept", "application/json");    	
		    	https.setRequestProperty("cookie", Cookie);
		    	https.setRequestProperty("ALM_OCTANE_TECH_PREVIEW", techPreview);
		    	//https.setRequestProperty("hpeclienttype", clientType);
		    	try(OutputStream os = conn.getOutputStream()){
					byte[] input = payload.getBytes("utf-8");
					os.write(input,0,input.length);
				}
				
		    	LOGGER.info("Response Code sendGETRequest: " + https.getResponseCode());
	    	}
	    	else {
				LOGGER.info("Processing HTTP request");
		    	HttpURLConnection http = (HttpURLConnection) conn;
		    	http.setDoOutput(true);
		    	http.setDoInput(true);
		    	http.setRequestMethod("POST");
		    	http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	http.setRequestProperty("Accept", "application/json");    	
		    	http.setRequestProperty("cookie", Cookie);  	
		    	http.setRequestProperty("ALM_OCTANE_TECH_PREVIEW", techPreview);
		    	//http.setRequestProperty("hpeclienttype", clientType);
		    	try(OutputStream os = conn.getOutputStream()){
					byte[] input = payload.getBytes("utf-8");
					os.write(input,0,input.length);
				}
				
		    	LOGGER.info("Response Code sendGETRequest: " + http.getResponseCode());

	    	}
	    	
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line+"\n");
	        }
	        br.close();

			response = sb.toString();

	    	
		} catch(IOException e) {
	    	LOGGER.error("Error occurred while POSTing resource: " + OctaneResource);
	    	e.printStackTrace();
	    	response = "Error occurred while POSTing resource: " + OctaneResource;
		}
		
		
	
		return response;

	}
	
	
	private String SendPutRequest(OctaneConnection octaneConn, OctaneParameters Octane, String OctaneResource, String OctanePayload) throws IOException, InterruptedException {
		URLConnection connection = null;
    	String payload = OctanePayload;    	
    	String response = null;
    	String sharedspace = Octane.getSharedspace();
    	String workspace = Octane.getWorkspace();
		String baseURL = octaneConn.getBaseURL();
    	String Cookie = octaneConn.getCookie();

		LOGGER.info("Creating connection "+ baseURL + "/api/shared_spaces/" + sharedspace + "/workspaces/"+ workspace + "/" + OctaneResource);

    	String techPreview = Octane.getTechpreview();
    	String clientType = Octane.getClienttype();

    	
    			
    	URL url = new URL(baseURL + "/api/shared_spaces/" + sharedspace + "/workspaces/"+ workspace + "/" + OctaneResource);
    	URLConnection conn = url.openConnection();

    	LOGGER.info("Using Cookie: " + Cookie);

    	try {
			
	    	if(conn instanceof HttpsURLConnection){
				LOGGER.info("Processing HTTPS request");
		    	HttpsURLConnection https = (HttpsURLConnection) conn;
		    	https.setDoOutput(true);
		    	https.setDoInput(true);
		    	https.setRequestMethod("PUT");
		    	https.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	https.setRequestProperty("Accept", "application/json");    	
		    	https.setRequestProperty("cookie", Cookie);
		    	https.setRequestProperty("ALM_OCTANE_TECH_PREVIEW", techPreview);
		    	//https.setRequestProperty("hpeclienttype", clientType);
		    	try(OutputStream os = conn.getOutputStream()){
					byte[] input = payload.getBytes("utf-8");
					os.write(input,0,input.length);
				}
				
		    	LOGGER.info("Response Code sendGETRequest: " + https.getResponseCode());
	    	}
	    	else {
				LOGGER.info("Processing HTTP request");
		    	HttpURLConnection http = (HttpURLConnection) conn;
		    	http.setDoOutput(true);
		    	http.setDoInput(true);
		    	http.setRequestMethod("PUT");
		    	http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		    	http.setRequestProperty("Accept", "application/json");    	
		    	http.setRequestProperty("cookie", Cookie);  	
		    	http.setRequestProperty("ALM_OCTANE_TECH_PREVIEW", techPreview);
		    	//http.setRequestProperty("hpeclienttype", clientType);
		    	try(OutputStream os = conn.getOutputStream()){
					byte[] input = payload.getBytes("utf-8");
					os.write(input,0,input.length);
				}
				
		    	LOGGER.info("Response Code sendGETRequest: " + http.getResponseCode());

	    	}
	    	
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        StringBuilder sb = new StringBuilder();
	        String line;
	        while ((line = br.readLine()) != null) {
	            sb.append(line+"\n");
	        }
	        br.close();

			response = sb.toString();

	    	
		} catch(IOException e) {
	    	LOGGER.error("Error occurred while POSTing resource: " + OctaneResource);
	    	e.printStackTrace();
	    	response = "Error occurred while POSTing resource: " + OctaneResource;
		}
		
		
	
		return response;

	}
	

}
