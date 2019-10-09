package com.example.createnote.sqlite;

/**
 * Represents a database error.
 *
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class DatabaseException extends Exception {

    public DatabaseException() {

    }

    public DatabaseException(String s) {
        super(s);
    }

    public DatabaseException(Exception e) {
        super(e);
    }
}
