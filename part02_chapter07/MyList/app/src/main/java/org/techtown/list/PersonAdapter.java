package org.techtown.list;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {
    ArrayList<Person> items = new ArrayList<Person>();
    OnPersonItemClickListener listener;

    public void addItem (Person item) {      //ArrayList에 item 하나 추가
        items.add(item);
    }

    public void setItems (ArrayList<Person> items) { //전체 ArrayList 추가
        this.items = items;
    }

    public Person getItem (int position) {
        return items.get(position);
    }

    public void setItem (int position, Person item) {   //변경
        items.set(position, item);
    }

    public void setOnItemClickListener(OnPersonItemClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    //ViewHolder를 생성하는 시점에 자동으로 호출
    //각 아이템을 위한 레이아웃을 인플레이션
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.person_item, parent, false);
        return new ViewHolder(itemView, listener);
    }

    @Override
    //ViewHolder 객체를 새로 만들지 않고 데이터만 설정해주기
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    //객체에 뷰를 담아두고 필요할때 재사용하는 홀더
    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView, textView2;

        public ViewHolder(View itemView, final OnPersonItemClickListener listner) {  //itemView는 현재 LinearLayout을 가리킴
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View itemView) {
                    int position = getAdapterPosition();

                    if (listner != null) {
                        listner.onItemClick(ViewHolder.this, itemView, position);
                    }

                }
            });
        }

        public void setItem (Person item) {
            textView.setText(item.getName());
            textView2.setText(item.getMobile());
        }
    }




}
