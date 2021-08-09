package com.example.appdelembretesetarefas.datasource

import com.example.appdelembretesetarefas.model.Task

object taskdatasource {
    private val list = arrayListOf<Task>()

    fun getList() = list.toList()

    /*Vamos colocar após list o toList, para que o programa não fique na lista fake, e sim mantenha a lista na memoria.*/

    fun insertTask(task: Task) {
        if (task.id == 0) {
            list.add(task.copy(id = list.size + 1))
        } else {
            list.remove(task)
            list.add(task)
        }

    }

    fun findById(taskId: Int) = list.find {it.id == taskId}

    fun deleteTask(task: Task) {
        list.remove(task)

    }
}
