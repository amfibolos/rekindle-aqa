namespace RekindleTaf.Model.Factory;

public interface IFactory<out T> where T : IValueObject
{
    T Create(IValueKey key);
}