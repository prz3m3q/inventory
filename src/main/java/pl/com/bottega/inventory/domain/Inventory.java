package pl.com.bottega.inventory.domain;

import pl.com.bottega.inventory.domain.commands.AddInventoryCommand;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {

    @Id
    private String skuCode;

    private Integer amount;

    public Inventory() {
    }

    public Inventory(AddInventoryCommand command) {
        skuCode = command.getSkuCode();
        amount = command.getAmount();
    }

    public String getSkuCode() {
        return skuCode;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public void updateAmount(Integer amount) {
        this.amount += amount;
    }

    public void decreaseAmount(Integer amount) {
        this.amount = this.amount - amount;
    }

    public boolean canPurchase(Integer amount) {
        return this.amount >= amount;
    }
}
