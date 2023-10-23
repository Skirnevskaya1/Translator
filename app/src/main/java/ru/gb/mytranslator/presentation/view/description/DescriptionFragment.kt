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
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import gb.ru.utils.network.OnlineLiveData
import gb.ru.utils.ui.AlertDialogFragment
import ru.gb.mytranslator.R
import ru.gb.mytranslator.databinding.FragmentDescriptionBinding

class DescriptionFragment : Fragment() {

    private lateinit var binding: FragmentDescriptionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDescriptionBinding.inflate(layoutInflater)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setActionbarHomeButtonAsUp()
        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener { startLoadingOrShowError() }
        setData()

    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityDescriptionBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        setActionbarHomeButtonAsUp()
//        binding.descriptionScreenSwipeRefreshLayout.setOnRefreshListener { startLoadingOrShowError() }
//        setData()
//    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            android.R.id.home -> {
//                onBackPressed()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

    private fun setActionbarHomeButtonAsUp() {
//        requireActivity().supportActionBar?.setHomeButtonEnabled(true)
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun setData() {
//        val bundle = intent.extras
//        binding.descriptionHeader.text = bundle?.getString(WORD_EXTRA)
//        binding.descriptionTextview.text = bundle?.getString(DESCRIPTION_EXTRA)

        with(binding) {
            descriptionHeader.text = arguments?.getString(WORD_EXTRA)
            descriptionTextview.text = arguments?.getString(DESCRIPTION_EXTRA)
        }
        val imageLink = arguments?.getString(URL_EXTRA)
        if (imageLink.isNullOrBlank()) {
            stopRefreshAnimationIfNeeded()
        } else {
            usePicassoToLoadPhoto(binding.descriptionImageview, imageLink)
//            useGlideToLoadPhoto(binding.descriptionImageview, imageLink)
//            useGlideToLoadPhoto(binding.descriptionImageview, imageLink)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun startLoadingOrShowError() {
        OnlineLiveData(requireContext()).observe(
            viewLifecycleOwner,
            {
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
        )
    }

    private fun stopRefreshAnimationIfNeeded() {
        if (binding.descriptionScreenSwipeRefreshLayout.isRefreshing) {
            binding.descriptionScreenSwipeRefreshLayout.isRefreshing = false
        }
    }

    private fun usePicassoToLoadPhoto(imageView: ImageView, imageLink: String) {
        Picasso.get().load("https:$imageLink")
            .placeholder(R.drawable.ic_no_photo_vector).fit().centerCrop()
            .into(imageView, object : Callback {
                override fun onSuccess() {
                    stopRefreshAnimationIfNeeded()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                        val blurEffect =
                            RenderEffect.createBlurEffect(15f, 0f, Shader.TileMode.MIRROR)
                        imageView.setRenderEffect(blurEffect)
                        binding.root.setRenderEffect(blurEffect)
                    }
                }

                override fun onError(e: Exception?) {
                    stopRefreshAnimationIfNeeded()
                    imageView.setImageResource(R.drawable.ic_load_error_vector)
                }
            })
    }

//    private fun useGlideToLoadPhoto(imageView: ImageView, imageLink: String) {
//        Glide.with(imageView)
//            .load("https:$imageLink")
//            .listener(object : RequestListener<Drawable> {
//                override fun onLoadFailed(
//                    e: GlideException?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    stopRefreshAnimationIfNeeded()
//                    imageView.setImageResource(R.drawable.ic_load_error_vector)
//                    return false
//                }
//
//                override fun onResourceReady(
//                    resource: Drawable?,
//                    model: Any?,
//                    target: Target<Drawable>?,
//                    dataSource: DataSource?,
//                    isFirstResource: Boolean
//                ): Boolean {
//                    stopRefreshAnimationIfNeeded()
//
//                    return false
//                }
//            }).apply(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_no_photo_vector)
//                    .centerCrop()
//            )
//            .into(imageView)
//    }

//    @RequiresApi(Build.VERSION_CODES.S)
//    private fun useCoilToLoadPhoto(imageView: ImageView, imageLink: String) {
//        val request = LoadRequest.Builder(this)
//            .data("https:$imageLink")
//            .target(
//                onStart = {},
//                onSuccess = { result ->
//                    imageView.setImageDrawable(result)
//
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
//                        val blurEffect = RenderEffect.createBlurEffect(15f, 0f, Shader.TileMode.MIRROR)
//                        //imageView.setRenderEffect(blurEffect)
//                        binding.root.setRenderEffect(blurEffect)
//                    }
//                },
//                onError = {
//                    imageView.setImageResource(R.drawable.ic_load_error_vector)
//                }
//            )
//            .transformations(
//                CircleCropTransformation(),
//            )
//            .build()
//
//        ImageLoader(this).execute(request)
//    }

    companion object {
        private const val DIALOG_FRAGMENT_TAG = "8c7dff51-9769-4f6d-bbee-a3896085e76e"
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