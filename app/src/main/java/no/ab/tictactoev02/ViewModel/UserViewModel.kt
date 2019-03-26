package no.ab.tictactoev02.ViewModel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import kotlinx.coroutines.*
import no.ab.tictactoev02.IO.UserEntity
import no.ab.tictactoev02.IO.UserRepository
import no.ab.tictactoev02.IO.UserRoomDatabase
import kotlin.coroutines.CoroutineContext

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: UserRepository
    val allUsersLive: LiveData<List<UserEntity>>

    private var parentJob = Job()
    private val coroutineContext: CoroutineContext get() = parentJob + Dispatchers.Main
    private val scope = CoroutineScope(coroutineContext)

    init{
        val userDAO = UserRoomDatabase.getDatabase(application.applicationContext).userDao()
        repository = UserRepository(userDAO)
        allUsersLive = repository.allUsersLive
    }

    fun insert(userEntity: UserEntity) = scope.launch(Dispatchers.IO) {
        repository.insert(userEntity)
    }

    fun update(userEntity: UserEntity) = scope.launch(Dispatchers.IO) {
        repository.update(userEntity)
    }

    fun delete(userEntity: UserEntity) = scope.launch(Dispatchers.IO) {
        repository.delete(userEntity)
    }

    fun deleteAll() = scope.launch(Dispatchers.IO) {
        repository.deleteAll()
    }

}