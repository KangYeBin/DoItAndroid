package org.techtown.fragment2;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ListFragment extends Fragment {
    private Button button, button2, button3;
    ImageSelectionCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        if (context instanceof ImageSelectionCallback) {
            callback = (ImageSelectionCallback) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_list, container, false);

        button = rootView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                이렇게 해도 가능하지만 MainActivity가 아니어도 사용할 수 있도록 onAttach 이용
//                MainActivity mainActivity = (MainActivity) getActivity();
//                mainActivity.onImageSelected(0);
                if (callback != null)
                    callback.onImageSelected(0);
            }
        });

        button2 = rootView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onImageSelected(1);
            }
        });

        button3 = rootView.findViewById(R.id.button3);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null)
                    callback.onImageSelected(2);
            }
        });
        return rootView;
    }
}
