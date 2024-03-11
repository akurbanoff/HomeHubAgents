package ru.hmhub.agents.utils

import java.security.MessageDigest

actual class HashUtils {
    actual fun sha256(input: String): String {
        val bytes = input.toByteArray()
        val md = MessageDigest.getInstance("SHA-256")
        val digest = md.digest(bytes)

        val hexString = StringBuilder()
        for (byte in digest) {
            hexString.append(String.format("%02x", byte))
        }

        return hexString.toString()
    }
}