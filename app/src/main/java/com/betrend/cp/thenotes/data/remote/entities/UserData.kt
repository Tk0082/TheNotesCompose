package com.betrend.cp.thenotes.data.remote.entities


data class UserData(
    val username: String,
    val email: String,
    val photoUrl: String? = null
)