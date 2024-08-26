namespace RekindleTaf.Model.Factory;

public sealed class CustomerKey(string displayName) : BaseKey(displayName)
{
    public static readonly CustomerKey StdUser = new("STD_USER");
}