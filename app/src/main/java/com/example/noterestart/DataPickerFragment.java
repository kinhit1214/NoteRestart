package com.example.noterestart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import java.time.Year;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class DataPickerFragment extends Fragment {

    public DataPickerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_data_picker, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatePicker datePicker = view.findViewById(R.id.data_picker);
        Calendar today = Calendar.getInstance();
        Button buttonSaveData = view.findViewById(R.id.button_save_data);
        GregorianCalendar calendar = new GregorianCalendar();
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH), today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                calendar.set(year,monthOfYear,dayOfMonth);
            }
        });
        buttonSaveData.setOnClickListener(v -> getContrct().saveData(calendar));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(getContext() instanceof Contract))
            throw new IllegalStateException("ll");
    }

    private Contract getContrct(){
        return (Contract)getActivity();
    }

    interface Contract{
        public void saveData(GregorianCalendar calendar);
    }
}