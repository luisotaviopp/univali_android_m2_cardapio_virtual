package com.luisZimermann.cardapioVirtualM2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.luisZimermann.cardapioVirtualM2.databinding.ItemProductBinding
import com.luisZimermann.cardapioVirtualM2.model.ProductModel
import com.luisZimermann.cardapioVirtualM2.service.DatabaseService
import com.squareup.picasso.Picasso

class ProductAdapter : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root)

        private val diffCallback = object : DiffUtil.ItemCallback<ProductModel>(){
            override fun areItemsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem.product_name == newItem.product_name
            }

            override fun areContentsTheSame(oldItem: ProductModel, newItem: ProductModel): Boolean {
                return oldItem == newItem
            }
        }

        private val differ = AsyncListDiffer(this, diffCallback)

        var products: List<ProductModel>
            get() = differ.currentList
            set(value) {differ.submitList(value)}

        // Quantidade de Itens que serão inseridos na lista
        override fun getItemCount() = products.size

        // Cria o layout do ítem
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
            return ProductViewHolder(ItemProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        }

        // Insere os valores vindos da lista no layout
        override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
            holder.binding.apply {
                val product = products[position]

                // Adiciona os produtos no SQL
                val db = DatabaseService(holder.binding.root.context)
                db.addProduct(product)

                // Busca as imagens do URL
                Picasso.get().load(product.thumb_url)
                    .into(imageViewProduct)

                tvProductName.text = product.product_name
                tvProductDescription.text = product.description
                tvProductValue.text = "R$${(product.amount / 100)}"
            }
        }
    }