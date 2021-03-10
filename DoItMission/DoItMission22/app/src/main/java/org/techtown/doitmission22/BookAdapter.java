package org.techtown.doitmission22;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.ViewHolder> {
    ArrayList<BookInfo> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.book_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BookInfo item = getItem(position);
        holder.setItem(item);
    }

    public BookInfo getItem(int position) {
        return items.get(position);
    }

    public void addItem(BookInfo item) {
        items.add(item);
    }

    public void setItems(ArrayList<BookInfo> items) {
        this.items = items;
    }

    public void setItem(BookInfo item, int position) {
        items.set(position, item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        EditText showTitle, showAuthor, showContents;

        public ViewHolder(View itemView) {
            super(itemView);
            showTitle = itemView.findViewById(R.id.showTitle);
            showAuthor = itemView.findViewById(R.id.showAuthor);
            showContents = itemView.findViewById(R.id.showContents);
        }

        public void setItem(BookInfo item) {
            showTitle.setText(item.getTitle());
            showAuthor.setText(item.getAuthor());
            showContents.setText(item.getContents());
        }
    }
}
