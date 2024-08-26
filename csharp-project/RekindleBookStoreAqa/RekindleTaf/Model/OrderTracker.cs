using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class OrderTracker  : IValueObject
{
    [JsonPropertyName("trackingId")]
    public string? TrackingId;
    
    [JsonPropertyName("orderId")]
    public string? OrderId;
}