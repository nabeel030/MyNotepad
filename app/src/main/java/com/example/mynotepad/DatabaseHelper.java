package com.example.mynotepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "notesStore", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table user(id integer primary key autoincrement, username text not null unique, password text, password_hint text)");
        db.execSQL("create table mynotes(id integer primary key autoincrement, name text, title text unique, note text unique)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
        db.execSQL("drop table if exists mynotes");
    }

    public boolean register_user(String username, String password, String password_hint)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("username",username);
        values.put("password",password);
        values.put("password_hint",password_hint);

        long status = db.insert("user",null,values);

        if (status==-1)
            return false;
        else
            return true;
    }

    public Cursor getUsers()
    {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("select *from user",null);
        return cursor;
    }

    public boolean insertNote(String name, String title, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("name",name);
        values.put("title",title);
        values.put("note",note);

        long status = db.insert("mynotes",null,values);

        if (status==-1)
            return false;
        else
            return true;
    }

    public ArrayList<MyNote> getNotes(String user)
    {
        ArrayList<MyNote> myNotes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("Select * from mynotes",null);

        while (cursor.moveToNext())
        {
            if (user.equals(cursor.getString(1))) {

                    MyNote noteList = new MyNote();
                    noteList.setTitle(cursor.getString(2));
                    noteList.setNote(cursor.getString(3));
                    myNotes.add(noteList);
            }
        }

        return myNotes;
    }

    public Cursor getSelectedNote()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from mynotes",null);
        return cursor;
    }

    public void updateNote(String mytitle, String note)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("note",note);
        db.update("mynotes",values,"title = ?",new String[] { mytitle });
    }

    public void deleteNote(String title)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("mynotes","title =?",new String[] { title });
    }
}
