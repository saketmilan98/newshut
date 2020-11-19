package com.webhubsolution.newshutnew.dataclass

import com.google.gson.annotations.SerializedName

/*
Copyright (c) 2019 Kotlin Data Classes Generated from JSON powered by http://www.json2kotlin.com

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

For support, please feel free to contact me at https://www.linkedin.com/in/syedabsar */


data class Post (

	@SerializedName("id") val id : Int,
	@SerializedName("type") val type : String,
	@SerializedName("slug") val slug : String,
	@SerializedName("url") val url : String,
	@SerializedName("status") val status : String,
	@SerializedName("title") val title : String,
	@SerializedName("title_plain") val title_plain : String,
	@SerializedName("content") val content : String,
	@SerializedName("excerpt") val excerpt : String,
	@SerializedName("date") val date : String,
	@SerializedName("modified") val modified : String,
	@SerializedName("categories") val categories : List<Category>,
	@SerializedName("tags") val tags : List<Tags>,
	@SerializedName("author") val author : Author,
	@SerializedName("comments") val comments : List<Comment>,
	@SerializedName("attachments") val attachments : List<Attachments>,
	@SerializedName("comment_count") val comment_count : Int,
	@SerializedName("comment_status") val comment_status : String,
	@SerializedName("thumbnail") val thumbnail : String,
	@SerializedName("custom_fields") val custom_fields : Custom_fields,
	@SerializedName("thumbnail_size") val thumbnail_size : String,
	@SerializedName("thumbnail_images") val thumbnail_images : Thumbnailimages
)