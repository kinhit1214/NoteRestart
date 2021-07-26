package com.example.noterestart;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends AppCompatActivity implements NoteMainFragment.Contract, CreatNoteFragment.Contract {
    private static final String NOTES_LIST_FRAGMENT_TAG="NOTES_LIST_FRAGMENT_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showNoteMain();
    }

    private void showNoteMain() {
        getSupportFragmentManager()
                .beginTransaction().
                add(R.id.container_fragments,new NoteMainFragment(),"NOTES_LIST_FRAGMENT_TAG")
                .commitAllowingStateLoss();
    }

    @Override
    public void onCreatNote() {
        showCreaNote(null);
    }

    @Override
    public void onNote(NoteEntity note) {
        Bundle args = new Bundle();
        args.putString("putNote", String.valueOf(note.getId()));
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_fragments,new CreatNoteFragment())
                .commit();
    }

    private void showCreaNote(NoteEntity note) {
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.container_fragments,new CreatNoteFragment())
                .commitAllowingStateLoss();
    }

    public void creatNote(NoteEntity note){
        showCreaNote(note);
    }

    @Override
    public void onSaveNote(NoteEntity note) {
        getSupportFragmentManager().popBackStack();
        NoteMainFragment noteMainFragment =(NoteMainFragment)getSupportFragmentManager() .findFragmentByTag(NOTES_LIST_FRAGMENT_TAG);
        noteMainFragment.addNote(note);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bottom_line,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int idMenuItem = item.getItemId();
        switch (idMenuItem){
            case (R.id.title_menu):;
            case (R.id.edit_menu):;
            case (R.id.settings_menu):;
        }
        return super.onOptionsItemSelected(item);
    }
}