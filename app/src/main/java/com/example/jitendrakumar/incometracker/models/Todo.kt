package com.example.jitendrakumar.incometracker.models

data class Todo (
    val id: Int?,
    val taskName: String,
    val taskDate: String,
    var done: Boolean
)