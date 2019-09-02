package com.incursio.randomusers.repository.remote.model

import android.annotation.SuppressLint
import androidx.room.Embedded
import androidx.room.Entity
import com.incursio.randomusers.isoDateToFormatted
import com.incursio.randomusers.title
import java.time.LocalDate
import java.util.*

data class Name(
    val title: String,
    val first: String,
    val last: String
)

data class Coordinates(
    val latitude: String,
    val longitude: String
)

data class Location(
    val street: String,
    val city: String,
    val state: String,
    val postcode: String,
    @Embedded val coordinates: Coordinates
)

data class Login(
    val uuid: String,
    val username: String,
    val password: String,
    val salt: String,
    val md5: String,
    val sha1: String,
    val sha256: String
)

data class DateAge(
    val date: String
) {
    fun formatted() = date.isoDateToFormatted()
}

data class Id(
    val name: String,
    // NOTE: Should be encrypted in a real app
    val value: String
)

data class WebImage(
    val large: String,
    val medium: String,
    val thumbnail: String
)

// TODO: Use i18n
private val countries = mapOf(
    "AU" to "Australia",
    "BR" to "Brasil",
    "CA" to "Canada",
    "CH" to "Switzerland",
    "DE" to "Germany",
    "DK" to "Denmark",
    "ES" to "Spain",
    "FI" to "Finland",
    "FR" to "France",
    "GB" to "United Kingdom",
    "IE" to "Ireland",
    "IR" to "Iran",
    "NO" to "Norway",
    "NL" to "Netherlands",
    "NZ" to "New Zealand",
    "TR" to "Turkey",
    "US" to "United States"
).withDefault { "Unknown" }

// TODO: Use i18n
private val nationalities = mapOf(
    "AU" to "Australian",
    "BR" to "Brasilian",
    "CA" to "Canadian",
    "CH" to "Swiss",
    "DE" to "German",
    "DK" to "Danish",
    "ES" to "Spanish",
    "FI" to "Finnish",
    "FR" to "French",
    "GB" to "British",
    "IE" to "Irish",
    "IR" to "Iranian",
    "NO" to "Norwegian",
    "NL" to "Dutch",
    "NZ" to "New Zealander",
    "TR" to "Turkish",
    "US" to "American"
).withDefault { "Unknown" }

@Entity(tableName = "users", primaryKeys = ["uuid"])
data class User(
    val gender: String,
    @Embedded(prefix = "name_") val name: Name,
    @Embedded val location: Location,
    val email: String,
    @Embedded val login: Login,
    @Embedded val dob: DateAge,
    val phone: String,
    val cell: String,
    @Embedded(prefix = "id_") val id: Id,
    @Embedded(prefix = "img_") val picture: WebImage,
    val nat: String,
    val isSaved: Boolean
) {
    // TODO: Capitalize when saving to local storage
    val fullName
        @ExperimentalStdlibApi
        @SuppressLint("DefaultLocale")
        get() = with(name) { "${first.capitalize(Locale.US)} ${last.capitalize(Locale.US)}" }

    val titleAndName
        @ExperimentalStdlibApi
        @SuppressLint("DefaultLocale")
        get() = "${name.title.capitalize(Locale.US)}. $fullName"

    val idValue
        get() = login.uuid

    @ExperimentalStdlibApi
    val locationTitle
        get() = with(location) { "$city, $state".title() }

    val nationality
        get() = nationalities.getValue(nat)

    val country
        get() = countries.getValue(nat)
}