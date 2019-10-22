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
    private final Table<User> userTable;
    private final Table<Collaborator> collaboratorTable;

    public NoteDatabaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        noteTable = TableFactory.makeFactory(this, Note.class)
                .setSeedData(SampleData.getData())
                .useDateFormat("yyyyMMdd hh:mm:ss")
                .getTable();

        userTable = TableFactory.makeFactory(this, User.class)
                .setSeedData(SampleData.getUsers(context))
                .getTable();

        collaboratorTable = TableFactory.makeFactory(this, Collaborator.class)
                .getTable();
    }

    public Table<Note> getNoteTable(){
        return noteTable;
    }
    public Table<User> getUserTable(){
        return userTable;
    }
    public Table<Collaborator> getcollaboratorTable(){
        return collaboratorTable;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(noteTable.getCreateTableStatement());
        if (noteTable.hasInitialData())
        {
            noteTable.initialize(db);
        }

        db.execSQL(userTable.getCreateTableStatement());
        if (userTable.hasInitialData())
        {
            userTable.initialize(db);
        }

        db.execSQL(collaboratorTable.getCreateTableStatement());
        if (collaboratorTable.hasInitialData())
        {
            collaboratorTable.initialize(db);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(noteTable.getDropTableStatement());
        db.execSQL(noteTable.getCreateTableStatement());

        db.execSQL(userTable.getDropTableStatement());
        db.execSQL(userTable.getCreateTableStatement());

        db.execSQL(collaboratorTable.getDropTableStatement());
        db.execSQL(collaboratorTable.getCreateTableStatement());
    }
}
