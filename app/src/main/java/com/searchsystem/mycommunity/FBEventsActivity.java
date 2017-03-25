package com.searchsystem.mycommunity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Luan on 3/5/17.
 */

public class FBEventsActivity extends AppCompatActivity {
    ListView listView;

    String accessToken; // FB user access token

    ArrayList<Event> EventArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fbevents);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            accessToken = extras.getString("accessToken");
        }

        listView = (ListView) findViewById(R.id.listView);

        new getFBEventsJson().execute();
    }

    public void onYelpClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
               yelpStart();
            }
        }).start();
    }

    private void yelpStart() {
        Intent goToIntent = new Intent(this, EventsActivity.class);
        startActivity(goToIntent);
    }

    public void onSearchEngineClick(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startSearchEngine();
            }
        }).start();
    }

    private void startSearchEngine() {
        Intent goToIntent = new Intent(this, SearchEngineActivity.class);
        startActivity(goToIntent);
    }

    private class getFBEventsJson extends AsyncTask<Void, Void, ArrayList<String>> {

        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            ClientServerConnector clientServerConnector = new ClientServerConnector();
            Map<String, String> map = new HashMap<>();

            String latitude = "40.711217064583";    // pass in current lat from GPS from Seth
            String longitude = "-73.966384349735";  // pass in current lng from GPS from Seth
            map.put("lat", latitude);
            map.put("lng", longitude);
            map.put("accessToken", accessToken);

            JSONObject fbEventsJson = clientServerConnector.getJsonObject("FBEvents", map);

            Event fbEvent = new Event();
            EventArray = fbEvent.getEventsFromJson(fbEventsJson);

            ArrayList<String> events = new ArrayList<>();
            for (Event event : EventArray) {
                events.add(event.getName());
            }
            return events;
        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String eventName = listView.getItemAtPosition(position).toString();
                    Intent singleFBEvent = new Intent(getApplicationContext(), EventPageActivity.class);
                    Event fbEvent = new Event();
                    Event event = fbEvent.getFBeventByName(EventArray, eventName);
                    singleFBEvent.putExtra("event", event); // better use Parcelable
                    startActivity(singleFBEvent);
                }
            });
        }
    }
}
