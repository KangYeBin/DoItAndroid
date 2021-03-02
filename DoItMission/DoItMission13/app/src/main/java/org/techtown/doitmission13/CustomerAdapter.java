package org.techtown.doitmission13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> {
    ArrayList<Customer> items = new ArrayList<>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.customer_item, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer item = items.get(position);
        holder.setItem(item);
    }

    public void addItem(Customer item) {
        items.add(item);
    }

    public Customer getItem(int position) {
        return items.get(position);
    }

    public void setItem(int position, Customer item) {
        items.set(position, item);
    }

    public void setItems(ArrayList<Customer> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName, textViewDate, textViewPhone;

        public ViewHolder(View itemView, CustomerAdapter customerAdapter) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewPhone = itemView.findViewById(R.id.textViewPhone);
        }

        public void setItem(Customer item) {
            textViewName.setText(item.getName());
            textViewDate.setText(item.getDate());
            textViewPhone.setText(item.getPhone());
        }
    }
}
