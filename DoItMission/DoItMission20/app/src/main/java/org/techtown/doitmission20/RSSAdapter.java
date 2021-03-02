package org.techtown.doitmission20;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RSSAdapter extends RecyclerView.Adapter<RSSAdapter.ViewHolder> {
    ArrayList<RSSItem> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.rss_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RSSItem item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<RSSItem> items) {
        this.items = items;
    }

    public RSSItem getItem(int position) {
        return items.get(position);
    }

    public void setItem(RSSItem item, int position) {
        items.set(position, item);
    }

    public void addItem(RSSItem item) {
        items.add(item);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewTitle, textViewContents;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewContents = itemView.findViewById(R.id.textViewContents);

        }

        public void setItem(RSSItem item) {
            textViewTitle.setText(item.getTitle());
            textViewContents.setText(item.getContents());
        }
    }
}
