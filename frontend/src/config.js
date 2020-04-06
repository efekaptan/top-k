let IS_DEBUG = false;

if (process.env.NODE_ENV !== 'production') {
    IS_DEBUG = true;
}

export { IS_DEBUG };
export const API_URL = 'http://localhost:8080';
export const SOCKET_URL = 'http://localhost:8080/ws';
export const POLLING_INTERVAL = 10 * 1000;
export const TABLE_SIZE = 20;
export const TOPIC = '/topic';
