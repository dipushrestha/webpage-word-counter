package com.lft;

import java.net.URL;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public class URLUtility {
	
	private final URL url;
	private String content;
	
	public URLUtility(final String url) throws Exception  {
		this.url = new URL(url);
	}
	
	public void readContent() throws Exception {
		content = "";
		BufferedReader response = new BufferedReader(
    			new InputStreamReader(url.openStream())
    		);
    	
    	String inputLine;
    	while ((inputLine = response.readLine()) != null) {
    		content += inputLine;
    	}

    	content = content.replaceAll("\\s{2,}", " ");
    	response.close();
	}
	
	public String getContent() throws Exception {
		if (content == null) {
			readContent();
		}
		
		return content;
	}
}
