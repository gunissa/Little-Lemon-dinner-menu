package com.littlelemon.menu

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class ProductActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Retrieve values from the intent
        val title = intent.getStringExtra(KEY_TITLE)
        val price = intent.getDoubleExtra(KEY_PRICE, 0.0)
        val category = intent.getStringExtra(KEY_CATEGORY)
        val image = intent.getIntExtra(KEY_IMAGE, -1)

        // Create a ProductItem instance with the retrieved values
        val productItem = ProductItem(title ?: "", price, category ?: "", image)
//todo replace with the passed values from intent
        // Set the content and pass the productItem to ProductDetails
        setContent { ProductDetails(productItem) }
    }
    //
    companion object{
        const val KEY_TITLE = "title"
        const val KEY_PRICE = "price"
        const val KEY_IMAGE = "image"
        const val KEY_CATEGORY = "category"
    }

}