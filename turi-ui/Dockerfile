FROM node:18 AS build

WORKDIR /app

COPY turi-ui/package.json turi-ui/package-lock.json ./

RUN npm install

COPY turi-ui/ .

RUN npm run build

FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

CMD ["nginx", "-g", "daemon off;"]