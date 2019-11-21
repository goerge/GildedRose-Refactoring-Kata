package com.gildedrose;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

class GildedRose {
  private static final String AGED_BRIE = "Aged Brie";
  private static final String BACKSTAGE_PASS = "Backstage passes to a TAFKAL80ETC concert";
  private static final String SULFURAS = "Sulfuras, Hand of Ragnaros";

  private Item[] items;

  public GildedRose(Item[] items) {
    this.items = items;
  }

  private static final Map<String, Consumer<Item>> UPDATER = new HashMap<String, Consumer<Item>>() {
    {
      put(AGED_BRIE, GildedRose::updateAgedBrie);
      put(BACKSTAGE_PASS, GildedRose::updateBackstagePass);
      put(SULFURAS, GildedRose::updateSulfaras);
    }

    @Override
    public Consumer<Item> get(Object key) {
      return super.getOrDefault(key, GildedRose::updateDefault);
    }
  };

  public void updateQuality() {
    for (Item item : items) {
      UPDATER.get(item.name).accept(item);
    }
  }

  private static void updateDefault(Item item) {
    item.sellIn = item.sellIn - 1;
    if (item.quality > 0) {
      item.quality = item.quality - 1;
    }
    if (item.sellIn < 0) {
      if (item.quality > 0) {
        item.quality = item.quality - 1;
      }
    }
  }

  private static void updateAgedBrie(Item item) {
    item.sellIn = item.sellIn - 1;
    if (item.quality < 50) {
      item.quality = item.quality + 1;
    }
    if (item.sellIn < 0) {
      if (item.quality < 50) {
        item.quality = item.quality + 1;
      }
    }
  }

  private static void updateBackstagePass(Item item) {
    if (item.quality < 50) {
      item.quality = item.quality + 1;

      if (item.sellIn < 11) {
        if (item.quality < 50) {
          item.quality = item.quality + 1;
        }
      }

      if (item.sellIn < 6) {
        if (item.quality < 50) {
          item.quality = item.quality + 1;
        }
      }
    }
    item.sellIn = item.sellIn - 1;

    if (item.sellIn < 0) {
      item.quality = 0;
    }
  }

  private static void updateSulfaras(Item item) {
  }
}
