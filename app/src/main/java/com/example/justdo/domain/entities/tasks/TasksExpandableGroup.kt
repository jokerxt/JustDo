package com.example.justdo.domain.entities.tasks

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup

class TasksExpandableGroup(date: String, items: List<TodoTask>) : ExpandableGroup<TodoTask>(date, items)