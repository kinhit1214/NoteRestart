package com.example.noterestart;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Locale;


public class CreatNoteFragment extends Fragment {
    private Button saveButton;
    private Button cancelButton;
    private EditText titleEditText;
    private EditText themeEditText;
    private EditText textEdittext;


    public CreatNoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
     //   String strText = getArguments().getString("putNote");
        View view = inflater.inflate(R.layout.fragment_creat_note, container, false);
        saveButton = view.findViewById(R.id.save);
        cancelButton = view.findViewById(R.id.cancel);
        textEdittext = view.findViewById(R.id.edit_text);
        titleEditText = view.findViewById(R.id.edit_name);
        themeEditText = view.findViewById(R.id.edit_theme);
      //  if (strText!=null)
       //     titleEditText.setText(String.format(Locale.getDefault(),"%s",strText));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        saveButton.setOnClickListener(v -> {
            getContract().onSaveNote(gatherNote());
        });
        cancelButton.setOnClickListener(v ->{
            getActivity().getSupportFragmentManager().popBackStack();
        });
    }

    private NoteEntity gatherNote() {
        return new NoteEntity(
                titleEditText.getText().toString(),
                themeEditText.getText().toString(),
                textEdittext.getText().toString());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract))
            throw new IllegalStateException("ll");
    }

    private Contract getContract(){
        return (Contract)getActivity();
    }
    interface Contract{
        void onSaveNote(NoteEntity note);
    }
}