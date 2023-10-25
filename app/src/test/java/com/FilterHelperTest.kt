package com

import com.littlelemon.menu.FilterHelper
import com.littlelemon.menu.FilterType
import com.littlelemon.menu.ProductItem
import org.junit.Assert.assertEquals
import org.junit.Test

class FilterHelperTest {
    @Test
    fun filterProducts_filterTypeDessert_croissantReturned() {
        // Create a list with several items of different categories
        val sampleProductsList = mutableListOf(
            ProductItem(title = "Black tea", price = 3.00, category = "Drinks", R.drawable.black_tea),
            ProductItem(title = "Croissant", price = 7.00, category = "Dessert", R.drawable.croissant),
            ProductItem(title = "Bouillabaisse", price = 20.00, category = "Food", R.drawable.bouillabaisse)
        )


        val filterHelper = FilterHelper()
        val filteredList = filterHelper.filterProducts(FilterType.Dessert, sampleProductsList)

        // Assert that the filtered list contains only one item, which is "Croissant"
        assertEquals(1, filteredList.size)
        assertEquals("Croissant", filteredList[0].title)
    }
}