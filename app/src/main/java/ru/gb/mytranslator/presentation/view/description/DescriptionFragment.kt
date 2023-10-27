package ru.gb.mytranslator.presentation.view.description

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.request.LoadRequest
import coil.transform.CircleCropTransformation
import gb.ru.utils.network.OnlineLiveData
import gb.ru.utils.ui.AlertDialogFragment
import ru.gb.mytranslator.R
import ru.gb.mytranslator.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {
    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener { startLoadingOrShowError() }
        setData()
    }

    private fun setData() {
        with(binding) {
            descriptionHeader.text = arguments?.getString(WORD_EXTRA)
            descriptionTextview.text = arguments?.getString(DESCRIPTION_EXTRA)
        }
        val imageLink = arguments?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            useCoilToLoadPhoto(binding.descriptionImageview, imageLink)
        }
    }

    private fun startLoadingOrShowError() {
        OnlineLiveData(requireContext()).observe(
            viewLifecycleOwner
        ) {
            if (it) {
                setData()
            } else {
                AlertDialogFragment.newInstance(
                    getString(R.string.dialog_title_device_is_offline),
                    getString(R.string.dialog_message_device_is_offline)
                ).show(
                    requireActivity().supportFragmentManager,
                    DIALOG_FRAGMENT_TAG
                )
                stopRefreshAnimationIfNeeded()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setBlurEffect() {
        val blurEffect =
            RenderEffect.createBlurEffect(15f, 0f, Shader.TileMode.MIRROR)
        binding.descriptionImageview.setRenderEffect(blurEffect)
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
        val request = LoadRequest.Builder(requireContext())
            .data("https:$imageLink")
            .target(
                onStart = {},
                onSuccess = { result ->
                    imageView.setImageDrawable(result)
                    stopRefreshAnimationIfNeeded()
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        setBlurEffect()
                    }
                },
                onError = {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            )
            .transformations(
                CircleCropTransformation(),
            )
            .build()

        ImageLoader(requireContext()).execute(request)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"
        private const val WORD_EXTRA = "WORD_EXTRA"
        private const val DESCRIPTION_EXTRA = "DESCRIPTION_EXTRA"
        private const val URL_EXTRA = "URL_EXTRA"
        fun newInstance(word: String, description: String, url: String) =
            DescriptionFragment().apply {
                arguments = Bundle().apply {
                    putString(WORD_EXTRA, word)
                    putString(DESCRIPTION_EXTRA, description)
                    putString(URL_EXTRA, url)
                }
            }
    }
}
