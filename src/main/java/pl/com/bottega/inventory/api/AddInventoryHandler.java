package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import java.util.Optional;

@Component
public class AddInventoryHandler implements Handler<AddInventoryCommand, Void> {

    private InventoryRepository inventoryRepository;

    public AddInventoryHandler(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional
    public Void handle(AddInventoryCommand command) {
        Optional<Inventory> inventoryOptional = inventoryRepository.get(command.getSkuCode());
        Inventory inventory;
        if (inventoryOptional.isPresent()) {
            inventory = inventoryOptional.get();
            inventory.increaseAmount(command.getAmount());
            inventoryRepository.save(inventory);
        } else {
            inventoryRepository.save(new Inventory(command));
        }
        return null;
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return AddInventoryCommand.class;
    }
}
