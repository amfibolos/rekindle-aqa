using System.Collections.Generic;
using System.Text.Json.Serialization;

namespace RekindleTaf.Model;

public class Order : IValueObject
{
    [JsonPropertyName("customerId")] public string? CustomerId;

    [JsonPropertyName("bookstoreId")] public string? BookstoreId;

    [JsonPropertyName("items")] public List<Item>? Items;

    [JsonPropertyName("address")] public Address? Address;
}