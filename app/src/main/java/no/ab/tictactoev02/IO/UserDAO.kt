package no.ab.tictactoev02.IO

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface UserDAO {

    @Insert
    fun insert(vararg user: UserEntity)

    @Update
    fun update(vararg user: UserEntity)

    @Delete
    fun delete(vararg userEntity: UserEntity)

    @Query("DELETE FROM user_table")
    fun deleteAll()

    @Query("SELECT * FROM user_table")
    fun getAllUsersLive() : LiveData<List<UserEntity>>

    @Query("SELECT * FROM user_table")
    fun getAllUsers() : List<UserEntity>

    @Query("SELECT * FROM user_table WHERE name = :name")
    fun getUser(vararg name: String) : UserEntity
}