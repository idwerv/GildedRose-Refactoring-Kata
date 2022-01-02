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
        assertThat(item.quality, equalTo(38))
    }

    @Test
    fun qualityDegradesByTwoWhenSellByDateHasPassedNeverNegative() {
        val item = updateQuality(Item("foo", -1, 1))
        assertThat(item.sellIn, equalTo(-2))
        assertThat(item.quality, equalTo(0))
    }

    @Test
    fun agedBrieQualityIncreasesBy1() {
        val item = updateQuality(Item("Aged Brie", 10, 0))
        assertThat(item.sellIn, equalTo(9))
        assertThat(item.quality, equalTo(1))
    }

    @Test
    fun agedBrieQualityIncreasesBy2WhenSellByDateHasPassed() {
        val item = updateQuality(Item("Aged Brie", -1, 0))
        assertThat(item.sellIn, equalTo(-2))
        assertThat(item.quality, equalTo(2))
    }

    @Test
    fun agedBrieQualityDoesntIncreaseOver50() {
        val item = updateQuality(Item("Aged Brie", 0, 50))
        assertThat(item.sellIn, equalTo(-1))
        assertThat(item.quality, equalTo(50))
    }

    @Test
    fun agedBrieQualityDoesntIncreaseOver50EvenWhenSellByDatePassed() {
        val item = updateQuality(Item("Aged Brie", -1, 49))
        assertThat(item.sellIn, equalTo(-2))
        assertThat(item.quality, equalTo(50))
    }

    // Backstage passes = bp
    @Test
    fun bpQualityIncreasesBy1() {
        val item = updateQuality(Item("Backstage passes to a TAFKAL80ETC concert", 20, 0))
        assertThat(item.sellIn, equalTo(19))
        assertThat(item.quality, equalTo(1))
    }

    @Test
    fun bpQualityIncreasesBy2() {
        var item = updateQuality(Item("Backstage passes to a TAFKAL80ETC concert", 10, 0))
        assertThat(item.sellIn, equalTo(9))
        assertThat(item.quality, equalTo(2))

        item = updateQuality(Item("Backstage passes to a TAFKAL80ETC concert", 6, 0))
        assertThat(item.sellIn, equalTo(5))
        assertThat(item.quality, equalTo(2))
    }

    @Test
    fun bpQualityIncreasesBy3() {
        var item = updateQuality(Item("Backstage passes to a TAFKAL80ETC concert", 5, 0))
        assertThat(item.sellIn, equalTo(4))
        assertThat(item.quality, equalTo(3))

        item = updateQuality(Item("Backstage passes to a TAFKAL80ETC concert", 1, 0))
        assertThat(item.sellIn, equalTo(0))
        assertThat(item.quality, equalTo(3))
    }

    @Test
    fun bpQualityIsSetTo0WhenSellByDatePassed() {
        val item = updateQuality(Item("Backstage passes to a TAFKAL80ETC concert", 0, 40))
        assertThat(item.sellIn, equalTo(-1))
        assertThat(item.quality, equalTo(0))
    }

    @Test
    fun bpQualityNeverMoreThan50() {
        val item = updateQuality(Item("Backstage passes to a TAFKAL80ETC concert", 3, 49))
        assertThat(item.sellIn, equalTo(2))
        assertThat(item.quality, equalTo(50))
    }

    @Test
    fun sulfurasQualityAndSellInNeverChange() {
        var item = updateQuality(Item("Sulfuras, Hand of Ragnaros", 50, 50))
        assertThat(item.sellIn, equalTo(50))
        assertThat(item.quality, equalTo(50))

        item = updateQuality(Item("Sulfuras, Hand of Ragnaros", 10, 10))
        assertThat(item.sellIn, equalTo(10))
        assertThat(item.quality, equalTo(10))
    }

    @Test
    fun conjuredItemQualityDegrades2xFaster() {
        val item = updateQuality(Item("Conjured item", 5, 10))
        assertThat(item.sellIn, equalTo(4))
        assertThat(item.quality, equalTo(8))
    }

}


