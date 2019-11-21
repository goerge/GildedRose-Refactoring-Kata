package com.gildedrose;

import java.util.Map;
import java.util.function.Consumer;

import static java.lang.Math.max;
import static java.lang.Math.min;

class GildedRose {
  private static final String AGED_BRIE = "Aged Brie";
  private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
  private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

  private Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
  }

  private static final Map<String, Consumer<Item>> UPDATER = Map.of(
    AGED_BRIE, GildedRose::updateAgedBrie,
    BACKSTAGE_PASS, GildedRose::updateBackstagePass,
    SULFURAS, GildedRose::updateSulfaras);

  public void updateQuality() {
    for (Item item : items) {
      UPDATER.getOrDefault(item.name, GildedRose::updateDefault).accept(item);
    }
  }

  private static void updateDefault(Item item) {
    item.sellIn--;
    item.quality = decreaseQualityDownToZero(item.quality);
    if (item.sellIn < 0) {
      item.quality = decreaseQualityDownToZero(item.quality);
    }
  }

  private static void updateAgedBrie(Item item) {
    item.sellIn--;
    item.quality = increaseQualityUpTo50(item.quality);
    if (item.sellIn < 0) {
      item.quality = increaseQualityUpTo50(item.quality);
    }
  }

  private static void updateBackstagePass(Item item) {
    item.sellIn--;
    item.quality = increaseQualityUpTo50(item.quality);
    if (item.sellIn < 10) {
      item.quality = increaseQualityUpTo50(item.quality);
    }
    if (item.sellIn < 5) {
      item.quality = increaseQualityUpTo50(item.quality);
    }
    if (item.sellIn < 0) {
      item.quality = 0;
    }
  }

  private static void updateSulfaras(Item item) {
  }

  private static int decreaseQualityDownToZero(int quality) {
    return max(0, quality - 1);
  }

  private static int increaseQualityUpTo50(int quality) {
    return min(quality + 1, 50);
  }
}
