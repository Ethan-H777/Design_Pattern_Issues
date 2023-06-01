package au.edu.sydney.brawndo.erp.spfea.products;

import au.edu.sydney.brawndo.erp.ordering.Product;

import java.util.Arrays;

public class ProductImpl implements Product {

    private final String name;
    private final double cost;

    //dataFlyweight contains five types of data
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

        // pass in these data into flyweight factory to get flyweight instance
        dataFlyweight = ProductFlyweightFactory.getInstance().getProductData(manufacturingData,recipeData,marketingData,safetyData,licensingData);

    }

    //Value object: override equals and hashCode
    @Override
    public boolean equals(Object object) {
        if (object == this) return true;
        if (!(object instanceof Product)) return false;
        Product obj = (Product) object;

        return obj.getProductName().equals(this.name) && obj.getCost() == this.cost && Arrays.equals(obj.getManufacturingData(), getManufacturingData()) &&
                Arrays.equals(obj.getRecipeData(), getRecipeData()) &&
                Arrays.equals(obj.getMarketingData(), getMarketingData()) &&
                Arrays.equals(obj.getSafetyData(), getSafetyData()) &&
                Arrays.equals(obj.getLicensingData(), getLicensingData());
    }

    @Override
    public int hashCode() {
        int result = (name != null ? name.hashCode() : 0);

        //convert the cost into bits and hash the bits, then cast the hash into integer
        long doubleBits = Double.doubleToLongBits(cost);
        int doubleHash = (int) (doubleBits ^ (doubleBits >>> 32));
        result = 31 * result + doubleHash;
        result = 31 * result + (dataFlyweight != null ? dataFlyweight.hashCode() : 0);
        return result;
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
