package com.thakur.project1;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class GoogleSearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private Button searchButton;
    private ListView resultsListView;

    private RequestQueue requestQueue;
    private ArrayAdapter<String> resultsAdapter;

    private final String API_KEY = "YOUR_API_KEY";
    private final String SEARCH_ENGINE_ID = "34ecd9802c1b64747";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_googlesearch);

        searchEditText = findViewById(R.id.searchEditText);
        searchButton = findViewById(R.id.searchButton);
        resultsListView = findViewById(R.id.resultsListView);

        requestQueue = Volley.newRequestQueue(this);
        resultsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        resultsListView.setAdapter(resultsAdapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });
    }

    private void performSearch() {
        String query = searchEditText.getText().toString();
        String url = "https://www.googleapis.com/customsearch/v1?key=" + API_KEY + "&cx=" + SEARCH_ENGINE_ID + "&q=" + query;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseSearchResults(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                    }
                });

        requestQueue.add(request);
    }

    private void parseSearchResults(JSONObject response) {
        resultsAdapter.clear();
        try {
            JSONArray items = response.getJSONArray("items");
            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                String title = item.getString("title");
                resultsAdapter.add(title);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
