using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Item : IValueObject
{
    [JsonPropertyName("productId")]
    public string? ProductId;
    
    [JsonPropertyName("quantity")]
    public int? Quantity;
    
    [JsonPropertyName("price")]
    public decimal? Price;
    
    [JsonPropertyName("subTotal")]
    public decimal? SubTotal;
}