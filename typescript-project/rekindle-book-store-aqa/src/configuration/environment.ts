const config = require('config');

export interface Environment {
    baseUrl: string;
    tokenUrl: string;
    grantType: string;
    scope: string;
    clientId: string;
    clientSecret: string;
}

export class Configuration implements Environment {
    baseUrl: string;
    tokenUrl: string;
    grantType: string;
    scope: string;
    clientId: string;
    clientSecret: string;


    constructor() {
        this.baseUrl = config.get('envConfig.baseUrl');
        this.tokenUrl = config.get('envConfig.tokenUrl');
        this.grantType = config.get('envConfig.grantType');
        this.scope = config.get('envConfig.scope');
        this.clientId = config.get('envConfig.clientId');
        this.clientSecret = config.get('envConfig.clientSecret');
    }
}