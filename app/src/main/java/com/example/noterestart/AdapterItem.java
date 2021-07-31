package com.example.noterestart;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterItem extends RecyclerView.Adapter<AdapterItem.ItemViewHolder> {
    private ArrayList<NoteEntity> notes;

    private  OnItemClickListenner listener;

    public AdapterItem(ArrayList<NoteEntity> notes){
        this.notes = notes;
    }

    public  void setListener(@Nullable OnItemClickListenner listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note,parent,false);
        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterItem.ItemViewHolder holder, int position) {
        holder.getNoteTitle().setText(notes.get(position).title);
        holder.getNoteTheme().setText(notes.get(position).theme);
        holder.getNoteTime().setText(notes.get(position).data.toString());
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{
        TextView noteTitle;
        TextView noteTheme;
        TextView noteTime;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle = (TextView) itemView.findViewById(R.id.note_title);
            noteTheme = (TextView) itemView.findViewById(R.id.note_theme);
            noteTime = (TextView) itemView.findViewById(R.id.note_time);
            itemView.setOnClickListener(v -> {
                listener.OnItemClick(getAdapterPosition());
            });
            itemView.setOnLongClickListener(v -> {
                listener.OnItemLongClick(itemView,getAdapterPosition());
                return false;
            });
        }

        public TextView getNoteTheme() { return noteTheme; }

        public TextView getNoteTime() { return noteTime; }

        public TextView getNoteTitle() { return noteTitle; }
    }

    interface OnItemClickListenner{
        void OnItemClick(int position);
        void OnItemLongClick(View parent, int position);
    }
}
