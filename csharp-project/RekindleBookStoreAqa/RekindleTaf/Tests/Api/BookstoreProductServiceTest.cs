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
public class BookstoreProductServiceTest(
    IFactoryKit factoryKit,
    IBookstoreController bookstoreController,
    IBookstoreProductController productController) : Base
{
    private readonly List<Product> Products = new();
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
        Products.ForEach(productController.DeleteSuccessfully);
        bookstoreController.DeleteSuccessfully(bookstore);
    }
    
    [Test]
    public void Create_NewProduct_ShouldReturnOk()
    {
        var product = factoryKit.Factory<Product>(FactoryKey.Product).Create(ProductKey.StdProductAvailable);
        product.BookstoreId = bookstore.Id;
        product = productController.PostSuccessfully(product);
        Products.Add(product);
        product.Id.Should().NotBeEmpty();
    }
    
    [Test]
    public void Get_NewProduct_ShouldReturnOk()
    {
        var product = factoryKit.Factory<Product>(FactoryKey.Product).Create(ProductKey.StdProductAvailable);
        product.BookstoreId = bookstore.Id;
        product = productController.PostSuccessfully(product);
        Products.Add(product);
        var fetchedProduct = productController.GetSuccessfully(product.Id!);
        fetchedProduct.Id.Should().Be(product.Id);
        fetchedProduct.Name.Should().Be(product.Name);
        fetchedProduct.Available.Should().Be(product.Available);
        fetchedProduct.Price.Should().Be(product.Price);
    }
    
    [Test]
    public void Get_AllProduct_ShouldReturnOk()
    {
        var products = productController.GetAllSuccessfully();
        products.Should().NotBeEmpty();
    }
    
    [Test]
    public void Update_NewProduct_ShouldReturnNoContent()
    {
        var product = factoryKit.Factory<Product>(FactoryKey.Product).Create(ProductKey.StdProductAvailable);
        product.BookstoreId = bookstore.Id;
        product = productController.PostSuccessfully(product);
        Products.Add(product);
        decimal newPrice = new decimal(999.99);
        product.Price = newPrice;
        productController.PutSuccessfully(product);
        var fetchedProduct = productController.GetSuccessfully(product.Id!);
        fetchedProduct.Price.Should().Be(product.Price);
    }
    
    [Test]
    public void Delete_NewProduct_ShouldReturnNoContent()
    {
        var product = factoryKit.Factory<Product>(FactoryKey.Product).Create(ProductKey.StdProductAvailable);
        product.BookstoreId = bookstore.Id;
        product = productController.PostSuccessfully(product);
        productController.DeleteSuccessfully(product);
        var response = productController.Get(product.Id!).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.ProductNotFound);
    }
}