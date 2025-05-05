package hung.deptrai.mycomic.core.domain.mapper

//import hung.deptrai.mycomic.core.domain.model.AuthorEntity
import hung.deptrai.mycomic.core.data.dto.DTOject
import hung.deptrai.mycomic.feature.search.data.dto.author.AuthorAttributes
import hung.deptrai.mycomic.feature.search.domain.model.AuthorSearch

fun AuthorDTOtoAuthorSearch (
    authorDTO: DTOject<AuthorAttributes>
): AuthorSearch {
    val mangaCount = authorDTO.relationships
        .count{
            it.type == "manga"
        }
    return AuthorSearch(
        id = authorDTO.id,
        name = authorDTO.attributes.name ?: "",
        mangaCount = mangaCount
    )
}