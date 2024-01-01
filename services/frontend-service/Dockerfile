# Use a smaller base image
FROM node:21-alpine as builder

# Set the working directory to /app
WORKDIR /app

# Copy the source code to the working directory
COPY . .

# Install app dependencies
RUN npm install

# Build the React app
RUN npm run build

# Use a smaller base image for the final image
FROM nginx:1-alpine-slim

# Copy the build files from the builder stage to the nginx folder
COPY --from=builder /app/build /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port 80
EXPOSE 80

# Start nginx when the container launches
CMD ["nginx", "-g", "daemon off;"]
