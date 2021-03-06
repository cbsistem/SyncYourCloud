package api;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.jsonextract.JsonExtractionException;
import com.dropbox.client2.jsonextract.JsonMap;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.WebAuthSession;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.simple.JSONObject;

public class DriveGoogle implements IntDrive {
	
	private static String app_key,app_secret,app_redirect_uri;
	
	public String authUrl;
	private String uid;
	
	private GoogleAuthorizationCodeFlow flow;
	private HttpTransport httpTransport;
	private JsonFactory jsonFactory;
	private Drive service;
	private GoogleCredential credential;
	
	public static void init(String key, String secret, String redirect_uri){
		app_key = key;
		app_secret = secret;
		app_redirect_uri = redirect_uri;
	}
	
	public DriveGoogle() {
		httpTransport = new NetHttpTransport();
	    jsonFactory = new JacksonFactory();
	   
	    flow = new GoogleAuthorizationCodeFlow.Builder(
	        httpTransport, jsonFactory, app_key, app_secret, Arrays.asList(DriveScopes.DRIVE))
	        .setAccessType("offline").setApprovalPrompt("force").build();
	    
	    authUrl = flow.newAuthorizationUrl().setRedirectUri(app_redirect_uri).build();
	    
	}
	public DriveGoogle(JsonMap config) {
		try {
			this.uid = config.get("uid").expectString();
			//access = new AccessTokenPair(config.get("key").expectString(),config.get("secret").expectString());
		} catch (JsonExtractionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}/*
		session = new WebAuthSession(DriveDropBox.appKey, Session.AccessType.DROPBOX, access);
		this.api = new DropboxAPI<WebAuthSession>(session);
		
		try {
			accountInfos = this.api.accountInfo();
		} catch (DropboxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
	public Boolean validateToken(String code){
	    GoogleTokenResponse response;
		try {
			response = new GoogleAuthorizationCodeTokenRequest(httpTransport,jsonFactory,app_key,
			        app_secret,code,app_redirect_uri).execute();
			credential = new GoogleCredential().setFromTokenResponse(response);
			//Create a new authorized API client
		    service = new Drive.Builder(httpTransport, jsonFactory, credential).build();
		    uid = credential.getServiceAccountId();
		    
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void uploadFile(String path, OutputStream file) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entry getEntryInfo(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Entry> getEntries(String path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addDrive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeDrive() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IntDrive listDrive() {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject savedState() {
		JSONObject save = new JSONObject();
		save.put("type", "googledrive");
		save.put("uid", this.uid);
		save.put("token", credential.getAccessToken());
		return save;
	}

	@Override
	public String getNiceName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNiceSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sync getSync() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSync(String localpath) {
		// TODO Auto-generated method stub
		
	}

}
