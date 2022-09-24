// A tiny wrapper around fetch(), borrowed from
// https://kentcdodds.com/blog/replace-axios-with-a-simple-custom-fetch-wrapper

import { API_STATUS } from "./consts";

export async function client(endpoint, token, { body, ...customConfig } = {}) {
  const headers = { "Content-Type": "application/json", "AUTHORIZATION":  `BEARER ${token}`};

  const config = {
    method: body ? "POST" : "GET",
    ...customConfig,
    headers: {
      ...headers,
      ...customConfig.headers,
    },
  };

  if (body) {
    config.body = JSON.stringify(body);
  }

  let data;
  try {
    const response = await fetch(endpoint, config);
      console.log('response', response)

    if (response.status !== 204) {
      // hack because sometimes response has a body of length 0
      const text = await response.text();
      if(text.length > 0)
        data = JSON.parse(text);
    }
    if (response.ok) {
      // Return a result object similar to Axios
      return {
        status: API_STATUS.SUCCEEDED,
        data,
      };
    }

    let status;
    if (response.status === 401) {
      status = API_STATUS.UNAUTHORIZED;
    } else if (response.status === 403) {
        status = API_STATUS.ACCESS_DENIED;
    } else if (response.status === 404) {
      status = API_STATUS.PAGE_NOT_FOUND;
    } else {
      status = API_STATUS.FAILED;
    }

    // noinspection ExceptionCaughtLocallyJS
    throw new Error(
      JSON.stringify({
        status: status,
        statusCode: response.status,
        data: response.statusText,
      })
    );
  } catch (err) {
    return Promise.reject(err);
  }
}

client.get = function (endpoint, token, customConfig = {}) {
  return client(endpoint, token, {body: null, ...customConfig, method: "GET" });
};

client.post = function (endpoint, token, body, customConfig = {}) {
  const cnf =  {body: body, ...customConfig, method: "POST" };
console.log('cnf', cnf)
  return client(endpoint, token, cnf);
};

client.put = function (endpoint, token, body, customConfig = {}) {
  return client(endpoint, token, { body: body, ...customConfig, method: "PUT" });
};

client.delete = function (endpoint, token, customConfig = {}) {
  return client(endpoint, token, {body: null, ...customConfig, method: "DELETE" });
};
