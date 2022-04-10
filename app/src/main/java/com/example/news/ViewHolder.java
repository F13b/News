package com.example.news;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolder extends RecyclerView.ViewHolder {

    TextView newsNameView, newsTextView;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        newsNameView = itemView.findViewById(R.id.txtNameNews);
        newsTextView = itemView.findViewById(R.id.txtTextNews);
    }
}
