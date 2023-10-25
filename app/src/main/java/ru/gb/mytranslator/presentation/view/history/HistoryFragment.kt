package ru.gb.mytranslator.presentation.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import ru.gb.core.databinding.LoadingLayoutBinding
import ru.gb.domain.models.DataModel
import ru.gb.mytranslator.databinding.FragmentHistoryBinding
import ru.gb.mytranslator.presentation.AppState
import ru.gb.mytranslator.presentation.BaseFragment

class HistoryFragment : BaseFragment<AppState>() {

    private lateinit var binding: FragmentHistoryBinding
    private lateinit var bindingLoading : LoadingLayoutBinding
    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
//        return FragmentHistoryBinding.inflate(layoutInflater).also {
//            binding = it
//        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViewModel()
        initViews()
        bindingLoading = LoadingLayoutBinding.inflate(layoutInflater)
        bindingLoading.loadingFrameLayout.visibility = View.VISIBLE
    }
    override fun onResume() {
        super.onResume()
        model.getData("", false)
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.setData(data)
    }

    private fun iniViewModel() {
        if (binding.historyActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        val viewModel: HistoryViewModel by inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner, { renderData(it) })
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }
}
