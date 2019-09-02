package com.incursio.randomusers.repository.remote.model

import androidx.room.TypeConverter

private const val coordSep = ", "
private const val idSep = ";"

class Converters {
    @TypeConverter
    fun coordinatesToString(coordinates: Coordinates?): String? {
        return coordinates?.let { with(it) { "$latitude$coordSep$longitude" } }
    }

    @TypeConverter
    fun stringToCoordinates(string: String?): Coordinates? {
        if (string == null) return null
        val (latitude, longitude) = string.split(coordSep, limit = 2)

        return Coordinates(latitude, longitude)
    }

    @TypeConverter
    fun idToString(id: Id?): String? {
        return id?.let { with(it) { "$name$idSep$value" } }
    }

    @TypeConverter
    fun stringToId(string: String?): Id? {
        if (string == null) return null
        val (name, value) = string.split(idSep, limit = 2)

        return Id(name, value)
    }
}