package com.doaa.mosalam.librarymanagementsystem.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.doaa.mosalam.domain.model.favorite.FavoriteBooks
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.databinding.ItemMyShelfBinding

class FavoriteBooksAdapter(
    private val onRemoveClick: (FavoriteBooks) -> Unit,
    private val onStatusChange: (FavoriteBooks, String) -> Unit
) : RecyclerView.Adapter<FavoriteBooksAdapter.FavoriteBooksViewHolder>() {

    private val favoriteBooksList = mutableListOf<FavoriteBooks>()

    fun setData(newList: List<FavoriteBooks>) {
        favoriteBooksList.clear()
        favoriteBooksList.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteBooksViewHolder {
        val binding = ItemMyShelfBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoriteBooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteBooksViewHolder, position: Int) {
        holder.bind(favoriteBooksList[position])
    }

    override fun getItemCount(): Int = favoriteBooksList.size

    inner class FavoriteBooksViewHolder(private val binding: ItemMyShelfBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(fvBooks: FavoriteBooks) = with(binding) {
            tvBookStatus.text = when (fvBooks.readingStatus) {
                "not_started" -> "not started"
                "reading" -> "Reading"
                "rented" -> "Rented"
                "finished" -> "Read"
                else -> "Unknown"
            }

            Glide.with(imgCover.context)
                .load(fvBooks.thumbnail)
                .placeholder(R.drawable.bg_placeholder)
                .into(imgCover)

            // button to remove from favorite
//            btnRemoveFav.setOnClickListener {
//                onRemoveClick(fvBooks)
//            }

//            btnChangeStatus.setOnClickListener {
//                // هنا ممكن تفتحي Dialog أو Menu يختار منه
//                onStatusChange(fvBooks, "reading")
//            }
        }
    }
}
