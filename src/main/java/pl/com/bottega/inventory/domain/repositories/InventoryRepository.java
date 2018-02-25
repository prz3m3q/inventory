package pl.com.bottega.inventory.domain.repositories;

import pl.com.bottega.inventory.domain.Inventory;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface InventoryRepository {
    Optional<Inventory> get(String skuCode);
    void save(Inventory inventory);
    Map<String, Inventory> getAll(Set<String> skus);
}
