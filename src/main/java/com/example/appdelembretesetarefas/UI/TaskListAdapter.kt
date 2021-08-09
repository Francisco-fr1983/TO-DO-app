package com.example.appdelembretesetarefas.UI

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.appdelembretesetarefas.R
import com.example.appdelembretesetarefas.databinding.ItemTaskBinding
import com.example.appdelembretesetarefas.model.Task


/*O adapter ele vai fazer com que os dados dos itens virem uma view em lista,
seria para visualizar os itens da lista do recyclerview*/

class TaskListAdapter : ListAdapter<Task, TaskListAdapter.TaskViewHolder>(DiffCallback()) {

    var listenerEdit : (Task) -> Unit = {}
    var listenerDelete : (Task) -> Unit = {}

    inner class TaskViewHolder(private val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Task) {
            binding.tvTitle.text = item.title
            binding.tvDescricao.text = item.descricao
            binding.tvDate.text = "${item.data} ${item.hora}"
            binding.ivMore.setOnClickListener{
                showPopup(item)
            }
            /*Nossa classe precisa ser inner class, para que a lambda consiga acessar nossa classe principal*/
        }
        private fun showPopup(item: Task) {
            val ivMore = binding.ivMore
            val popupMenu = PopupMenu(ivMore.context, ivMore)
            popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener {
                when(it.itemId) {
                    R.id.action_edit -> listenerEdit (item)
                    R.id.action_deletar -> listenerDelete (item)
                }
                return@setOnMenuItemClickListener true
            }
            popupMenu.show()
            /*Vou criar duas lambdas, uma de editar e outra deletar, mas antes vou criar as variáveis acima.*/
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskBinding.inflate(inflater, parent, false)
        return TaskViewHolder(binding)
    }
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
class DiffCallback : DiffUtil.ItemCallback<Task>() {
    override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem

    override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
}

