package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.commands.InvalidCommandException;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Map;

@Component
public class PurchaseHandler implements Handler<PurchaseCommand, PurchaseDto> {

    private InventoryRepository invetoryRepository;

    public PurchaseHandler(InventoryRepository invetoryRepository) {
        this.invetoryRepository = invetoryRepository;
    }

    @Transactional
    public PurchaseDto handle(PurchaseCommand command) {
        Map<String, Inventory> inventoryList = invetoryRepository.getAll(command.getSkus().keySet());
        validateInvetoryList(inventoryList, command);

        PurchaseDto purchaseDto = new PurchaseDto();
        command.getSkus().forEach((sku, amount) -> prosessPurchasePosition(purchaseDto, sku, amount, inventoryList));

        if (purchaseDto.getMissingProducts().size() > 0) {
            purchaseDto.getPurchasedProducts().clear();
            return purchaseDto;
        }

        purchaseDto.getPurchasedProducts().forEach((sku, amount) -> {
            Inventory inventory = inventoryList.get(sku);
            updateInvetoryAmount(inventory, amount);
        });

        purchaseDto.setSuccess(true);
        return purchaseDto;
    }

    private void validateInvetoryList(Map<String, Inventory> inventoryList, PurchaseCommand command) {
        Validatable.ValidationErrors validationErrors = new Validatable.ValidationErrors();
        command.getSkus().forEach((sku, amount) -> {
            if (!inventoryList.containsKey(sku)) {
                validationErrors.add(sku, "no such sku");
            }
        });
        if (!validationErrors.isValid()) {
            throw new InvalidCommandException(validationErrors);
        }
    }

    public void updateInvetoryAmount(Inventory inventory, Integer amount) {
        inventory.decreaseAmount(amount);
        invetoryRepository.save(inventory);
    }

    private void prosessPurchasePosition(PurchaseDto purchaseDto, String sku, Integer amount, Map<String, Inventory> inventoryList) {
        Inventory inventory = inventoryList.get(sku);
        if (inventory.canPurchase(amount)) {
            purchaseDto.getPurchasedProducts().put(sku, amount);
        } else {
            purchaseDto.getMissingProducts().put(sku, amount);
        }
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return PurchaseCommand.class;
    }
}
