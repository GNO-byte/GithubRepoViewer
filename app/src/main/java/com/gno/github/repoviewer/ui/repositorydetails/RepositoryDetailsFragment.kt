package com.gno.github.repoviewer.ui.repositorydetails

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.gno.github.repoviewer.activityComponent
import com.gno.github.repoviewer.databinding.FragmentRepositoryDetailsBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject


class RepositoryDetailsFragment : Fragment() {

    private lateinit var binding: FragmentRepositoryDetailsBinding

    private val url by lazy { arguments?.getString("url", "") ?: "" }

    @Inject
    lateinit var viewModelFactory: RepositoryDetailsViewModel.RepositoryDetailsViewModelFactory.Factory
    private val viewModel: RepositoryDetailsViewModel by viewModels {
        viewModelFactory.create(url)
    }


    override fun onAttach(context: Context) {
        context.activityComponent.fragmentComponent().fragment(this).build()
            .inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRepositoryDetailsBinding.inflate(inflater, container, false).let {
        binding = it
        it.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        initFad(binding.fad)
        initWebView(binding.web)

        viewModel.urlLiveData.observe(viewLifecycleOwner) {
            if (savedInstanceState == null) {
                binding.web.loadUrl(it)
            }
        }
    }

    private fun initFad(fad: FloatingActionButton) {
        fad.setOnClickListener {
            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
            i.putExtra(Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(i, "Share URL"))
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    fun initWebView(webView: WebView) {

        webView.settings.loadsImagesAutomatically = true
        webView.settings.javaScriptEnabled = true

        webView.webViewClient = MyWebView(binding.progress)
    }

    private class MyWebView(private val progressbar: ProgressBar) : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            return true
        }

        override fun onLoadResource(view: WebView, url: String) {
            super.onLoadResource(view, url)
            progressbar.progress = view.progress
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            progressbar.invalidate()
            progressbar.visibility = View.GONE
        }

    }
}