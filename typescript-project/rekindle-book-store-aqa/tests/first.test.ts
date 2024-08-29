import {describe, expect, it, test} from "@jest/globals";
import {Configuration} from "@/conf/environment";
import {RekindleClient} from "../src/rest/rekindle-rest-client";
import {TokenDto} from "../src/model/token-dto";

describe('sum module', () => {
    test('Acquire token', () => {
        let config = new Configuration();
        let client = new RekindleClient(config);
        let resp = client.fetchToken();
        //let dto : TokenDto = resp.data;
       // console.log(dto.access_token);
        console.log(resp);
    });
});