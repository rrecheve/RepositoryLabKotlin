package es.unex.giiis.asee.repositorylabkotlin.data.network

import es.unex.giiis.asee.repositorylabkotlin.data.model.Repo
import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Path


interface GitHubService {
    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo>>
}
