package org.techtown.doitmission09;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MainFragment extends Fragment {
    private TextView textView;
    private EditText editTextName, editTextAge;
    private Button birthButton, saveButton;
    Date selectedDate;
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_main, container, false);

        birthButton = rootView.findViewById(R.id.birthButton);
        birthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String birthDate = birthButton.getText().toString();

                Calendar calendar = Calendar.getInstance();
                Date curBirthDate = new Date();
                try {
                    curBirthDate = dateFormat.parse(birthDate);
                } catch(Exception ex) {
                    ex.printStackTrace();
                }

                calendar.setTime(curBirthDate);

                int curYear = calendar.get(Calendar.YEAR);
                int curMonth = calendar.get(Calendar.MONTH);
                int curDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(),  birthDateSetListener,  curYear, curMonth, curDay);
                dialog.show();
            }
        });

        editTextName = rootView.findViewById(R.id.editTextName);
        editTextAge = rootView.findViewById(R.id.editTextAge);

        saveButton = rootView.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String age = editTextAge.getText().toString();
                String date = birthButton.getText().toString();

                Toast.makeText(getActivity(), "이름 : " + name +
                        "\n나이 : " + age + "\n생년월일 : " + date, Toast.LENGTH_LONG).show();
            }
        });

        return rootView;
    }

    private DatePickerDialog.OnDateSetListener birthDateSetListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            Calendar selectedCalendar = Calendar.getInstance();
            selectedCalendar.set(Calendar.YEAR, year);
            selectedCalendar.set(Calendar.MONTH, monthOfYear);
            selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            Date curDate = selectedCalendar.getTime();
            setSelectedDate(curDate);
        }
    };

    private void setSelectedDate(Date curDate) {
        selectedDate = curDate;

        String selectedDate = dateFormat.format(curDate);
        birthButton.setText(selectedDate);
    }
}