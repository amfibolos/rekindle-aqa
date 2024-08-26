namespace RekindleTaf.Model.Factory;

public interface IFactoryKit
{ 
    IFactory<T> Factory<T>(IValueKey key) where T : IValueObject;
}