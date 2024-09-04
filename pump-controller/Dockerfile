FROM nginx:alpine
WORKDIR /usr/share/nginx/html
RUN rm -rf ./*

COPY --from=./build build .

EXPOSE 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]