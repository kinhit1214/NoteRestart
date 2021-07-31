package com.example.noterestart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.GregorianCalendar;

public class MainActivity extends AppCompatActivity implements NoteMainFragment.Contract, CreatNoteFragment.Contract, DataPickerFragment.Contract {

    private static final String NOTE_BOX_KEY="NOTE_BOX_KEY";
    private static final String NOTE_CREAT_TAG = "NOTE_CREAT_TAG";

    NoteMainFragment noteMainFragment = new NoteMainFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNoteMain();
        initBottomNavigation();
    }

    public void showNote(NoteEntity note) {

        Bundle bundle = new Bundle();
        bundle.putStringArray(NOTE_BOX_KEY,new String[]{note.title,note.theme,note.text,note.data});

        Fragment fragNote = new CreatNoteFragment();
        fragNote.setArguments(bundle);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragments,fragNote)
                .commitAllowingStateLoss();
    }



    private void onSettings() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragments,new SettingsFragment())
                .commitAllowingStateLoss();
    }

    private void showNoteMain() {
        getSupportFragmentManager()
                .beginTransaction().
                replace(R.id.container_fragments,noteMainFragment)
                .commitAllowingStateLoss();
    }

    private void showCreatNote() {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_fragments,new CreatNoteFragment(),NOTE_CREAT_TAG)
                .commitAllowingStateLoss();
    }

    private void showRedNote(NoteEntity note,int position) {
        Bundle bundle = new Bundle();
        bundle.putStringArray(NOTE_BOX_KEY,new String[]{note.title,note.theme,note.text,note.data,String.valueOf(position)});
        Fragment fragRedNote = new CreatNoteFragment();
        fragRedNote.setArguments(bundle);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragments,fragRedNote)
                .commitAllowingStateLoss();
    }

    private void showDataPicker() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragments,new DataPickerFragment())
                .commitAllowingStateLoss();
    }

    private void initBottomNavigation() {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.creat_menu:
                                onCreatNote();
                                return true;
                            case R.id.title_menu:
                                getSupportFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.container_fragments,noteMainFragment)
                                        .commitAllowingStateLoss();
                                return true;
                            case R.id.settings_menu:
                                onSettings();
                                return true;
                        };
                        return false;
                    }
                }
        );
    }

    @Override
    public void onNote(NoteEntity note){showNote(note);}

    @Override
    public void onRedNote(NoteEntity note,int position) {showRedNote(note,position);}

    public void onCreatNote(){
        showCreatNote();
    }

    @Override
    public void onSaveNote(NoteEntity note) {
        getSupportFragmentManager().popBackStack();
        noteMainFragment.addNote(note);
    }

    @Override
    public void onDataPicker() {
        showDataPicker();
    }

    @Override
    public void saveData(GregorianCalendar calendar) {
        CreatNoteFragment noteCreat = (CreatNoteFragment) getSupportFragmentManager().findFragmentByTag(NOTE_CREAT_TAG);
        noteCreat.setData(calendar);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragments,noteCreat,NOTE_CREAT_TAG)
                .commitAllowingStateLoss();
    }
}