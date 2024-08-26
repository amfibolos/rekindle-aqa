using System;
using System.Net;
using FluentAssertions;
using NUnit.Extension.DependencyInjection;
using RekindleTaf.Controllers;
using RekindleTaf.Exceptions;
using RekindleTaf.Model;
using RekindleTaf.Utils;

namespace RekindleTaf.Tests.Api;
[DependencyInjectingTestFixture]
public class BookstoreServiceNegativeTest(IBookstoreController bookstoreController) : Base
{
    [Test]
    public void Verify_CannotCreateBookstore_WithEmptyValues()
    {
        var bookstore = new Bookstore{Name = "", IsActive = null};
        var response = bookstoreController.Post(bookstore).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("name").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("isActive").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
    }
    
    [Test]
    public void Verify_CannotCreateBookstore_WithNullValues()
    {
        var bookstore = new Bookstore();
        var response = bookstoreController.Post(bookstore).Result;
        response.StatusCode.Should().Be(HttpStatusCode.BadRequest);
        response.GetNode("name").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
        response.GetNode("isActive").Should().BeOneOf(ErrorMessage.NotBlank, ErrorMessage.NotNull);
    }
    
    [Test]
    public void Verify_CannotGet_NonExistingBookstore()
    {
        var response = bookstoreController.Get(Guid.NewGuid().ToString()).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.BookstoreNotFound);
    }
    
    [Test]
    public void Verify_CannotUpdate_NonExistingBookstore()
    {
        var bookstore = new Bookstore{Id = Guid.NewGuid().ToString(),Name = "Test", IsActive = true};
        var response = bookstoreController.Put(bookstore).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.BookstoreNotFound);
    }
    
    [Test]
    public void Verify_CannotDelete_NonExistingBookstore()
    {
        var bookstore = new Bookstore{Id = Guid.NewGuid().ToString()};
        var response = bookstoreController.Delete(bookstore).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NotFound);
        response.GetNode("errorMessage").Should().Be(ErrorMessage.BookstoreNotFound);
    }
}