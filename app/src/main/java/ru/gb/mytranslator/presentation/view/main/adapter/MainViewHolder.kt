package ru.gb.mytranslator.presentation.view.main.adapter

import androidx.recyclerview.widget.RecyclerView
import ru.gb.data.convertMeaningsToSingleString
import ru.gb.domain.models.DataModel
import ru.gb.mytranslator.databinding.FragmentMainRecyclerviewItemBinding

class MainViewHolder(
    private val binding: FragmentMainRecyclerviewItemBinding,
    listener: (Int) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.root.setOnClickListener {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener(position)
            }
        }
    }

    fun bind(item: DataModel) {
        with(binding) {
            headerTextviewRecyclerItem.text = item.text
            descriptionTextviewRecyclerItem.text = convertMeaningsToSingleString(item.meanings)
        }
    }
}