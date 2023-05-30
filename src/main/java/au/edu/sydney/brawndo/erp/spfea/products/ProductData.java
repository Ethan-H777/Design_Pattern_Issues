package au.edu.sydney.brawndo.erp.spfea.products;

public class ProductData implements ProductDataFlyweight{
    private double[] manufacturingData;
    private double[] recipeData;
    private double[] marketingData;
    private double[] safetyData;
    private double[] licensingData;

    public ProductData(double[] manufacturingData,
                       double[] recipeData,
                       double[] marketingData,
                       double[] safetyData,
                       double[] licensingData) {
        this.manufacturingData = manufacturingData;
        this.recipeData = recipeData;
        this.marketingData = marketingData;
        this.safetyData = safetyData;
        this.licensingData = licensingData;
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
}
