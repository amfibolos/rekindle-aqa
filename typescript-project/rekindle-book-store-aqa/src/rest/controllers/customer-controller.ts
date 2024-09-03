import {Customer} from "@/model/domain-model";
import {AxiosInstance, AxiosResponse, HttpStatusCode} from "axios";
import {CustomerEndpoints} from "@/rest/Endpoint";
import {CustomerCrudController} from "@/rest/controllers/rest-controllers";
import {expect} from "@jest/globals";
import {RestClient} from "@/rest/rekindle-rest-client";
import {inject, injectable} from "tsyringe";

@injectable()
export class CustomerController implements CustomerCrudController {

    private readonly client: AxiosInstance;

    constructor(@inject('RestClient') client: RestClient) {
        this.client = client.getAxios();
    }

    async get(param?: string): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'GET',
            url: CustomerEndpoints.customerById({customerId: param})
        });
    }

    async getSuccessfully(param?: string): Promise<Customer> {
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
            url: CustomerEndpoints.customers
        });
    }

    async getAllSuccessfully(param?: string): Promise<Customer[]> {
        try {
            const resp = await this.getAll(param);
            expect(resp.status).toBe(HttpStatusCode.Ok);
            return resp.data
        } catch (err) {
            throw err;
        }
    }

    async post(param: Customer): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'POST',
            url: CustomerEndpoints.customers,
            data: param
        });
    }

    async postSuccessfully(param: Customer): Promise<Customer> {
        try {
            let bookstore = param;
            const resp = await this.post(param);
            expect(resp.status).toBe(HttpStatusCode.Created);
            bookstore.id = resp.data.customerId;
            return bookstore;
        } catch (err) {
            throw err;
        }
    }

    async put(param: Customer): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'PUT',
            url: CustomerEndpoints.customerById({customerId: param.id}),
            data: param
        });
    }

    async putSuccessfully(param: Customer): Promise<void> {
        try {
            const resp = await this.put(param);
            expect(resp.status).toBe(HttpStatusCode.NoContent);
        } catch (err) {
            throw err;
        }
    }

    async delete(param: Customer): Promise<AxiosResponse> {
        return await this.client.request({
            method: 'DELETE',
            url: CustomerEndpoints.customerById({customerId: param.id})
        });
    }

    async deleteSuccessfully(param: Customer): Promise<void> {
        try {
            const resp = await this.delete(param);
            expect(resp.status).toBe(HttpStatusCode.Ok);
        } catch (err) {
            throw err;
        }
    }
}