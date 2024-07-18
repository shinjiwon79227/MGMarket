package com.sjw.mgmarket

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val index: Int,
    val imgSrc: Int,
    val name: String,
    val content: String,
    val seller: String,
    val price: String,
    val address: String,
    var favorite: Long,
    val chatting: Long
    ): Parcelable