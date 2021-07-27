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

public class MainActivity extends AppCompatActivity implements NoteMainFragment.Contract, CreatNoteFragment.Contract {
    private static final String NOTES_LIST_FRAGMENT_TAG="NOTES_LIST_FRAGMENT_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNoteMain();
        initBottomNavigation();
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
                                        .beginTransaction().
                                        replace(R.id.container_fragments,getSupportFragmentManager().findFragmentByTag(NOTES_LIST_FRAGMENT_TAG))
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



    private void onSettings() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container_fragments,new SettingsFragment())
                .commitAllowingStateLoss();
    }

    private void showNoteMain() {
        getSupportFragmentManager()
                .beginTransaction().
                add(R.id.container_fragments,new NoteMainFragment(),"NOTES_LIST_FRAGMENT_TAG")
                .commitAllowingStateLoss();
    }

    public void onCreatNote() {
        showCreatNote(null);
    }

    private void showCreatNote(NoteEntity note) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_fragments,new CreatNoteFragment())
                .commitAllowingStateLoss();
    }

    @Override
    public void onNote(NoteEntity note){showNote(note);}

    public void showNote(NoteEntity note) {
        Bundle args = new Bundle();
        args.putString("putNote", String.valueOf(note.getId()));
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_fragments,new CreatNoteFragment())
                .commit();
    }

    public void creatNote(NoteEntity note){
        showCreatNote(note);
    }

    @Override
    public void onSaveNote(NoteEntity note) {
        getSupportFragmentManager().popBackStack();
        NoteMainFragment noteMainFragment =(NoteMainFragment)getSupportFragmentManager() .findFragmentByTag(NOTES_LIST_FRAGMENT_TAG);
        noteMainFragment.addNote(note);
    }
}