package com.example.makebuttongobrrr;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import javax.net.ssl.*;

public class SSLSocketClient {


    public static String getQuote(float value) throws Exception {
        String message, ret = null;
        JSONObject json = new JSONObject();
        json.put("Request_Type", "Quote_price");
        json.put("Payment_Type", "BITCOIN");
        json.put("Cash type", "USD");
        json.put("Cash amount", String.valueOf(value));

        try {
            SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            int PORT = 25560;
            String HOST = "cipherbank.money";
            SSLSocket socket = (SSLSocket) factory.createSocket(HOST, PORT);


            socket.startHandshake();

            PrintWriter out = new PrintWriter(
                    new BufferedWriter(
                            new OutputStreamWriter(
                                    socket.getOutputStream())));
            message = json.toString();
            out.println(message);
            out.println();
            out.flush();

            /*
             * Make sure there were no surprises
             */
            if (out.checkError())
                System.out.println("SSLSocketClient:  java.io.PrintWriter error");

            /* read response */
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);
            ret = inputLine;
            in.close();
            out.close();
            socket.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return (ret);
    }
}

