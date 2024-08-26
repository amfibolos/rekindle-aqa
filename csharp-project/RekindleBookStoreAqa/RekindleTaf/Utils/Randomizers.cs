using System;

namespace RekindleTaf.Utils;

public static class Randomizers
{
    private static readonly Random Random = new();

    public static decimal RandomPriceLessThanThousand()
    {
        return (decimal) Math.Round(((Random.NextDouble() * Math.Abs(1000 - 1)) + 1), 2);
    }
}