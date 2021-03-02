package org.techtown.doitmission14;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder> implements onProductItemClickListener{
    ArrayList<Product> items = new ArrayList<>();
    onProductItemClickListener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.product_item, parent, false);
        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductAdapter.ViewHolder holder, int position) {
        Product item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Product item) {
        items.add(item);
    }

    public Product getItem(int position) {
        return items.get(position);
    }

    public void setItems(ArrayList<Product> items) {
        this.items = items;
    }

    public void setItem(int position, Product item) {
        items.set(position, item);
    }

    public void setOnItemListener(onProductItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewName, textViewCost, textViewDetail;

        public ViewHolder(@NonNull View itemView, onProductItemClickListener listener) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            textViewCost = itemView.findViewById(R.id.textViewCost);
            textViewDetail = itemView.findViewById(R.id.textViewDetail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, v, position);
                    }
                }
            });
        }

        public void setItem(Product item) {
            textViewName.setText(item.getName());
            textViewCost.setText(item.getCost());
            textViewDetail.setText(item.getDetail());
        }
    }
}
