using RekindleTaf.Configuration.Rest.Endpoints;

namespace RekindleTaf.Configuration;

public interface IEnvConfig
{
    string Get(string param);

    string BaseUrl();

    BookstoreEndpoints BookstoreEndpoints();

    CustomerEndpoints CustomerEndpoints();

    OrderEndpoints OrderEndpoints();

    PaymentEndpoints PaymentEndpoints();
}