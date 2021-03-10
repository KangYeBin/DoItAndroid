package org.techtown.doitmission29;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> implements OnFriendItemClickListner {
    ArrayList<FriendInfo> items = new ArrayList<>();
    OnFriendItemClickListner listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.friend_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.setItem(items.get(position));
    }

    public void addItem(FriendInfo item) {
        items.add(item);
    }

    public void setItem(FriendInfo item, int position) {
        items.set(position, item);
    }

    public void setItems(ArrayList<FriendInfo> items) {
        this.items = items;
    }

    public FriendInfo getItem(int position) {
        return items.get(position);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnFriendItemClickListner listener) {
        this.listener = listener;
    }
    @Override
    public void onItemClick(View view, int position) {
        if (listener != null) {
            listener.onItemClick(view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewPhone;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (listener != null) {
                            listener.onItemClick(itemView, position);
                        }
                    }
                }
            });
        }

        public void setItem(FriendInfo item) {
            textViewName.setText(item.getName());
            textViewPhone.setText(item.getPhone());
        }
    }
}
