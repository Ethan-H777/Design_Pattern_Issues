package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

public class ProductImpl implements Product {

    private final String name;
//    private final double[] manufacturingData;
    private final double cost;
//    private double[] recipeData;
//    private double[] marketingData;
//    private double[] safetyData;
//    private double[] licensingData;

    private ProductFlyweightFactory factory = new ProductFlyweightFactory();
    private ProductDataFlyweight dataFlyweight;

    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.name = name;
        this.cost = cost;
        dataFlyweight = factory.getProductData(manufacturingData,recipeData,marketingData,safetyData,licensingData);
    }

    @Override
    public String getProductName() {
        return name;
    }

    @Override
    public double getCost() {
        return cost;
    }

    @Override
    public double[] getManufacturingData() {
        return dataFlyweight.getManufacturingData();
    }

    @Override
    public double[] getRecipeData() {
        return dataFlyweight.getRecipeData();
    }

    @Override
    public double[] getMarketingData() {
        return dataFlyweight.getMarketingData();
    }

    @Override
    public double[] getSafetyData() {
        return dataFlyweight.getSafetyData();
    }

    @Override
    public double[] getLicensingData() {
        return dataFlyweight.getLicensingData();
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }
}
