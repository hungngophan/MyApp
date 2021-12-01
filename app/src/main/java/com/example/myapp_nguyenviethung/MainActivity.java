package com.example.myapp_nguyenviethung;



import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapp_nguyenviethung.databinding.ActivityMainBinding;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;


import Search.SearchActivity;


public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    public static final String API_KEY = "77b832c15e166c740bc841d0eb43c3c0";
    // Recycle view
    ArrayList<Product> productRCView;
    AdapterProductRCView adapterProductRCView;
    //list view
    ArrayList<Product> productList;
    AdapterProductList adapterProductList;
    //location
    private LocationManager locationManager;
    private int PEMISSION_CODE =1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);

        //Recycle view
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        binding.rcView.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, linearLayoutManager.getOrientation());
        binding.rcView.addItemDecoration(dividerItemDecoration);

        //location
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION}, PEMISSION_CODE);
        }
        Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

        String city = getCity(location.getLongitude(),location.getLatitude());
        GetWeatherCurrent(city);
        GetWeatherCurrentRCView(city);
        GetWeatherCurrentList(city);

        binding.imgLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String city = getCity(location.getLongitude(),location.getLatitude());
                GetWeatherCurrent(city);
                GetWeatherCurrentRCView(city);
                GetWeatherCurrentList(city);

            }
        });

        binding.btnSearchLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
//                startActivity(intent);
                startActivityForResult(intent,2);
            }
        });

        binding.btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });


    }
//========================================================================================================================================

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            switch (resultCode) {
                case RESULT_OK:
                    GetWeatherCurrent(data.getStringExtra("cityname"));
                    GetWeatherCurrentRCView(data.getStringExtra("cityname"));
                    GetWeatherCurrentList(data.getStringExtra("cityname"));
                    break;
            }
        }
        if (requestCode == 2){
            switch (resultCode) {
                case RESULT_CANCELED:
                    GetWeatherCurrent(data.getStringExtra("cityname2"));
                    GetWeatherCurrentRCView(data.getStringExtra("cityname2"));
                    GetWeatherCurrentList(data.getStringExtra("cityname2"));
                    break;
            }
        }

    }


//========================================================================================================================================

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==PEMISSION_CODE){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"pesmission grant",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"please provide the permission ",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }


    //========================================================================================================================================
    public String getCity(double longitude,double latitude){
        String city="not found";
        Geocoder gcd = new Geocoder(getBaseContext(), Locale.getDefault());
        try{
            List<Address> addresses = gcd.getFromLocation(latitude,longitude,10);
            for (Address adr :addresses ){
                String citydefault = adr.getLocality();
                if(citydefault!=null && !citydefault.equals("")){
                    city = citydefault;
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return city;
    }

    //============================================THỜI_TIẾT_HIỆN_TẠI============================================================================================
    public void GetWeatherCurrent(String city){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url ="https://api.openweathermap.org/data/2.5/weather?q="+city+"&units=metric&appid="+API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject= new JSONObject(response);

                            //lấy tên thành phố
                            String cityInfo = jsonObject.getString("name");
                            binding.tvresultCity.setText(cityInfo);


                            // lấy trạng thái thời tiết
                            String weatherInfo = jsonObject.getString("weather");
                            JSONArray arr = new JSONArray(weatherInfo);
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject jsonObjectWeather = arr.getJSONObject(i);

                                String main = jsonObjectWeather.getString("main");
                                String description = jsonObjectWeather.getString("description");
                                binding.tvresultCloud.setText(main + ":" + description);

                                //lấy hỉnh ảnh thời tiết
                                String icon = jsonObjectWeather.getString("icon");
                                Picasso.with(MainActivity.this).load("https://openweathermap.org/img/w/" + icon + ".png").into(binding.imgIcon);

                            }
                            // lấy tốc độ gió
                            JSONObject jsonObjectWind = jsonObject.getJSONObject("wind");
                            String speed = jsonObjectWind.getString("speed");
                            binding.tvresultSpeedWind.setText("Speed wind :" + speed);

                            // lấy nhiệt độ
                            JSONObject jsonObjectMain = jsonObject.getJSONObject("main");
                            String temp = jsonObjectMain.getString("temp");
                            String temp_max = jsonObjectMain.getString("temp_max");
                            String temp_min = jsonObjectMain.getString("temp_min");
                            String feels_like = jsonObjectMain.getString("feels_like");
                            String humidity = jsonObjectMain.getString("humidity");

                            Double a = Double.valueOf(temp);
                            Double b = Double.valueOf(temp_max);
                            Double c = Double.valueOf(temp_min);
                            Double d = Double.valueOf(feels_like);

                            String Temp = String.valueOf(a.intValue());
                            String Temp_max = String.valueOf(b.intValue());
                            String Temp_min = String.valueOf(c.intValue());
                            String Feels_like = String.valueOf(d.intValue());

                            binding.tvresultTemp.setText(Temp + "°");
                            binding.tvresultTempmax.setText("Temp max   :" + Temp_max + "°");
                            binding.tvresultTempmin.setText("Temp min   :" + Temp_min + "°");
                            binding.tvresultFeellikes.setText("Feel likes :" + Feels_like + "°");
                            binding.tvresultHudimity.setText("Hudimity   :" + humidity);

                            // lấy ngày hiện tại
                            String dayInfo = jsonObject.getString("dt");
                            long dt = Long.valueOf(dayInfo);
                            Date date = new Date(dt * 1000);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE yyyy-MM-dd HH-mm-ss");
                            String Day = simpleDateFormat.format(date);
                            binding.tvresltDay.setText(Day);

                            // lấy thời gian mặt trời mọc , lặn
                            JSONObject jsonObjectSys = jsonObject.getJSONObject("sys");
                            long sunrise = jsonObjectSys.getLong("sunrise");
                            long sunset = jsonObjectSys.getLong("sunset");


                            // set background
                            if (dt >= sunrise && dt <= sunset) {
                                binding.background.setBackgroundResource(R.drawable.sun);
                            } else {
                                binding.background.setBackgroundResource(R.drawable.night);
                            }



                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    //======================================THỜI_TIẾT_THEO_GIỜ_TRONG_RECYCLE_VIEW==================================================================================================


    public void GetWeatherCurrentRCView(String city){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url ="https://api.openweathermap.org/data/2.5/forecast?q=" + city + "&units=metric&appid=" + API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Product product;
                            productRCView = new ArrayList<>();


                            JSONObject jsonObject = new JSONObject(response);

                            String listInfo = jsonObject.getString("list");
                            JSONArray arr1 = new JSONArray(listInfo);
                            for (int i = 0; i < arr1.length(); i++) {
                                JSONObject jsonObjectList = arr1.getJSONObject(i);
                                //lấy ra nhiệt độ max, min
                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectMain.getString("temp_max");
                                String min = jsonObjectMain.getString("temp_min");
                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String temp_max = String.valueOf(a.intValue());
                                String temp_min = String.valueOf(b.intValue());

                                //lấy ra ngày hiện tại
                                String dayInfo = jsonObjectList.getString("dt");
                                long l = Long.valueOf(dayInfo);
                                Date date = new Date(l * 1000);
                                // HH-mm-ss
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE HH-mm-ss ");
                                String dt = simpleDateFormat.format(date);

                                //lấy ra icon của thời tiết và trạng thái
                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather1 = jsonArrayWeather.getJSONObject(0);
                                String description = jsonObjectWeather1.getString("description");
                                String icon = jsonObjectWeather1.getString("icon");



                                productRCView.add(new Product(dt,temp_min,temp_max,icon));

                            }//end for
                            adapterProductRCView = new AdapterProductRCView(MainActivity.this,productRCView);
                            binding.rcView.setAdapter(adapterProductRCView);



                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }

    //==========================================THỜI_TIẾT_THEO_NGÀY_TRONG_LISTVIEW==============================================================================================
    public void GetWeatherCurrentList(String city){
        RequestQueue requestQueue= Volley.newRequestQueue(MainActivity.this);
        String url ="https://api.openweathermap.org/data/2.5/forecast?q="+city+"&units=metric&cnt=96&appid="+API_KEY;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Product product;
                            productList = new ArrayList<Product>();
//                        List<Product> products = new ArrayList<>();

                            JSONObject jsonObject = new JSONObject(response);

                            JSONObject jsonObjectCity = jsonObject.getJSONObject("city");
                            String name =jsonObjectCity.getString("name");


                            String listInfo = jsonObject.getString("list");
                            JSONArray arr1 = new JSONArray(listInfo);
                            for (int i = 0; i < arr1.length(); i+=8) {
                                JSONObject jsonObjectList = arr1.getJSONObject(i);
                                //lấy ra nhiệt độ max, min
                                JSONObject jsonObjectMain = jsonObjectList.getJSONObject("main");
                                String max = jsonObjectMain.getString("temp_max");
                                String min = jsonObjectMain.getString("temp_min");
                                Double a = Double.valueOf(max);
                                Double b = Double.valueOf(min);
                                String temp_max = String.valueOf(a.intValue());
                                String temp_min = String.valueOf(b.intValue());

                                //lấy ra ngày hiện tại
                                String dayInfo = jsonObjectList.getString("dt");
                                long l = Long.valueOf(dayInfo);
                                Date date = new Date(l * 1000);
                                // HH-mm-ss     yyyy-MM-dd
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE  ");
                                String dt = simpleDateFormat.format(date);

                                //lấy ra icon của thời tiết và trạng thái
                                JSONArray jsonArrayWeather = jsonObjectList.getJSONArray("weather");
                                JSONObject jsonObjectWeather1 = jsonArrayWeather.getJSONObject(0);
                                String description = jsonObjectWeather1.getString("description");
                                String icon = jsonObjectWeather1.getString("icon");


                                productList.add(new Product(dt,temp_min,temp_max,description,icon));
                            }
                            adapterProductList = new AdapterProductList(MainActivity.this,productList);
                            binding.lvViewnd.setAdapter(adapterProductList);
                            adapterProductList.notifyDataSetChanged();



                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Could not find weather", Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        requestQueue.add(stringRequest);
    }




}