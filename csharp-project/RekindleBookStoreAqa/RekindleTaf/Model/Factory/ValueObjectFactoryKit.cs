using System.Collections.Generic;
using RekindleTaf.Exceptions;

namespace RekindleTaf.Model.Factory;

public class ValueObjectFactoryKit : IFactoryKit
{
    private IDictionary<IValueKey, IFactory<IValueObject>> Map = new Dictionary<IValueKey, IFactory<IValueObject>>()
    {
        {FactoryKey.Bookstore, new BookstoreFactory()},
        {FactoryKey.Product, new ProductFactory()},
        {FactoryKey.Customer, new CustomerFactory()},
    };

    public IFactory<T> Factory<T>(IValueKey key) where T : IValueObject
    {
        if (Map.TryGetValue(key, out IFactory<IValueObject>? value))
        {
            return (IFactory<T>) value;
        }

        throw new ValueObjectNotFoundException($"Factory identified by {key} has not yet been implemented");
    }
}