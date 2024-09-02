import {ValueObject} from "@/model/domain-model";
import {BookstoreFactory} from "@/model/bookstore-factory";
import {CustomerFactory} from "@/model/customer-factory";
import {ProductFactory} from "@/model/product-factory";

export interface Factory<T extends ValueObject> {

    create(key: Symbol): T

}

export const FactoryKey = {
    bookstore: Symbol('Bookstore'),
    product: Symbol('Product'),
    customer: Symbol('Customer')
};


export interface FactoryKit {
    factory<T extends ValueObject>(key: Symbol): Factory<T>
}

export class ValueObjectFactory implements FactoryKit {
    // @ts-ignore
    private readonly map: Map<Symbol, Supplier<Factory<ValueObject>>> = new Map([
        [FactoryKey.bookstore, () => new BookstoreFactory()],
        [FactoryKey.customer, () => new CustomerFactory()],
        [FactoryKey.product, () => new ProductFactory()]
    ]);

    factory<T extends ValueObject>(key: Symbol): Factory<T> {
        const supplier = this.map.get(key);
        if (supplier) {
            return supplier() as Factory<T>;
        }
        throw new Error(`Factory identified by ${key.toString()} has not yet been implemented`);
    }

}