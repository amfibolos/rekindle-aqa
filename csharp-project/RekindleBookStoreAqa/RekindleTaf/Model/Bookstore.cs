using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Bookstore : IValueObject
{
    [JsonPropertyName("id")]
    public string? Id;
    
    [JsonPropertyName("name")]
    public string? Name;
    
    [JsonPropertyName("isActive")]
    public bool? IsActive;
}