import {describe, expect, it, test} from "@jest/globals";
import {RekindleAuthClient, RekindleClient} from "@/rest/rekindle-rest-client";

describe('Rekindle test', () => {
    test('Acquire token', async () => {
        let client = new RekindleClient();
        client.getAxios();
    });
});