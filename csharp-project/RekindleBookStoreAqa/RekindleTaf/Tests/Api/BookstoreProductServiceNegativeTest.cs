using System;
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
public class BookstoreProductServiceNegativeTest(
    IFactoryKit factoryKit,
    IBookstoreController bookstoreController,
    IBookstoreProductController productController) : Base
{
    private Bookstore bookstore;

    [OneTimeSetUp]
    public void Setup()
    {
        bookstore = factoryKit.Factory<Bookstore>(FactoryKey.Bookstore).Create(BookstoreKey.StdBookstoreActive);
        bookstore = bookstoreController.PostSuccessfully(bookstore);
    }

    [OneTimeTearDown]
    public void Cleanup()
    {
        bookstoreController.DeleteSuccessfully(bookstore);
    }

    [Test]
    public void Verify_CannotCreateProduct_WithEmptyValues()
    {
        var product = new Product
            {Available = null, Name = "", Price = null, Bookstores = null, BookstoreId = bookstore.Id};
        var response = productController.Post(product).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("name").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("available").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("price").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
    }

    [Test]
    public void Verify_CannotCreateProduct_WithNullValues()
    {
        var product = new Product
            {Available = null, Name = null, Price = null, Bookstores = null, BookstoreId = bookstore.Id};
        var response = productController.Post(product).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("name").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("available").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("price").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
    }

    [Test]
    public void Verify_CannotGet_NonExistingProduct()
    {
        var product = factoryKit.Factory<Product>(FactoryKey.Product).Create(ProductKey.StdProductAvailable);
        product.Id = Guid.NewGuid().ToString();
        var response = productController.Get(product.Id).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.ProductNotFound);
    }

    [Test]
    public void Verify_CannotUpdate_NonExistingProduct()
    {
        var product = factoryKit.Factory<Product>(FactoryKey.Product).Create(ProductKey.StdProductAvailable);
        product.BookstoreId = bookstore.Id;
        product.Id = Guid.NewGuid().ToString();
        var response = productController.Put(product).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.ProductNotFound);
    }
    
    [Test]
    public void Verify_CannotDelete_NonExistingProduct()
    {
        var product = factoryKit.Factory<Product>(FactoryKey.Product).Create(ProductKey.StdProductAvailable);
        product.BookstoreId = bookstore.Id;
        product.Id = Guid.NewGuid().ToString();
        var response = productController.Delete(product).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.ProductNotFound);
    }
}