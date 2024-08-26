namespace RekindleTaf.Model.Factory;

public sealed class ProductKey(string displayName) : BaseKey(displayName)
{
    public static readonly ProductKey StdProductAvailable = new("STD_PRODUCT_AVAILABLE");
    public static readonly ProductKey StdProductNonAvailable = new("STD_PRODUCT_NON_AVAILABLE");
}