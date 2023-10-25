package ru.gb.mytranslator.presentation.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import gb.ru.translator.view.main.SearchDialogFragment
import org.koin.android.ext.android.inject
import ru.gb.data.convertMeaningsToSingleString
import ru.gb.domain.models.DataModel
import ru.gb.mytranslator.R
import ru.gb.mytranslator.databinding.FragmentMainBinding
import ru.gb.mytranslator.presentation.AppState
import ru.gb.mytranslator.presentation.BaseFragment
import ru.gb.mytranslator.presentation.view.description.DescriptionFragment
import ru.gb.mytranslator.presentation.view.main.adapter.MainAdapter

private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG = "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

class MainFragment : BaseFragment<AppState>() {

    private lateinit var binding: FragmentMainBinding
    override lateinit var model: MainViewModel
    private val adapter: MainAdapter by lazy { MainAdapter { onListItemClickListener.invoke(it) } }

    private val fabClickListener: View.OnClickListener =
        View.OnClickListener {
            val searchDialogFragment = SearchDialogFragment.newInstance()
            searchDialogFragment.setOnSearchClickListener(onSearchClickListener)
            searchDialogFragment.show(
                requireActivity().supportFragmentManager,
                BOTTOM_SHEET_FRAGMENT_DIALOG_TAG
            )
        }

    private val onListItemClickListener: (item: DataModel) -> Unit = { data ->
        val descriptionFragment = DescriptionFragment.newInstance(
            data.text,
            convertMeaningsToSingleString(data.meanings),
            data.meanings[0].imageUrl
        )
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, descriptionFragment)
            .addToBackStack(null)
            .commit()
    }


    private val onSearchClickListener: SearchDialogFragment.OnSearchClickListener =
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String, fromLocalSource: Boolean) {
                if (!isNetworkAvailable && !fromLocalSource) {
                    showNoInternetConnectionDialog()
                } else {
                    model.getData(searchWord, fromLocalSource)
                }
//                if (isNetworkAvailable) {
//                    model.getData(searchWord, isNetworkAvailable)
//                } else {
//                    showNoInternetConnectionDialog()
//                }
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentMainBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniViewModel()
        initViews()
    }

    override fun setDataToAdapter(data: List<DataModel>) {
        adapter.submitList(data)
    }


    private fun iniViewModel() {
        if (binding.mainActivityRecyclerview.adapter != null) {
            throw IllegalStateException("The ViewModel should be initialised first")
        }

        val viewModel: MainViewModel by inject()
        model = viewModel
        model.subscribe().observe(viewLifecycleOwner) { renderData(it) }
    }

    private fun initViews() {
        binding.searchFab.setOnClickListener(fabClickListener)
        binding.mainActivityRecyclerview.adapter = adapter
    }
}