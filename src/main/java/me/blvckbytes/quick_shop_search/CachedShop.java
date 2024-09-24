package me.blvckbytes.quick_shop_search;

import com.ghostchu.quickshop.api.shop.Shop;
import me.blvckbytes.bukkitevaluable.IItemBuildable;
import me.blvckbytes.bukkitevaluable.ItemBuilder;
import me.blvckbytes.bukkitevaluable.section.ItemStackSection;
import me.blvckbytes.gpeee.interpreter.EvaluationEnvironmentBuilder;
import org.bukkit.inventory.ItemStack;

public class CachedShop {

  private final Shop shop;
  private final ItemStackSection representativePatch;
  private final EvaluationEnvironmentBuilder shopEnvironment;

  private IItemBuildable representativeBuildable;
  private int cachedStock;

  public CachedShop(Shop shop, ItemStackSection representativePatch) {
    this.shop = shop;
    this.representativePatch = representativePatch;
    this.representativeBuildable = makeBuildable(shop.getItem());
    this.cachedStock = shop.getRemainingStock();

    var shopLocation = shop.getLocation();
    var shopWorld = shopLocation.getWorld();

    this.shopEnvironment = new EvaluationEnvironmentBuilder()
      .withLiveVariable("owner", shop.getOwner()::getDisplay)
      .withLiveVariable("price", shop::getPrice)
      .withLiveVariable("currency", shop::getCurrency)
      .withLiveVariable("remaining_stock", () -> this.cachedStock)
      .withLiveVariable("is_buying", shop::isBuying)
      .withLiveVariable("is_selling", shop::isSelling)
      .withLiveVariable("is_unlimited", shop::isUnlimited)
      .withLiveVariable("loc_world", () -> shopWorld == null ? null : shopWorld.getName())
      .withLiveVariable("loc_x", shopLocation::getBlockX)
      .withLiveVariable("loc_y", shopLocation::getBlockY)
      .withLiveVariable("loc_z", shopLocation::getBlockZ);
  }

  public Shop getShop() {
    return shop;
  }

  public int getCachedStock() {
    return cachedStock;
  }

  public IItemBuildable getRepresentativeBuildable() {
    return representativeBuildable;
  }

  public EvaluationEnvironmentBuilder getShopEnvironment() {
    return shopEnvironment;
  }

  public void onStockChange(int newStock) {
    this.cachedStock = newStock;
  }

  public void onItemChange(ItemStack newItem) {
    this.representativeBuildable = makeBuildable(newItem);
  }

  private IItemBuildable makeBuildable(ItemStack item) {
    return new ItemBuilder(item, item.getAmount())
      .patch(representativePatch);
  }
}