# Stage 1: Build the React app
FROM node:18-alpine AS build-stage
WORKDIR /app

# Tells Docker that this build expects a variable.
ARG VITE_GOOGLE_CLIENT_ID
# Makes the variable avaiable.
ENV VITE_GOOGLE_CLIENT_ID=${VITE_GOOGLE_CLIENT_ID}

COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# Stage 2: Serve with Ngnix
FROM nginx:stable-alpine
# Copy the build output from Stage 1 to Nginx's html folder
COPY --from=build-stage /app/dist /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]