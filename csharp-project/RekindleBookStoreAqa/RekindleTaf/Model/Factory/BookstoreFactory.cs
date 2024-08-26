using System.Collections.Generic;
using Bogus;
using RekindleTaf.Exceptions;
using RekindleTaf.Utils;

namespace RekindleTaf.Model.Factory;

public class BookstoreFactory : IFactory<Bookstore>
{
    private static readonly Faker<Bookstore> BookstoreActiveFaker = new Faker<Bookstore>()
        .RuleFor(bookstore => bookstore.IsActive, () => true)
        .RuleFor(bookstore => bookstore.Name, (faker) => faker.Company.CompanyName());
    
    private static readonly Faker<Bookstore> BookstoreInActiveFaker = new Faker<Bookstore>()
        .RuleFor(bookstore => bookstore.IsActive, () => false)
        .RuleFor(bookstore => bookstore.Name, (faker) => faker.Company.CompanyName());
    
    private IDictionary<IValueKey, Supplier<Bookstore>>
        Map = new Dictionary<IValueKey, Supplier<Bookstore>>()
        {
            {BookstoreKey.StdBookstoreActive, () => BookstoreActiveFaker.Generate()},
            {BookstoreKey.StdBookstoreInactive, () => BookstoreInActiveFaker.Generate()}
        };

    public Bookstore Create(IValueKey key)
    {
        if (Map.TryGetValue(key, out Supplier<Bookstore>? value))
        {
            return value.Invoke();
        }
        throw new ValueObjectNotFoundException($"Bookstore identified by {key} has not yet been implemented");
    }
}