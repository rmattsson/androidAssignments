package com.example.createnote.model;


import com.example.createnote.sqlite.Identifiable;

public class Collaborator implements Identifiable<Long> {

    private long id;
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
}

