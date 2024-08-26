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

public class CustomerController(IEnvConfig envConfig, IRestConfig restConfig) : ICustomerController
{
    public async Task<RestResponse> Get(string param = "")
    {
        var request = new RestRequest(envConfig.CustomerEndpoints().CustomerById)
            .AddUrlSegment("customerId", param);
        return await restConfig.RekindleClient().ExecuteGetAsync(request);
    }

    public Customer GetSuccessfully(string param = "")
    {
        var response = Get(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.OK);
        return restConfig.Deserialize<Customer>(response);
    }

    public async Task<RestResponse> GetAll(string param = "")
    {
        var request = new RestRequest(envConfig.CustomerEndpoints().Customers);
        return await restConfig.RekindleClient().ExecuteGetAsync(request);
    }

    public List<Customer> GetAllSuccessfully(string param = "")
    {
        var response = GetAll(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.OK);
        return restConfig.Deserialize<List<Customer>>(response);
    }

    public async Task<RestResponse> Post(Customer param)
    {
        var request = new RestRequest(envConfig.CustomerEndpoints().Customers, Method.Post)
            .AddJsonBody(param);
        return await restConfig.RekindleClient().ExecutePostAsync(request);
    }

    public Customer PostSuccessfully(Customer param)
    {
        var response = Post(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.Created);
        var responseData = restConfig.Deserialize<JsonNode>(response);
        param.Id = responseData["customerId"]!.ToString();
        return param;
    }

    public async Task<RestResponse> Put(Customer param)
    {
        var request = new RestRequest(envConfig.CustomerEndpoints().CustomerById, Method.Put)
            .AddUrlSegment("customerId", param.Id)
            .AddJsonBody(param);
        return await restConfig.RekindleClient().ExecutePutAsync(request);
    }

    public void PutSuccessfully(Customer param)
    {
        var response = Put(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.NoContent);
    }

    public async Task<RestResponse> Delete(Customer param)
    {
        var request = new RestRequest(envConfig.CustomerEndpoints().CustomerById, Method.Delete)
            .AddUrlSegment("customerId", param.Id);
        return await restConfig.RekindleClient().ExecuteDeleteAsync(request);
    }

    public void DeleteSuccessfully(Customer param)
    {
        var response = Delete(param).Result;
        response.StatusCode.Should().Be(HttpStatusCode.OK);
    }
}