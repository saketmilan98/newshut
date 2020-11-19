package com.webhubsolution.newshutnew

import org.jetbrains.annotations.PropertyKey
import retrofit2.Call
import com.google.gson.JsonObject
import com.webhubsolution.newshutnew.dataclass.*
import com.webhubsolution.newshutnew.dataclass.callbacks.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.io.File


interface apiKotlin {

    @Headers("Cache-Control: max-age=0", "User-Agent: Wordpress")
    @GET("?json=get_posts$EXCLUDE_FIELD")
    fun getPostByPage(
        @Query("page") page: Int,
        @Query("count") count: Int
    ): Call<CallbackListPost>

    @Headers("Cache-Control: max-age=0", "User-Agent: Wordpress")
    @GET("?json=get_post")
    fun getPostDetailsById(
        @Query("id") id: Int
    ): Call<CallbackDetailsPost>




    companion object {
        val BASE_URL = "http://www.newshut.co.in/api/"
        const val EXCLUDE_FIELD = "&exclude=content,tags,comments,custom_fields"
        //val BASE_URL = "http://192.168.0.110:8000"
    }
}