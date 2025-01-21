package com.example.chess_master_maker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

data class FavoriteItem(
    val imageUrl: String,
    val title: String
)

class FavoritesAdapter(
    private val favoriteItems: List<FavoriteItem>
) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.favoriteImageView)
        val titleView: TextView = itemView.findViewById(R.id.favoriteTitleView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favoriteItem = favoriteItems[position]

        // Load image using Picasso
        Picasso.get()
            .load(favoriteItem.imageUrl)
            .into(holder.imageView)

        holder.titleView.text = favoriteItem.title
    }

    override fun getItemCount(): Int {
        return favoriteItems.size
    }
}
