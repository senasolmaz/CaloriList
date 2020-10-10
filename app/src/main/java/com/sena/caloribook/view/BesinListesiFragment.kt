package com.sena.caloribook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.sena.caloribook.R
import com.sena.caloribook.adapter.BesinRecyclerAdapter
import com.sena.caloribook.viewmodel.BesinListesiViewModel
import kotlinx.android.synthetic.main.fragment_besin_listesi.*

class BesinListesiFragment : Fragment() {

    private lateinit var viewModel: BesinListesiViewModel
    private val recyclerAdapter = BesinRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_besin_listesi, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //baglama islemi
        viewModel = ViewModelProviders.of(this).get(BesinListesiViewModel::class.java)
        viewModel.refreshData()

        recylcerView.layoutManager = LinearLayoutManager(context)
        recylcerView.adapter = recyclerAdapter

        swipeRefresh.setOnRefreshListener {
            recylcerView.visibility = View.GONE
            progressBar.visibility = View.VISIBLE
            hataMesaj.visibility = View.GONE
            viewModel.refreshFromInternet()
            swipeRefresh.isRefreshing = false
        }

        observeLiveData()
    }

    fun observeLiveData() {

        viewModel.besinler.observe(viewLifecycleOwner, Observer { besinler ->
            besinler?.let {
                recylcerView.visibility = View.VISIBLE
                recyclerAdapter.listeGuncelle(besinler)
            }
        })

        viewModel.hataMesaji.observe(viewLifecycleOwner, Observer { hata ->
            hata?.let {
                if (it) {
                    recylcerView.visibility = View.GONE
                    progressBar.visibility = View.GONE
                    hataMesaj.visibility = View.VISIBLE
                } else {
                    hataMesaj.visibility = View.GONE
                }
            }
        })

        viewModel.besinYukleniyor.observe(viewLifecycleOwner, Observer { progress ->
            progress?.let {
                if (it) {
                    recylcerView.visibility = View.GONE
                    hataMesaj.visibility = View.GONE
                    progressBar.visibility = View.VISIBLE
                } else {
                    progressBar.visibility = View.GONE
                }
            }
        })
    }


}