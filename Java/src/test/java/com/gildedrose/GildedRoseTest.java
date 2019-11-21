package com.gildedrose;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GildedRoseTest {

  @ParameterizedTest
  @CsvSource({
    "foo, 3, 2, 2, 1",
    "foo, 2, 0, 1, 0",
    "foo, -1, 0, -2, 0",
    "foo, -1, 1, -2, 0",
    "foo, -1, 3, -2, 1",
    "Aged Brie, 1, 0, 0, 1",
    "Aged Brie, 0, 0, -1, 2",
    "Aged Brie, -1, 2, -2, 4",
    "Aged Brie, -1, 50, -2, 50",
    "'Sulfuras, Hand of Ragnaros', 1, 80, 1, 80",
    "'Sulfuras, Hand of Ragnaros', 10, 80, 10, 80",
    "'Backstage passes to a TAFKAL80ETC concert', 11, 2, 10, 3",
    "'Backstage passes to a TAFKAL80ETC concert', 10, 3, 9, 5",
    "'Backstage passes to a TAFKAL80ETC concert', 5, 7, 4, 10",
    "'Backstage passes to a TAFKAL80ETC concert', 1, 50, 0, 50",
    "'Backstage passes to a TAFKAL80ETC concert', 0, 7, -1, 0",
  })
  void updateQuality(String name, int initialSellIn, int initialQuality, int updatedSellIn, int updatedQuality) {
    final Item item = new Item(name, initialSellIn, initialQuality);

    new GildedRose(new Item[]{item}).updateQuality();

    assertEquals(name, item.name, "name");
    assertEquals(updatedSellIn, item.sellIn, "sell in");
    assertEquals(updatedQuality, item.quality, "quality");
  }
}
