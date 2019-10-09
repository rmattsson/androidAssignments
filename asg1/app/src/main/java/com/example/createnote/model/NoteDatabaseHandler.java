package com.example.createnote.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.createnote.sqlite.Table;
import com.example.createnote.sqlite.TableFactory;

public class NoteDatabaseHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "note.bd";
    public static final int DATABASE_VERSION = 1;
    private final Table<Note> noteTable;

    public NoteDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        noteTable = TableFactory.makeFactory(this, Note.class)
                .setSeedData(SampleData.getData())
                .useDateFormat("yyyyMMdd hh:mm:ss")
                .getTable();
    }

    public Table<Note> getNoteTable(){
        return noteTable;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(noteTable.getCreateTableStatement());
        if (noteTable.hasInitialData())
        {
            noteTable.initialize(db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(noteTable.getDropTableStatement());
        db.execSQL(noteTable.getCreateTableStatement());
    }
}
