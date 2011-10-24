package me.zimity.android.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;

//TODO: Perhaps have an imprint object class
//TODO: Change to RoboAsyncTask
public class DataDelivery extends AsyncTask<HashMap<String, String>, Integer, Long> {

    private String REQUEST_PREFIX = "data[Imprint][";
    private String REQUEST_SUFFIX = "]";

    @Override
    protected Long doInBackground(HashMap<String, String>... imprintData) {
        int count = imprintData.length;

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://dev.zimity.me/imprints/add");
        //HttpPost httppost = new HttpPost("http://192.168.1.112:8888/imprints/add");

        Log.e("Entering ZIMITY datamanager", "sup?");
        
        for (int i = 0; i < count; i++) {
            try {
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                if (imprintData[i].containsKey(Common.IMPRINT_EXTRA)) {
                   FileBody bin = new FileBody(new File(imprintData[i].get(Common.IMPRINT_EXTRA)));
                   reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_EXTRA + REQUEST_SUFFIX, bin);
                }

                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_TYPE + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_TYPE)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_NOTE + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_NOTE)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_USERID + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_USERID)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_CLIENT + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_CLIENT)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_LATITUDE + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_LATITUDE)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_LONGITUDE + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_LONGITUDE)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_ALTITUDE + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_ALTITUDE)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_BEARING + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_BEARING)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_SPEED + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_SPEED)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_SHARING + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_SHARING)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_ACCURACY + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_ACCURACY)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_SYNCD + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_SYNCD)));
                reqEntity.addPart(REQUEST_PREFIX + Common.IMPRINT_DELETED + REQUEST_SUFFIX, new StringBody(imprintData[i].get(Common.IMPRINT_DELETED)));

                httppost.setEntity(reqEntity);

                HttpResponse response = httpclient.execute(httppost);
                HttpEntity resEntity = response.getEntity();
                
                InputStream instream = resEntity.getContent();
                String result = convertStreamToString(instream);
                
                Log.e("status", result);

               // Log.e("response", EntityUtils.toString(resEntity));
            } catch (ClientProtocolException e) {
                Log.e("ZIMITY datamanager", "clientprotocolexception");
            } catch (IOException e) {
                Log.e("ZIMITY datamanager", e.getMessage());
            }
        }

        return null;
    }
    
    private static String convertStreamToString(InputStream is) {
        /*
         * To convert the InputStream to String we use the BufferedReader.readLine()
         * method. We iterate until the BufferedReader return null which means
         * there's no more data to read. Each line will appended to a StringBuilder
         * and returned as String.
         */
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
 
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
