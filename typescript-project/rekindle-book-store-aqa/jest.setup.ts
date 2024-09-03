import "reflect-metadata";
import {container, Lifecycle} from "tsyringe";
import {ValueObjectFactory} from "./src/model/factory";
import {RekindleAuthClient, RekindleClient} from "./src/rest/rekindle-rest-client";
import {CustomerController} from "./src/rest/controllers/customer-controller";
import {CustomerCrudController} from "./src/rest/controllers/rest-controllers";

container.register('FactoryKit', {useClass: ValueObjectFactory});
container.register('AuthClient', {useClass: RekindleAuthClient});
container.register(
    "RestClient",
    {useClass: RekindleClient},
    {lifecycle: Lifecycle.Singleton} // <- this is important
);
container.register<CustomerCrudController>('CustomerCrudController', {useClass: CustomerController});


// declare global {
//     var rekindleClient: RekindleClient
// }
//
// const setup = async (): Promise<void> => {
//     global.rekindleClient = new RekindleClient(new RekindleAuthClient());
// };
//
// export default setup;