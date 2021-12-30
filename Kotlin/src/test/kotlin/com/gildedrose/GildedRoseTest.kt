package com.gildedrose

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import org.junit.jupiter.api.Test

internal class GildedRoseTest {

    @Test
    fun foo() {
        val items = arrayOf(Item("foo", 0, 0))
        val app = GildedRose(items)
        app.updateQuality()

        val item = app.items[0]

        assertThat(item.name, equalTo("foo"))
        assertThat(item.sellIn, equalTo(-1))
        assertThat(item.quality, equalTo(-1))
    }
}


