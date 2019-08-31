package com.incursio.randomusers.repository.remote.model

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Timezone(
    val offset: String,
    val description: String
)

data class Location(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String,
    val coordinates: Coordinates,
    val timezone: Timezone
)

data class Login(
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

data class DateAge(
    val date: String,
    val age: Int
)

data class Id(
    val name: String,
    val value: String
)

data class WebImage(
    val large: String,
    val medium: String,
    val thumbnail: String
)

data class User(
    val gender: String,
    val name: Name,
    val location: Location,
    val email: String,
    val login: Login,
    val dob: DateAge,
    val registered: DateAge,
    val phone: String,
    val cell: String,
    val id: Id,
    val picture: WebImage,
    val nat: String
)