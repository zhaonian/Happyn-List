package com.searchsystem.mycommunity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.yelp.fusion.client.connection.YelpFusionApi;
import com.yelp.fusion.client.connection.YelpFusionApiFactory;
import com.yelp.fusion.client.models.Business;
import com.yelp.fusion.client.models.SearchResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.searchsystem.mycommunity.R.id.listView;

/**
 * Created by Luan on 3/14/17.
 */

public class EventsActivity  extends AppCompatActivity {

    private String appId = "OeXJXCJfKSc5oHtw-zUayA";
    private String appSecret = "vz3vkeqDP2QDJnCu9E95HanKIZL81fcC6MGOeRvo9AAhxlylgl0xHQlEYyFVFgZW";

    private YelpFusionApiFactory apiFactory = null;
    private YelpFusionApi yelpFusionApi = null;
    private TextView results;
    private ArrayList<Business> businesses;
    private final ArrayList<Business> b = new ArrayList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        // a list of yelp events

        results = (TextView) findViewById(R.id.singleEvent);
        Toast.makeText(this, "hello", Toast.LENGTH_LONG).show();

        new getYelpResults().execute();
    }

    private class getYelpResults extends AsyncTask<Object, Object, Void> {
        @Override
        protected Void doInBackground(Object... voids) {
            apiFactory = new YelpFusionApiFactory();
            try {
                yelpFusionApi = apiFactory.createAPI(appId, appSecret);
            } catch (IOException e) {
                e.printStackTrace();
            }

            Map<String, String> params = new HashMap<>();

            // general params, NEED TO MAKE DYNAMIC
            params.put("term", "shopping malls");
            params.put("latitude", "40.711217064583");
            params.put("longitude", "-73.966384349735");

            Call<SearchResponse> call = yelpFusionApi.getBusinessSearch(params);

            Callback<SearchResponse> callback = new Callback<SearchResponse>() {
                @Override
                public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                    SearchResponse searchResponse = response.body();
                    ArrayList<Business> businesses = searchResponse.getBusinesses();
                    int totalNumberOfResult = searchResponse.getTotal();
                    String businessName = businesses.get(0).getName();
//                    results.setText(businessName + "\n" + totalNumberOfResult);
                    Log.i("Values", businessName);
                    System.out.print(businessName);
                    for (int i = 0; i < businesses.size(); i++) {
                        b.add(businesses.get(i));
                        Log.i("object:", businesses.get(i).getName() + " " + businesses.get(i).getLocation());
                    }
                    return;
                }

                @Override
                public void onFailure(Call<SearchResponse> call, Throwable t) {
                    // HTTP error happened, do something to handle it.
                    Log.i("Values", "Error");
                    System.out.print("Error");

                    return;
                }
            };
            call.enqueue(callback);

            return null;
        }

//        @Override
//        protected void onPostExecute(Void aVoid) {
//            ArrayAdapter<String> adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
//            listView.setAdapter(adapter);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                }
//            }
//        }
//    }

    }
}
