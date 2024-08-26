using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class TokenResponse
{
    [JsonPropertyName("access_token")]
    public string? AccessToken;
    
    [JsonPropertyName("scope")]
    public string? Scope;
    
    [JsonPropertyName("token_type")]
    public string? TokenType;
    
    [JsonPropertyName("expires_in")]
    public int? ExpiresIn;
}