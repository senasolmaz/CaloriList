package com.sena.caloribook.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sena.caloribook.R
import com.sena.caloribook.databinding.BesinItemBinding
import com.sena.caloribook.model.Besin
import com.sena.caloribook.util.gorselIndir
import com.sena.caloribook.util.placeHolderYap
import com.sena.caloribook.view.BesinListesiFragmentDirections
import kotlinx.android.synthetic.main.besin_item.view.*

class BesinRecyclerAdapter(val besinListesi: ArrayList<Besin>) :
    RecyclerView.Adapter<BesinRecyclerAdapter.BesinViewHolder>(), BesinClickListener {

    class BesinViewHolder(var view: BesinItemBinding) : RecyclerView.ViewHolder(view.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BesinViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        // val view = inflater.inflate(R.layout.besin_item, parent, false)
        val view =
            DataBindingUtil.inflate<BesinItemBinding>(inflater, R.layout.besin_item, parent, false)
        return BesinViewHolder(view)
    }

    override fun getItemCount(): Int {
        return besinListesi.size
    }

    override fun onBindViewHolder(holder: BesinViewHolder, position: Int) {

        holder.view.model = besinListesi[position]
        holder.view.click = this
        /* holder.itemView.isim.text = besinListesi.get(position).isim
         holder.itemView.kalori.text = besinListesi.get(position).kalori
         holder.itemView.besinImage.gorselIndir(
             besinListesi.get(position).gorsel,
             placeHolderYap(holder.itemView.context)
         )

         holder.itemView.setOnClickListener {
             val action =
                 BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetailFragment(besinListesi.get(position).uuid)
             Navigation.findNavController(it).navigate(action)
         }

         */
    }

    fun listeGuncelle(yeniListe: List<Besin>) {
        besinListesi.clear()
        besinListesi.addAll(yeniListe)
        notifyDataSetChanged()
    }

    override fun besinTiklandi(view: View) {
        val action =
            BesinListesiFragmentDirections.actionBesinListesiFragmentToBesinDetailFragment(
                view.uuid.text.toString().toInt()
            )
        Navigation.findNavController(view).navigate(action)
    }
}