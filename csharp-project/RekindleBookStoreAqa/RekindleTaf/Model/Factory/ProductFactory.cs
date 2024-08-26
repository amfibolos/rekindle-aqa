using System.Collections.Generic;
using Bogus;
using RekindleTaf.Exceptions;
using RekindleTaf.Utils;

namespace RekindleTaf.Model.Factory;

public class ProductFactory : IFactory<Product>
{
    private static readonly Faker<Product> ActiveProductFaker = new Faker<Product>()
        .RuleFor(product => product.Name, (faker) => faker.Commerce.Product())
        .RuleFor(product => product.Available, () => true)
        .RuleFor(product => product.Price, () => Randomizers.RandomPriceLessThanThousand());

    private static readonly Faker<Product> InActiveProductFaker = new Faker<Product>()
        .RuleFor(product => product.Name, (faker) => faker.Commerce.Product())
        .RuleFor(product => product.Available, () => false)
        .RuleFor(product => product.Price, () => Randomizers.RandomPriceLessThanThousand());

    private IDictionary<IValueKey, Supplier<Product>> Map = new Dictionary<IValueKey, Supplier<Product>>()
    {
        {ProductKey.StdProductAvailable, () => ActiveProductFaker.Generate()},
        {ProductKey.StdProductNonAvailable, () => InActiveProductFaker.Generate()}
    };

    public Product Create(IValueKey key)
    {
        if (Map.TryGetValue(key, out Supplier<Product>? value))
        {
            return value.Invoke();
        }

        throw new ValueObjectNotFoundException($"Product identified by {key} has not yet been implemented");
    }
}