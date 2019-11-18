package com.example.createnote.model;


import com.example.createnote.sqlite.Identifiable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

public class Collaborator implements Identifiable<Long> {


    public String getNote() {
        return note;
    }

    public String getUser() {
        return user;
    }

    @Expose
    private long id;
    @Expose
    private String note;
    @Expose
    private String user;

    private long noteId;
    private long userId;


    public Collaborator() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public long getNoteId() {
        return noteId;
    }

    public Collaborator setNoteId(long noteId) {
        this.noteId = noteId;
        return this;
    }

    public long getUserId() {
        return userId;
    }

    public Collaborator setUserId(long userId) {
        this.userId = userId;
        return this;
    }

    public Collaborator setNote(String noteId) {

        this.note = noteId;
        return this;
    }

    public Collaborator setUser(String userId) {
        this.user = userId;
        return this;
    }

    public String format()
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }
}

