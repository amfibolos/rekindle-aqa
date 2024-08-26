using System.Text.Json.Serialization;

namespace RekindleTaf.Model;
[JsonConverter(typeof(JsonStringEnumConverter<PaymentStatus>))]
public enum PaymentStatus
{
    Completed, Cancelled, Failed
}