let API_URL, SOCKET_URL, IS_DEBUG = false;

if (process.env.NODE_ENV === 'production') {
    API_URL = 'http://frequency-api.efekaptan.com';
    SOCKET_URL = 'http://frequency-api.efekaptan.com/ws';
} else {
    API_URL = 'http://localhost:8080';
    SOCKET_URL = 'http://localhost:8080/ws';
    IS_DEBUG = true;
}

export { API_URL, SOCKET_URL, IS_DEBUG };

export const POLLING_INTERVAL = 10 * 1000;
export const TABLE_SIZE = 20;
export const TOPIC = '/topic';
