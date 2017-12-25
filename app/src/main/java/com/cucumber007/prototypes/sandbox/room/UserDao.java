package com.cucumber007.prototypes.sandbox.room;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.TypeConverters;
import android.database.Cursor;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    User findByName(String first, String last);
/*

    @Query("SELECT * FROM user WHERE age > :minAge")
    public User[] loadAllUsersOlderThan(int minAge);

    @Query("SELECT first_name, last_name FROM user")
    public List<NameTuple> loadFullName();

    @Query("SELECT * from user where id = :id LIMIT 1")
    public Flowable<User> loadUserById(int id);

    @Query("SELECT * FROM user WHERE age > :minAge LIMIT 5")
    public Cursor loadRawUsersOlderThan(int minAge);
*/
/*

    @Query("SELECT * FROM book "
            + "INNER JOIN loan ON loan.book_id = book.id "
            + "INNER JOIN user ON user.id = loan.user_id "
            + "WHERE user.name LIKE :userName")
    public List<Book> findBooksBorrowedByNameSync(String userName);

    @Query("SELECT user.name AS userName, pet.name AS petName "
            + "FROM user, pet "
            + "WHERE user.id = pet.user_id")
    public List<UserPet> loadUserAndPetNames();
*/


    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    public class NameTuple {
        @ColumnInfo(name="first_name")
        public String firstName;

        @ColumnInfo(name="last_name")
        public String lastName;
    }

    public static class UserPet {
        public String userName;
        public String petName;
    }
}
