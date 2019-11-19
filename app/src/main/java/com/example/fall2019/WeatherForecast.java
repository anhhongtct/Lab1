package com.example.fall2019;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.xmlpull.v1.XmlPullParser.END_TAG;
import static org.xmlpull.v1.XmlPullParser.START_TAG;
import static org.xmlpull.v1.XmlPullParser.TEXT;

public class WeatherForecast extends AppCompatActivity {
    private Context thisApp;
    ImageView weatherImage;
    TextView curTempView;
    TextView maxTempView;
    TextView minTempView;
    TextView uvTempView;
    ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather);
        weatherImage = findViewById(R.id.weatherImg);
        curTempView = findViewById(R.id.currentTemp);
        maxTempView = findViewById(R.id.maxTemp);
        minTempView = findViewById(R.id.minTemp);
        uvTempView = findViewById(R.id.UVrate);
        bar = findViewById(R.id.weatherBar);
        bar.setVisibility(View.VISIBLE);
        MyNetworkQuery theQuery = new MyNetworkQuery();
        theQuery.execute();

    }

    public boolean fileExistance(String fname) {
        File file = getBaseContext().getFileStreamPath(fname);
        return file.exists();
    }

    //                                      Type1, Type2   Type3
    private class MyNetworkQuery extends AsyncTask<String, Integer, String> {
        String curTemp;
        String minTemp;
        String maxTemp;
        String iconName;
        String UVRate;
        Bitmap image;

        @Override                       //Type 1
        protected String doInBackground(String... strings) {
            publishProgress(25);
            String ret = null;
            String queryURL = "http://api.openweathermap.org/data/2.5/weather?q=ottawa,ca&APPID=7e943c97096a9784391a981c4d878b22&mode=xml&units=metric";


            try {       // Connect to the server:
                URL url = new URL(queryURL);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //Set up the XML parser:
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(false);
                XmlPullParser xpp = factory.newPullParser();
                xpp.setInput(inStream, "UTF-8");

                //Iterate over the XML tags:
                int EVENT_TYPE;         //While not the end of the document:
                publishProgress(50);
                while ((EVENT_TYPE = xpp.getEventType()) != XmlPullParser.END_DOCUMENT) {
                    switch (EVENT_TYPE) {
                        case START_TAG:         //This is a start tag < ... >
                            String tagName = xpp.getName(); // What kind of tag?
                            if (tagName.equals("temperature")) {
                                curTemp = xpp.getAttributeValue(null, "value"); //What is the String associated with message?
                                //try{Thread.sleep(500);} catch(Exception e) { }
                                maxTemp = xpp.getAttributeValue(null, "max");
                                //try{Thread.sleep(500);} catch(Exception e) { }
                                minTemp = xpp.getAttributeValue(null, "min");
                                //try{Thread.sleep(500);} catch(Exception e) { }

                            } else if (tagName.equals("weather")) {
                                iconName = xpp.getAttributeValue(null, "icon");
                                String imagefile = iconName + ".png";

                                if (fileExistance(imagefile)) {
//                                    FileInputStream fis = null;
//                                    try {
//                                        fis = new FileInputStream(getBaseContext().getFileStreamPath(imagefile));
//                                       }
//                                    catch (FileNotFoundException e) {e.printStackTrace();}
//                                    image = BitmapFactory.decodeStream(fis);
                                    FileInputStream fis = null;
                                    try {
                                        fis = openFileInput(imagefile);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }
                                    image = BitmapFactory.decodeStream(fis);
                                    Log.i("Image found locally:", imagefile);


                                } else {
                                    String urlString = "http://openweathermap.org/img/w/" + iconName + ".png";
                                    URL url2 = new URL(urlString);
                                    HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
                                    connection.connect();
                                    int responseCode = connection.getResponseCode();
                                    if (responseCode == 200) {
                                        image = BitmapFactory.decodeStream(connection.getInputStream());
                                        Log.i("Image downloaded:", imagefile);
                                    }
                                    // try{Thread.sleep(500);} catch(Exception e) { }

                                    FileOutputStream outputStream = openFileOutput(imagefile, Context.MODE_PRIVATE);
                                    image.compress(Bitmap.CompressFormat.PNG, 80, outputStream);
                                    outputStream.flush();
                                    outputStream.close();
                                }
                                publishProgress(75);

                            }
                            break;
                        case END_TAG:           //This is an end tag: </ ... >
                            break;
                        case TEXT:              //This is text between tags < ... > Hello world </ ... >
                            break;
                    }
                    xpp.next(); // move the pointer to next XML element
                }
            } catch (MalformedURLException mfe) {
                ret = "Malformed URL exception";
            } catch (IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";
            } catch (XmlPullParserException pe) {
                ret = "XML Pull exception. The XML is not properly formed";
            }


            String queryURL2 = "http://api.openweathermap.org/data/2.5/uvi?appid=7e943c97096a9784391a981c4d878b22&lat=45.348945&lon=-75.759389";

            try {
                // Connect to the server:
                URL url2 = new URL(queryURL2);
                HttpURLConnection urlConnection = (HttpURLConnection) url2.openConnection();
                InputStream inStream = urlConnection.getInputStream();

                //Set up the JSON object parser:
                // json is UTF-8 by default
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = null;
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
                String result = sb.toString();
                JSONObject jObject = new JSONObject(result);
                UVRate = jObject.getString("value");

            } catch (JSONException ex) {
                ret = "JSON exception";
            } catch (MalformedURLException mfe) {
                ret = "Malformed URL exception";
            } catch (IOException ioe) {
                ret = "IO Exception. Is the Wifi connected?";
            }

            //What is returned here will be passed as a parameter to onPostExecute:
            publishProgress(100);
            return ret;
        }

        @Override                   //Type 3
        protected void onPostExecute(String sentFromDoInBackground) {
            super.onPostExecute(sentFromDoInBackground);
            weatherImage.setImageBitmap(image);
            curTempView.setText(curTempView.getText() + curTemp);
            maxTempView.setText(maxTempView.getText() + maxTemp);
            minTempView.setText(minTempView.getText() + minTemp);
            uvTempView.setText(uvTempView.getText() + UVRate);
            bar.setVisibility(View.INVISIBLE);
            //update GUI Stuff:

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            bar.setMax(100);
        }

        @Override                       //Type 2
        protected void onProgressUpdate(Integer... value) {
            super.onProgressUpdate(value);
            bar.setProgress(value[0]);
            bar.setVisibility(View.VISIBLE);
            //Update GUI stuff only:
        }
    }


}
