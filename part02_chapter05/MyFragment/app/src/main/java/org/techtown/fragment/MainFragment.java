package org.techtown.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainFragment extends Fragment {
    private Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //XML 레이아웃 내용을 메모리에 올려주는 작업
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);
        button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //전환은 액티비티에 위임
                MainActivity activity = (MainActivity) getActivity();
                activity.onFragmentChanged(1);
            }
        });

        return rootView;
    }
}