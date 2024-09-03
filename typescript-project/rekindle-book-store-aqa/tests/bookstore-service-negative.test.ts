import { beforeAll, describe, expect, test } from '@jest/globals';
import { Bookstore } from '@/model/domain-model';
import { BookstoreController } from '@/rest/controllers/bookstore-controller';
import { container } from 'tsyringe';
import { RestClient } from '@/rest/rekindle-rest-client';
import { HttpStatusCode } from 'axios';
import {ErrorMessage} from "../src/utils/utils";
//@ts-ignore
import {v4 as uuidv4} from 'uuid';

beforeAll(async () => {
    const restClient: RestClient = container.resolve("RestClient");
    await restClient.setupToken();
});

describe('Bookstore Service Negative Tests', () => {
    let bookstoreController: BookstoreController;

    beforeAll(() => {
        bookstoreController = container.resolve(BookstoreController);
    });

    test('Verify Cannot Create Bookstore With Empty Values', async () => {
        const bookstore: Bookstore = { name: '', isActive: null as any };
        try {
            const response = await bookstoreController.post(bookstore);
            expect(response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(response.data.name);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(response.data.isActive);
        } catch (error: any) {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.name);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.isActive);
        }
    });

    test('Verify Cannot Create Bookstore With Null Values', async () => {
        const bookstore: Bookstore = {};
        try {
            const response = await bookstoreController.post(bookstore);
            expect(response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(response.data.name);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(response.data.isActive);
        } catch (error: any) {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.name);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.isActive);
        }
    });

    test('Verify Cannot Get Non-Existing Bookstore', async () => {
        try {
            const response = await bookstoreController.get(uuidv4());
            expect(response.status).toBe(HttpStatusCode.NotFound);
            expect(response.data.errorMessage).toBe(ErrorMessage.bookstoreNotFound);
        } catch (error: any) {
            expect(error.response.status).toBe(HttpStatusCode.NotFound);
            expect(error.response.data.errorMessage).toBe(ErrorMessage.bookstoreNotFound);
        }
    });

    test('Verify Cannot Update Non-Existing Bookstore', async () => {
        const bookstore: Bookstore = { id: uuidv4(), name: 'Test', isActive: true };
        try {
            const response = await bookstoreController.put(bookstore);
            expect(response.status).toBe(HttpStatusCode.NotFound);
            expect(response.data.errorMessage).toBe(ErrorMessage.bookstoreNotFound);
        } catch (error: any) {
            expect(error.response.status).toBe(HttpStatusCode.NotFound);
            expect(error.response.data.errorMessage).toBe(ErrorMessage.bookstoreNotFound);
        }
    });

    test('Verify Cannot Delete Non-Existing Bookstore', async () => {
        const bookstore: Bookstore = { id: uuidv4() };
        try {
            const response = await bookstoreController.delete(bookstore);
            expect(response.status).toBe(HttpStatusCode.NotFound);
            expect(response.data.errorMessage).toBe(ErrorMessage.bookstoreNotFound);
        } catch (error: any) {
            expect(error.response.status).toBe(HttpStatusCode.NotFound);
            expect(error.response.data.errorMessage).toBe(ErrorMessage.bookstoreNotFound);
        }
    });
});