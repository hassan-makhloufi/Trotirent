/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

/**
 *
 * @author Lenovo
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class BadWords {

    private static final String PURGOMALUM_API_ENDPOINT = "https://www.purgomalum.com/service/containsprofanity?text=This%20sentence%20contains%20no%20profanity";

    public static String checkWords(String paragraph) {
        try {
            String sanitizedParagraph = paragraph.replaceAll("\\s", "");
            URL url = new URL(PURGOMALUM_API_ENDPOINT + sanitizedParagraph);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                StringBuilder content;
                try (BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()))) {
                    String inputLine;
                    content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                }
                return content.toString();
            } else {
                throw new IOException("HTTP error code: " + status);
            }
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL: " + PURGOMALUM_API_ENDPOINT, e);
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to PurgoMalum API", e);
        }
    }
}

