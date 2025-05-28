package hung.deptrai.mycomic.core.presentation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import hung.deptrai.mycomic.core.domain.model.Tag

// Ưu tiên sắp xếp
fun getTagPriority(tag: Tag): Int {
    return when (tag.group.lowercase()) {
        "content" -> 1
        "format" -> 2
        "genre" -> 3
        "theme" -> 4
        else -> 5
    }
}

// Màu background cho tag
fun getTagBackgroundColor(tag: Tag): Color? {
    return when (tag.group.lowercase()) {
        "content" -> Color(0xFFE57373) // đỏ
        "format" -> if (tag.name.equals("doujinshi", ignoreCase = true)) Color(0xFFBA68C8) else null
        else -> null
    }
}

// Màu chữ cho tag
@Composable
fun getTagTextColor(background: Color?): Color {
    return if (background != null) Color.White else MaterialTheme.colorScheme.onBackground
}

// Màu cho contentRating
@Composable
fun getContentRatingBackgroundColor(rating: String?): Color? {
    return when (rating?.lowercase()) {
        "erotica", "pornographic" -> Color(0xFFE57373)
        "safe" -> Color(0xFF64B5F6)
        "suggestive" -> Color(0xFFFFC107)
        else -> null
    }
}

@Composable
fun getContentRatingTextColor(rating: String?): Color {
    return if (getContentRatingBackgroundColor(rating) != null) Color.White
    else MaterialTheme.colorScheme.onBackground
}