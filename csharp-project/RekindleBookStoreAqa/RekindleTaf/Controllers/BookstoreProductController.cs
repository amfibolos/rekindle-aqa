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

public class BookstoreProductController(IEnvConfig envConfig, IRestConfig restConfig) : IBookstoreProductController
{
    public async Task<RestResponse> Get(string param = "")
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoresProductById)
            .AddUrlSegment("productId", param);
        return await restConfig.RekindleClient().ExecuteGetAsync(request);
    }

    public Product GetSuccessfully(string param = "")
    {
        var response = Get(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.OK);
        return restConfig.Deserialize<Product>(response);
    }

    public async Task<RestResponse> GetAll(string param = "")
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoresProducts);
        return await restConfig.RekindleClient().ExecuteGetAsync(request);
    }

    public List<Product> GetAllSuccessfully(string param = "")
    {
        var response = GetAll(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.OK);
        return restConfig.Deserialize<List<Product>>(response);
    }

    public async Task<RestResponse> Post(Product param)
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoreProductByStoreId, Method.Post)
            .AddUrlSegment("bookstoreId", param.BookstoreId)
            .AddJsonBody(param);
        return await restConfig.RekindleClient().ExecutePostAsync(request);
    }

    public Product PostSuccessfully(Product param)
    {
        var response = Post(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.Created);
        var responseData = restConfig.Deserialize<JsonNode>(response);
        param.Id = responseData.ToString();
        return param;
    }

    public async Task<RestResponse> Put(Product param)
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoresProductById, Method.Put)
            .AddUrlSegment("productId", param.Id)
            .AddJsonBody(param);
        return await restConfig.RekindleClient().ExecutePutAsync(request);
    }

    public void PutSuccessfully(Product param)
    {
        var response = Put(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NoContent);
    }

    public async Task<RestResponse> Delete(Product param)
    {
        var request = new RestRequest(envConfig.BookstoreEndpoints().BookstoreProductByStoreIdAndProductId,
                Method.Delete)
            .AddUrlSegment("bookstoreId", param.BookstoreId)
            .AddUrlSegment("productId", param.Id);
        return await restConfig.RekindleClient().ExecuteDeleteAsync(request);
    }

    public void DeleteSuccessfully(Product param)
    {
        var response = Delete(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NoContent);
    }
}