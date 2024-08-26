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
public class BookstoreServiceTest(IFactoryKit factoryKit, IBookstoreController bookstoreController) : Base
{
    private readonly List<Bookstore> Bookstores = new();
    
    [OneTimeTearDown]
    public void Cleanup()
    {
        if (Bookstores.Count == 0) return;
        Bookstores.ForEach(bookstoreController.DeleteSuccessfully);
    }

    [Test]
    public void Create_NewBookstore_Should_Return_OK()
    {
        var bookstore = factoryKit.Factory<Bookstore>(FactoryKey.Bookstore).Create(BookstoreKey.StdBookstoreActive);
        bookstore = bookstoreController.PostSuccessfully(bookstore);
        Bookstores.Add(bookstore);
        bookstore.Id.Should().NotBeEmpty();
    }
    
    [Test]
    public void Get_NewBookstore_Should_Return_OK()
    {
        var bookstore = factoryKit.Factory<Bookstore>(FactoryKey.Bookstore).Create(BookstoreKey.StdBookstoreActive);
        bookstore = bookstoreController.PostSuccessfully(bookstore);
        Bookstores.Add(bookstore);
        var bookstoreFetched = bookstoreController.GetSuccessfully(bookstore.Id!);
        bookstoreFetched.Should().BeEquivalentTo(bookstore);
    }
    
    [Test]
    public void Get_AllBookstores_Should_Return_OK()
    {
        var bookstoresFetched = bookstoreController.GetAllSuccessfully();
        bookstoresFetched.Should().NotBeEmpty();
    }
    
    [Test]
    public void Update_Bookstore_Should_Return_NoContent()
    {
        var bookstore = factoryKit.Factory<Bookstore>(FactoryKey.Bookstore).Create(BookstoreKey.StdBookstoreActive);
        bookstore = bookstoreController.PostSuccessfully(bookstore);
        Bookstores.Add(bookstore);
        var updatedName = "UpdatedName";
        bookstore.Name = updatedName;
        bookstoreController.PutSuccessfully(bookstore);
        var bookstoreFetched = bookstoreController.GetSuccessfully(bookstore.Id!);
        bookstoreFetched.Should().BeEquivalentTo(bookstore);
    }
    
    [Test]
    public void Delete_Bookstore_Should_Return_NoContent()
    {
        var bookstore = factoryKit.Factory<Bookstore>(FactoryKey.Bookstore).Create(BookstoreKey.StdBookstoreActive);
        bookstore = bookstoreController.PostSuccessfully(bookstore);
        bookstoreController.DeleteSuccessfully(bookstore);
        var response = bookstoreController.Get(bookstore.Id!).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.BookstoreNotFound);
    }
}