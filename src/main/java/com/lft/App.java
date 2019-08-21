package com.lft;

public class App 
{
    public static void main( String[] args )
    {	
    	try {
    		final WebCrawler webCrawler = new WebCrawler();
        	webCrawler.start();
    	} catch (Exception e) {
    		System.out.println(e.getMessage());
    	}
    }
}
