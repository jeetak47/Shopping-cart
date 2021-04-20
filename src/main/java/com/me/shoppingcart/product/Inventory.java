package com.me.shoppingcart.product;

import java.util.List;

public interface Inventory {
   void add(InventoryItem item);
   void add(List<InventoryItem> items);
   void remove(InventoryItem item);
   void remove(List<InventoryItem> items);
   List<InventoryItem> getAll();
   boolean isAvailableInStock(InventoryItem item);
}
