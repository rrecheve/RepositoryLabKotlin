package es.unex.giiis.asee.repositorylabkotlin.adapter

import es.unex.giiis.asee.repositorylabkotlin.data.model.Repo


interface OnReposLoadedListener {
    fun onReposLoaded(repos: List<Repo>)
}
