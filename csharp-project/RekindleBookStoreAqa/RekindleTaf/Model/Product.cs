using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Product : IValueObject
{
    [JsonPropertyName("id")]
    public string? Id;
    
    [JsonPropertyName("name")]
    public string? Name;
    
    [JsonPropertyName("price")]
    public decimal? Price;
    
    [JsonPropertyName("available")]
    public bool? Available;
    
    [JsonPropertyName("bookstoreId")]
    public string? BookstoreId;
    
    [JsonPropertyName("bookstores")]
    public List<string>? Bookstores;
}