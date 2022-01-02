package com.gildedrose

private const val AGED_BRIE = "Aged Brie"
private const val BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert"
private const val SULFURAS = "Sulfuras, Hand of Ragnaros"

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (item in items) {
            updateSellIn(item)
            updateItemQuality(item)
        }
    }

    fun updateSellIn(item: Item) {
        if (item.name != SULFURAS) item.sellIn -= 1
    }

    private fun updateItemQuality(item: Item) {
        item.quality = when (item.name) {
            AGED_BRIE -> item.quality + 1
            BACKSTAGE_PASS -> {
                when (item.sellIn) {
                    in 0..4 -> item.quality + 3
                    in 5..9 -> item.quality + 2
                    in 10..49 -> item.quality + 1
                    else -> 0
                }
            }
            SULFURAS -> item.quality
            else -> item.quality - 1
        }.plus(getSellInQualityAdjustment(item))
            .coerceIn(0..50)
    }

    private fun getSellInQualityAdjustment(item: Item): Int {
        if (item.sellIn >= 0) return 0
        return when (item.name) {
            AGED_BRIE -> 1
            BACKSTAGE_PASS, SULFURAS -> 0
            else -> -1
        }
    }
}


