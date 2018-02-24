package pl.com.bottega.inventory.ui;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.com.bottega.inventory.api.CommandGateway;
import pl.com.bottega.inventory.api.PurchaseDto;
import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;
import pl.com.bottega.inventory.domain.commands.PurchaseCommand;

import java.util.Map;

@RestController
public class WarehouseController {

    private CommandGateway commandGateway;

    public WarehouseController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping("/inventory")
    public void inventory(@RequestBody AddInventoryCommand cmd) {
        commandGateway.execute(cmd);
    }

    @PostMapping("/purchase")
    public PurchaseDto purchase(@RequestBody Map<String, Integer> products) {
        PurchaseCommand purchaseCommand = new PurchaseCommand();
        purchaseCommand.setSkus(products);
        return commandGateway.execute(purchaseCommand);
    }
}
