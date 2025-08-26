package com.doaa.mosalam.librarymanagementsystem.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.librarymanagementsystem.R
import com.doaa.mosalam.librarymanagementsystem.databinding.BooksItemBinding


class BooksAdapter(
    private val booksList: MutableList<Volume> = mutableListOf(),
    private val onRentClick: (Volume) -> Unit,
    private val onFavClick: (Volume) -> Unit,
    private val onItemClick: (Volume) -> Unit,
    private val onCheckFavorite: (String) -> Boolean
) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {

    inner class BooksViewHolder(private val binding: BooksItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(book: Volume) {
            book.volumeInfo
            binding.apply {
                // Title and Author
                tvTitle.text = book.volumeInfo?.title ?: "Unknown Title"
                tvAuthor.text = book.volumeInfo?.authors?.joinToString(", ") ?: "Unknown Author"

                Glide.with(imgCover.context)
                    .load(book.volumeInfo?.imageLinks?.thumbnail)
                    .placeholder(R.drawable.bg_placeholder)
                    .into(imgCover)
                // Rating
                val price = book.saleInfo?.retailPrice
                binding.btnRent.text = if (price?.amount != null && price.currencyCode != null) {
                    "${price.amount} ${price.currencyCode}"
                } else {
                    "Not for Sale"
                }
// change favorite icon based on isFavorite
                val isFavorite = onCheckFavorite(book.id ?: "")
                if (isFavorite) {
                    btnFav.setImageResource(R.drawable.ic_favorite)
                } else {
                    btnFav.setImageResource(R.drawable.ic_favorite_border)
                }
                // Click Listeners
                btnRent.setOnClickListener { onRentClick(book) }
                btnFav.setOnClickListener { onFavClick(book) }
                root.setOnClickListener { onItemClick(book) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
        val binding = BooksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BooksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind(booksList[position])
    }

    override fun getItemCount(): Int = booksList.size

    fun setData(newList: List<Volume>) {
        booksList.clear()
        booksList.addAll(newList)
        notifyDataSetChanged()
    }
}