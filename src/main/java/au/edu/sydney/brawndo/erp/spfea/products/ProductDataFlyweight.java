package au.edu.sydney.brawndo.erp.spfea.products;

public interface ProductDataFlyweight {

    public double[] getManufacturingData();
    public double[] getRecipeData();
    public double[] getMarketingData();
    public double[] getSafetyData();
    public double[] getLicensingData();


}
