package com.searchsystem.mycommunity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Luan on 3/12/17.
 */

public class EventPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        Intent intentFromEvents = getIntent();
        Event event = (Event) intentFromEvents.getSerializableExtra("event");

        TextView eventTextView = (TextView) findViewById(R.id.singleEvent);
        eventTextView.setText("Name: " + event.getName() + "\n" +
                "Location: " + event.getLocation() + "\n" +
                "Start Time: " + event.getStartTime() + "\n" +
                "End Time: " + event.getEndTime() + "\n" +
                "Category: " + event.getCategory() + "\n" +
                "Description: " + event.getDescription());
    }
}
