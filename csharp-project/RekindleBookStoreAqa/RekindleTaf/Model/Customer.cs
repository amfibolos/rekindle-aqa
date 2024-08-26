using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Customer : IValueObject
{
    [JsonPropertyName("id")]
    public string? Id;
    
    [JsonPropertyName("username")]
    public string? Username;
    
    [JsonPropertyName("firstName")]
    public string? FirstName;
    
    [JsonPropertyName("lastName")]
    public string? LastName;
}