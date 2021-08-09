package com.example.appdelembretesetarefas.UI

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.appdelembretesetarefas.databinding.ActivityMain2Binding
import com.example.appdelembretesetarefas.datasource.taskdatasource


@Suppress("DEPRECATION")
class  MainActivitySeg : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    private val adapter by lazy { TaskListAdapter() }

    /*Tive que colocar o onCreate igual da MainActivity, depois disso carregou as duas telas*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.rvTasks.adapter = adapter
        updateList()
        insertListeners()
    }
    private fun insertListeners() {
        binding.fab.setOnClickListener{
            startActivityForResult(Intent(this, MainActivity::class.java), CREATE_NEW_TASK)
        }
        adapter.listenerEdit = {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.TASK_ID, it.id)
            startActivityForResult(intent, CREATE_NEW_TASK)
        }
        adapter.listenerDelete = {
            taskdatasource.deleteTask(it)
            updateList()
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CREATE_NEW_TASK && resultCode == Activity.RESULT_OK) updateList()
    }
    private fun updateList() {
        val list = taskdatasource.getList()
        if(list.isEmpty()) {
            binding.includeEmpty.emptyState.visibility = View.VISIBLE
        } else {
            binding.includeEmpty.emptyState.visibility = View.GONE
        }
        adapter.submitList(list)
    }
    companion object {
        private const val CREATE_NEW_TASK = 1000
    }

/*O metodo startActivity vai chamar uma outra activity*/
}
/*Criar o Adapter aqui.*/