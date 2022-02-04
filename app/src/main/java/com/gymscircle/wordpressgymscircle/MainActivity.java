package com.gymscircle.wordpressgymscircle;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;
    NavigationView navView;
    RecyclerView post_list;
    List<Posts> posts;
    PostAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);

        posts = new ArrayList<>();

        drawer = findViewById(R.id.drawer);
        post_list = findViewById(R.id.post_list);

        toggle = new ActionBarDrawerToggle(this,drawer,toolBar,R.string.open,R.string.close);
        navView = findViewById(R.id.navigation_view);

        toggle.setDrawerIndicatorEnabled(true);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        extractPosts(getResources().getString(R.string.URL));

        GridLayoutManager manager = new GridLayoutManager(this,2);
        post_list.setLayoutManager(manager);

        adapter = new PostAdapter(posts);
        post_list.setAdapter(adapter);


    }
    public void extractPosts(String URL){
        //use Volley to extract the data

        RequestQueue queue = Volley.newRequestQueue(this);

        @SuppressLint("NotifyDataSetChanged") JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, URL, null, response -> {
            Log.d("TAG", "onResponse: " + response.toString());
            for(int i = 0; i < response.length();i++){
                //extract date
                try {
                    Posts p = new Posts();

                    JSONObject jsonObjectData = response.getJSONObject(i);
                    //extract date
                    p.setDate(jsonObjectData.getString("date"));
                    //extract feature image
                    p.setFeatureImage(jsonObjectData.getString("0"));
                    //extract Title
                    JSONObject titleObject = jsonObjectData.getJSONObject("title");
                    p.setTitle(titleObject.getString("rendered"));

                    //extract content
                    JSONObject contentObject = jsonObjectData.getJSONObject("content");
                    p.setTitle(contentObject.getString("rendered"));

                    //extract excerpt
                    JSONObject excerptObject = jsonObjectData.getJSONObject("excerpt");
                    p.setTitle(excerptObject.getString("rendered"));

                    posts.add(p);
                    adapter.notifyDataSetChanged();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, error -> Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show());

        queue.add(request);
    }
}