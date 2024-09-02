import {Customer} from "@/model/domain-model";
import {faker} from '@faker-js/faker';
import {Factory} from "@/model/factory";

export const CustomerKey = {
    stdCustomerActive: Symbol('StdCustomerActive')
};

export class CustomerFactory implements Factory<Customer> {

    private readonly map: Map<Symbol, Supplier<Customer>> = new Map([
        [CustomerKey.stdCustomerActive, () => this.createActiveCustomer()],
    ]);

    private createActiveCustomer(): Customer {
        return {
            firstName: faker.person.firstName(),
            lastName: faker.person.lastName(),
            username: faker.internet.userName()
        }
    }

    create(key: Symbol): Customer {
        const supplier = this.map.get(key);
        if (supplier) {
            return supplier();
        }
        throw new Error(`Bookstore identified by ${key.toString()} has not yet been implemented`);
    }

}