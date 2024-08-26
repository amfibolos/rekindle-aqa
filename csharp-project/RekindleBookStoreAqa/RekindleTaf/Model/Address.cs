using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Address : IValueObject
{
    [JsonPropertyName("street")]
    public string? Street;
    
    [JsonPropertyName("postalCode")]
    public string? PostalCode;
    
    [JsonPropertyName("city")]
    public string? City;
}