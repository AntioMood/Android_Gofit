package com.example.gofit.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.gofit.R
import com.example.gofit.model.ModelDepositKelas
import java.util.*
import kotlin.collections.ArrayList

class DepositKelasAdapter (private var depositKelasList: List<ModelDepositKelas>, context: Context?) :
    RecyclerView.Adapter<DepositKelasAdapter.ViewHolder>(), Filterable {
    private var filteredDepositKelasList: MutableList<ModelDepositKelas>
    private val context: Context

    init{
        filteredDepositKelasList = ArrayList(depositKelasList)
        this.context = context!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_deposit_kelas, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int{
        return filteredDepositKelasList.size
    }

    fun setDepositKelasList(depositKelasList: Array<ModelDepositKelas>){
        this.depositKelasList = depositKelasList.toList()
        filteredDepositKelasList = depositKelasList.toMutableList()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val deposit_kelas = filteredDepositKelasList[position]
        holder.tvNamaKelas.text = deposit_kelas.nama_kelas
        holder.tvDepositKelas.text = deposit_kelas.deposit_kelas
        holder.tvTanggalKadaluarsa.text = deposit_kelas.tgl_exp
    }

    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val charSequenceString = charSequence.toString()
                val filtered: MutableList<ModelDepositKelas> = java.util.ArrayList()
                if (charSequenceString.isEmpty()){
                    filtered.addAll(depositKelasList)
                }else{
                    for(deposit_kelas in depositKelasList){
                        if(deposit_kelas.nama_kelas.lowercase(Locale.getDefault())
                                .contains(charSequenceString.lowercase(Locale.getDefault()))
                        ) filtered.add(deposit_kelas)
                    }
                }
                val filterResults = FilterResults()
                filterResults.values = filtered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence, filterResults: FilterResults) {
                filteredDepositKelasList.clear()
                filteredDepositKelasList.addAll((filterResults.values as List<ModelDepositKelas>))
                notifyDataSetChanged()
            }
        }
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var tvNamaKelas: TextView
        var tvDepositKelas: TextView
        var tvTanggalKadaluarsa: TextView

        init {
            tvNamaKelas = itemView.findViewById(R.id.tv_nama_kelas_deposit_kelas)
            tvDepositKelas = itemView.findViewById(R.id.tv_jumlah_deposit_kelas)
            tvTanggalKadaluarsa = itemView.findViewById(R.id.tv_tgl_kadaluarsa_deposit_kelas)
        }
    }
}