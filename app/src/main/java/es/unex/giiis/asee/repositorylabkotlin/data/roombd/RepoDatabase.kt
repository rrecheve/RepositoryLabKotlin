package es.unex.giiis.asee.repositorylabkotlin.data.roombd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.unex.giiis.asee.repositorylabkotlin.data.model.Repo

@Database(entities = [Repo::class], version = 1, exportSchema = false)
abstract class RepoDatabase : RoomDatabase() {
    abstract fun repoDao(): RepoDao?

    companion object {
        private var INSTANCE: RepoDatabase? = null
        @Synchronized
        fun getInstance(context: Context?): RepoDatabase? {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(
                    context!!,
                    RepoDatabase::class.java, "repos.db"
                ).build()
            }
            return INSTANCE
        }
    }
}