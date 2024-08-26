using System;
using System.Text.Json;
using System.Text.Json.Nodes;
using RestSharp;

namespace RekindleTaf.Utils;

public static class ExtensionMethods
{
    public static string GetNode(this RestResponse response, string nodeName)
    {
        var nodes = JsonSerializer.Deserialize<JsonNode>(response.Content);
        return nodes[nodeName].ToString() ?? throw new ArgumentException("Invalid node name or null node returned");
    }
}