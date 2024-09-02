import {afterAll, beforeAll, describe, expect, it, test} from "@jest/globals";
import {Customer} from "@/model/domain-model";
import {FactoryKey, FactoryKit, ValueObjectFactory} from "@/model/factory";
import {CustomerController} from "@/rest/controllers/customer-controller";
import {CustomerKey} from "@/model/customer-factory";
import {AxiosError, AxiosResponse, HttpStatusCode} from "axios";
import {ErrorMessage} from "../src/utils/utils";

describe('Customer Service Test', () => {
    let factoryKit: FactoryKit;
    let customerController: CustomerController;
    let customers: Customer[] = [];

    beforeAll(() => {
        //  factoryKit = Container.resolve<IFactoryKit>(IFactoryKit);
        //  customerController = Container.resolve<ICustomerController>(ICustomerController);
        factoryKit = new ValueObjectFactory();
        customerController = new CustomerController(global.rekindleClient);
    });

    afterAll(async () => {
        if (customers.length === 0) return;
        for (const customer of customers) {
            await customerController.deleteSuccessfully(customer);
        }
    });

    it('should create a new user and return OK', async () => {
        const customerFactory = factoryKit.factory<Customer>(FactoryKey.customer);
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        customers.push(customer);

        expect(customer.id).toBeDefined();
        expect(customer.firstName).toBeTruthy();
        expect(customer.lastName).toBeTruthy();
        expect(customer.username).toBeTruthy();
    });

    it('should get a new user and return OK', async () => {
        const customerFactory = factoryKit.factory<Customer>(FactoryKey.customer);
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        customers.push(customer);

        const fetchedCustomer = await customerController.getSuccessfully(customer.id);
        expect(fetchedCustomer).toEqual(customer);
    });

    it('should get all customers and return OK', async () => {
        const fetchedCustomers = await customerController.getAllSuccessfully();
        expect(fetchedCustomers).not.toHaveLength(0);
    });

    it('should update a new user and return no content', async () => {
        const customerFactory = factoryKit.factory<Customer>(FactoryKey.customer);
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        customers.push(customer);

        const updatedUsername = "Updated Username";
        customer.username = updatedUsername;
        await customerController.putSuccessfully(customer);

        const fetchedCustomer = await customerController.getSuccessfully(customer.id!);
        expect(fetchedCustomer.username).toBe(updatedUsername);
    });

    it('should delete a new user and return OK', async () => {
        const customerFactory = factoryKit.factory<Customer>(FactoryKey.customer);
        let customer = customerFactory.create(CustomerKey.stdCustomerActive);
        customer = await customerController.postSuccessfully(customer);
        await customerController.deleteSuccessfully(customer);


        try {
            await customerController.get(customer.id!); // @ts-ignore
        } catch (error: AxiosError) {
            expect(error.response.status).toBe(HttpStatusCode.BadRequest);
            expect(error.response.data.errorMessage).toBe(ErrorMessage.customerNotFound);
        }
    });
});