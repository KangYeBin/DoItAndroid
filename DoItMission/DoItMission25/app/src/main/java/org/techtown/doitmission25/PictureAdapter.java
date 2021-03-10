package org.techtown.doitmission25;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
    ArrayList<PictureInfo> items = new ArrayList<>();

    @NonNull
    @Override
    public PictureAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =  LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.picture_item, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureAdapter.ViewHolder holder, int position) {
        PictureInfo item = items.get(position);
        holder.setItem(item);
    }

    public void addItem(PictureInfo item) {
        items.add(item);
    }

    public PictureInfo getItem(int position) {
        return items.get(position);
    }

    public void setItem(PictureInfo item, int position) {
        items.set(position, item);
    }

    public void setItems(ArrayList<PictureInfo> items) {
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        EditText editTextName, editTextDate;
        BitmapFactory.Options options = new BitmapFactory.Options();

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
            editTextName = itemView.findViewById(R.id.editTextName);
            editTextDate = itemView.findViewById(R.id.editTextDate);

            options.inSampleSize = 12;
        }

        public void setItem(PictureInfo item) {
            Bitmap bitmap = BitmapFactory.decodeFile(item.getImage(), options);
            imageView.setImageBitmap(bitmap);
            editTextName.setText(item.getName());
            editTextDate.setText(item.getDate());
        }
    }
}
