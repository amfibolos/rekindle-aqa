import {beforeAll, describe, expect, test} from "@jest/globals";
import {Customer} from "@/model/domain-model";
import {Factory, FactoryKey, FactoryKit} from "@/model/factory";
import {CustomerController} from "@/rest/controllers/customer-controller";
import {CustomerKey} from "@/model/customer-factory";
import {AxiosResponse, HttpStatusCode} from "axios";
import {ErrorMessage} from "../src/utils/utils";
import {container} from "tsyringe";
import {RestClient} from "@/rest/rekindle-rest-client";
//@ts-ignore
import {v4 as uuidv4} from 'uuid';

describe('Customer Service Test', () => {
    let restClient: RestClient = container.resolve('RestClient');
    let factoryKit: FactoryKit = container.resolve('FactoryKit');
    let customerController: CustomerController = container.resolve('CustomerCrudController');
    let customerFactory: Factory<Customer> = factoryKit.factory<Customer>(FactoryKey.customer);

    beforeAll(async () => {
        await restClient.setupToken();
    });

    test('should not create customer with empty values', async () => {
        const customer: Customer = {firstName: '', lastName: '', username: ''};
        await customerController.post(customer).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.firstName);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.lastName);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.username);
        })
    });

    test('should not create customer with null values', async () => {
        const customer: Customer = {};
        await customerController.post(customer).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.firstName);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.lastName);
            expect([ErrorMessage.notBlank, ErrorMessage.notNull]).toContain(error.response.data.username);
        })
    });

    test('should not get non-existing customer', async () => {
        await customerController.get(uuidv4().toString()).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect(error.response.data.errorMessage).toBe(ErrorMessage.customerNotFound);
        });
    });

    test('should not delete non-existing customer', async () => {
        const customer: Customer = {id: uuidv4().toString()};
        await customerController.delete(customer).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect(error.response.data.errorMessage).toBe(ErrorMessage.customerNotFound);
        });
    });

    test('should not update non-existing customer', async () => {
        const customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer.id = uuidv4().toString();

        await customerController.put(customer).catch((error) => {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect(error.response.data.errorMessage).toBe(ErrorMessage.customerNotFound);
        })
    });
});