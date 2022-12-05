package es.unex.giiis.asee.repositorylabkotlin.data.network

import es.unex.giiis.asee.repositorylabkotlin.AppExecutors
import es.unex.giiis.asee.repositorylabkotlin.adapter.OnReposLoadedListener
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ReposNetworkLoaderRunnable(username: String, onReposLoadedListener: OnReposLoadedListener) :
    Runnable {
    private val mOnReposLoadedListener: OnReposLoadedListener
    private val mUsername: String

    init {
        mOnReposLoadedListener = onReposLoadedListener
        mUsername = username
    }

    override fun run() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val service = retrofit.create(GitHubService::class.java)

        try {
            val repos = service.listRepos(mUsername).execute().body()
            AppExecutors.instance?.mainThread()?.execute {
                mOnReposLoadedListener.onReposLoaded(repos!!)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}