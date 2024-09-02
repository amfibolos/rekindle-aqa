import {Bookstore} from "@/model/domain-model";
import {AxiosInstance, AxiosResponse, HttpStatusCode} from "axios";
import {BookstoreEndpoints} from "@/rest/Endpoint";
import {BookstoreCrudController} from "@/rest/controllers/rest-controllers";
import {expect} from "@jest/globals";
import {RekindleClient} from "@/rest/rekindle-rest-client";

export class BookstoreController implements BookstoreCrudController {

    private readonly client: AxiosInstance;

    constructor(client: RekindleClient) {
        this.client = client.getAxios();
    }

    async get(param?: string): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'GET',
            url: BookstoreEndpoints.bookstoresById({bookstoreId: param})
        });
    }

    async getSuccessfully(param?: string): Promise<Bookstore> {
        try {
            const resp = await this.get(param);
            expect(resp.status).toBe(HttpStatusCode.Ok);
            return resp.data;
        } catch (err) {
            throw err;
        }
    }

    async getAll(param?: string): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'GET',
            url: BookstoreEndpoints.bookstores
        });
    }

    async getAllSuccessfully(param?: string): Promise<Bookstore[]> {
        try {
            const resp = await this.getAll(param);
            expect(resp.status).toBe(HttpStatusCode.Ok);
            return resp.data
        } catch (err) {
            throw err;
        }
    }

    async post(param: Bookstore): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'POST',
            url: BookstoreEndpoints.bookstores,
            data: param
        });
    }

    async postSuccessfully(param: Bookstore): Promise<Bookstore> {
        try {
            let bookstore = param;
            const resp = await this.post(param);
            expect(resp.status).toBe(HttpStatusCode.Created);
            bookstore.id = resp.data
            return bookstore;
        } catch (err) {
            throw err;
        }
    }

    async put(param: Bookstore): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'PUT',
            url: BookstoreEndpoints.bookstoresById({bookstoreId: param.id}),
            data: param
        });
    }

    async putSuccessfully(param: Bookstore): Promise<void> {
        try {
            const resp = await this.put(param);
            expect(resp.status).toBe(HttpStatusCode.NoContent);
        } catch (err) {
            throw err;
        }
    }

    async delete(param: Bookstore): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'DELETE',
            url: BookstoreEndpoints.bookstoresById({bookstoreId: param.id})
        });
    }

    async deleteSuccessfully(param: Bookstore): Promise<void> {
        try {
            const resp = await this.delete(param);
            expect(resp.status).toBe(HttpStatusCode.NoContent);
        } catch (err) {
            throw err;
        }
    }
}