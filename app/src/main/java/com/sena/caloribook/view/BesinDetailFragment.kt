package com.sena.caloribook.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.sena.caloribook.R
import com.sena.caloribook.databinding.BesinItemBinding
import com.sena.caloribook.databinding.FragmentBesinDetailBinding
import com.sena.caloribook.util.gorselIndir
import com.sena.caloribook.util.placeHolderYap
import com.sena.caloribook.viewmodel.BesinDetayViewModel
import kotlinx.android.synthetic.main.fragment_besin_detail.*

class BesinDetailFragment : Fragment() {

    private lateinit var viewModel: BesinDetayViewModel
    var besinId = 0
    private lateinit var dataBinding: FragmentBesinDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_besin_detail, container, false)
        // Inflate the layout for this fragment
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            besinId = BesinDetailFragmentArgs.fromBundle(
                it
            ).besinid
        }

        viewModel = ViewModelProviders.of(this).get(BesinDetayViewModel::class.java)
        viewModel.roomVerisiniAl(besinId)

        observeLiveData()
    }

    fun observeLiveData() {

        viewModel.besinLiveData.observe(viewLifecycleOwner, Observer { besin ->
            besin?.let {
                dataBinding.detay = it
                /* detayIsim.text = it.isim
                 detayKalori.text = it.kalori
                 detayKarbon.text = it.karbonhidrat
                 detayProtein.text = it.protein
                 detayYag.text = it.yag
                 context?.let {
                     detayImage.gorselIndir(besin.gorsel, placeHolderYap(it))

                 }                */

            }
        })
    }

}