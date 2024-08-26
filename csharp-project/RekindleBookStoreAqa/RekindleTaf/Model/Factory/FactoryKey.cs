namespace RekindleTaf.Model.Factory;

public class FactoryKey(string displayName) : BaseKey(displayName)
{
    public static readonly FactoryKey Customer = new("Customer");
    public static readonly FactoryKey Bookstore = new("Bookstore");
    public static readonly FactoryKey Product = new("Product");
}