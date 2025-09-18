local cjson = require "cjson.safe"

if ngx.var.uri == "/api/login" or ngx.var.uri == "/api/register" then
    return
end

local headers = ngx.req.get_headers()
local auth = headers["authorization"]

if not auth then
    ngx.status = 401
    ngx.say("Unauthorized: missing Authorization header")
    return ngx.exit(401)
end

local _, _, token = string.find(auth, "Bearer%s+(.+)")

if not token then
    ngx.status = 401
    ngx.say("Unauthorized: invalid Authorization format")
    return ngx.exit(401)
end

local http = require "resty.http";

local httpc = http.new();

local query = "?authorization=" .. token;

local res, err = httpc:request_uri("http://jwt:1001/verify" .. query, {
    method = "GET"
});

if res then
    httpc:set_keepalive()
end

if not res then
    ngx.status = 500
    ngx.say("Auth service error: ", err)
    return ngx.exit(500)
end

if res.status ~= 200 then
    ngx.status = 401
    ngx.say("Unauthorized")
    return ngx.exit(401)
end

local payload = cjson.decode(res.body)
ngx.req.set_header("X-Username", payload.username)
