package ru.gb.mytranslator.presentation.view.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import ru.gb.domain.model.DataModel
import ru.gb.mytranslator.databinding.FragmentMainRecyclerviewItemBinding

class MainAdapter(private val listener: (item: DataModel) -> Unit) :
    ListAdapter<DataModel, MainViewHolder>(MainDiffCallback()) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MainViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FragmentMainRecyclerviewItemBinding.inflate(layoutInflater, parent, false)
        return MainViewHolder(
            binding = binding,
            listener = { position -> listener.invoke(getItem(position)) }
        )
    }

    override fun onBindViewHolder(
        holder: MainViewHolder,
        position: Int
    ) = holder.bind(getItem(position))
}
