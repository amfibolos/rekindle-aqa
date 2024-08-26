namespace RekindleTaf.Model.Factory;

public abstract class BaseKey(string displayName) : IValueKey
{
    public override string ToString()
    {
        return displayName;
    }

    public IValueKey GetKey()
    {
        return this;
    }
}