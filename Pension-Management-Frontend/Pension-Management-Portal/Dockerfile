FROM node:latest
COPY . /app
WORKDIR /app
RUN npm install -g @angular/cli
RUN npm install
RUN npm install @auth0/angular-jwt --force
RUN npm install angular-notifier --force
CMD ["ng", "serve", "--host", "0.0.0.0"]