import {Product} from "@/model/domain-model";
import {AxiosInstance, AxiosResponse, HttpStatusCode} from "axios";
import {BookstoreEndpoints} from "@/rest/Endpoint";
import {BookstoreProductCrudController} from "@/rest/controllers/rest-controllers";
import {expect} from "@jest/globals";
import {RekindleClient} from "@/rest/rekindle-rest-client";

export class BookstoreProductController implements BookstoreProductCrudController {

    private readonly client: AxiosInstance;

    constructor(client: RekindleClient) {
        this.client = client.getAxios();
    }

    async get(param?: string): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'GET',
            url: BookstoreEndpoints.bookstoresProductById({productId: param})
        });
    }

    async getSuccessfully(param?: string): Promise<Product> {
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
            url: BookstoreEndpoints.bookstoresProducts
        });
    }

    async getAllSuccessfully(param?: string): Promise<Product[]> {
        try {
            const resp = await this.getAll(param);
            expect(resp.status).toBe(HttpStatusCode.Ok);
            return resp.data
        } catch (err) {
            throw err;
        }
    }

    async post(param: Product): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'POST',
            url: BookstoreEndpoints.bookstoreProductByStoreId({bookstoreId: param.bookstoreId}),
            data: param
        });
    }

    async postSuccessfully(param: Product): Promise<Product> {
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

    async put(param: Product): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'PUT',
            url: BookstoreEndpoints.bookstoresProductById({productId: param.id}),
            data: param
        });
    }

    async putSuccessfully(param: Product): Promise<void> {
        try {
            const resp = await this.put(param);
            expect(resp.status).toBe(HttpStatusCode.NoContent);
        } catch (err) {
            throw err;
        }
    }

    async delete(param: Product): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'DELETE',
            url: BookstoreEndpoints.bookstoreProductByStoreIdAndProductId({
                bookstoreId: param.bookstoreId,
                productId: param.id
            })
        });
    }

    async deleteSuccessfully(param: Product): Promise<void> {
        try {
            const resp = await this.delete(param);
            expect(resp.status).toBe(HttpStatusCode.NoContent);
        } catch (err) {
            throw err;
        }
    }
}