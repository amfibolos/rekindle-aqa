import "reflect-metadata";
import {container, Lifecycle} from "tsyringe";
import {ValueObjectFactory} from "./src/model/factory";
import {RekindleAuthClient, RekindleClient, RestClient} from "./src/rest/rekindle-rest-client";
import {CustomerController} from "./src/rest/controllers/customer-controller";
import {
    BookstoreCrudController,
    BookstoreProductCrudController,
    CustomerCrudController
} from "./src/rest/controllers/rest-controllers";
import {BookstoreController} from "./src/rest/controllers/bookstore-controller";
import {BookstoreProductController} from "./src/rest/controllers/bookstore-product-controller";

container.register('FactoryKit', {useClass: ValueObjectFactory});
container.register('AuthClient', {useClass: RekindleAuthClient});
container.register(
    "RestClient",
    {useClass: RekindleClient},
    {lifecycle: Lifecycle.Singleton} // <- this is important
);
container.register<CustomerCrudController>('CustomerCrudController', {useClass: CustomerController});
container.register<BookstoreProductCrudController>('BookstoreProductCrudController', {useClass: BookstoreProductController});
container.register<BookstoreCrudController>('BookstoreCrudController', {useClass: BookstoreController});