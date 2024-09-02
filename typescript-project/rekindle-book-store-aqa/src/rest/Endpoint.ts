// @ts-nocheck
export namespace BookstoreEndpoints {
    export const bookstores = "/rekindle/bookstores";
    export const bookstoresById = ({bookstoreId}) => `/rekindle/bookstores/${bookstoreId}`;
    export const bookstoreProductByStoreId = ({bookstoreId}) => `/rekindle/bookstores/${bookstoreId}/product`;
    export const bookstoreProductByStoreIdAndProductId = ({
                                                              bookstoreId,
                                                              productId
                                                          }) => `/rekindle/bookstores/${bookstoreId}/product/${productId}`;
    export const bookstoresProductById = ({productId}) => `/rekindle/bookstores/product/${productId}`;
    export const bookstoresProducts = "/rekindle/bookstores/product";
}

export namespace CustomerEndpoints {
    export const customers = "/rekindle/customers";
    export const customerById = ({customerId}) => `/rekindle/customers/${customerId}`;
}

export namespace OrderEndpoints {
    export const orders = "/rekindle/orders";
    export const ordersByTrackingId = ({trackingId}) => `/rekindle/orders/${trackingId}`;
}

export namespace PaymentEndpoints {
    export const paymentCredit = "/rekindle/payments/credit";
    export const paymentByOrderId = ({orderId}) => `/rekindle/payments/${orderId}`;
    export const paymentCreditHistoryByCustomerId = ({customerId}) => `/rekindle/payments/credit/history/${customerId}`;
}