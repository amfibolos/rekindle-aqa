namespace RekindleTaf.Configuration.Rest.Endpoints;

public class PaymentEndpoints
{
    public readonly string PaymentCredit = "/rekindle/payments/credit";
    public readonly string PaymentByOrderId = "/rekindle/payments/{orderId}";
    public readonly string PaymentCreditHistoryByCustomerId = "/rekindle/payments/credit/history/{customerId}";
}