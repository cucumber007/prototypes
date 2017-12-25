package com.cucumber007.prototypes.sandbox.room;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Bitmap;

import java.util.Date;
import java.util.Random;

@Entity
//primaryKeys = {"firstName", "lastName"}
//tableName = "users"
//indices = {@Index("name"), @Index(value = {"last_name", "address"})}
public class User {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "first_name")
    public String firstName;

    @ColumnInfo(name = "last_name")
    public String lastName;

    @Embedded
    public Address address;

    public Date birthday;

    //Entities can have either an empty constructor (if the corresponding DAO class can
    // access each persisted field) or a constructor whose parameters contain types and
    // names that match those of the fields in the entity. Room can also use full or partial
    // constructors, such as a constructor that receives only some of the fields.

    // Getters and setters are ignored for brevity,
    // but they're required for Room to work.
    // or just make field public

    @Ignore
    Bitmap picture;

    public User(String firstName, String lastName, Address address, Date birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.birthday = birthday;
    }

    public static User getDefault() {
        return new User("Name "+ new Random().nextInt(), "Surname "+ new Random().nextInt(),
                Address.getDefault(), new Date());
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address=" + address +
                ", birthday=" + birthday +
                ", picture=" + picture +
                '}';
    }
}