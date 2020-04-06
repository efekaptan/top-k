FROM node:10

WORKDIR .app

COPY . ../source/

WORKDIR ../source

CMD yarn install && yarn build && node server.js