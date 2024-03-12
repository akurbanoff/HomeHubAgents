package ru.hmhub.agents.utils

fun isSuccess(status: Int) : Boolean{
    return if(status in 400..<600) false else true
}