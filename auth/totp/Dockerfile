FROM node:20

WORKDIR /app

COPY package*.json .

COPY . .

RUN npm install

EXPOSE 1002

CMD ["node", "./src/index.js"]
