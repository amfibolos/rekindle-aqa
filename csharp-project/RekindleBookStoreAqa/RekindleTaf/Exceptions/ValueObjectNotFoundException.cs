using System;

namespace RekindleTaf.Exceptions;

public class ValueObjectNotFoundException(string? message) : ArgumentException(message);