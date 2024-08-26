using System;
using System.Text.Json;
using RekindleTaf.Configuration.Rest.Authorization;
using RekindleTaf.Configuration.Rest.Interceptors;
using RestSharp;
using RestSharp.Serializers.Json;

namespace RekindleTaf.Configuration.Rest;

public class RestConfig : IRestConfig
{
    private readonly RestClient _restClient;

    public RestConfig(IEnvConfig envConfig)
    {
        var baseUrl = envConfig.Get("BaseUrl");
        var options = new RestClientOptions(baseUrl)
        {
            Interceptors =
            [
                new RequestLoggingInterceptor(), new ResponseLoggingInterceptor()
            ],
            Authenticator = new RekindleApiAuthorization(envConfig)
        };
        _restClient = new RestClient(options,
            configureSerialization: s => s.UseSystemTextJson(ConfigureSerializerSettings()));
    }

    private static JsonSerializerOptions ConfigureSerializerSettings()
    {
        var settings = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.SnakeCaseLower,
            WriteIndented = true,
            IncludeFields = true
        };
        return settings;
    }

    public RestClient RekindleClient()
    {
        return _restClient;
    }

    public T Deserialize<T>(RestResponse restResponse)
    {
        return RekindleClient().Serializers.DeserializeContent<T>(restResponse) ??
               throw new InvalidOperationException();
    }
    
}