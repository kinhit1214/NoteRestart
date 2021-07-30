package com.example.noterestart;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
        initRecycler(view);
        return view;
    }

    private void initRecycler(View view) {
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        AdapterItem adapterItem = new AdapterItem((ArrayList<NoteEntity>) notes);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL);
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.separator,null));
        recyclerView.addItemDecoration(dividerItemDecoration);

        recyclerView.setAdapter(adapterItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapterItem.setListener(
                new AdapterItem.OnItemClickListenner() {
                    @Override
                    public void OnItemClick(int position) {
                        getContract().onNote(notes.get(position));
                    }

                    @Override
                    public void OnItemLongClick(View parent,int position) {
                        PopupMenu popupMenu = new PopupMenu(getContext(),parent);
                        popupMenu.inflate(R.menu.popup_menu_note);
                        popupMenu.show();
                        popupMenu
                                .setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                                    @Override
                                    public boolean onMenuItemClick(MenuItem item) {
                                        if (item.getItemId() == R.id.menu_note_delte) {
                                            notes.remove(position);
                                            adapterItem.notifyDataSetChanged();
                                            return true;
                                        }
                                        return false;
                                    }
                                });
                    }
                }
        );
    }


    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected void addNote(NoteEntity note){
        notes.add(note);
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