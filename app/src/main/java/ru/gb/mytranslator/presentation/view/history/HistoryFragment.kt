package ru.gb.mytranslator.presentation.view.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.koin.android.ext.android.inject
import ru.gb.domain.models.DataModel
import ru.gb.mytranslator.databinding.FragmentHistoryBinding
import ru.gb.mytranslator.databinding.LoadingLayoutBinding
import ru.gb.mytranslator.presentation.AppState
import ru.gb.mytranslator.presentation.BaseFragment

class HistoryFragment : BaseFragment<AppState>() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var _bindingLoading: LoadingLayoutBinding? = null
    private val bindingLoading get() = _bindingLoading!!

    override lateinit var model: HistoryViewModel
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHistoryBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViewModel()
        initViews()
        _bindingLoading = LoadingLayoutBinding.inflate(layoutInflater)
        bindingLoading.loadingFrameLayout.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        model.getData()
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
        model.subscribe().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun initViews() {
        binding.historyActivityRecyclerview.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        _bindingLoading = null
    }
}
