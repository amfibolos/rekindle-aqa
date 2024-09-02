import {Bookstore} from "@/model/domain-model";
import { faker } from '@faker-js/faker';
import {Factory} from "@/model/factory";

export const BookstoreKey = {
    stdBookstoreActive: Symbol('StdBookstoreActive'),
    stdBookstoreInactive: Symbol('StdBookstoreInactive')
};

export class BookstoreFactory implements Factory<Bookstore> {

    private readonly map: Map<Symbol, Supplier<Bookstore>> = new Map([
        [BookstoreKey.stdBookstoreActive, () => this.createActiveBookstore()],
        [BookstoreKey.stdBookstoreInactive, () => this.createInActiveBookstore()]
    ]);

    private createActiveBookstore() : Bookstore {
        return {
            name: faker.company.name(),
            isActive: true
        }
    }

    private createInActiveBookstore() : Bookstore {
        return {
            name: faker.company.name(),
            isActive: false
        }
    }

    create(key: Symbol): Bookstore {
        const supplier = this.map.get(key);
        if (supplier) {
            return supplier();
        }
        throw new Error(`Bookstore identified by ${key.toString()} has not yet been implemented`);
    }

}