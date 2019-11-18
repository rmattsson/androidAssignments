package com.example.createnote.model;

import android.graphics.Bitmap;

import com.example.createnote.sqlite.Identifiable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.Objects;


public class User implements Identifiable<Long> {

    public class Users {
        public class Embedded {
            public User[] users;
        }

        private Embedded _embedded;
    }

    public class Links {
        public class Link{
            public URL href;
        }

        private Link self;
        private Link user;
        private Link notes;

    }



    public String getUuid() {
        return Uuid;
    }

    public User setUuid(String uuid) {
        Uuid = uuid;
        return this;
    }


    public Links _links;
    private String Uuid;
    private long id;
    @Expose
    private String name;
    private transient Bitmap avatar;
    @Expose
    private String email;

    public User() {
        this(-1);
    }

    public User(long id) {
        this.id = id;
    }

    public User(long id, String name, Bitmap avatar, String email) {
        this(id);
        this.name = name;
        this.avatar = avatar;
        this.email = email;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public User setAvatar(Bitmap avatar) {
        this.avatar = avatar;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar=" + avatar +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id &&
                Objects.equals(name, user.name) &&
                Objects.equals(avatar, user.avatar) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, avatar, email);
    }

    public String format()
    {
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation()
                .create();
        return gson.toJson(this);
    }
    public static User parse(String json)
    {
        Gson g = new Gson();
        User u = g.fromJson(json, User.class);
        String file = u._links.self.href.getFile();
        String id = file.split("/")[2];
        u.setUuid(id);
        return u;
    }
    public static User[] parseArray(String json)
    {
        Gson g = new Gson();

        Users u = g.fromJson(json, Users.class);
        for (User user: u._embedded.users) {
            String file = user._links.self.href.getFile();
            String id = file.split("/")[2];
            user.setUuid(id);
        }

        return u._embedded.users;
    }
}
