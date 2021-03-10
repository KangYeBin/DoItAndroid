package org.techtown.doitmission22;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fragment1 extends Fragment {
    EditText editTextTitle, editTextAuthor, editTextContents;
    Button saveButton;
    onDatabaseCallback callback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        callback = (onDatabaseCallback) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_1, container, false);

        editTextTitle = rootView.findViewById(R.id.editTextTitle);
        editTextAuthor = rootView.findViewById(R.id.editTextAuthor);
        editTextContents = rootView.findViewById(R.id.editTextContents);

        saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTextTitle.getText().toString();
                String author = editTextAuthor.getText().toString();
                String contents = editTextContents.getText().toString();

                callback.insert(title, author, contents);
                Toast.makeText(getContext(), "책 정보를 추가했습니다.", Toast.LENGTH_SHORT).show();

                editTextTitle.setText(null);
                editTextAuthor.setText(null);
                editTextContents.setText(null);
            }
        });

        return rootView;
    }
}