package com.luisZimermann.cardapioVirtualM2.service

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.luisZimermann.cardapioVirtualM2.model.ProductModel
import java.util.*

class DatabaseService (ctx: Context): SQLiteOpenHelper(ctx, "restaurant_menu",null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE =
            "CREATE TABLE product (" +
                    "id INTEGER PRIMARY KEY," +
                    " name TEXT," +
                    " amount INTEGER," +
                    " description TEXT," +
                    " thumb_url TEXT," +
                    " category_name TEXT" +
                    ");"
        db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val DROP_TABLE = "DROP TABLE IF EXISTS product"
        db?.execSQL(DROP_TABLE)
        onCreate(db)
    }

     fun addProduct(product: ProductModel): Boolean {
         println("Salvando produto ${product.product_name}" )
        val db = this.writableDatabase
        val values = ContentValues()

        values.put("name", product.product_name)
        values.put("amount", product.amount)
        values.put("description", product.description)
        values.put("thumb_url", product.thumb_url)

        val _success = db.insert("product",null, values)

        return (("$_success").toInt() != -1)
    }

    @SuppressLint("Range")
    fun getAllProducts(): ArrayList<ProductModel> {
        val productList = ArrayList<ProductModel>()
        val db = writableDatabase
        val selectQuery = "SELECT * FROM product"
        val cursor = db.rawQuery(selectQuery, null)
        if(cursor != null){
            if(cursor.moveToFirst()){
                do{
                    val id = cursor.getInt(cursor.getColumnIndex("name"))
                    val amount = cursor.getFloat(cursor.getColumnIndex("amount"))
                    val description = cursor.getString(cursor.getColumnIndex("description"))
                    val product_name = cursor.getString(cursor.getColumnIndex("name"))
                    val thumb_url = cursor.getString(cursor.getColumnIndex("thumb_url"))

                    val product = ProductModel(
                        id,
                        amount,
                        description,
                        product_name,
                        thumb_url
                    )

                    productList.add(product)
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        return productList
    }

    fun deleteAllProducts(): Boolean {
        val db = this.writableDatabase
        val _success = db.delete("product", null,null).toLong()
        db.close()
        return ("$_success").toInt() != -1
    }
}