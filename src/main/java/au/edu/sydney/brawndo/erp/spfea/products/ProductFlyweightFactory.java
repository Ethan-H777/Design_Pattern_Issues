package au.edu.sydney.brawndo.erp.spfea.products;

import java.util.HashMap;
import java.util.Map;

public class ProductFlyweightFactory {
    private Map<double[], ProductData> flyweights;

    public ProductFlyweightFactory() {
        this.flyweights = new HashMap<>();
    }

    public ProductData getProductData(double[] data) {
        if (!flyweights.containsKey(data)) {
            //create new data flyweight
            flyweights.put(data, new ProductData(data));
        }

        return flyweights.get(data);
    }
}
