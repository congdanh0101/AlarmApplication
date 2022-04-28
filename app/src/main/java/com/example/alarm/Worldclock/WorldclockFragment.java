package com.example.alarm.Worldclock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.alarm.Adapter.WorldClockAdapter;
import com.example.alarm.DAO.ICityDAO;
import com.example.alarm.DB.SQLiteCity;
import com.example.alarm.HttpHandler;
import com.example.alarm.R;
import com.example.alarm.model.City;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class WorldclockFragment extends Fragment {

    public static final String LOG_TAG = WorldclockFragment.class.getSimpleName();
    final int REQUEST_CODE = 1;
    private ArrayList<City> cityArrayList;
    private RecyclerView recyclerView;
    private WorldClockAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ImageView iv_add;
    private ICityDAO db;
    private Handler handler = new Handler();
    private volatile boolean stopThread = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(LOG_TAG,"onCreateView");
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worldclock, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG,"onCreate");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(LOG_TAG,"onStop");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"onStart");
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(LOG_TAG,"onAttach");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG,"onDestroy");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(LOG_TAG,"onResume");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(LOG_TAG,"onDestroyView");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(LOG_TAG,"onDetach");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(LOG_TAG,"onPause");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(LOG_TAG,"onActivityCreated");
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(LOG_TAG,"onViewCreated");

        iv_add = view.findViewById(R.id.iv_add_worldclock);
        recyclerView = view.findViewById(R.id.rv_list_worldclock);

        cityArrayList = new ArrayList<>();

        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAllCities();
            }
        });
        buildRecyclerView();

        db = new SQLiteCity(getActivity());
        if (db.isDbEmpty())
            setupDatabase();
        showSavedCities();

        startThread();
    }

    private void startThread() {
        stopThread = false;
        Runnable timeUpdater = new TimeUpdater(1000);
        new Thread(timeUpdater).start();
    }

    private void setupDatabase() {
        Toast.makeText(getActivity(), "Call API and building DB", Toast.LENGTH_LONG).show();
        Runnable json = new APIThread();
        new Thread(json).start();
        Toast.makeText(getActivity(), "All Done", Toast.LENGTH_SHORT).show();
    }

    private void buildRecyclerView() {
        layoutManager = new LinearLayoutManager(getActivity());
        adapter = new WorldClockAdapter(cityArrayList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }



    private void showAllCities() {
        Intent intent = new Intent(getActivity(), ShowAllWorldClock.class);
        getActivity().startActivityFromFragment(this,intent,REQUEST_CODE);
        startThread();
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        getActivity();
        if(requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK){

            showSavedCities();
            startThread();
        }
    }

    private void showSavedCities() {
        cityArrayList.clear();
        cityArrayList = db.load(true);
        adapter.changeList(cityArrayList);
        adapter.notifyDataSetChanged();
    }

    class TimeUpdater implements Runnable {
        int sleep;

        public TimeUpdater(int sleep) {
            this.sleep = sleep;
        }

        @Override
        public void run() {
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            while (stopThread == false) {
                if (cityArrayList.size() != 0)
                    for (int i = 0; i < cityArrayList.size(); i++)
                        cityArrayList.get(i).updateTime(dateFormat);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });

                try {
                    Thread.sleep(sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    class APIThread implements Runnable {

        ArrayList<City> cities;

        APIThread() {
            cities = new ArrayList<>();
        }

        @Override
        public void run() {
            HttpHandler http = new HttpHandler();
            String url = "https://api.timezonedb.com/v2.1/list-time-zone?key=AAAKZ8GJTJH5&format=json&fields=zoneName";
            String jsonStr = http.makeServiceCall(url);
            if (jsonStr != null) {
                try {
                    JSONObject object = new JSONObject(jsonStr);
                    JSONArray zones = object.getJSONArray("zones");

                    for (int i = 0; i < zones.length(); i++) {
                        JSONObject c = zones.getJSONObject(i);
                        String zoneName = c.getString("zoneName");
                        String cityName = zoneName.substring(zoneName.indexOf('/') + 1);
                        if (cityName.indexOf('/') == -1) {
                            if (cityName.indexOf('_') == -1)
                                cityName = cityName.replace('_', ' ');
                            cityArrayList.add(new City(cityName, zoneName));
                        }

                    }
                    db.fillDb(cityArrayList);
                } catch (final JSONException e) {
                    Log.d(LOG_TAG, "JSON parsing error: " + e.getMessage());
                }
            }
        }
    }
}