package com.thecat.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ServiceConnector {

	private ServiceConnector connection;


	/**
	 * This will connect to the requested url.  Use this one when you have and Input
	 *
	 * @param urlString
	 * @param inputString
	 * @return BufferReader
	 * @throws MalformedURLException
	 * @throws IOException
	 */
	public static BufferedReader connectPost( String urlString, String inputString ) throws IOException {

		try {
			URL url = new URL(urlString);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Type", "application/json");

			OutputStream os = conn.getOutputStream();

			os.write(inputString.getBytes());
			os.flush();

			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			conn.disconnect();
			return new BufferedReader(new InputStreamReader( conn.getInputStream() ) );
		} catch (  IOException exception ) {
			exception.printStackTrace();
			throw exception;

		}
	}
}