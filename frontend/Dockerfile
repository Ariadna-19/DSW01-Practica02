# syntax=docker/dockerfile:1

FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build && \
    mkdir -p /tmp/dist-output && \
    if [ -d dist/frontend/browser ]; then \
      cp -r dist/frontend/browser/. /tmp/dist-output/; \
    else \
      cp -r dist/frontend/. /tmp/dist-output/; \
    fi

FROM nginx:1.27-alpine

RUN printf '%s\n' \
    'server {' \
    '    listen 80;' \
    '    server_name _;' \
    '    root /usr/share/nginx/html;' \
    '    index index.html;' \
    '    location / {' \
    '        try_files $uri $uri/ /index.html;' \
    '    }' \
    '}' > /etc/nginx/conf.d/default.conf

COPY --from=build /tmp/dist-output/ /usr/share/nginx/html/

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]