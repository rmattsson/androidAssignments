package com.example.createnote.model;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.example.createnote.sqlite.Identifiable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Date;

/**
 * Represent a single note in the "Notes" app.
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Note implements Identifiable<Long>, Parcelable {

    public class Notes {
        public class Embedded {
            public Note[] notes;
        }

        private Embedded _embedded;
    }

    public class Links {
        public class Link{
            public URL href;
        }

        private Link self;
        private Link note;
        private Link collaborators;

    }

    public Links _links;

    // basic note elements
    private String Uuid;
    private long id;

    @Expose
    private String title;
    @Expose
    private String body;
    @Expose
    private Category category;

    // reminders
    private boolean hasReminder;
    @Expose
    private Date reminder;

    // creation and modification times.
    @Expose
    private Date created;
    @Expose
    private Date modified;

    /**
     * Create a blank note.
     */
    public Note() {
        this(-1);
    }

    /**
     * Create a blank note with a specific ID.
     * @param id
     */
    public Note(long id) {
        this.id = id;
        this.title = "";
        this.body = "";
        this.category = Category.ORANGE;
        this.hasReminder = false;
        this.reminder = null;
        this.created = new Date();
        this.modified = new Date();
    }

    /**
     * Create a note.
     * @param id
     * @param title
     * @param body
     * @param category
     * @param hasReminder
     * @param reminder
     * @param created
     * @param modified
     */
    public Note(long id, String title, String body, Category category, boolean hasReminder, Date reminder, Date created, Date modified) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.category = category;
        this.hasReminder = hasReminder;
        this.reminder = reminder;
        this.created = created;
        this.modified = modified;
    }


    protected Note(Parcel in) {
        id = in.readLong();
        title = in.readString();
        body = in.readString();
        category = Category.values()[in.readInt() -1];
        hasReminder = in.readByte() != 0;
        reminder=null;
        if (hasReminder) {
            reminder = new Date(in.readLong());
        }
        created = new Date(in.readLong());
        modified = new Date(in.readLong());
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public Note setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public Note setBody(String body) {
        this.body = body;
        return this;
    }

    public Category getCategory() {
        return category;
    }

    public Note setCategory(Category category) {
        this.category = category;
        return this;
    }

    public boolean isHasReminder() {
        return hasReminder;
    }

    public Note setHasReminder(boolean hasReminder) {
        this.hasReminder = hasReminder;
        return this;
    }

    public Date getReminder() {
        return reminder;
    }

    public Note setReminder(Date reminder) {
        this.reminder = reminder;
        return this;
    }

    public Date getCreated() {
        return created;
    }

    public Note setCreated(Date created) {
        this.created = created;
        return this;
    }

    public Date getModified() {
        return modified;
    }

    public Note setModified(Date modified) {
        this.modified = modified;
        return this;
    }
    public String getUuid() {
        return Uuid;
    }

    public Note setUuid(String Uuid) {
        this.Uuid = Uuid;
        return this;
    }

    /**
     * Create a duplicate (aka clone) of the note.
     * @return
     */
    public Note clone() {
        Note clone = new Note();
        clone.id = this.id;
        clone.title = this.title;
        clone.body = this.body;
        clone.category = this.category;
        clone.created = this.created;
        clone.hasReminder = this.hasReminder;
        clone.reminder = this.reminder;
        clone.modified = this.modified;
        return clone;
    }

    @Override
    public String toString() {
        /*
        return "Note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", category=" + category +
                ", hasReminder=" + hasReminder +
                ", reminder=" + reminder +
                ", created=" + created +
                ", modified=" + modified +
                '}';
                */
         return title;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //@RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(id);
        parcel.writeString(title);
        parcel.writeString(body);
        parcel.writeInt(category.getColorId());

        if(hasReminder)
        {
            parcel.writeByte((byte) 1);
            parcel.writeLong(reminder.getTime());
        }
        else
        {
            parcel.writeByte((byte) 0);
        }



        parcel.writeLong(created.getTime());
        parcel.writeLong(modified.getTime());
    }

    public String format()
    {
    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").create();
    return gson.toJson(this);
    }
    public static Note parse(String json)
    {
        Gson g = new Gson();
        Note n = g.fromJson(json, Note.class);
        if (n.reminder != null)
        {
            n.hasReminder = true;
        }
        String file = n._links.self.href.getFile();
        String id = file.split("/")[2];
        n.setUuid(id);
        return n;
    }
    public static Note[] parseArray(String json)
    {
        Gson g = new Gson();
        Notes n = g.fromJson(json, Notes.class);
        for (Note note: n._embedded.notes) {
            String file = note._links.self.href.getFile();
            String id = file.split("/")[2];
            note.setUuid(id);

            if (note.reminder != null)
            {
                note.hasReminder = true;
            }


        }

        return n._embedded.notes;
    }
}
