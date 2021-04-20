package com.me.shoppingcart.product;

import com.me.shoppingcart.error.StockNotAvailable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ProductInventoryImpl implements Inventory{

    Set<InventoryItem> items = new HashSet<>();

    @Override
    public void add(InventoryItem item) {
        if(items.contains(item)){
            InventoryItem inventoryItem = items.stream().filter(e -> e.equals(item)).findAny().get();
            Long total=inventoryItem.getStock()+item.getStock();
            inventoryItem.setStock(total);
            items.add(inventoryItem);
        }else {
            items.add(item);
        }

    }

    @Override
    public void add(List<InventoryItem> items) {
            items.forEach(e->add(e));
    }

    @Override
    public void remove(InventoryItem item) {
        boolean contains = items.contains(item);
        if(contains){
            InventoryItem inventoryItem = items.stream().filter(e -> e.equals(item)).findAny().get();
           contains=inventoryItem.getStock()-item.getStock()>=0;
           if(contains){
              Long total= inventoryItem.getStock()-item.getStock();
              inventoryItem.setStock(total);
              items.add(inventoryItem);
           }
        }
        if(!contains){
            throw new StockNotAvailable();
        }
    }

    @Override
    public void remove(List<InventoryItem> items) {
        items.forEach(e->add(e));
    }

    @Override
    public List<InventoryItem> getAll() {
        return items.stream().filter(e->e.getStock()>0).collect(Collectors.toList());
    }

    @Override
    public boolean isAvailableInStock(InventoryItem item) {
        return items.stream().filter(e->e.equals(item) && e.getStock()>0).findAny().isPresent();
    }
}
