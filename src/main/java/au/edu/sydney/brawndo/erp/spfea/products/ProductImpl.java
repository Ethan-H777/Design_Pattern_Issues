package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

public class ProductImpl implements Product {

    private final String name;
    private final double[] manufacturingData;
    private final double cost;
    private double[] recipeData;
    private double[] marketingData;
    private double[] safetyData;
    private double[] licensingData;

    private ProductFlyweightFactory factory = new ProductFlyweightFactory();

//    private final ProductDataFlyweight manufacturingData;
//    private ProductDataFlyweight recipeData;
//    private ProductDataFlyweight marketingData;
//    private ProductDataFlyweight safetyData;
//    private ProductDataFlyweight licensingData;

    public ProductImpl(String name,
                       double cost,
                       double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.name = name;
        this.cost = cost;
//        this.manufacturingData = manufacturingData;
//        this.recipeData = recipeData;
//        this.marketingData = marketingData;
//        this.safetyData = safetyData;
//        this.licensingData = licensingData;
        this.manufacturingData = factory.getProductData(manufacturingData).getData();
        this.recipeData = factory.getProductData(recipeData).getData();
        this.marketingData = factory.getProductData(marketingData).getData();
        this.safetyData = factory.getProductData(safetyData).getData();
        this.licensingData = factory.getProductData(licensingData).getData();
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
        return manufacturingData;
    }

    @Override
    public double[] getRecipeData() {
        return recipeData;
    }

    @Override
    public double[] getMarketingData() {
        return marketingData;
    }

    @Override
    public double[] getSafetyData() {
        return safetyData;
    }

    @Override
    public double[] getLicensingData() {
        return licensingData;
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }
}
