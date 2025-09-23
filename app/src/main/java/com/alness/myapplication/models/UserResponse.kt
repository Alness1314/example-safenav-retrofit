package com.alness.myapplication.models

data class UserResponse (
    val id: String,
    val username: String,
    val fullName: String,
    val sendExpirationAlert: Boolean,
    val profile: String,
    val createdAt: String,
    val updatedAt: String,
    val erased: Boolean
)