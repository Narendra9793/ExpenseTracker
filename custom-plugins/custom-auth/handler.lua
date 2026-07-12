local kong = kong
local http = require "resty.http"

local CustomAuthHandler = {
    PRIORITY = 1000,
    VERSION = "1.0",
}

function CustomAuthHandler:access(config)

    -- Get Authorization header
    local authorization = kong.request.get_header("Authorization")

    -- Reject request if Authorization header is missing
    if not authorization or authorization == "" then
        return kong.response.exit(401, {
            message = "Authorization header is missing"
        })
    end

    -- Create HTTP client
    local httpc = http.new()

    -- Connection timeout, send timeout, read timeout (milliseconds)
    httpc:set_timeouts(3000, 3000, 3000)

    -- Call Auth Service
    local res, err = httpc:request_uri(config.auth_service_url, {
        method = "GET",
        headers = {
            ["Authorization"] = authorization
        }
    })

    -- Network failure while contacting Auth Service
    if not res then
        kong.log.err("Unable to reach Auth Service: ", err)

        return kong.response.exit(503, {
            message = "Authentication service unavailable"
        })
    end

    -- Invalid or expired token
    if res.status == 401 then
        return kong.response.exit(401, {
            message = "Unauthorized"
        })
    end

    -- Forbidden
    if res.status == 403 then
        return kong.response.exit(403, {
            message = "Forbidden"
        })
    end

    -- Any other unexpected response
    if res.status ~= 200 then
        kong.log.err(
            "Unexpected response from Auth Service. Status: ",
            res.status,
            ", Body: ",
            res.body or ""
        )

        return kong.response.exit(500, {
            message = "Authentication service error"
        })
    end

    -- AuthService currently returns only the userId as plain text
    local user_id = (res.body or ""):match("^%s*(.-)%s*$")

    if user_id == "" then
        kong.log.err("Auth Service returned an empty user ID")

        return kong.response.exit(500, {
            message = "Invalid authentication response"
        })
    end

    -- Forward user ID to downstream services
    kong.service.request.set_header("X-User-ID", user_id)

    kong.log.debug("Authenticated user: ", user_id)
end

return CustomAuthHandler