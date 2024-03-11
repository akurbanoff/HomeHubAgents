package ru.hmhub.agents.utils

expect class HashUtils() {
    fun sha256(input: String): String
}