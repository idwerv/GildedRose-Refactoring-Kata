package com.gildedrose

private const val AGED_BRIE = "Aged Brie"
private const val BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert"
private const val SULFURAS = "Sulfuras, Hand of Ragnaros"
private const val DEFAULT = ""


class GildedRose(var items: Array<Item>) {

    fun updateQuality() {
        for (item in items) {
            val rule: QualityRule =
                qualityRules.firstOrNull { it.name == item.name } ?: qualityRules.first { it.name == DEFAULT }
            if (rule.sellInAdjustment) item.sellIn -= 1

            item.quality =
                (item.quality + rule.qualityIncrement(item) + pastDueDateAdjustment(item, rule)).coerceIn(0..50)
        }
    }

    private fun pastDueDateAdjustment(item: Item, rule: QualityRule): Int =
        if (item.sellIn >= 0) 0 else rule.pastDueDateAdjustment(item)

}

class QualityRule(
    val name: String,
    val qualityIncrement: (Item) -> Int = { -1 },
    val pastDueDateAdjustment: (Item) -> Int = { -1 },
    val sellInAdjustment: Boolean = true,
    val conjuredItem: Boolean = false
)

val qualityRules = arrayOf(
    QualityRule(AGED_BRIE, { 1 }, { 1 }),
    QualityRule(BACKSTAGE_PASS, { backstagePassRule(it) }, { -it.quality } ),
    QualityRule(SULFURAS, { 0 }, { 0 }, false),
    QualityRule(DEFAULT)
)

private fun backstagePassRule(it: Item) = when (it.sellIn) {
    in 0..4 -> 3
    in 5..9 -> 2
    in 10..49 -> 1
    else -> 0
}
