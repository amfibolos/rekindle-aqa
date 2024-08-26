namespace RekindleTaf.Configuration.Rest.Endpoints;

public class BookstoreEndpoints
{
    public readonly string Bookstores = "/rekindle/bookstores";
    public readonly string BookstoresById = "/rekindle/bookstores/{bookstoreId}";
    public readonly string BookstoreProductByStoreId = "/rekindle/bookstores/{bookstoreId}/product";
    public readonly string BookstoreProductByStoreIdAndProductId = "/rekindle/bookstores/{bookstoreId}/product/{productId}";
    public readonly string BookstoresProductById = "/rekindle/bookstores/product/{productId}";
    public readonly string BookstoresProducts = "/rekindle/bookstores/product";
}