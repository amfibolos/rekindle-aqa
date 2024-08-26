using RestSharp;

namespace RekindleTaf.Configuration.Rest;

public interface IRestConfig
{
    RestClient RekindleClient();

    T Deserialize<T>(RestResponse restResponse);
}