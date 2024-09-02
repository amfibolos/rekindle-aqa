import {RekindleClient} from "../rest/rekindle-rest-client";

declare global {
  var rekindleClient: RekindleClient
}

const setup = async (): Promise<void> => {
  global.rekindleClient = new RekindleClient();
  await global.rekindleClient.setupToken();
};

export default setup;