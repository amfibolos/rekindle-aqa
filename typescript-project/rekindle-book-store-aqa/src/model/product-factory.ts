import {Product} from "@/model/domain-model";
import {faker} from '@faker-js/faker';
import {Factory} from "@/model/factory";
import {randomPriceLessThanThousand} from "../utils/utils";

export const ProductKey = {
    stdProductActive: Symbol('StdProductActive'),
    stdProductInactive: Symbol('StdProductInactive')
};

export class ProductFactory implements Factory<Product> {

    private readonly map: Map<Symbol, Supplier<Product>> = new Map([
        [ProductKey.stdProductActive, () => this.createActiveProduct()],
        [ProductKey.stdProductInactive, () => this.createInActiveProduct()]
    ]);

    private createActiveProduct(): Product {
        return {
            name: faker.commerce.productName(),
            available: true,
            price: randomPriceLessThanThousand()
        }
    }

    private createInActiveProduct(): Product {
        return {
            name: faker.commerce.productName(),
            available: false,
            price: randomPriceLessThanThousand()
        }
    }

    create(key: Symbol): Product {
        const supplier = this.map.get(key);
        if (supplier) {
            return supplier();
        }
        throw new Error(`Bookstore identified by ${key.toString()} has not yet been implemented`);
    }

}