import axios, {AxiosInstance, AxiosResponse} from 'axios';
import {Environment, getEnv} from "../configuration/environment";
import * as querystring from "node:querystring";
import {TokenDto} from "@/model/domain-model";
import {inject, injectable, singleton} from "tsyringe";
import * as AxiosLogger from 'axios-logger';

export interface RestClient {
    getAxios(): AxiosInstance

    setupToken(): Promise<void>
}

export interface AuthClient {
    getAxios(): AxiosInstance

    acquireToken(): Promise<AxiosResponse>
}

abstract class Client {
    protected readonly env: Environment = getEnv();
    protected readonly axiosInstance: AxiosInstance;

    protected constructor(axiosInstance: AxiosInstance) {
        this.axiosInstance = axiosInstance;
        AxiosLogger.setGlobalConfig({
            method: true,
            url: true,
            params: true,
            data: true,
            status: true,
            statusText: true,
            headers: true
        });
        setRequestInterceptor(this.axiosInstance);
        setResponseInterceptor(this.axiosInstance);
    }

}

@injectable()
export class RekindleAuthClient extends Client implements AuthClient {

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

    async acquireToken(): Promise<AxiosResponse> {
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

@injectable()
@singleton()
export class RekindleClient extends Client implements RestClient {

    private readonly auth: AuthClient;
    private tokenDto?: TokenDto;

    constructor(@inject('AuthClient') auth: AuthClient) {
        super(axios.create({
            timeout: 10000,
            headers: {
                "Content-Type": "application/json"
            },
        }))
        this.axiosInstance.defaults.baseURL = this.env.baseUrl;
        this.auth = auth;
    }

    getAxios(): AxiosInstance {
        return this.axiosInstance;
    }


    async setupToken(): Promise<void> {
        if (this.tokenDto == null) {
            this.tokenDto = await this.auth.acquireToken().then(rsp => rsp.data)
                .catch((err) => {
                    throw err
                });
            // @ts-ignore
            this.axiosInstance.defaults.headers.common = {'Authorization': `${this.tokenDto.token_type} ${this.tokenDto.access_token}`}
        }
    }
}

function setRequestInterceptor(axiosInstance: AxiosInstance): void {
    axiosInstance.interceptors.request.use(AxiosLogger.requestLogger, AxiosLogger.errorLogger);
}

function setResponseInterceptor(axiosInstance: AxiosInstance): void {
    axiosInstance.interceptors.response.use(AxiosLogger.responseLogger, AxiosLogger.errorLogger);
}