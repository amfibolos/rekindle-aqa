using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Credit : IValueObject
{
    [JsonPropertyName("id")]
    public string? Id;
    
    [JsonPropertyName("customerId")]
    public string? CustomerId;
    
    [JsonPropertyName("totalPrice")]
    public decimal? TotalPrice;
    
    [JsonPropertyName("transactionType")]
    public TransactionType? TransactionType;
}