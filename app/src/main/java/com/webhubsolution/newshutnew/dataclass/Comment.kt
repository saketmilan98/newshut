package com.webhubsolution.newshutnew.dataclass

import com.google.gson.annotations.SerializedName


/*
"comments": [
        {
          "id": 4,
          "name": "anonymous3",
          "url": "",
          "date": "2019-09-24 09:57:46",
          "content": "<p>nice posttt<\/p>\n",
          "parent": 0
        }
      ]
 */
data class Comment(
    @SerializedName("content") val content : String,
    @SerializedName("date") val date : String,
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("parent") val parent : Int,
    @SerializedName("url") val url : String
)
/*val content: String,
    val date: String,
    val id: Int,
    val name: String,
    val parent: Int,
    val url: String*/