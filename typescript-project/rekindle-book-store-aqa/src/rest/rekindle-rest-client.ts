import axios, {AxiosInstance, AxiosResponse} from 'axios';
import {Environment} from "@/conf/environment";
import * as querystring from "node:querystring";
import {TokenDto} from "../model/token-dto";

export interface IRekindleClient {

}


export class RekindleClient {

    private readonly rekindleClient: AxiosInstance;
    private readonly environment: Environment;

    constructor(environment: Environment) {
        this.environment = environment;
        this.rekindleClient = axios.create({
            baseURL: environment.baseUrl
        });
    }

    fetchToken(): Promise<AxiosResponse<TokenDto>> {
        const api = axios.create({
            timeout: 5000,
            auth: {
                username: this.environment.clientId,
                password: this.environment.clientSecret
            }
        })
        api.interceptors.request.use(request => {
            console.log('Starting Request', request)
            return request
        })

        api.interceptors.response.use(response => {
            console.log('Response:', response)
            return response
        })

        return api.post(this.environment.tokenUrl, querystring.stringify({
            grant_type: this.environment.grantType,
            scope: this.environment.scope
        }), {
            headers: {
                "Content-Type": "application/x-www-form-urlencoded"
            }
        });
    }
}