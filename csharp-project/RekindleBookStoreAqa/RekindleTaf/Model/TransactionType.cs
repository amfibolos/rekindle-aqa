using System.Text.Json.Serialization;

namespace RekindleTaf.Model;
[JsonConverter(typeof(JsonStringEnumConverter<TransactionType>))]
public enum TransactionType
{
    Debit,
    Credit
}