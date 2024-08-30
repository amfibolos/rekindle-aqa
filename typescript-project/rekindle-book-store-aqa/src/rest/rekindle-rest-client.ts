import axios, {AxiosInstance, AxiosResponse} from 'axios';
import {Environment, getEnv} from "../configuration/environment";
import * as querystring from "node:querystring";
import {TokenDto} from "@/model/token-dto";
import * as Console from "node:console";

abstract class Client {
    protected readonly env: Environment = getEnv();
    protected readonly axiosInstance: AxiosInstance;

    protected constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
        setRequestInterceptor(this.axiosInstance);
        setResponseInterceptor(this.axiosInstance);
    }

    abstract getAxios(): AxiosInstance;

}

export class RekindleAuthClient extends Client {

    constructor() {
        super(axios.create({
            timeout: 5000,
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            },
        }))
    }

    getAxios() {
        return this.axiosInstance;
    }

    async acquireToken(): Promise<AxiosResponse<TokenDto>> {
        return this.axiosInstance.request({
            url: this.env.tokenUrl,
            method: 'POST',
            auth: {
                username: this.env.clientId,
                password: this.env.clientSecret
            },
            data: querystring.stringify({
                grant_type: this.env.grantType,
                scope: this.env.scope
            })
        });
    }
}

export function getClient() : RekindleClient{
    return RekindleClient.instance;
}

export class RekindleClient extends Client {

    static #instance: RekindleClient;
    private readonly auth: RekindleAuthClient = new RekindleAuthClient();
    private tokenDto?: TokenDto;

    constructor() {
        super(axios.create({
            timeout: 10000,
            headers: {
                "Content-Type": "application/json"
            },
        }))
        this.axiosInstance.defaults.baseURL = this.env.baseUrl;
    }

    public static get instance(): RekindleClient {
        if (!RekindleClient.#instance) {
            RekindleClient.#instance = new RekindleClient();
        }
        return RekindleClient.#instance;
    }

    getAxios(): AxiosInstance {
        this.axiosInstance.defaults.headers.common =
            {'Authorization': `${this.tokenDto?.token_type} ${this.tokenDto?.access_token}`};
        return this.axiosInstance;
    }


    async setupToken(): Promise<TokenDto> {
        let tokenData: TokenDto = await this.auth.acquireToken().then(rsp => rsp.data)
            .catch((err) => {throw err});
        return new Promise(() => tokenData);
    }

}

function setRequestInterceptor(axiosInstance: AxiosInstance): void {
    axiosInstance.interceptors.request.use(request => {
        Console.log('----------#########################----------')
        Console.log('Starting Request', request.method, request.baseURL, request.url)
        Console.log('Request Headers', request.headers)
        Console.log('Request data', request.data)
        return request
    })
}

function setResponseInterceptor(axiosInstance: AxiosInstance): void {
    axiosInstance.interceptors.response.use(response => {
        Console.log('Response Status Code', response.status)
        Console.log('Response headers', response.headers)
        Console.log('Response data', response.data)
        Console.log('----------#########################----------')
        return response
    })
}

