package hung.deptrai.mycomic.feature.search.presentation.basicSearch.viewmodel

import hung.deptrai.mycomic.R
import hung.deptrai.mycomic.core.domain.exception.DataError
import hung.deptrai.mycomic.core.domain.exception.Error
import hung.deptrai.mycomic.core.domain.exception.QueryError
import hung.deptrai.mycomic.core.domain.wrapper.Result

fun Error.asUiText(): UiText {
    return when (this) {
        DataError.Network.REQUEST_TIMEOUT -> UiText.StringResource(
            R.string.the_request_timed_out
        )

        DataError.Network.TOO_MANY_REQUESTS -> UiText.StringResource(
            R.string.youve_hit_your_rate_limit
        )

        DataError.Network.NO_INTERNET -> UiText.StringResource(
            R.string.no_internet
        )

        DataError.Network.PAYLOAD_TOO_LARGE -> UiText.StringResource(
            R.string.file_too_large
        )

        DataError.Network.SERVER_ERROR -> UiText.StringResource(
            R.string.server_error
        )

        DataError.Network.SERIALIZATION -> UiText.StringResource(
            R.string.error_serialization
        )

        DataError.Network.UNKNOWN -> UiText.StringResource(
            R.string.unknown_error
        )

        DataError.Local.DISK_FULL -> UiText.StringResource(
            R.string.error_disk_full
        )

        // New error cases
        DataError.Network.BAD_REQUEST -> UiText.StringResource(
            R.string.bad_request
        )
        DataError.Network.UNAUTHORIZED -> UiText.StringResource(
            R.string.unauthorized
        )
        DataError.Network.FORBIDDEN -> UiText.StringResource(
            R.string.forbidden
        )
        DataError.Network.NOT_FOUND -> UiText.StringResource(
            R.string.not_found
        )
        DataError.Network.METHOD_NOT_ALLOWED -> UiText.StringResource(
            R.string.method_not_allowed
        )
        DataError.Network.CONFLICT -> UiText.StringResource(
            R.string.conflict
        )
        DataError.Network.GONE -> UiText.StringResource(
            R.string.gone
        )
        DataError.Network.LENGTH_REQUIRED -> UiText.StringResource(
            R.string.length_required
        )
        DataError.Network.UNSUPPORTED_MEDIA_TYPE -> UiText.StringResource(
            R.string.unsupported_media_type
        )
        DataError.Network.BAD_GATEWAY -> UiText.StringResource(
            R.string.bad_gateway
        )
        DataError.Network.SERVICE_UNAVAILABLE -> UiText.StringResource(
            R.string.service_unavailable
        )
        DataError.Network.GATEWAY_TIMEOUT -> UiText.StringResource(
            R.string.gateway_timeout
        )

        QueryError.USER_NOT_LOGGED_IN -> UiText.StringResource(
            R.string.not_logged_in
        )
    }
}

fun Result.Error<*, DataError>.asErrorUiText(): UiText {
    return error.asUiText()
}
fun PresentationError.asUiText(): UiText {
    return when (this) {
        PresentationError.EmptyInput -> UiText.StringResource(R.string.error_empty_search)
        PresentationError.EmptyList -> UiText.StringResource(R.string.empty_list)
    }
}