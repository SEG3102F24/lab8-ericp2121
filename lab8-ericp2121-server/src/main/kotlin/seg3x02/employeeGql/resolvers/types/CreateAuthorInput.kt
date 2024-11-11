package seg3x02.booksapigraphql.resolvers.types

data class CreateAuthorInput(
    val bookNumber: Int,
    val firstName: String,
    val lastName: String
)
