package com.doaa.mosalam.librarymanagementsystem.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.doaa.mosalam.domain.model.trendingBooks.Volume
import com.doaa.mosalam.librarymanagementsystem.databinding.BooksItemBinding
import com.doaa.mosalam.librarymanagementsystem.R


class BooksAdapter(
//    private val booksList: MutableList<Books> = mutableListOf(),
//    private val onRentClick: (Books) -> Unit,
//    private val onFavClick: (Books) -> Unit,
//    private val onItemClick: (Books) -> Unit
//) : RecyclerView.Adapter<BooksAdapter.BooksViewHolder>() {
//
//    inner class BooksViewHolder(private val binding: BooksItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(book: Books) {
//            binding.apply {
//                // Title and Author
//                tvTitle.text = book.title ?: "Unknown Title"
//                tvAuthor.text = book.author ?: "Unknown Author"
//
//                // Cover Image
//                Glide.with(imgCover.context)
//                    .load(book.coverImageUrl)
//                    .placeholder(R.drawable.bg_placeholder)
//                    .into(imgCover)
//
//                btnRent.text = "Rent for $4.99"
//
//                // Click Listeners
//                btnRent.setOnClickListener { onRentClick(book) }
//                btnFav.setOnClickListener { onFavClick(book) }
//                root.setOnClickListener { onItemClick(book) }
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder {
//        val binding = BooksItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//        return BooksViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
//        holder.bind(booksList[position])
//    }
//
//    override fun getItemCount(): Int = booksList.size
//
//    fun setData(newList: List<Books>) {
//        booksList.clear()
//        booksList.addAll(newList)
//        notifyDataSetChanged()
//    }
    private val booksList: MutableList<Volume> = mutableListOf(),
    private val onRentClick: (Volume) -> Unit,
    private val onFavClick: (Volume) -> Unit,
    private val onItemClick: (Volume) -> Unit
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

                btnRent.text = "Rent for $4.99"

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