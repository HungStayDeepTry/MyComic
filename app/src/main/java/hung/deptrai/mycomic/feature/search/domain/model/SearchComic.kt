package hung.deptrai.mycomic.feature.search.domain.model

data class SearchComic(
    val id: String,
    val title: String,
    val description: String,
    val imageUrl: String,
    val status: String,
    val tags: List<String>,
    val authors: List<String>?,        // Danh sách tác giả
    val coverArtUrl: String?,
    val contentRating: String?,// URL ảnh bìa
    val rating: Double?,              // Rating chung
    val views: Int?,                  // Số lượt xem
    val chapters: Int?,               // Số chương
    val follows: Int? = null,         // Số lượt theo dõi
    val averageRating: Double? = null, // Điểm đánh giá trung bình
    val bayesianRating: Double? = null, // Điểm đánh giá bayesian
    val commentsCount: Int? = null    // Số lượt bình luận
)