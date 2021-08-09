package com.example.appdelembretesetarefas.model

data class Task (
    val title: String,
    val descricao: String,
    val data: String,
    val hora: String,
    val id: Int = 0
    /*Shift+shif => digite equals() and hashCode()*/
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Task
        if (id != other.id) return false
        return true
    }
    override fun hashCode(): Int {
        return id
    }
}
/*Configuraçãõ no equals e hashCode para considerar apenas o "ID" .*/