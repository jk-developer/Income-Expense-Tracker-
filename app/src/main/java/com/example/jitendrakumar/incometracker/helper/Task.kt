package com.example.jitendrakumar.incometracker.helper

import java.util.*

data class Task (
        val id: Int?,
        val taskName: String,
        val taskDate: String,
        var done: Boolean
)