import {getClient} from "../rest/rekindle-rest-client";

const setup = async (): Promise<void> => {
    await getClient().setupToken();
};

export default setup;