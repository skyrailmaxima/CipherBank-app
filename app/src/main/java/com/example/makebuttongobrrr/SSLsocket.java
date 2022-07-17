package com.example.makebuttongobrrr;

import android.os.AsyncTask;
import android.os.StrictMode;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

import java.io.IOException;
import java.io.InputStream;

import java.io.OutputStream;
import org.json.JSONObject;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class SSLsocket {


    private static final int delay = 1000; // in millis
    private static final String[] protocols = new String[] {"TLSv1.3"};
    private static final String[] cipher_suites = new String[] {"TLS_AES_128_GCM_SHA256"};
    private static final String message =
            "{\"Request_Type\": \"Quote_price\", \"Payment_Type\": \"BITCOIN\", \"Cash type\": \"USD\", \"Cash amount\": \"34.55\"}\r\n\r\n";

    public static void main(String[] args) {
    }
    public static String getQuote(double value)  throws Exception {
        System.out.println("Starting");
        String response = "";
        JSONObject json = new JSONObject();
        json.put("Request_Type", "Quote_price");
        json.put("Payment_Type", "BITCOIN");
        json.put("Cash type", "USD");
        json.put("Cash amount", String.valueOf(value));
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try (SSLSocket socket = createSocket("cipherbank.money", 25560))
        {
            System.out.println("Created SSL Socket");

            InputStream is = new BufferedInputStream(socket.getInputStream());
            OutputStream os = new BufferedOutputStream(socket.getOutputStream());

            System.out.println("Created streams.");

            os.write(message.getBytes());
            os.flush();

            System.out.println("Sent Message.");

            byte[] data = new byte[2048];



            System.out.println("Starting response.");

            while(response.indexOf("\r\n\r\n") == -1)
            {
                int len = is.read(data);

                if (len <= 0) {
                    System.out.println("no data received");
                }

                response += new String(data, 0, len);
            }

            System.out.println(response);

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        JSONObject retoken = new JSONObject(response);
        String ret = retoken.getString("CASH_AMOUNT");
        return ret;
    }

    public static SSLSocket createSocket(String host, int port) throws IOException {
        SSLSocket socket = (SSLSocket) SSLSocketFactory.getDefault()
                .createSocket(host, port);
        socket.setEnabledProtocols(protocols);
        socket.setEnabledCipherSuites(cipher_suites);
        return socket;
    }

}
