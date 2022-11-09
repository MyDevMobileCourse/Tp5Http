package com.example.tp5
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tp5.api.Offre

class OffresListAdapter(private val context: Context, private val listOffers: MutableList<Offre>, private val mRowLayout: Int) : RecyclerView.Adapter<OffresListAdapter.OffreViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return OffreViewHolder(view)
    }

    override fun onBindViewHolder(holder: OffreViewHolder, position: Int) {
        val offre = listOffers[position]
        holder.codeF.text = offre.code.toString()
        holder.intituléF.text = offre.intitulé
        holder.specialitéF.text = offre.specialité
        holder.sociétéF.text = offre.société
        holder.nbpostesF.text = offre.nbpostes.toString()
        holder.paysF.text = offre.pays
    }

    override fun getItemCount(): Int {
        return listOffers.size
    }

    class OffreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var codeF : TextView = itemView.findViewById(R.id.codeF)
        var intituléF : TextView = itemView.findViewById(R.id.intituleF)
        var specialitéF : TextView = itemView.findViewById(R.id.specialitéF)
        var sociétéF : TextView = itemView.findViewById(R.id.societéF)
        var nbpostesF : TextView = itemView.findViewById(R.id.nbPosteF)
        var paysF : TextView = itemView.findViewById(R.id.paysF)
    }

}