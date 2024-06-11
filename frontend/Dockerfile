FROM ubuntu:latest
LABEL authors="megvagyhadnagy01"
# Base image
FROM node:14

# Set working directory
WORKDIR /app

# Install dependencies
COPY package*.json ./
RUN npm install

# Copy app files
COPY . .

# Build the app
RUN npm run build

# Expose the port
EXPOSE 3000

# Start the app
CMD ["npm", "start"]

ENTRYPOINT ["top", "-b"]