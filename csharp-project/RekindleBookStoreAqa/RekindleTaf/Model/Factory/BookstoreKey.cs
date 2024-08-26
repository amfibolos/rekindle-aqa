namespace RekindleTaf.Model.Factory;

public sealed class BookstoreKey(string displayName) : BaseKey(displayName)
{
    public static readonly BookstoreKey StdBookstoreActive = new("STD_BOOKSTORE_ACTIVE");
    public static readonly BookstoreKey StdBookstoreInactive = new("STD_BOOKSTORE_INACTIVE");
}