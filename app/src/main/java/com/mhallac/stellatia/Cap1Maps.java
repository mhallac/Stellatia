package com.mhallac.stellatia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.reimaginebanking.api.nessieandroidsdk.NessieError;
import com.reimaginebanking.api.nessieandroidsdk.NessieResultsListener;
import com.reimaginebanking.api.nessieandroidsdk.models.ATM;
import com.reimaginebanking.api.nessieandroidsdk.models.PaginatedResponse;
import com.reimaginebanking.api.nessieandroidsdk.requestclients.NessieClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class Cap1Maps extends AppCompatActivity {
    public static double latitudeD;
    public static double longitudeD;
    public static double latitudeO;
    public static double longitudeO;
    public static String mName;
    public static String sNum;
    public static String sName;
    public static String mCity;
    public static String mState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        String value = intent.getStringExtra("key");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cap1_maps);

        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        double longitude = location.getLongitude();
        longitudeO = longitude;
        String lon = Double.toString(longitude);
        double latitude = location.getLatitude();
        latitudeO = latitude;
        String lat = Double.toString(latitude);

        float lonf = (float) longitude;
        float latf = (float) latitude;

        TextView tvLat = findViewById(R.id.lat);
        TextView tvLon = findViewById(R.id.lon);
        tvLat.setText(lat);
        tvLon.setText(lon);

        float rad = 100;

        TextView tvName = findViewById(R.id.mname);
        TextView tvSNum = findViewById(R.id.mstreetnumber);
        TextView tvSName = findViewById(R.id.mstreetname);
        TextView tvCity = findViewById(R.id.mcity);
        TextView tvState = findViewById(R.id.mstate);

        final NessieClient client = NessieClient.getInstance("95da64ac415c67c30510773378555e8b");
        client.ATM.getATMs(latf, lonf, rad, new NessieResultsListener() {
            @Override
            public void onSuccess(Object result) {
                final PaginatedResponse<ATM> response = (PaginatedResponse<ATM>) result;
                // get the next set of ATMs

                client.ATM.getATMs(response.getPagingObject().getNextPage(), new NessieResultsListener() {
                    // implement callback interface here
                    public void onSuccess(Object result) {
                        List<ATM> atms = response.getObjectList();
                        latitudeD = atms.get(0).getGeocode().getLat();
                        longitudeD = atms.get(0).getGeocode().getLng();
                        sName = atms.get(0).getAddress().getStreetName();
                        mName = atms.get(0).getName();
                        sNum = atms.get(0).getAddress().getStreetNumber();
                        mCity = atms.get(0).getAddress().getCity();
                        mState = atms.get(0).getAddress().getmState();

                    }

                    @Override
                    public void onFailure(NessieError error) {
                        throw new IllegalArgumentException("idk what this is");
                    }
                });
            }
            @Override
            public void onFailure(NessieError error) {
                // handle error
            }
        });

        //tvName.setText(mName);
        //tvSNum.setText(sNum);
        //tvSName.setText(sName);
        //tvCity.setText(mCity);
        //tvState.setText(mState);

        /*
        try {
            sendGet();
        }
        catch (Exception e) {
            System.out.println("jakdfhlaj");
        }

*/
/*

        try {
            System.out.println("TESTING IF THIS PRINTS");
            URL link = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + lat + "," + lon + "&destination=" + latitude + "," + longitude + "&mode=walking&key=AIzaSyCkrAZxZ0ovcXMUs98qzJmPBP9piTdvHfE");
            HttpURLConnection con = (HttpURLConnection) link.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println("TESTING IF THIS PRINTS");
            System.out.println(content);
        } catch (Exception e) {
            System.out.println("you are a failure");
        }*/
        Button seeButton = findViewById(R.id.takemehere);
        seeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Cap1Maps.this, See.class);
                myIntent.putExtra("key", "Important Message"); //Optional parameters
                Cap1Maps.this.startActivity(myIntent);
            }
        });

    }

    private void sendGet() throws Exception {
        try {
            System.out.println("TESTING IF THIS PRINTS");
            URL link = new URL("https://maps.googleapis.com/maps/api/directions/json?origin=" + latitudeO + "," + longitudeO + "&destination=" + latitudeD + "," + longitudeD + "&mode=walking&key=AIzaSyCkrAZxZ0ovcXMUs98qzJmPBP9piTdvHfE");
            HttpURLConnection con = (HttpURLConnection) link.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();
            System.out.println("TESTING IF THIS PRINTS");
            System.out.println(content);

            /*
            String url = "https://maps.googleapis.com/maps/api/directions/json?origin=\" + lat + \",\" + lon + \"&destination=\" + latitude + \",\" + longitude + \"&mode=walking&key=AIzaSyCkrAZxZ0ovcXMUs98qzJmPBP9piTdvHfE";

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            // con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            System.out.println("\nSending 'GET' request to URL : " + url);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            System.out.println(response.toString());*/
        } catch (IOException ioe)

        {
            ioe.printStackTrace();
        }
    }

}
