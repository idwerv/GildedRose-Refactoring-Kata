package com.gildedrose

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    lateinit var app: GildedRose

    fun updateQuality(item: Item): Item {
        app = GildedRose(arrayOf(item))
        app.updateQuality()
        return app.items[0]
    }

    @Test
    fun qualityIsNeverNegative() {
        val item = updateQuality(Item("foo", 0, 0))

        assertThat(item.name, equalTo("foo"))
        // SellIn can be negative
        assertThat(item.sellIn, equalTo(-1))
        // Quality can't be negative
        assertThat(item.quality, equalTo(0))
    }

    @Test
    fun qualityDegradesByTwoWhenSellByDateHasPassed() {
        val item = updateQuality(Item("foo", -1, 40))

        assertThat(item.sellIn, equalTo(-2))
        // Quality can't be negative
        assertThat(item.quality, equalTo(38))
    }

}


