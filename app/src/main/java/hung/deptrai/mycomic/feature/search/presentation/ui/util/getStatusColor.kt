package hung.deptrai.mycomic.feature.search.presentation.ui.util

import androidx.compose.ui.graphics.Color

fun getStatusColor(status: String): Color {
    return when (status.lowercase()) {
        "ongoing" -> Color(0xFF4CAF50)    // Màu xanh lá cây
        "completed" -> Color(0xFF2196F3)  // Màu xanh dương
        "hiatus" -> Color(0xFFFFC107)     // Màu vàng
        "cancelled" -> Color(0xFFF44336)  // Màu đỏ
        "dropped" -> Color(0xFF9E9E9E)    // Màu xám
        else -> Color.Gray                // Màu mặc định
    }
}