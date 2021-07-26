package com.example.noterestart;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NoteEntity implements Parcelable {
    public final String id;
    public final String title;
    public final String theme;
    public final String text;
    public final String data;

    public NoteEntity(String title, String theme, String text) {
        id = (Math.random()*100000)+"";
        this.title = title;
        this.theme = theme;
        this.text = text;
        data = getCurrentData();
    }
    public static String getCurrentData(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        return dateFormat.format(Calendar.getInstance().getTimeInMillis());
    }

    public String getId(){
        return id;
    }

    protected NoteEntity(Parcel in) {
        id = in.readString();
        title = in.readString();
        theme = in.readString();
        text = in.readString();
        data = in.readString();
    }

    public static final Creator<NoteEntity> CREATOR = new Creator<NoteEntity>() {
        @Override
        public NoteEntity createFromParcel(Parcel in) {
            return new NoteEntity(in);
        }

        @Override
        public NoteEntity[] newArray(int size) {
            return new NoteEntity[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(theme);
        dest.writeString(text);
        dest.writeString(data);
    }
}
