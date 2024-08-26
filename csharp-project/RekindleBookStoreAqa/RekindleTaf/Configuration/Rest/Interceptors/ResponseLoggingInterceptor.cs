using System;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NLog;
using RestSharp.Interceptors;

namespace RekindleTaf.Configuration.Rest.Interceptors;

public class ResponseLoggingInterceptor : Interceptor
{
    private static readonly Logger Log = LogManager.GetCurrentClassLogger();

    public override ValueTask AfterHttpRequest(HttpResponseMessage responseMessage, CancellationToken cancellationToken)
    {
        Log.Info(
            $"RESPONSE TO: {responseMessage.RequestMessage!.Method} RequestUri {responseMessage.RequestMessage!.RequestUri}");
        Log.Info(responseMessage);
        var responseBody = responseMessage.Content.ReadAsStringAsync(cancellationToken);
        try
        {
            Log.Info($"RESPONSE BODY:\n{JToken.Parse(responseBody.Result).ToString(Formatting.Indented)}");
        }
        catch (Exception)
        {
            Log.Info($"RESPONSE BODY:\n{responseBody.Result}");
        }

        Log.Info("###################################");
        return ValueTask.CompletedTask;
    }
}