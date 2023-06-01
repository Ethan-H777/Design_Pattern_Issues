package au.edu.sydney.brawndo.erp.spfea.products;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ProductFlyweightFactory {
    //A collection of flyweight objects
    private Map<String, ProductData> flyweights = new HashMap<>();

    // single factory throughout the program which keeps the flyweights safe and consistent.
    private static final ProductFlyweightFactory instance = new ProductFlyweightFactory();

    public static ProductFlyweightFactory getInstance() {
        return instance;
    }

    public ProductData getProductData(double[] manufacturingData,
                                      double[] recipeData,
                                      double[] marketingData,
                                      double[] safetyData,
                                      double[] licensingData) {
        //generate a key for the hashMap
        String key = Arrays.toString(manufacturingData) + Arrays.toString(recipeData) + Arrays.toString(marketingData) + Arrays.toString(safetyData) + Arrays.toString(licensingData);

        if (!flyweights.containsKey(key)) {
            //create new data flyweight
            flyweights.put(key, new ProductData(manufacturingData, recipeData, marketingData, safetyData, licensingData));
        }

        return flyweights.get(key);
    }

}
