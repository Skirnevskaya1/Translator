package ru.gb.mytranslator.presentation.view.main

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import ru.gb.mytranslator.databinding.SearchDialogFragmentBinding

class SearchDialogFragment : BottomSheetDialogFragment() {

    private var _binding: SearchDialogFragmentBinding? = null
    private val binding get() = _binding!!

    private var onSearchClickListener: OnSearchClickListener? = null

    private val textWatcher = object : TextWatcher {

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            with(binding) {
                val searchEditTextIsEmpty = searchEditText.text.isNullOrEmpty()

                searchButtonTextview.isEnabled = !searchEditTextIsEmpty
                if (searchEditTextIsEmpty) {
                    clearTextImageview.visibility = View.INVISIBLE
                } else {
                    clearTextImageview.visibility = View.VISIBLE
                }
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun afterTextChanged(s: Editable) {}
    }

    private val onSearchButtonClickListener =
        View.OnClickListener {
            onSearchClickListener?.onClick(
                binding.searchEditText.text.toString(),
                binding.searchInHistoryCheckbox.isChecked
            )
            dismiss()
        }

    internal fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchButtonTextview.setOnClickListener(onSearchButtonClickListener)
        binding.searchEditText.addTextChangedListener(textWatcher)
        addOnClearClickListener()
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
    }

    private fun addOnClearClickListener() {
        with(binding) {
            clearTextImageview.setOnClickListener {
                searchEditText.setText("")
                searchButtonTextview.isEnabled = false
            }
        }
    }

    interface OnSearchClickListener {
        fun onClick(searchWord: String, fromLocalSource: Boolean)
    }

    companion object {
        fun newInstance(): SearchDialogFragment {
            return SearchDialogFragment()
        }
    }
}
