using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Payment  : IValueObject
{
    [JsonPropertyName("customerId")]
    public string? CustomerId;
    
    [JsonPropertyName("price")]
    public decimal? Price;
    
    [JsonPropertyName("status")]
    public PaymentStatus? Status;
}