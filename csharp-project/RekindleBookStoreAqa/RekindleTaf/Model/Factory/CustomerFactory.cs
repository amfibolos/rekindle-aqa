using System.Collections.Generic;
using Bogus;
using RekindleTaf.Exceptions;
using RekindleTaf.Utils;

namespace RekindleTaf.Model.Factory;

public class CustomerFactory : IFactory<Customer>
{
    private static readonly Faker<Customer> CustomerFaker = new Faker<Customer>()
        .RuleFor(customer => customer.FirstName, faker => faker.Person.FirstName)
        .RuleFor(customer => customer.LastName, faker => faker.Person.LastName)
        .RuleFor(customer => customer.Username, faker => faker.Person.UserName);

    private IDictionary<IValueKey, Supplier<Customer>>
        Map = new Dictionary<IValueKey, Supplier<Customer>>()
        {
            {CustomerKey.StdUser, () => CustomerFaker.Generate()}
        };

    public Customer Create(IValueKey key)
    {
        if (Map.TryGetValue(key, out Supplier<Customer>? value))
        {
            return value.Invoke();
        }

        throw new ValueObjectNotFoundException($"Customer identified by {key} has not yet been implemented");
    }
}