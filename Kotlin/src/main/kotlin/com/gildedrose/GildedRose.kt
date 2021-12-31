package com.gildedrose

class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (item in items) {

            // Decrease the -QUALITY- by 1, except for Aged Brie, Backstage passes and Sulfuras
            if (item.name != "Aged Brie" && item.name != "Backstage passes to a TAFKAL80ETC concert") {
                if (item.quality > 0) {
                    if (item.name != "Sulfuras, Hand of Ragnaros") {
                        item.quality = item.quality - 1
                    }
                }
            } else {
                // Aged Brie & Backstage passes -QUALITY- changes
                if (item.quality < 50) {
                    item.quality = item.quality + 1

                    // Backstage passes only code
                    if (item.name == "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.sellIn < 11) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1
                            }
                        }

                        if (item.sellIn < 6) {
                            if (item.quality < 50) {
                                item.quality = item.quality + 1
                            }
                        }
                    }
                }
            }

            // Decrease -SELLIN- date by 1, except for Sulfuras
            if (item.name != "Sulfuras, Hand of Ragnaros") {
                item.sellIn = item.sellIn - 1
            }

            if (item.sellIn < 0) {
                if (item.name != "Aged Brie") {
                    if (item.name != "Backstage passes to a TAFKAL80ETC concert") {
                        if (item.quality > 0) {
                            if (item.name != "Sulfuras, Hand of Ragnaros") {
                                item.quality = item.quality - 1
                            }
                        }
                    // Backstage passes QUALITY = 0 when SellIn date is 0 or less.
                    } else {
                        item.quality = item.quality - item.quality
                    }
                // Aged Brie
                } else {
                    if (item.quality < 50) {
                        item.quality = item.quality + 1
                    }
                }
            }
        }
    }
}

