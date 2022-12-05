package es.unex.giiis.asee.repositorylabkotlin.data.network

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import es.unex.giiis.asee.repositorylabkotlin.AppExecutors
import es.unex.giiis.asee.repositorylabkotlin.adapter.OnReposLoadedListener
import es.unex.giiis.asee.repositorylabkotlin.data.model.Repo

class RepoNetworkDataSource private constructor() {
    // LiveData storing the latest downloaded weather forecasts
    private val mDownloadedRepos: MutableLiveData<Array<Repo>> = MutableLiveData()

    val currentRepos: LiveData<Array<Repo>>
        get() = mDownloadedRepos

    /**
     * Gets the newest repos
     */
    fun fetchRepos(username: String) {
        Log.d(LOG_TAG, "Fetch repos started")
        // Get gata from network and pass it to LiveData
        AppExecutors.instance?.networkIO()?.execute(
            ReposNetworkLoaderRunnable(
                username,
                object : OnReposLoadedListener {
                    override fun onReposLoaded(repos: List<Repo>) {
                        mDownloadedRepos.postValue(repos.toTypedArray())
                    }
                })
        )
    }

    companion object {
        private val LOG_TAG = RepoNetworkDataSource::class.java.simpleName
        private var sInstance: RepoNetworkDataSource? = null

        @get:Synchronized
        val instance: RepoNetworkDataSource?
            get() {
                Log.d(LOG_TAG, "Getting the network data source")
                if (sInstance == null) {
                    sInstance = RepoNetworkDataSource()
                    Log.d(LOG_TAG, "Made new network data source")
                }
                return sInstance
            }
    }
}