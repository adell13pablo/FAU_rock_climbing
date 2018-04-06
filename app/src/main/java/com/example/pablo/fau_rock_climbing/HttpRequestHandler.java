package com.example.pablo.fau_rock_climbing;

/**
 * Created by Pablo on 3/27/2018.
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Belal on 9/5/2017.
 */

//Code seen in https://www.simplifiedcoding.net/android-login-and-registration-tutorial/

public class HttpRequestHandler {


    //this method will send a post request to the specified url
    //in this app we are using only post request
    //in the hashmap we have the data to be sent to the server in keyvalue pairs
    public String sendPostRequest(String requestURL, HashMap<String, String> postDataParams) {
        URL url;

        StringBuilder sb = new StringBuilder();
        try {
            url = new URL(requestURL); //Build up new URL
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            //Set up the HttpURL connection to perform the request

            OutputStream os = conn.getOutputStream(); //This abstract class allows us to write data to an output or destination

            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams)); //Create the bufferWriter to get the data into the OutputStream
                                                            // And send it to the requested URL

            writer.flush();
            writer.close();
            os.close();
            //Clean and close the Output stream and the buffer so we free some memory

            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) { //if the connection was successful, read the result

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); //Create a BufferReader to read all the information
                                                                                                      //Coming from the InputStream (As it happened with OutputStream and the BufferWriter)
                sb = new StringBuilder(); //Intialize String builder to get the response
                String response;

                while ((response = br.readLine()) != null) { //When BufferWriter pointer goes to null, no more line are to read so we stop loading lines on the string variable and appending it to the StringBuilder

                    sb.append(response);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString(); //Return the server's response
    }

    public String sendGetRequest(String requestURL){

        URL url;
        StringBuilder sb = new StringBuilder();
        try{
             url = new URL(requestURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(15000);
            conn.setReadTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setDoOutput(true);


            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) { //if the connection was successful, read the result

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream())); //Create a BufferReader to read all the information
                //Coming from the InputStream (As it happened with OutputStream and the BufferWriter)
                sb = new StringBuilder(); //Intialize String builder to get the response
                String response;

                while ((response = br.readLine()) != null) { //When BufferWriter pointer goes to null, no more line are to read so we stop loading lines on the string variable and appending it to the StringBuilder

                    sb.append(response);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return sb.toString();
    }


    //this method is converting key-value pairs data into a query string as needed to send to the server
    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : params.entrySet()) { //For every value within the Hashmap, we execute the loop and put them together
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString(); //Return the final POST values
    }
}
