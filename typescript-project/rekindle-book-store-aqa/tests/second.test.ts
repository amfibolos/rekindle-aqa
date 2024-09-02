import {describe, expect, it, test} from "@jest/globals";
import {BookstoreFactory, BookstoreKey} from "@/model/bookstore-factory";
import {FactoryKey, ValueObjectFactory} from "@/model/factory";
import {Bookstore, Product} from "@/model/domain-model";
import {ProductKey} from "@/model/product-factory";

describe('Rekindle test2', () => {
    test('Acquire token2', async () => {
        let factory = new BookstoreFactory();
        let aa = factory.create(BookstoreKey.stdBookstoreActive);
        let factoryKit = new ValueObjectFactory();
        let bookstoreFactory = factoryKit.factory<Bookstore>(FactoryKey.bookstore);
        let newBookstore = bookstoreFactory.create(BookstoreKey.stdBookstoreActive);
        let productFactory = factoryKit.factory<Product>(FactoryKey.product);
        let newProduct = productFactory.create(ProductKey.stdProductActive);
    });
});