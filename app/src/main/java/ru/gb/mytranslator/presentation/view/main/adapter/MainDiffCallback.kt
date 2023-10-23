package ru.gb.mytranslator.presentation.view.main.adapter

import androidx.recyclerview.widget.DiffUtil
import ru.gb.domain.model.DataModel

class MainDiffCallback : DiffUtil.ItemCallback<DataModel>() {
    override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel) =
        oldItem.text == newItem.text

    override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel) =
        oldItem == newItem
}