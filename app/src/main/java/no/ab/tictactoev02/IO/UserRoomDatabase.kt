package no.ab.tictactoev02.IO

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context


@Database(entities = [UserEntity::class],version = 1, exportSchema = false)
public abstract class UserRoomDatabase : RoomDatabase() {
    abstract fun userDao() : UserDAO

    companion object {
        @Volatile
        private var INSTANCE: UserRoomDatabase? = null

        fun getDatabase(context: Context): UserRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null)
                return tempInstance

            synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext,
                    UserRoomDatabase::class.java,
                    "tictactoe_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}