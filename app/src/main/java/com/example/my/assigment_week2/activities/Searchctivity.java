package com.example.my.assigment_week2.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.my.assigment_week2.Article;
import com.example.my.assigment_week2.ArticleAdapter;
import com.example.my.assigment_week2.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;

public class Searchctivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{
    Button btnSearch;
    GridView gvReasult;
    EditText etQuery;

    ArrayList<Article> articles;
    ArticleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchctivity);
        setupView();
       isOnline();


    }
    public void  setupView(){
        btnSearch = (Button)findViewById(R.id.btnSearch);
        gvReasult = (GridView)findViewById(R.id.gvResult);
        etQuery = (EditText)findViewById(R.id.etQuery);
        articles = new ArrayList<>();
        adapter = new ArticleAdapter(this,articles);
        gvReasult.setAdapter(adapter);
        gvReasult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(Searchctivity.this,"hello",Toast.LENGTH_LONG).show();
                // Create an intent to display Article
                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                //Get the Article to display
                Article article = articles.get(position);
                // Pass that article to intent
                i.putExtra("article", (Parcelable) article);
                //i.putExtra("url",article.getWebUrl());
                // Launch the Activity
                startActivity(i);


            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_searchctivity, menu);
        MenuItem shareItem = menu.findItem(R.id.action_share);
        ShareActionProvider myShareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onArticleSearch(View view) {
        String query = etQuery.getText().toString();

        Toast.makeText(this, "Searching for" + query, Toast.LENGTH_LONG).show();
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "http://api.nytimes.com/svc/search/v2/articlesearch.json";

        RequestParams params = new RequestParams();
        params.put("api-key", "dd45a4863c89b95f37b205cbc935c737:5:74754793");
        params.put("page", 0);
        params.put("q", query);

        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("DEBUG : ", response.toString());
                JSONArray articleJsonresult = null;

                try {
                    articleJsonresult = response.getJSONObject("response").getJSONArray("docs");
                    Log.d("Debug: ", articleJsonresult.toString());
                    adapter.addAll(Article.fromJSONArray(articleJsonresult));
                    adapter.notifyDataSetChanged();
                    Log.d("Debug: ", adapter.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

        });

        }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }


    public void setting(MenuItem item) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentDialog fragmentDialog = FragmentDialog.newInstance("Some Title");
        fragmentDialog.show(fm, "fragment_edit_name");
    }
    public void showDatePickerDialog(View v) {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    // handle the date selected
    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        // store the values selected into a Calendar instance
        final Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, monthOfYear);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
    }

}

