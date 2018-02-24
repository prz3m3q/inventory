package pl.com.bottega.inventory.api;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.com.bottega.inventory.domain.Inventory;
import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;
import pl.com.bottega.inventory.domain.commands.Validatable;
import pl.com.bottega.inventory.domain.repositories.InventoryRepository;

import javax.persistence.NoResultException;

@Component
public class AddInventoryHandler implements Handler<AddInventoryCommand, Void> {

    private InventoryRepository invetoryRepository;

    public AddInventoryHandler(InventoryRepository invetoryRepository) {
        this.invetoryRepository = invetoryRepository;
    }

    @Transactional
    public Void handle(AddInventoryCommand command) {
        Inventory inventory = null;
        try {
            inventory = invetoryRepository.get(command.getSkuCode());
            inventory.updateAmount(command.getAmount());
        } catch (NoResultException e) {
            inventory = new Inventory(command);
        }
        invetoryRepository.save(inventory);
        return null;
    }

    @Override
    public Class<? extends Validatable> getSupportedCommandClass() {
        return AddInventoryCommand.class;
    }
}
