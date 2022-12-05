package es.unex.giiis.asee.repositorylabkotlin.data.roombd

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import es.unex.giiis.asee.repositorylabkotlin.data.model.Repo

@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun bulkInsert(repo: List<Repo?>?)

    @Query("SELECT * FROM repo WHERE owner = :owner")
    fun getReposByOwner(owner: String?): LiveData<List<Repo>>

    @Query("SELECT count(*) FROM repo WHERE owner = :owner")
    fun getNumberReposByUser(owner: String?): Int

    @Query("DELETE FROM repo WHERE owner = :owner")
    fun deleteReposByUser(owner: String?): Int
}