package seg3x02.booksapigraphql.resolvers.types

data class CreateBookInput(
    val bookNumber: Int,
    val category: String,
    val title: String,
    val cost: Float,
    val year: String?,
    val description: String?
)
