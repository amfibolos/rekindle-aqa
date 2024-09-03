import {afterAll, beforeAll, describe, expect, test} from '@jest/globals';
import {Bookstore, Product} from '@/model/domain-model';
import {Factory, FactoryKey, FactoryKit} from '@/model/factory';
import {container} from 'tsyringe';
import {HttpStatusCode} from 'axios';
import {ProductKey} from "@/model/product-factory";
import {BookstoreKey} from "@/model/bookstore-factory";
import {BookstoreCrudController, BookstoreProductCrudController} from "@/rest/controllers/rest-controllers";
import {ErrorMessage} from "../src/utils/utils";
//@ts-ignore
import {v4 as uuidv4} from 'uuid';
import {RestClient} from "@/rest/rekindle-rest-client";

describe('Bookstore Product Service Negative Tests', () => {
    let factoryKit: FactoryKit;
    let bookstoreController: BookstoreCrudController;
    let productController: BookstoreProductCrudController;
    let productFactory: Factory<Product>;
    let bookstore: Bookstore;

    beforeAll(async () => {
        await container.resolve<RestClient>('RestClient').setupToken();
        factoryKit = container.resolve('FactoryKit');
        productFactory = factoryKit.factory<Product>(FactoryKey.product);
        bookstoreController = container.resolve('BookstoreCrudController');
        productController = container.resolve('BookstoreProductCrudController');

        const bookstoreFactory: Factory<Bookstore> = factoryKit.factory<Bookstore>(FactoryKey.bookstore);
        bookstore = bookstoreFactory.create(BookstoreKey.stdBookstoreActive);
        bookstore = await bookstoreController.postSuccessfully(bookstore);
    });

    afterAll(async () => {
        bookstoreController.deleteSuccessfully(bookstore);
    });

    test('Verify Cannot Create Product With Empty Values', async () => {
        const product: Product = {
            available: null as any,
            name: null as any,
            price: null as any,
            bookstores: null as any,
            bookstoreId: bookstore.id
        };
        await productController.post(product).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.name);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.available);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.price);
        })
    });

    test('Verify Cannot Create Product With Null Values', async () => {
        const product: Product = {
            available: null as any,
            name: null as any,
            price: null as any,
            bookstores: null as any,
            bookstoreId: bookstore.id
        };

        await productController.post(product).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.name);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.available);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.price);
        });
    });

    test('Verify Cannot Get Non-Existing Product', async () => {
        const product: Product = productFactory.create(ProductKey.stdProductActive);
        product.id = uuidv4();
        await productController.get(product.id).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.NotFound);
            expect(error.response.data?.errorMessage).toBe(ErrorMessage.productNotFound);
        });
    });

    test('Verify Cannot Update Non-Existing Product', async () => {
        const product: Product = productFactory.create(ProductKey.stdProductActive);
        product.bookstoreId = bookstore.id;
        product.id = uuidv4();
        await productController.put(product).catch((error) => {
            expect(error.response?.status).toBe(HttpStatusCode.NotFound);
            expect(error.response?.data?.errorMessage).toBe(ErrorMessage.productNotFound);
        });
    });

    test('Verify Cannot Delete Non-Existing Product', async () => {
        const product: Product = productFactory.create(ProductKey.stdProductActive);
        product.bookstoreId = bookstore.id;
        product.id = uuidv4();
        await productController.delete(product).catch((error) => {
            expect(error.response?.status).toBe(HttpStatusCode.NotFound);
            expect(error.response?.data?.errorMessage).toBe(ErrorMessage.productNotFound);
        });
    });
});
