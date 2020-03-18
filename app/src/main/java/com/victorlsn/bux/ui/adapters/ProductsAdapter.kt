package com.victorlsn.bux.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.victorlsn.bux.R
import com.victorlsn.bux.data.api.models.Product
import com.victorlsn.bux.util.extensions.inflate

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ItemHolder>() {

    private val products: MutableList<Product> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = parent.inflate(R.layout.item_product, false)
        return ItemHolder(view)
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(products[position])
    }


    override fun getItemCount(): Int {
        return products.size
    }

    fun setProducts(productsList: List<Product>) {
        products.clear()
        products.addAll(productsList)
        notifyDataSetChanged()
    }

    fun addProduct(product: Product) {
        products.add(product)
        notifyDataSetChanged()
    }


    class ItemHolder(val v: View) : RecyclerView.ViewHolder(v) {
        private var statusIcon: ImageView = itemView.findViewById(R.id.statusImageView)
        private var productName: TextView = itemView.findViewById(R.id.productNameTextView)
        private var productPrice: TextView = itemView.findViewById(R.id.productPriceTextView)
        private var productPriceDiff: TextView =
            itemView.findViewById(R.id.productPriceDifferenceTextView)

        fun bind(product: Product) {

            productName.text = product.displayName
            productPrice.text = product.currentPrice?.getFormattedPrice()
            productPriceDiff.text = product.getFormattedPriceDiff()
        }
    }
}
