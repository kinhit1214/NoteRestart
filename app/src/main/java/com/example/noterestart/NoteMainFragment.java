package com.example.noterestart;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class NoteMainFragment extends Fragment {
    private LinearLayout linerNotes;

    private ArrayList<NoteEntity> notes = new ArrayList<>();

//    private Contract contract; Нельзя

    public NoteMainFragment() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note_main, container, false);
        linerNotes = view.findViewById(R.id.liner_notes);
        renderList(notes);
        return view;
    }

    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    protected void addNote(NoteEntity note){
        notes.add(note);
    }

    private void renderList(List<NoteEntity> notes) {
        linerNotes.removeAllViews();
        for (NoteEntity note :
                notes) {
            linerNotes.addView(generetNoteView(note));
        }
    }

    private View generetNoteView(NoteEntity note) {
        View noteView = getLayoutInflater().inflate(R.layout.note,null);
        TextView noteTitle = (TextView) noteView.findViewById(R.id.note_title);
        TextView noteTheme = (TextView) noteView.findViewById(R.id.note_theme);
        TextView noteTime = (TextView) noteView.findViewById(R.id.note_time);
        noteTitle.setText(String.format(Locale.getDefault(), "%s",note.title ));
        noteTheme.setText(String.format(Locale.getDefault(), "%s", note.theme));
        noteTime.setText(String.format(Locale.getDefault(), "%s", note.data));
        noteView.setOnClickListener(v -> getContract().onNote(note));
        noteView.setOnLongClickListener(v -> {
            PopupMenu popupMenu = new PopupMenu(getContext(),noteView);
            popupMenu.inflate(R.menu.popup_menu_note);
            popupMenu.show();
            popupMenu
                    .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            if (item.getItemId() == R.id.menu_note_delte) {
                                noteTitle.setText(String.format(Locale.getDefault(), "%s","Удален"));
                                return true;
                            }
                            return false;
                        }
                    });
            return true;
        });
        return noteView;
    }

    private Contract getContract(){
        return (Contract)getActivity();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof Contract)) {
            throw new IllegalStateException("активити не поддерживает контракт");
        }
    }
    interface Contract{
        void onNote(NoteEntity note);
    }
}