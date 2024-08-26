using System;
using System.Text.Json;
using System.Threading.Tasks;
using RekindleTaf.Configuration.Rest.Interceptors;
using RekindleTaf.Model;
using RestSharp;
using RestSharp.Authenticators;
using RestSharp.Serializers.Json;

namespace RekindleTaf.Configuration.Rest.Authorization;

public class RekindleApiAuthorization(IEnvConfig envConfig) : AuthenticatorBase(string.Empty)
{
    private readonly string tokenUrl = envConfig.Get("BaseTokenUrl");
    private readonly string grantType = envConfig.Get("GrantType");
    private readonly string scope = envConfig.Get("Scope");
    private readonly string clientId = envConfig.Get("ClientId");
    private readonly string clientSecret = envConfig.Get("ClientSecret");

    protected override async ValueTask<Parameter> GetAuthenticationParameter(string accessToken)
    {
        this.Token = string.IsNullOrEmpty(this.Token) ? await this.GetToken() : this.Token;
        return new HeaderParameter(KnownHeaders.Authorization, this.Token);
    }

    private async Task<string> GetToken()
    {
        var options = new RestClientOptions(this.tokenUrl)
        {
            Interceptors =
            [
                new RequestLoggingInterceptor(), new ResponseLoggingInterceptor()
            ],
            Authenticator = new HttpBasicAuthenticator(clientId, clientSecret)
        };
        var client = new RestClient(options, configureSerialization: s => s.UseSystemTextJson(SerializerOptions()));
        var request = new RestRequest()
            .AddHeader("Content-Type", "application/x-www-form-urlencoded")
            .AddParameter("grant_type", grantType, ParameterType.GetOrPost)
            .AddParameter("scope", scope, ParameterType.GetOrPost);
        var response = await client.ExecutePostAsync<TokenResponse>(request);
        ArgumentNullException.ThrowIfNull(response.Data);
        return $"{response.Data.TokenType} {response.Data.AccessToken}";
    }

    private static JsonSerializerOptions SerializerOptions()
    {
        var settings = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.SnakeCaseLower,
            WriteIndented = true,
            IncludeFields = true
        };
        return settings;
    }
}