package com.luisZimermann.cardapioVirtualM2.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

        // Não precisa checar previamente, a API tenta conectar e retorna um erro se não conseguir
        // Chama o SQLITE apenas se a API retornar algum erro.
        //
        // -------------------------------------------------> Checando a Internet
        // val cm = baseContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        //    cm?.run {
        //
        //        cm.getNetworkCapabilities(cm.activeNetwork)?.run {
        //            if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
        //                hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
        //                hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
        //
        //                    // -------------> CONECTADO
        //                    Toast.makeText(applicationContext, "Conectado na wifi", Toast.LENGTH_LONG).show()
        //
        //
        //            } else {
        //                Toast.makeText(applicationContext, "Sem conexão", Toast.LENGTH_LONG).show()
        //            }
        //
        //        }
        //    }
        //} else {
        //    Toast.makeText(applicationContext, "Versão do android indisponível.", Toast.LENGTH_LONG).show()
        //}



        // -------------------------------------------------> Iniciando o GET na API.
        getDataFromApi()
    }

    private fun getDataFromApi(){

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

                getDataFromSqLite()

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
                Toast.makeText(applicationContext, "Pegando dados da API...", Toast.LENGTH_LONG).show()
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

    private fun getDataFromSqLite(){
        Toast.makeText(applicationContext, "Pegando dados do SQLITE...", Toast.LENGTH_LONG).show()
    }
}