FROM openresty/openresty:alpine

COPY nginx.conf /usr/local/openresty/nginx/conf/nginx.conf
COPY lua/ /usr/local/openresty/nginx/conf/lua/

RUN apk add --no-cache git ca-certificates && update-ca-certificates && \
    git clone https://github.com/ledgetech/lua-resty-http.git /tmp/lua-resty-http && \
    cp -r /tmp/lua-resty-http/lib/resty/* /usr/local/openresty/lualib/resty/ && \
    rm -rf /tmp/lua-resty-http

RUN apk add --no-cache git lua5.1 lua5.1-dev luarocks5.1 build-base openssl-dev && \
    luarocks-5.1 install lua-resty-openssl

EXPOSE 1007

CMD ["/usr/local/openresty/bin/openresty", "-g", "daemon off;", "-c", "/usr/local/openresty/nginx/conf/nginx.conf"]
