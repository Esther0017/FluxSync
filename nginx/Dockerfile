FROM openresty/openresty:alpine

COPY nginx.conf /usr/local/openresty/nginx/conf/nginx.conf
COPY lua/ /usr/local/openresty/nginx/conf/lua/

EXPOSE 1007

CMD ["nginx", "-g", "daemon off;", "-c", "/usr/local/openresty/nginx/conf/nginx.conf"]