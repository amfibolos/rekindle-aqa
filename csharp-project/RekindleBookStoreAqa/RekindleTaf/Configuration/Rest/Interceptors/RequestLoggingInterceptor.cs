using System;
using System.Net.Http;
using System.Threading;
using System.Threading.Tasks;
using Newtonsoft.Json;
using Newtonsoft.Json.Linq;
using NLog;
using RestSharp.Interceptors;

namespace RekindleTaf.Configuration.Rest.Interceptors;

public class RequestLoggingInterceptor : Interceptor
{
    private static readonly Logger Log = LogManager.GetCurrentClassLogger();

    public override ValueTask BeforeHttpRequest(HttpRequestMessage requestMessage, CancellationToken cancellationToken)
    {
        Log.Info("###################################");
        Log.Info(requestMessage);
        if (requestMessage.Content != null)
        {
            var requestBody = requestMessage.Content.ReadAsStringAsync(cancellationToken);
            try
            {
                Log.Info($"REQUEST BODY:\n{JToken.Parse(requestBody.Result).ToString(Formatting.Indented)}");
            }
            catch (Exception)
            {
                Log.Info($"REQUEST BODY:\n{requestBody.Result}");
            }
        }

        return ValueTask.CompletedTask;
    }
}