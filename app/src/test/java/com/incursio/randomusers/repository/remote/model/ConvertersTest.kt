package com.incursio.randomusers.repository.remote.model

import org.junit.Test

import org.junit.Assert.*

class ConvertersTest {

    @Test
    fun coordinatesToString() {
        val expected = "128, 256"
        val coordinates = Coordinates("128", "256")
        val converter = Converters()

        with(converter) {
            assertEquals(coordinatesToString(coordinates), expected)
        }
    }

    @Test
    fun stringToCoordinates() {
        val converter = Converters()
        val expected = Coordinates("128", "256")
        val string = "128, 256"

        with(converter) {
            assertEquals(stringToCoordinates(string), expected)
        }
    }

    @Test
    fun idToString() {
        val expected = "SSN;123123"
        val id = Id("SSN", "123123")
        val converter = Converters()

        with(converter) {
            assertEquals(idToString(id), expected)
        }
    }

    @Test
    fun stringToId() {
        val expected = Id("SSN", "123123")
        val string = "SSN;123123"
        val converter = Converters()

        with(converter) {
            assertEquals(stringToId(string), expected)
        }
    }
}