using System;
using System.Collections.Generic;
using System.Net;
using FluentAssertions;
using NUnit.Extension.DependencyInjection;
using RekindleTaf.Controllers;
using RekindleTaf.Exceptions;
using RekindleTaf.Model;
using RekindleTaf.Model.Factory;
using RekindleTaf.Utils;

namespace RekindleTaf.Tests.Api;
[DependencyInjectingTestFixture]
public class CustomerServiceNegativeTest(IFactoryKit factoryKit, ICustomerController customerController) : Base
{
    [Test]
    public void Verify_CannotCreateCustomer_WithEmptyValues()
    {
        var customer = new Customer{FirstName = "", LastName = "", Username = ""};
        var response = customerController.Post(customer).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("firstName").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("lastName").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("username").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
    }
    
    [Test]
    public void Verify_CannotCreateCustomer_WithNullValues()
    {
        var customer = new Customer();
        var response = customerController.Post(customer).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("firstName").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("lastName").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("username").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
    }
    
    [Test]
    public void Verify_CannotGet_NonExistingCustomer()
    {
        var response = customerController.Get(Guid.NewGuid().ToString()).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.CustomerNotFound);
    }
    
    [Test]
    public void Verify_CannotDelete_NonExistingCustomer()
    {
        var customer = new Customer {Id = Guid.NewGuid().ToString()};
        var response = customerController.Delete(customer).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.CustomerNotFound);
    }
    
    [Test]
    public void Verify_CannotUpdate_NonExistingCustomer()
    {
        var customer = factoryKit.Factory<Customer>(FactoryKey.Customer).Create(CustomerKey.StdUser);
        customer.Id = Guid.NewGuid().ToString();
        var response = customerController.Put(customer).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.CustomerNotFound);
    }
}