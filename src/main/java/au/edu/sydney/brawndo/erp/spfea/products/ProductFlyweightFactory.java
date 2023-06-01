package au.edu.sydney.brawndo.erp.spfea.products;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductFlyweightFactory {
    private Map<String, ProductData> flyweights = new HashMap<>();

    private static final ProductFlyweightFactory instance = new ProductFlyweightFactory();

    public static ProductFlyweightFactory getInstance() {
        return instance;
    }

    public ProductData getProductData(double[] manufacturingData,
                                      double[] recipeData,
                                      double[] marketingData,
                                      double[] safetyData,
                                      double[] licensingData) {
        String key = Arrays.toString(manufacturingData) + Arrays.toString(recipeData) + Arrays.toString(marketingData) + Arrays.toString(safetyData) + Arrays.toString(licensingData);

        if (!flyweights.containsKey(key)) {
            //create new data flyweight
            flyweights.put(key, new ProductData(manufacturingData, recipeData, marketingData, safetyData, licensingData));
        }

        return flyweights.get(key);
    }

}
