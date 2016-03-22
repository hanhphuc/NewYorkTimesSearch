package com.example.my.assigment_week2;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by my on 3/21/2016.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context, List<Article> articles) {
        super(context, android.R.layout.simple_list_item_1, articles);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //get the data item for position
        Article article = this.getItem(position);
        //not using a recycle view -> inflate the layout
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.list_item_result, parent, false);

        }
        //find the ImageView
        ImageView imageView = (ImageView) convertView.findViewById(R.id.ivImage);

        // clear out recycle image from convertview from last time

        imageView.setImageResource(0);

        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);

        tvTitle.setText(article.getHeadline());

        String thumnails = article.getThumnail();
        if (!TextUtils.isEmpty(thumnails)) {
            Picasso.with(getContext()).load(thumnails).into(imageView);


        }


        //popular the thumnail

        return convertView;
    }
}