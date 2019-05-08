package com.recyclerview.ssunchu.webapic;

import android.content.Intent;
import android.icu.util.Output;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {


    Button btnGetData,btnAlarmActivity,btnGetUID;

    final String mTag = this.getClass().getSimpleName();
    //final String API = "https://reqres.in/api/users";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetData = (Button) findViewById(R.id.btnSend);
        btnGetData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //new GetData().execute();
                try {
                    new GetToken().execute();
                } catch (Exception e) {
                    Log.d(mTag,"onCreate : "+ e.getMessage());
                }
            }
        });

        findViewById(R.id.btnPOST).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //POST Data
            }
        });


        btnAlarmActivity = findViewById(R.id.nextActivity);
        btnAlarmActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AlarmMngrActivity.class);
                startActivity(intent);

            }
        });

        btnGetUID = findViewById(R.id.getUID);
        btnGetUID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,getUID.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.btnRESTFulAPI).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,REStFulAPITest.class);
                startActivity(intent);
            }
        });

    }

    public class GetToken extends AsyncTask<Void,Void,String>{

        String inputLine;
        String result;
        final String webAPI = "http://192.168.0.112:8080/hit/a.php";
        final String mTag = this.getClass().getSimpleName();

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(webAPI);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            //httpURLConnection.setRequestMethod("GET");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.connect();

            JSONObject postDataParams = new JSONObject();
            postDataParams.put("name","Andre");
            postDataParams.put("email","andy@gmail.com");
            Log.d(mTag," POST Data : " + postDataParams.toString());

            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == httpURLConnection.HTTP_OK){

                InputStreamReader streamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();

                result = stringBuilder.toString();

                Log.d(mTag, "Output : "+ result + responseCode);
                return result;

            } else {
                return new String(" Data : " + result + responseCode);
            }

        } catch (Exception e) {
            Log.d(mTag, e.getMessage());
        }
        return result;
    }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
            Toast.makeText(getApplicationContext(), result , Toast.LENGTH_SHORT).show();
        }

    }

    public static String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

}



/*
    class GetData extends AsyncTask<Void,String,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            try {
                URL url = new URL(API);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

                InputStream is = httpURLConnection.getInputStream();
                int byteChar;
                String result = "";

                while ((byteChar = is.read()) != -1){
                    result += (char)byteChar;
                }

                Log.d(" JSON API = ","" + result + result.getClass().getSimpleName());

                JSONObject jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for(int i = 0 ; i < jsonArray.length(); i++){
                    JSONObject explrObj = jsonArray.getJSONObject(i);
                    //Log.d(mTag, explrObj.toString());
                    Log.d(mTag, "ID = " + explrObj.getString("id"));
                    Log.d(mTag, "First Name = " + explrObj.getString("first_name"));
                    //Log.d(mTag, explrObj.getString("last_name"));
                    //Log.d(mTag, explrObj.getString("avatar"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

    }
    */



/*
    class retrievedata extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            URL url;
            try {
                url = new URL("https://reqres.in/api/users");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET"); //Your method here
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null)
                    buffer.append(line + "\n");

                if (buffer.length() == 0)
                    return null;

                //Log.d(" Data : ",line);
                Log.d(mTag, buffer.getClass().getSimpleName());
                Log.d(mTag, "Buffer data=" + buffer.toString());


                //return buffer.toString();

                JSONArray jsonArray = new JSONArray(buffer.toString());
                String[] arr = new String[jsonArray.length()];

                JSONObject obj = jsonArray.getJSONObject(1);
                arr[1] = obj.getString("name");

                Log.d(mTag, arr[1]);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String response) {
            if (response != null) {

            }
        }

    }
*/