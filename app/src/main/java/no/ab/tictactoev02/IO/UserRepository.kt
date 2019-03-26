package no.ab.tictactoev02.IO

import android.arch.lifecycle.LiveData
import android.support.annotation.WorkerThread

class UserRepository(private val userDAO: UserDAO){
    val allUsersLive: LiveData<List<UserEntity>> =  userDAO.getAllUsersLive()


    @WorkerThread
    suspend fun insert(userEntity: UserEntity){
        userDAO.insert(userEntity)
    }

    @WorkerThread
    suspend fun update(userEntity: UserEntity){
        userDAO.update(userEntity)
    }

    @WorkerThread
    suspend fun deleteAll(){
        userDAO.deleteAll()
    }

    @WorkerThread
    suspend fun delete(userEntity: UserEntity){
        userDAO.delete(userEntity)
    }
}