package au.edu.sydney.brawndo.erp.spfea.products;

public class ProductData implements ProductDataFlyweight{
    private double[] data;

    public ProductData(double[] data) {
        this.data = data;
    }

    @Override
    public double[] getData() {
        return data;
    }
}
