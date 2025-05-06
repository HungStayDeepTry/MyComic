package hung.deptrai.mycomic.core.domain.exception

sealed interface DataError: Error {
    enum class Network : DataError {
        // ✅ Client errors (4xx)
        BAD_REQUEST,                // 400
        UNAUTHORIZED,              // 401
        FORBIDDEN,                 // 403
        NOT_FOUND,                 // 404
        METHOD_NOT_ALLOWED,       // 405
        REQUEST_TIMEOUT,          // 408
        CONFLICT,                 // 409
        GONE,                     // 410
        LENGTH_REQUIRED,          // 411
        PAYLOAD_TOO_LARGE,        // 413
        UNSUPPORTED_MEDIA_TYPE,   // 415
        TOO_MANY_REQUESTS,        // 429

        // ✅ Server errors (5xx)
        SERVER_ERROR,             // 500
        BAD_GATEWAY,              // 502
        SERVICE_UNAVAILABLE,      // 503
        GATEWAY_TIMEOUT,          // 504

        // ✅ Custom / app-defined
        NO_INTERNET,              // Khi không có mạng
        SERIALIZATION,            // Gặp lỗi khi parse JSON
        UNKNOWN                   // Trường hợp không xác định
    }

    enum class Local : DataError {
        DISK_FULL                 // Hết dung lượng lưu trữ
    }
}