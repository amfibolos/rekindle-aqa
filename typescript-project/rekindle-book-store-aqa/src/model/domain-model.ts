export interface ValueObject {

}

export interface TokenDto {
    access_token: string
    scope: string
    token_type: string
    expires_in: number
}

export interface Address extends ValueObject {
    street : string
    postalCode : string
    city : string
}

export interface Bookstore extends ValueObject {
    id? : string
    name : string
    isActive : boolean
}

export interface Credit extends ValueObject {
    id : string
    customerId : string
    totalPrice : number
    transactionType : TransactionType
}

export interface Customer extends ValueObject {
    id? : string
    username? : string
    firstName? : string
    lastName? : string
}

export interface Item extends ValueObject {
    productId : string
    quantity : number
    price : number
    subTotal : number
}

export interface Order extends ValueObject {
    customerId : string
    bookstoreId : string
    items : Array<Item>
    address : Address
}

export interface OrderTracker extends ValueObject {
    trackingId : string
    orderId : string
}

export interface Payment extends ValueObject {
    customerId : string
    price : number
    status : PaymentStatus
}

export interface Product extends ValueObject {
    id? : string
    name : string
    price : number
    available : boolean
    bookstoreId? : string
    bookstores? : Array<string>
}

export enum TransactionType {
    Debit, Credit
}

export enum PaymentStatus {
    Completed, Cancelled, Failed
}