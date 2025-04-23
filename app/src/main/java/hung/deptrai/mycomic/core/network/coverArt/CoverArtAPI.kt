package hung.deptrai.mycomic.core.network.coverArt

import hung.deptrai.mycomic.feature.search.data.remote.dto.coverArt.CoverArtDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoverArtAPI {
    @GET("cover/{id}")
    suspend fun getCoverArtById(@Path("id") id: String): Response<CoverArtDTO>
}