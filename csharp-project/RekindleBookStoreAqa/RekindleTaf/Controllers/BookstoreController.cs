using System.Collections.Generic;
using System.Net;
using System.Text.Json.Nodes;
using System.Threading.Tasks;
using FluentAssertions;
using RekindleTaf.Configuration;
using RekindleTaf.Configuration.Rest;
using RekindleTaf.Model;
using RestSharp;

namespace RekindleTaf.Controllers;

public class BookstoreController(IEnvConfig envConfig, IRestConfig restConfig) : IBookstoreController
{
    public async Task<RestResponse> Get(string param = "")
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoresById)
            .AddUrlSegment("bookstoreId", param);
        return await restConfig.RekindleClient().ExecuteGetAsync(request);
    }

    public Bookstore GetSuccessfully(string param = "")
    {
        var response = Get(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.OK);
        return restConfig.Deserialize<Bookstore>(response);
    }

    public async Task<RestResponse> GetAll(string param = "")
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().Bookstores);
        return await restConfig.RekindleClient().ExecuteGetAsync(request);
    }

    public List<Bookstore> GetAllSuccessfully(string param = "")
    {
        var response = GetAll(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.OK);
        return restConfig.Deserialize<List<Bookstore>>(response);
    }

    public async Task<RestResponse> Post(Bookstore param)
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().Bookstores, Method.Post)
            .AddJsonBody(param);
        return await restConfig.RekindleClient().ExecutePostAsync(request);
    }

    public Bookstore PostSuccessfully(Bookstore param)
    {
        var response = Post(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.Created);
        var responseData = restConfig.Deserialize<JsonNode>(response);
        param.Id = responseData.ToString();
        return param;
    }

    public async Task<RestResponse> Put(Bookstore param)
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoresById, Method.Put)
            .AddUrlSegment("bookstoreId", param.Id)
            .AddJsonBody(param);
        return await restConfig.RekindleClient().ExecutePutAsync(request);
    }

    public void PutSuccessfully(Bookstore param)
    {
        var response = Put(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NoContent);
    }

    public async Task<RestResponse> Delete(Bookstore param)
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoresById, Method.Delete)
            .AddUrlSegment("bookstoreId", param.Id);
        return await restConfig.RekindleClient().ExecuteDeleteAsync(request);
    }

    public void DeleteSuccessfully(Bookstore param)
    {
        var response = Delete(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NoContent);
    }
}