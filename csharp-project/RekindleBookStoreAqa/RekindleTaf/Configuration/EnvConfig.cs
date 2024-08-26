using System;
using NUnit.Framework;
using RekindleTaf.Configuration.Rest.Endpoints;

namespace RekindleTaf.Configuration;

public class EnvConfig : IEnvConfig
{
    private static readonly BookstoreEndpoints BookstoreEndpointsInstance = new();
    private static readonly CustomerEndpoints CustomerEndpointsInstance = new();
    private static readonly OrderEndpoints OrderEndpointsInstance = new();
    private static readonly PaymentEndpoints PaymentEndpointsInstance = new();

    public string Get(string param)
    {
        return TestContext.Parameters.Get(param) ??
               throw new ArgumentNullException($"Argument named {param} not found in runsettings");
    }

    public string BaseUrl()
    {
        return Get("BaseUrl");
    }

    public BookstoreEndpoints BookstoreEndpoints()
    {
        return BookstoreEndpointsInstance;
    }

    public CustomerEndpoints CustomerEndpoints()
    {
        return CustomerEndpointsInstance;
    }

    public OrderEndpoints OrderEndpoints()
    {
        return OrderEndpointsInstance;
    }

    public PaymentEndpoints PaymentEndpoints()
    {
        return PaymentEndpointsInstance;
    } 
}