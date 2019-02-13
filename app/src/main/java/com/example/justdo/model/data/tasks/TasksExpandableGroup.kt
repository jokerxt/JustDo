package com.example.justdo.model.data.tasks

import com.example.justdo.domain.entities.tasks.TodoTask
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class TasksExpandableGroup(date: String, items: List<TodoTask>) : ExpandableGroup<TodoTask>(date, items)