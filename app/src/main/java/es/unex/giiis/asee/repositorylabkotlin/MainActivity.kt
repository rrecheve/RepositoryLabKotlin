package es.unex.giiis.asee.repositorylabkotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import es.unex.giiis.asee.executorslabkotlin.R
import es.unex.giiis.asee.repositorylabkotlin.adapter.MyAdapter
import es.unex.giiis.asee.repositorylabkotlin.adapter.OnReposLoadedListener
import es.unex.giiis.asee.repositorylabkotlin.data.RepoRepository
import es.unex.giiis.asee.repositorylabkotlin.data.model.Repo
import es.unex.giiis.asee.repositorylabkotlin.data.network.RepoNetworkDataSource
import es.unex.giiis.asee.repositorylabkotlin.data.roombd.RepoDatabase


class MainActivity : AppCompatActivity(), MyAdapter.OnListInteractionListener, OnReposLoadedListener {
    private var mRepository: RepoRepository? = null
    private var recyclerView: RecyclerView? = null
    private var mAdapter: MyAdapter? = null
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var mProgressBar: ProgressBar? = null
    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    private var mUsername = ""

    // Called to search user repos
    private fun loadUserRepos(username: String) {
        this.mUsername = username
        mAdapter?.clear()
        mProgressBar!!.visibility = View.VISIBLE
        mRepository?.doFetchRepos(mUsername)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSwipeRefreshLayout = findViewById<View>(R.id.swipeRefreshLayout) as SwipeRefreshLayout
        recyclerView = findViewById<View>(R.id.repoList) as RecyclerView
        recyclerView!!.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        recyclerView!!.layoutManager = layoutManager
        mAdapter = MyAdapter(emptyList(), this)
        recyclerView!!.adapter = mAdapter

        val searchBox = findViewById<EditText>(R.id.searchBox)
        val searchButton = findViewById<Button>(R.id.searchButton)
        mProgressBar = findViewById(R.id.progressBar)

        mRepository = RepoRepository.getInstance(
            RepoDatabase.getInstance(this)!!.repoDao()!!,
            RepoNetworkDataSource.instance!!
        )
        val reposObserver = Observer<List<Repo>> {
            onReposLoaded(it)
        }
        mRepository?.currentRepos!!.observe(this, reposObserver)

        searchButton.setOnClickListener { view: View? ->
            loadUserRepos(searchBox.text.toString())
        }

        //TODO - Force Repository to fetch repos from network
    }

    override fun onListInteraction(url: String?) {
        val webpage = Uri.parse(url)
        val webIntent = Intent(Intent.ACTION_VIEW, webpage)
        startActivity(webIntent)
    }

    override fun onReposLoaded(repos: List<Repo>) {
        mProgressBar?.setVisibility(View.GONE)
        mSwipeRefreshLayout?.setRefreshing(false)
        runOnUiThread { mAdapter!!.swap(repos) }
    }
}
