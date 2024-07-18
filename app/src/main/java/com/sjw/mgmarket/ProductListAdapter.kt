package com.sjw.mgmarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sjw.mgmarket.databinding.ItemProductBinding
import java.text.DecimalFormat

class ProductListAdapter(val items: MutableList<Product>) : RecyclerView.Adapter<ProductListAdapter.Holder>() {

    interface ItemClick {
        fun onClick(view: View, position: Int)
    }

    var itemClick: ItemClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)
        }


        var isChecked = true

        holder.productImg.setImageResource(items[position].imgSrc)
        holder.productName.text = items[position].name
        holder.productLocation.text = items[position].address
        holder.productPrice.text = items[position].price
        holder.productChatCount.text = items[position].chatting.toString()
        holder.productFavoriteCount.text = items[position].favorite.toString()

        holder.productFavoriteBtn.setOnClickListener {
            if (isChecked) {
                items[position].favorite += 1
                holder.productFavoriteCount.text = items[position].favorite.toString()
                holder.productFavoriteBtn.setImageResource(R.drawable.ic_favorite_full)
                isChecked = false
            } else {
                items[position].favorite -= 1
                holder.productFavoriteCount.text = items[position].favorite.toString()
                holder.productFavoriteBtn.setImageResource(R.drawable.ic_favorite)
                isChecked = true
            }
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class Holder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {
        val productImg = binding.productImg
        val productName = binding.productName
        val productLocation = binding.productLocation
        val productPrice = binding.productPrice
        val productChatCount = binding.productChatCount
        val productFavoriteBtn = binding.productFavoriteBtn
        val productFavoriteCount = binding.productFavoriteCount
    }
}
