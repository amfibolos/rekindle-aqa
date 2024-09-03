import {afterAll, beforeAll, describe, expect, test} from "@jest/globals";
import {Customer} from "@/model/domain-model";
import {Factory, FactoryKey, FactoryKit} from "@/model/factory";
import {CustomerController} from "@/rest/controllers/customer-controller";
import {CustomerKey} from "@/model/customer-factory";
import {HttpStatusCode} from "axios";
import {ErrorMessage} from "../src/utils/utils";
import {container} from "tsyringe";
import {RestClient} from "@/rest/rekindle-rest-client";

describe('Customer Service Test', () => {
    let restClient : RestClient = container.resolve('RestClient');
    let factoryKit: FactoryKit = container.resolve('FactoryKit');
    let customerController: CustomerController = container.resolve('CustomerCrudController');
    let customerFactory : Factory<Customer> = factoryKit.factory<Customer>(FactoryKey.customer);
    let customers: Customer[] = [];

    beforeAll(async () => {
        await restClient.setupToken();
    });

    afterAll(async () => {
        if (customers.length === 0) return;
        for (const customer of customers) {
            await customerController.deleteSuccessfully(customer);
        }
    });

    test('should create a new user and return OK', async () => {
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        customers.push(customer);

        expect(customer.id).toBeDefined();
        expect(customer.firstName).toBeTruthy();
        expect(customer.lastName).toBeTruthy();
        expect(customer.username).toBeTruthy();
    });

    test('should get a new user and return OK', async () => {
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        customers.push(customer);
        const fetchedCustomer = await customerController.getSuccessfully(customer.id);
        expect(fetchedCustomer).toEqual(customer);
    });

    test('should get all customers and return OK', async () => {
        const fetchedCustomers = await customerController.getAllSuccessfully();
        expect(fetchedCustomers).not.toHaveLength(0);
    });

    test('should update a new user and return no content', async () => {
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        customers.push(customer);

        const updatedUsername = "Updated Username";
        customer.username = updatedUsername;
        await customerController.putSuccessfully(customer);

        const fetchedCustomer = await customerController.getSuccessfully(customer.id!);
        expect(fetchedCustomer.username).toBe(updatedUsername);
    });

    test('should delete a new user and return OK', async () => {
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        await customerController.deleteSuccessfully(customer);

        await customerController.get(customer.id!)
            .catch((error) => {
                expect(error.response.status).toBe(HttpStatusCode.BadRequest);
                expect(error.response.data.errorMessage).toBe(ErrorMessage.customerNotFound);
            })
    });
});