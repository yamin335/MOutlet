package com.mallzhub.shop.ui.barcode_print

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.mallzhub.shop.BR
import com.mallzhub.shop.R
import com.mallzhub.shop.databinding.PrintBarcodeFragmentBinding
import com.mallzhub.shop.ui.common.BaseFragment
import com.mallzhub.shop.util.AppConstants
import com.mallzhub.shop.util.FileUtils
import com.mallzhub.shop.util.showErrorToast
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File

class PrintBarcodeFragment : BaseFragment<PrintBarcodeFragmentBinding, BarcodePrintViewModel>() {
    override val bindingVariable: Int
        get() = BR.viewModel
    override val layoutId: Int
        get() = R.layout.fragment_print_barcode
    override val viewModel: BarcodePrintViewModel by viewModels {
        viewModelFactory
    }

    val args: PrintBarcodeFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerToolbar(viewDataBinding.toolbar)

        viewModel.showHideProgress.observe(viewLifecycleOwner, Observer { pair ->
            val shouldShowProgress = pair.first
            val progress = pair.second
            if (shouldShowProgress) {
                viewDataBinding.progressView.visibility = View.VISIBLE
                viewDataBinding.loader.progress = progress
                viewDataBinding.progress.text = "$progress%"
                if (progress == 100) viewDataBinding.progressView.visibility = View.GONE
            } else {
                viewDataBinding.progressView.visibility = View.GONE
            }
        })

        viewModel.pdfDownloadResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response.first) {
                loadPDF(File(response.second))
            } else {
                showErrorToast(requireContext(), "Error downloading report!")
            }
        })

        val url = args.downloadUrl

        if (url.isNotBlank()) {
            val filepath = FileUtils.getLocalStorageFilePath(
                requireContext(),
                AppConstants.downloadedPdfFiles
            )

            val fileName = url.split("/").last()
            val pdfFile = File("$filepath/$fileName")
            if (pdfFile.exists()) {
                loadPDF(pdfFile)
            } else {
                viewModel.downloadPdfFile(url, filepath, fileName)
            }
        }
    }

    private fun loadPDF(file: File) {
        if (file.exists()) {
            try {
                lifecycleScope.launch(Dispatchers.Main.immediate) {
                    try {
                        viewDataBinding.pdfView.fromFile(file)
                            .pageFitPolicy(FitPolicy.WIDTH)
                            .enableSwipe(true)
                            .swipeHorizontal(false)
                            .onError {
                                showErrorToast(requireContext(), "Error showing report!")
                            }
                            .load()
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}