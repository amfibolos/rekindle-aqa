const config = require('config');

export interface Environment {
    readonly baseUrl: string;
    readonly tokenUrl: string;
    readonly grantType: string;
    readonly scope: string;
    readonly clientId: string;
    readonly clientSecret: string;
}

export function getEnv() : Environment {
    return Configuration.instance;
}

class Configuration implements Environment {
    static #instance: Configuration;
    readonly baseUrl: string;
    readonly tokenUrl: string;
    readonly grantType: string;
    readonly scope: string;
    readonly clientId: string;
    readonly clientSecret: string;


    private constructor() {
        this.baseUrl = config.get('envConfig.baseUrl');
        this.tokenUrl = config.get('envConfig.tokenUrl');
        this.grantType = config.get('envConfig.grantType');
        this.scope = config.get('envConfig.scope');
        this.clientId = config.get('envConfig.clientId');
        this.clientSecret = config.get('envConfig.clientSecret');
    }

    public static get instance(): Configuration {
        if (!Configuration.#instance) {
            Configuration.#instance = new Configuration();
        }
        return Configuration.#instance;
    }
}