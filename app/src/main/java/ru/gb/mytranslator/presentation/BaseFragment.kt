package ru.gb.mytranslator.presentation

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.Toast
import gb.ru.utils.network.OnlineLiveData
import gb.ru.utils.ui.AlertDialogFragment
import org.koin.androidx.scope.ScopeFragment
import ru.gb.core.R
import ru.gb.domain.models.DataModel

private const val DIALOG_FRAGMENT_TAG = "DIALOG_FRAGMENT_TAG"

abstract class BaseFragment<T : AppState> : ScopeFragment() {
    private val loadingLayout by lazy { requireActivity().findViewById<FrameLayout>(R.id.loading_frame_layout) }

    abstract val model: BaseViewModel<T>
    protected var isNetworkAvailable: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        subscribeToNetworkChange()
    }


    override fun onResume() {
        super.onResume()
        if (!isNetworkAvailable && isDialogNull()) {
            showNoInternetConnectionDialog()
        }
    }

    protected fun renderData(appState: T) {
        when (appState) {
            is AppState.Success -> {
                showViewWorking()
                appState.data?.let {
                    if (it.isEmpty()) {
                        showAlertDialog(
                            getString(R.string.dialog_title_sorry),
                            getString(R.string.empty_server_response_on_success)
                        )
                    } else {
                        setDataToAdapter(it)
                    }
                }
            }

            is AppState.Loading -> {
                showViewLoading()
            }

            is AppState.Error -> {
                showViewWorking()
                showAlertDialog(getString(R.string.error_stub), appState.error.message)
            }
        }
    }

    protected fun showNoInternetConnectionDialog() {
        showAlertDialog(
            getString(R.string.dialog_title_device_is_offline),
            getString(R.string.dialog_message_device_is_offline)
        )
    }

    private fun showAlertDialog(title: String?, message: String?) {
        AlertDialogFragment.newInstance(title, message)
            .show(requireActivity().supportFragmentManager, DIALOG_FRAGMENT_TAG)
    }

    private fun showViewWorking() {
        loadingLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        loadingLayout.visibility = View.VISIBLE
    }

    private fun isDialogNull(): Boolean {
        return requireActivity().supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) == null
    }

    abstract fun setDataToAdapter(data: List<DataModel>)

    private fun subscribeToNetworkChange() {
        OnlineLiveData(requireContext()).observe(
            this@BaseFragment
        ) {
            isNetworkAvailable = it

            if (!isNetworkAvailable) {
                Toast.makeText(
                    this@BaseFragment.requireContext(),
                    R.string.dialog_message_device_is_offline,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}