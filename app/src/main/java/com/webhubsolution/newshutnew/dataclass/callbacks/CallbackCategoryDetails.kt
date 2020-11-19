package com.webhubsolution.newshutnew.dataclass.callbacks

import com.webhubsolution.newshutnew.dataclass.Category
import com.webhubsolution.newshutnew.dataclass.Post

import java.util.ArrayList

class CallbackCategoryDetails {

    var status = ""
    var count = -1
    var pages = -1
    var category: Category? = null
    var posts: List<Post> = ArrayList()
}
