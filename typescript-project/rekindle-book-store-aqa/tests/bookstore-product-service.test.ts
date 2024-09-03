import {afterAll, beforeAll, describe, expect, test} from '@jest/globals';
import {Bookstore, Product} from '@/model/domain-model';
import {Factory, FactoryKey, FactoryKit} from '@/model/factory';
import {container} from 'tsyringe';
import {HttpStatusCode} from 'axios';
import {BookstoreKey} from "@/model/bookstore-factory";
import {ProductKey} from "@/model/product-factory";
import {ErrorMessage, randomPriceLessThanThousand} from "../src/utils/utils";
import {BookstoreCrudController, BookstoreProductCrudController} from "@/rest/controllers/rest-controllers";
import {RestClient} from "@/rest/rekindle-rest-client";

describe('Bookstore Product Service Test', () => {
    let factoryKit: FactoryKit;
    let bookstoreController: BookstoreCrudController;
    let productController: BookstoreProductCrudController;
    let productFactory: Factory<Product>;
    let bookstore: Bookstore;
    const products: Product[] = [];

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
        if (products.length === 0) return;
        for (const product of products) {
            bookstoreController.deleteSuccessfully(product);
        }
    });

    test('Create New Product Should Return OK', async () => {
        const product: Product = productFactory.create(ProductKey.stdProductActive);
        product.bookstoreId = bookstore.id;

        const createdProduct = await productController.postSuccessfully(product);
        products.push(createdProduct);

        expect(createdProduct.id).toBeDefined();
    });

    test('Get New Product Should Return OK', async () => {
        const product: Product = productFactory.create(ProductKey.stdProductActive);
        product.bookstoreId = bookstore.id;

        const createdProduct = await productController.postSuccessfully(product);
        products.push(createdProduct);

        const fetchedProduct = await productController.getSuccessfully(createdProduct.id);
        expect(fetchedProduct).toMatchObject({
            id: createdProduct.id,
            name: createdProduct.name,
            available: createdProduct.available,
            price: createdProduct.price
        });
    });

    test('Get All Products Should Return OK', async () => {
        const allProducts = await productController.getAllSuccessfully();
        expect(allProducts).not.toHaveLength(0);
    });

    test('Update Product Should Return No Content', async () => {
        const product: Product = productFactory.create(ProductKey.stdProductActive);
        product.bookstoreId = bookstore.id;

        const createdProduct = await productController.postSuccessfully(product);
        products.push(createdProduct);

        const updatedPrice = randomPriceLessThanThousand();
        createdProduct.price = updatedPrice;
        await productController.putSuccessfully(createdProduct);

        const updatedProduct = await productController.getSuccessfully(createdProduct.id);
        expect(updatedProduct.price).toBe(updatedPrice);
    });

    test('Delete Product Should Return No Content', async () => {
        const productFactory: Factory<Product> = factoryKit.factory<Product>(FactoryKey.product);
        const product: Product = productFactory.create(ProductKey.stdProductActive);
        product.bookstoreId = bookstore.id;

        const createdProduct = await productController.postSuccessfully(product);

        await productController.deleteSuccessfully(createdProduct);

        await productController.get(createdProduct.id).catch((error) => {
            expect(error.response?.status).toBe(HttpStatusCode.NotFound);
            expect(error.response?.data?.errorMessage).toBe(ErrorMessage.productNotFound);
        });

    });
});
