package com.core.network.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Currency


@Parcelize
data class Rating(
    val rate: Double,
    val count: Int
) : Parcelable

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String,
    val rating: Rating
) : Parcelable