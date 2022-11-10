package com.example.tp5
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.tp5.api.Offre


class OffresListAdapter(private val context: Context, private val listOffers: MutableList<Offre>, private val mRowLayout: Int) : RecyclerView.Adapter<OffresListAdapter.OffreViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OffreViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(mRowLayout, parent, false)
        return OffreViewHolder(view)
    }

    var onItemClick: ((Offre,Int) -> Unit)? = null

    override fun onBindViewHolder(holder: OffreViewHolder, position: Int) {
        val offre = listOffers[position]
        holder.codeF.text = offre.code.toString()
        holder.intituléF.text = offre.intitulé
        holder.specialitéF.text = offre.specialité
        holder.sociétéF.text = offre.société
        holder.nbpostesF.text = offre.nbpostes.toString()
        holder.paysF.text = offre.pays
        val factory = DrawableCrossFadeFactory.Builder().setCrossFadeEnabled(true).build()

        Glide.with(context)
            .load(offre.logo)
            .transition(DrawableTransitionOptions.withCrossFade(factory))
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .sizeMultiplier(0.3f)
            .into(holder.logo)

        holder.specialitéF.setOnClickListener{
            onItemClick?.invoke(offre,position)
        }
        holder.user_item_scroll.setOnClickListener{
            onItemClick?.invoke(offre,position)
        }
        holder.user_item.setOnClickListener{
            onItemClick?.invoke(offre,position)
        }

    }

    override fun getItemCount(): Int {
        return listOffers.size
    }

    class OffreViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var codeF : TextView = itemView.findViewById(R.id.codeF)
        var user_item : CardView = itemView.findViewById(R.id.user_item)
        var intituléF : TextView = itemView.findViewById(R.id.intituleF)
        var specialitéF : TextView = itemView.findViewById(R.id.specialitéF)
        var sociétéF : TextView = itemView.findViewById(R.id.societéF)
        var nbpostesF : TextView = itemView.findViewById(R.id.nbPosteF)
        var paysF : TextView = itemView.findViewById(R.id.paysF)
        var user_item_scroll : HorizontalScrollView = itemView.findViewById(R.id.user_item_scroll)
        var logo : ImageView = itemView.findViewById(R.id.logo)

    }

}