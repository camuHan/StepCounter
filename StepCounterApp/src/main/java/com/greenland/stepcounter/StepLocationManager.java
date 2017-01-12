package com.greenland.stepcounter;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.greenland.stepcounter.stepvalue.Values;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.greenland.stepcounter.StepScreen.UIHANDLER_RESULT.UPDATE_ADREES;
import static com.greenland.stepcounter.StepScreen.UIHANDLER_RESULT.UPDATE_DISTANCE;

public class StepLocationManager {
    private static Context mContext = null;
    private static StepLocationManager mInstance = null;
    private Handler mHandler  = null;
    LocationManager mLocationManager = null;
    LocationListener mLocationListener = null;

    double myLat;
    double myLng;
    String tempData;

    Location lastKnownLocation = null;

    public static StepLocationManager getInstance(Context context) {
        mContext = context;

        synchronized (StepLocationManager.class) {
            if (mInstance == null) {
                mInstance = new StepLocationManager();
            }
            return mInstance;
        }
    }

    public static StepLocationManager getInstance() {
        synchronized (StepLocationManager.class) {
            if (mInstance == null) {
                mInstance = new StepLocationManager();
            }
            return mInstance;
        }
    }

    StepLocationManager() {
        tempData = new String();
    }

    public void setHandler(Handler handler) {
        mHandler = handler;
    }

    public Boolean isActiveNetwork() {

        return false;
    }

    public void getLocation() {
//		if (mHandler == null) {
//			return;
//		}

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                viewCurrentLocation();
            }
        });

        thread.start();
    }

    private void viewCurrentLocation(){
        Log.e("HSH", "HSH viewCurrentLocation(");
        String clientId = "Q9OnQFK67ccys2pvEw6k";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "_rzfteL9sX";//애플리케이션 클라이언트 시크릿값";
        try {
//          String addr = URLEncoder.encode("불정로 6", "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/map/reversegeocode?query=" + tempData; //json
            Log.e("HSH", "HSH apiURL= " + apiURL);
            //String apiURL = "https://openapi.naver.com/v1/map/geocode.xml?query=" + addr; // xml
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientId);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            Log.i("HSH", "HSH \\n" + response.toString());
            parseResult(response.toString());

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    private void parseResult(String jason) throws JSONException {
        JSONObject fistJObject = new JSONObject(jason);
        JSONObject resultJObject = fistJObject.getJSONObject("result");
        JSONArray jArrObject = resultJObject.getJSONArray("items");
        for(int i=0; i < jArrObject.length(); i++){
            JSONObject itemJObject = jArrObject.getJSONObject(i);  // JSONObject 추출
            Values.Address = itemJObject.getString("Address");
        }
        mHandler.sendEmptyMessage(UPDATE_ADREES);
    }


    public void getMyLocation(){
        // Acquire a reference to the system Location Manager
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        // GPS 프로바이더 사용가능여부
        Boolean isGPSEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        // 네트워크 프로바이더 사용가능여부
        Boolean isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        Log.d("Main", "isGPSEnabled=" + isGPSEnabled);
        Log.d("Main", "isNetworkEnabled=" + isNetworkEnabled);

        mLocationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                double lat = location.getLatitude();
                double lng = location.getLongitude();
                myLat = lat;
                myLng = lng;
                tempData = String.valueOf(lng) + "," + String.valueOf(lat);
//                Toast.makeText(mContext, lat + " " + lng, Toast.LENGTH_SHORT).show();
                getLocation();

                if(lastKnownLocation==null) {
                    lastKnownLocation = location;
                }
                else {
                    Values.Distance = lastKnownLocation.distanceTo(location);
                    Log.i("Distance","HSH Distance:"+ Values.Distance);
                    lastKnownLocation=location;
                    mHandler.sendEmptyMessage(UPDATE_DISTANCE);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates
        boolean isFineGranted = ((StartActivity)mContext).checkPermission(Manifest.permission.ACCESS_FINE_LOCATION);
        boolean isCoarseGranted = ((StartActivity)mContext).checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION);
        if(isFineGranted == false || isCoarseGranted == false){
            Runnable grantedRunnable = new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub
                        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
                        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

                    // 수동으로 위치 구하기
                    String locationProvider = LocationManager.GPS_PROVIDER;
                    Location lastKnownLocation = mLocationManager.getLastKnownLocation(locationProvider);
                    if (lastKnownLocation != null) {
                        double lng = lastKnownLocation.getLatitude();
                        double lat = lastKnownLocation.getLatitude();
                        myLat = lat;
                        myLng = lng;
                        Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
                    }

                    tempData = String.valueOf(myLat) + "," + String.valueOf(myLng);
                }
            };
            Runnable deniedRunnable = new Runnable() {
                @Override
                public void run() {
                    // TODO Auto-generated method stub

                }
            };
            ((StartActivity)mContext).requestPermission(StartActivity.PERMISSION_TYPE.LOCATION_FINE, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, grantedRunnable, deniedRunnable);
        }else{
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

            String locationProvider = LocationManager.GPS_PROVIDER;
            Location lastKnownLocation = mLocationManager.getLastKnownLocation(locationProvider);
            if (lastKnownLocation != null) {
                double lng = lastKnownLocation.getLatitude();
                double lat = lastKnownLocation.getLatitude();
                myLat = lat;
                myLng = lng;
                Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
            }

            tempData = String.valueOf(myLat) + "," + String.valueOf(myLng);
        }

    }

    public void resultRequest(){
        mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, mLocationListener);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);

        // 수동으로 위치 구하기
        String locationProvider = LocationManager.GPS_PROVIDER;
        Location lastKnownLocation = mLocationManager.getLastKnownLocation(locationProvider);
        if (lastKnownLocation != null) {
            double lng = lastKnownLocation.getLatitude();
            double lat = lastKnownLocation.getLatitude();
            myLat = lat;
            myLng = lng;
            Log.d("Main", "longtitude=" + lng + ", latitude=" + lat);
        }

        tempData = String.valueOf(myLat) + "," + String.valueOf(myLng);
    }


}