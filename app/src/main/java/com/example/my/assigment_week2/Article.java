package com.example.my.assigment_week2;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by my on 3/21/2016.
 */
public class Article implements Parcelable {
    String webUrl;
    String headline;
    String thumnail;

    public String getWebUrl() {
        return webUrl;
    }

    public String getHeadline() {
        return headline;
    }

    public String getThumnail() {
        return thumnail;
    }

    public Article(JSONObject jsonObject){
        try {
            this.webUrl = jsonObject.getString("web_url");
            this.headline = jsonObject.getJSONObject("headline").getString("main");
            JSONArray multimedia = jsonObject.getJSONArray("multimedia");
            if(multimedia.length()>0) {
                JSONObject multimediaJson = multimedia.getJSONObject(0);
                this.thumnail ="http://www.nytimes.com/" + multimediaJson.getString("url");
            }
            else {
                this.thumnail="";
            }

        }catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
    public static ArrayList<Article> fromJSONArray(JSONArray array){
        ArrayList<Article> result = new ArrayList<>();
        for (int x=0; x<array.length();x++){
            try {
                result.add(new Article(array.getJSONObject(x)));
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return result;
    }

    protected Article(Parcel in) {
        webUrl = in.readString();
        headline = in.readString();
        thumnail = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(webUrl);
        dest.writeString(headline);
        dest.writeString(thumnail);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Article> CREATOR = new Parcelable.Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };
}