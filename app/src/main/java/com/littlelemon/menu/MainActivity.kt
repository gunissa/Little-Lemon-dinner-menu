package com.littlelemon.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.core.view.MenuCompat
import com.littlelemon.menu.ProductsWarehouse.productsList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

class MainActivity : ComponentActivity() {


    private val productsState: MutableStateFlow<Products> =
        MutableStateFlow(Products(ProductsWarehouse.productsList))


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            InitUI(this::startProductActivity)
        }

    }

    private fun startProductActivity(productItem: ProductItem) {
        // Create an intent to start the ProductActivity
        val intent = Intent(this, ProductActivity::class.java)

        // Add extra data to the intent to pass to ProductActivity
        intent.putExtra(ProductActivity.KEY_TITLE, productItem.title)
        intent.putExtra(ProductActivity.KEY_PRICE, productItem.price)
        intent.putExtra(ProductActivity.KEY_IMAGE, productItem.image)
        intent.putExtra(ProductActivity.KEY_CATEGORY, productItem.category)

        // Start the ProductActivity with the intent
        startActivity(intent)
    }


    @Composable
    fun InitUI(startProductActivity: (ProductItem) -> Unit) {
        // Collect the product state as a Composable
        val products by productsState.collectAsState()

        // Display the products grid
        ProductsGrid(products = products, startProductActivity = startProductActivity)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the products menu and enable group dividers
        menuInflater.inflate(R.menu.products_menu, menu)
        MenuCompat.setGroupDividerEnabled(menu, true)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle menu item selections for sorting and filtering
        if (item.groupId == R.id.sorting) {
            // Determine the selected sorting option
            val type = when (item.itemId) {
                R.id.sort_a_z -> SortType.Alphabetically
                R.id.sort_price_asc -> SortType.PriceAsc
                R.id.sort_price_desc -> SortType.PriceDesc
                else -> SortType.Alphabetically
            }
            // Update the product state with sorted products
            productsState.update {
                Products(
                    SortHelper().sortProducts(
                        type,
                        productsList
                    )
                )
            }
        } else if (item.groupId == R.id.filter) {
            // Determine the selected filtering option
            val type = when (item.itemId) {
                R.id.filter_all -> FilterType.All
                R.id.filter_drinks -> FilterType.Drinks
                R.id.filter_food -> FilterType.Food
                R.id.filter_dessert -> FilterType.Dessert
                else -> FilterType.All
            }
            // Update the product state with filtered products
            productsState.update {
                Products(
                    FilterHelper().filterProducts(
                        type,
                        productsList
                    )
                )
            }
        }
        return true
    }
}