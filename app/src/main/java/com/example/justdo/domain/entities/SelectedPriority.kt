package com.example.justdo.domain.entities

data class SelectedPriority(
    val priority: Priority,
    var isSelected: Boolean = false
)