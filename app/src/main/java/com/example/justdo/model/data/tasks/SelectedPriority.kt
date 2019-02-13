package com.example.justdo.model.data.tasks

import com.example.justdo.domain.entities.Priority

data class SelectedPriority(
    val priority: Priority,
    var isSelected: Boolean = false
)