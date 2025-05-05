package hung.deptrai.mycomic.core.data.dto

import hung.deptrai.mycomic.feature.search.data.dto.Relationship

data class DTOject<T>(
    val attributes: T,
    val id: String,
    val relationships: List<Relationship>,
    val type: String
)