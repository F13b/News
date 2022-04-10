package com.example.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<ViewHolder> {

    interface OnStateClickListener{
        void onStateClick(Item items, int position);
    }
    private final OnStateClickListener onClickListener;

    Context context;
    List<Item> items;

    public Adapter(OnStateClickListener onClickListener, Context context, List<Item> items) {
        this.onClickListener = onClickListener;
        this.context = context;
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Item model = items.get(position);
        holder.newsNameView.setText(model.getNewsName());
        holder.newsTextView.setText(model.getNewsText());
        holder.itemView.setOnClickListener(view -> {
            onClickListener.onStateClick(model, position);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
