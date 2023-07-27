package com.core.network.model.request

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SignUpRequest(
    val firstName: String="",
    val email: String? = null,
    val password: String? = null,
    val website: String? = null,
    val imgUrl: String? = null
):Parcelable
