package com.luisZimermann.cardapioVirtualM2.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.luisZimermann.cardapioVirtualM2.adapter.ProductAdapter
import com.luisZimermann.cardapioVirtualM2.databinding.ActivityProductListViewBinding
import com.luisZimermann.cardapioVirtualM2.service.RetrofitInstance
import com.luisZimermann.cardapioVirtualM2.service.RetrofitInstance.TAG
import retrofit2.HttpException
import java.io.IOException

class ProductListView : AppCompatActivity() {
    private lateinit var binding: ActivityProductListViewBinding
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()

        //Iniciando chamada na API
        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true

            val response = try {
                // Faz a chamada na API
                RetrofitInstance.api.getAllProducts()
            } catch (e: IOException){
                // Aqui estão os erros que podem retornar por falta de internet,
                // telas incorretas, falta de login ou dados incompletos.
                // PRO FUTURO -----> Verificar, previamente se a internet está conectada e o usuário está logado.
                Log.e(TAG, e.message.toString())
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                // Aqui, a API já conectou, mas com algum erro (servidor errado, endereço errado,
                //  servidor fora do ar, chamada não autenticada, dados errados, etc)
                Log.e(TAG, "HttpException, unexpected response.")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }

            if (response.isSuccessful && response.body() != null) {
                productAdapter.products = response.body()!!
            } else {
                Log.e(TAG, "Ops, something went whong here...")
            }

            binding.progressBar.isVisible = false
        }
    }

    private fun setupRecyclerView() = binding.rvProducts.apply {
        productAdapter = ProductAdapter()
        adapter = productAdapter
        layoutManager = LinearLayoutManager(this@ProductListView)
    }
}