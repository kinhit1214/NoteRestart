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
    private EditText textEditText;

    private static final String NOTE_BOX_KEY="NOTE_BOX_KEY";

    public CreatNoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_creat_note, container, false);

        saveButton = view.findViewById(R.id.save);
        cancelButton = view.findViewById(R.id.cancel);
        textEditText = view.findViewById(R.id.edit_text);
        titleEditText = view.findViewById(R.id.edit_name);
        themeEditText = view.findViewById(R.id.edit_theme);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            String[] noteData = bundle.getStringArray(NOTE_BOX_KEY);
            initNote(noteData);
            if (noteData.length<4){
                titleEditText.setFocusable(false);
                titleEditText.setLongClickable(false);
                themeEditText.setFocusable(false);
                themeEditText.setLongClickable(false);
                textEditText.setFocusable(false);
                textEditText.setLongClickable(false);
                saveButton.setVisibility(View.INVISIBLE);
                cancelButton.setVisibility(View.INVISIBLE);
            }
        }
        return view;
    }

    private void initNote(String[] noteData) {
        titleEditText.setText(noteData[0]);
        themeEditText.setText(noteData[1]);
        textEditText.setText(noteData[2]);
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
                textEditText.getText().toString());
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
       // void onSaveRedNote()
    }
}