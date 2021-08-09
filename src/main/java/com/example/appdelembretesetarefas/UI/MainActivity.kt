package com.example.appdelembretesetarefas.UI

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.appdelembretesetarefas.databinding.ActivityAddBinding
import com.example.appdelembretesetarefas.datasource.taskdatasource
import com.example.appdelembretesetarefas.extensions.format
import com.example.appdelembretesetarefas.extensions.text
import com.example.appdelembretesetarefas.model.Task
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (intent.hasExtra(TASK_ID)) {
            val taskId = intent.getIntExtra(TASK_ID, 0)
            taskdatasource.findById(taskId)?.let {
                binding.title.text = it.title
                binding.descricao.text = it.descricao
                binding.data.text = it.data
                binding.hora.text = it.hora
            }
        }
        insertListener()
    }
    /*Como criar o calendario selecionavel.*/
    private fun insertListener() {
        binding.data.editText?.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.addOnPositiveButtonClickListener {
                val timeZone = TimeZone.getDefault()
                val offset = timeZone.getOffset(Date().time) * -1
                binding.data.text = Date(it + offset).format()
            }
            datePicker.show(supportFragmentManager, "DATE_PICKER_TAG" )
        }
        /*Tornando o campo Data, editavel, colocando no binding o data.editText*/
        /*Apertando no EditText, o comando CTRL+SPACE apresenta as opções,
        ex o edit está aceitando nulo,tem que resolver isso primeiro.*/
        /**/
        /*Digite Loge e depois enter*/
        binding.hora.editText?.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build()
            timePicker.addOnPositiveButtonClickListener {
                val minute = if (timePicker.minute in 0..9) "0${timePicker.minute}" else timePicker.minute
                val hour = if (timePicker.minute in 0..9) "${timePicker.hour}" else timePicker.hour
                binding.hora.text = "$hour:$minute"
            }
            timePicker.show(supportFragmentManager, null)
        }
        binding.cancelar.setOnClickListener {
            finish()
        }
        /*binding.criar ou btnNewTask seria o botão criar uma nova tarefa, vamos criar o que o botão faz.*/
        binding.criar.setOnClickListener{
            val task = Task(
                title = binding.title.text,
                descricao = binding.descricao.text,
                data = binding.data.text,
                hora = binding.hora.text,
                id = intent.getIntExtra(TASK_ID, 0)
            )
            taskdatasource.insertTask(task)
            /*Não tava aparecendo os dados na tela da lista, pois faltava o task acima, era somente instancia-lo.*/

            setResult(Activity.RESULT_OK)
            finish()
        }
    }
    companion object {
        const val TASK_ID = "task_id"
    }
}
/*Criação do View Binding*/

/*View Binding trata-se de uma alternativa para buscar Views do Android porém, por padrão, temos acesso ao findViewById()*/

/*view Binding vai pegar nossos XMLs e criar classes para eles, não precissando inflar os layouts*/

/*Coloque a activity como ActivityAddBinding que é vai referencia o layout =>activity_add*/

/*Temos que colocar a activity AddTaskActivity, dentro do manifest, ALT+ENTER, clique em , Add activity to manifest*/