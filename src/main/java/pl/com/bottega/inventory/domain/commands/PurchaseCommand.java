package pl.com.bottega.inventory.domain.commands;

import java.util.HashMap;
import java.util.Map;

public class PurchaseCommand implements Validatable {

    private Map<String, Integer> skus = new HashMap<>();

    public Map<String, Integer> getSkus() {
        return skus;
    }

    public void setSkus(Map<String, Integer> skus) {
        this.skus = skus;
    }

    @Override
    public void validate(ValidationErrors errors) {
        if (skus.size() < 1) {
            errors.add("skus", "are required");
        }

        if (skus.size() > 0) {
            skus.forEach((sku, amount) -> validateAmount(sku, amount, errors));
        }
    }

    private void validateAmount(String sku, Integer amount, ValidationErrors errors) {
        if (amount == null) {
            errors.add(sku, "can't be blank");
        }
        if (amount != null && (amount < 1 || amount > 999)) {
            errors.add(sku, "must be between 1 and 999");
        }
    }
}
