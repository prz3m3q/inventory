package pl.com.bottega.inventory.infrastructure;

import org.springframework.stereotype.Component;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.*;

@Component
public class JPAInventoryRepository implements InventoryRepository {

    private EntityManager entityManager;

    public JPAInventoryRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Inventory> get(String skuCode) {
        try {
            Inventory inventory = (Inventory) entityManager.createQuery("SELECT i FROM Inventory i WHERE i.skuCode = :skuCode")
                .setParameter("skuCode", skuCode)
                .getSingleResult();
            return Optional.of(inventory);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(Inventory inventory) {
        entityManager.persist(inventory);
    }

    @Override
    public Map<String, Inventory> getAll(Set<String> skus) {
        List<Inventory> products = entityManager.createQuery("SELECT i FROM Inventory i WHERE i.skuCode IN (:sku_codes)")
            .setParameter("sku_codes", skus)
            .getResultList();

        Map<String, Inventory> inventoryMap = new HashMap<>();
        products.stream().forEach(inventory -> inventoryMap.put(inventory.getSkuCode(), inventory));
        return inventoryMap;
    }
}
