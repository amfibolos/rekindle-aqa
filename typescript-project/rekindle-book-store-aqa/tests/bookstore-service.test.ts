import {beforeAll, afterAll, describe, expect, test} from '@jest/globals';
import {Bookstore} from '@/model/domain-model';
import {Factory, FactoryKey, FactoryKit} from '@/model/factory';
import {BookstoreKey} from '@/model/bookstore-factory';
import {HttpStatusCode} from 'axios';
import {ErrorMessage} from '../src/utils/utils';
import {container} from 'tsyringe';
import {RestClient} from '@/rest/rekindle-rest-client';
import {BookstoreCrudController} from "@/rest/controllers/rest-controllers";

describe('Bookstore Service Test', () => {
    let restClient: RestClient;
    let factoryKit: FactoryKit;
    let bookstoreController: BookstoreCrudController;
    let bookstoreFactory: Factory<Bookstore>;
    let bookstores: Bookstore[] = [];

    beforeAll(async () => {
        restClient = container.resolve('RestClient');
        factoryKit = container.resolve('FactoryKit');
        bookstoreController = container.resolve('BookstoreCrudController');
        bookstoreFactory = factoryKit.factory<Bookstore>(FactoryKey.bookstore);
        await restClient.setupToken();
    });

    afterAll(async () => {
        if (bookstores.length === 0) return;
        for (const bookstore of bookstores) {
            bookstoreController.deleteSuccessfully(bookstore);
        }
    });

    test('Create New Bookstore Should Return OK', async () => {
        let bookstore = bookstoreFactory.create(BookstoreKey.stdBookstoreActive);
        bookstore = await bookstoreController.postSuccessfully(bookstore);
        bookstores.push(bookstore);
        expect(bookstore.id).toBeDefined();
    });

    test('Get New Bookstore Should Return OK', async () => {
        let bookstore = bookstoreFactory.create(BookstoreKey.stdBookstoreActive);
        bookstore = await bookstoreController.postSuccessfully(bookstore);
        bookstores.push(bookstore);
        const bookstoreFetched = await bookstoreController.getSuccessfully(bookstore.id!);
        expect(bookstoreFetched).toEqual(bookstore);
    });

    test('Get All Bookstores Should Return OK', async () => {
        const bookstoresFetched = await bookstoreController.getAllSuccessfully();
        expect(bookstoresFetched).not.toHaveLength(0);
    });

    test('Update Bookstore Should Return No Content', async () => {
        let bookstore = bookstoreFactory.create(BookstoreKey.stdBookstoreActive);
        bookstore = await bookstoreController.postSuccessfully(bookstore);
        bookstores.push(bookstore);
        const updatedName = 'UpdatedName';
        bookstore.name = updatedName;
        await bookstoreController.putSuccessfully(bookstore);
        const bookstoreFetched = await bookstoreController.getSuccessfully(bookstore.id);
        expect(bookstoreFetched.name).toBe(updatedName);
    });

    test('Delete Bookstore Should Return No Content', async () => {
        let bookstore = bookstoreFactory.create(BookstoreKey.stdBookstoreActive);
        bookstore = await bookstoreController.postSuccessfully(bookstore);
        await bookstoreController.deleteSuccessfully(bookstore);
        await bookstoreController.get(bookstore.id!)
            .catch((error) => {
                expect(error.response.status).toBe(HttpStatusCode.NotFound);
                expect(error.response.data.errorMessage).toBe(ErrorMessage.bookstoreNotFound);
            });
    });
});