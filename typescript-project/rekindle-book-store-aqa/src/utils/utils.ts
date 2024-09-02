export namespace ErrorMessage {
    export const customerNotFound = 'Customer not found!';
    export const bookstoreNotFound = 'Bookstore not found';
    export const productNotFound = 'Product not found!';
    export const notBlank = 'must not be blank';
    export const notNull = 'must not be null';
}

export function randomPriceLessThanThousand(): number {
    const min = 1;
    const max = 1000;
    const randomValue = Math.random() * (max - min) + min;
    return parseFloat(randomValue.toFixed(2));
}