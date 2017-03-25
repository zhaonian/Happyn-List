package com.searchsystem.mycommunity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

/**
 * Created by Luan on 3/20/17.
 */

public class SearchEngineActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seach_engine);

        String url = "http://e8yes.org:8080/e8yesearch";
        WebView view = (WebView) this.findViewById(R.id.webView);
        view.getSettings().getJavaScriptEnabled();
        view.loadUrl(url);
    }
}
