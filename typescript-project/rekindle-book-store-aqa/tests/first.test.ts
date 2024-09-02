import {describe, expect, it, test} from "@jest/globals";
import {BookstoreController} from "@/rest/controllers/bookstore-controller";
import {Bookstore} from "@/model/domain-model";

describe('Rekindle test', () => {
    test('Acquire token', async () => {
        let client = global.rekindleClient;
        let rest = new BookstoreController(client);
        var res : Bookstore = await rest.getSuccessfully("306001b9-6983-46d7-a485-2aed5162ca61");
        var response = await rest.getAllSuccessfully();
        var sent = await rest.postSuccessfully({
            name: "Trololo",
            isActive: true
        });
    });
});