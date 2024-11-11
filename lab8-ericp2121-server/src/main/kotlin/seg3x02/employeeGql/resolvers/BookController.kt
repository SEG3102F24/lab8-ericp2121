package seg3x02.booksapigraphql.resolvers

import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.stereotype.Controller
import seg3x02.booksapigraphql.entity.Book
import seg3x02.booksapigraphql.repository.BookRepository
import seg3x02.booksapigraphql.repository.AuthorRepository
import java.util.*

@Controller
class BooksController(
    private val bookRepository: BookRepository,
    private val authorRepository: AuthorRepository
) {

    @QueryMapping
    fun books(): List<Book> {
        return bookRepository.findAll()
    }

    @QueryMapping
    fun bookById(@Argument bookId: String): Book? {
        return bookRepository.findById(bookId).orElse(null)
    }

    @QueryMapping
    fun bookByNumber(@Argument bookNumber: Int): Book? {
        return bookRepository.findAll().find { it.bookNumber == bookNumber }
    }

    @MutationMapping
    fun newBook(@Argument createBookInput: CreateBookInput): Book {
        val book = Book(
            createBookInput.bookNumber,
            createBookInput.category,
            createBookInput.title,
            createBookInput.cost,
            createBookInput.year,
            createBookInput.description
        )
        book.bookId = UUID.randomUUID().toString()
        return bookRepository.save(book)
    }

    @MutationMapping
    fun deleteBook(@Argument bookId: String): Boolean {
        return if (bookRepository.existsById(bookId)) {
            bookRepository.deleteById(bookId)
            true
        } else {
            false
        }
    }

    @MutationMapping
    fun updateBook(
        @Argument bookId: String,
        @Argument createBookInput: CreateBookInput
    ): Book? {
        val book = bookRepository.findById(bookId).orElse(null) ?: return null
        book.apply {
            bookNumber = createBookInput.bookNumber
            category = createBookInput.category
            title = createBookInput.title
            cost = createBookInput.cost
            year = createBookInput.year
            description = createBookInput.description
        }
        return bookRepository.save(book)
    }
}
