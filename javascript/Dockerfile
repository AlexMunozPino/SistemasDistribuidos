FROM node:16

WORKDIR /usr/scr/app

COPY package*.json ./

RUN npm install

COPY . .

CMD [ "node", "index.js" ]
