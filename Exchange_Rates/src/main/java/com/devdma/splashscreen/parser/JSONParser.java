package com.devdma.splashscreen.parser;

import android.util.Log;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class JSONParser
{
    public static JSONObject getJSONFromUrl(String str_url) {

        HttpURLConnection connection = null;
        BufferedReader reader = null;
        StringBuffer buffer = null;
        JSONObject jsonObject = null;

        try {
            URL url = new URL(str_url);
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                buffer = new StringBuffer();

                String line = "";
                while ((line = reader.readLine()) != null)
                {
                    buffer.append(line);
                }

                try {
                    if (buffer != null) {
                        // формируем JSON объект
                        jsonObject = new JSONObject(buffer.toString());
                    }
                } catch (JSONException e) {
                    Log.e(JSONParser.class.getName(), "Not valid JSON data.");
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        finally {
            if (connection != null) {
                connection.disconnect();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            // возвращаем сформированный JSON объект
            return jsonObject;
        }
    }
}
