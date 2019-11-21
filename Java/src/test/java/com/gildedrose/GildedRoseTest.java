package com.gildedrose;

import net.jqwik.api.ForAll;
import net.jqwik.api.Label;
import net.jqwik.api.Property;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Positive;

import static java.lang.Integer.MIN_VALUE;
import static java.lang.Math.min;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GildedRoseTest {

  @Property
  @Label("Quality of default item decreases")
  void qualityOfDefaultItemDecreases(
    @ForAll @Positive int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("foo", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(Math.max(quality - 1, 0), item.quality);
    assertTrue(item.quality <= 50, () -> "item quality must be below 50: " + item.quality);
  }

  @Property
  @Label("Once the sell by date has passed, Quality degrades twice as fast")
  void qualityOfDefaultItemDecreasesTwiceAfterSellDateHasPassed(
    // FIXME MIN_VALUE is not handled correctly in GildedRose
    @ForAll @IntRange(min = MIN_VALUE + 1, max = 0) int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("foo", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(Math.max(quality - 2, 0), item.quality);
    assertTrue(item.quality <= 50, () -> "item quality must be below 50: " + item.quality);
  }

  @Property
  @Label("'Aged Brie' actually increases in Quality the older it gets")
  void agedBrie(
    @ForAll @Positive int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("Aged Brie", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(min(quality + 1, 50), item.quality);
  }

  @Property
  @Label("'Aged Brie' quality increase twice as fast after sell date")
  void agedBrieAfterSellDate(
    @ForAll @IntRange(min = MIN_VALUE + 1, max = 0) int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("Aged Brie", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(min(quality + 2, 50), item.quality);
  }

  @Property
  @Label("'Sulfuras' is a legendary item and as such its Quality is 80 and it never alters")
  void sulfurasDoesNotAlter(@ForAll int sellIn) {
    final Item item = updateQuality("Sulfuras, Hand of Ragnaros", sellIn, 80);
    assertEquals(sellIn, item.sellIn);
    assertEquals(80, item.quality);
  }

  @Property
  @Label("Backstage passes increases in Quality as its SellIn value approaches")
  void backstagePasses(
    @ForAll @IntRange(min = 11) int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(min(quality + 1, 50), item.quality);
  }

  @Property
  @Label("Backstage passes quality increases by 2 when there are 10 days or less")
  void backstagePassesWhen10days(
    @ForAll @IntRange(min = 6, max = 10) int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(min(quality + 2, 50), item.quality);
  }

  @Property
  @Label("Backstage passes quality increases by 3 when there are 5 days or less")
  void backstagePassesWhen5days(
    @ForAll @IntRange(min = 1, max = 5) int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(min(quality + 3, 50), item.quality);
  }

  @Property
  @Label("Backstage passes quality drops to 0 after the concert")
  void backstagePassesAfterConcert(
    @ForAll @IntRange(min = MIN_VALUE + 1, max = 0) int sellIn,
    // FIXME GildedRose does no input validation
    @ForAll @IntRange(max = 50) int quality) {
    final Item item = updateQuality("Backstage passes to a TAFKAL80ETC concert", sellIn, quality);
    assertEquals(sellIn - 1, item.sellIn);
    assertEquals(0, item.quality);
  }

  private Item updateQuality(String name, int sellIn, int quality) {
    final Item item = new Item(name, sellIn, quality);
    GildedRose app = new GildedRose(new Item[]{item});
    app.updateQuality();
    return item;
  }
}
