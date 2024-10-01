package com.samy.zanb.pojo

import com.samy.zanb.pojo.Page

data class Book(
    val id:Int,
    val name:String,
    val  fancyName:String,
    val arr:List<Page>
)
