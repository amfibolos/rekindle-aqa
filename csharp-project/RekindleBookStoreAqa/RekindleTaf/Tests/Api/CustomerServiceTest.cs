using System;
using System.Collections.Generic;
using System.Net;
using FluentAssertions;
using NUnit.Extension.DependencyInjection;
using NUnit.Framework;
using NUnit.Framework.Internal;
using RekindleTaf.Configuration;
using RekindleTaf.Configuration.Rest;
using RekindleTaf.Controllers;
using RekindleTaf.Exceptions;
using RekindleTaf.Model;
using RekindleTaf.Model.Factory;
using RekindleTaf.Utils;
using RestSharp;

namespace RekindleTaf.Tests.Api;

[DependencyInjectingTestFixture]
public class CustomerServiceTest(IFactoryKit factoryKit, ICustomerController customerController) : Base
{
    private readonly List<Customer> Customers = new();

    [OneTimeTearDown]
    public void Cleanup()
    {
        if (Customers.Count == 0) return;
        Customers.ForEach(customerController.DeleteSuccessfully);
    }
    
    [Test]
    public void Create_NewUser_Should_Return_OK()
    {
        var customer = factoryKit.Factory<Customer>(FactoryKey.Customer).Create(CustomerKey.StdUser);
        customer = customerController.PostSuccessfully(customer);
        Customers.Add(customer);
        customer.Id.Should().NotBeEmpty();
        customer.FirstName.Should().NotBeEmpty();
        customer.LastName.Should().NotBeEmpty();
        customer.Username.Should().NotBeEmpty();
    }
    
    [Test]
    public void Get_NewUser_Should_Return_OK()
    {
        var customer = factoryKit.Factory<Customer>(FactoryKey.Customer).Create(CustomerKey.StdUser);
        customer = customerController.PostSuccessfully(customer);
        Customers.Add(customer);
        var fetchedCustomer = customerController.GetSuccessfully(customer.Id!);
        fetchedCustomer.Should().BeEquivalentTo(customer);
    }
    
    [Test]
    public void Get_AllCustomers_Should_Return_OK()
    {
        var fetchedCustomers = customerController.GetAllSuccessfully();
        fetchedCustomers.Should().NotBeEmpty();
    }
    
    [Test]
    public void Update_NewUser_Should_Return_NoContent()
    {
        var customer = factoryKit.Factory<Customer>(FactoryKey.Customer).Create(CustomerKey.StdUser);
        customer = customerController.PostSuccessfully(customer);
        Customers.Add(customer);
        var updatedUsername = "Updated Username";
        customer.Username = updatedUsername;
        customerController.PutSuccessfully(customer);
        var fetchedCustomer = customerController.GetSuccessfully(customer.Id!);
        fetchedCustomer.Username.Should().Be(updatedUsername);
    }
    
    [Test]
    public void Delete_NewUser_Should_Return_OK()
    {
        var customer = factoryKit.Factory<Customer>(FactoryKey.Customer).Create(CustomerKey.StdUser);
        customer = customerController.PostSuccessfully(customer);
        customerController.DeleteSuccessfully(customer);
        var response = customerController.Get(customer.Id!).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.CustomerNotFound);
    }
}