FROM nginx:alpine

WORKDIR /usr/share/nginx/html

COPY ./build/ /usr/share/nginx/html/

EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]