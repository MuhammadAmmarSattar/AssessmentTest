package com.core.network.model



data class LoginResponseMock(
//    val token : String,
//    val error : String ? =null
val user : User
)
{
    companion object{
        fun mock() = LoginResponseMock(user = User(
            id = "59201123412",
            name = "Muhammad",
            profilePicture = "https://www.profile.com",
            accessToken = "0123345",
            expiresIn = 0L,
            refreshToken = "01234558990"
        ))
    }
}
data class User(
    val id : String,
    val name : String,
    val profilePicture : String,
    val accessToken : String,
    val expiresIn: Long,
    val refreshToken: String,
)
