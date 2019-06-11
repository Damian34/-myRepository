package operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import operation.table.Book;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class BookOperationTest {

    private static Set<Book> books;
    private static BookOperation instance;

    @BeforeAll
    public static void setup() {
        books = new LinkedHashSet<>(Arrays.asList(new Book(1, "title", "author", "0-306-40615-2", 2010),
                new Book(2, "title2", "author2", "978-3-16-148410-0", 2012)));
        books = books.stream()
                .map(book -> {
                    if (book.getId() != 1) {
                        book.setBorrowed(true);
                    }
                    return book;
                })
                .collect(Collectors.toSet());
        instance = new BookOperation(books);
    }

    @Test
    public void shouldReturnListWithNumber1WhenListOfBooksGiven() {
        //given
        List<Integer> expResult = new ArrayList<>(Arrays.asList(1));
        //when
        List<Integer> result = instance.getAvailableBookIds();
        //then
        Assertions.assertEquals(expResult, result, "result is incorrect for getAvailableBookIds method");
    }

    @Test
    public void shouldReturnEmptyListWhenEmptyListOfBooksGiven() {
        //given
        List<Integer> expResult = new ArrayList<>();
        BookOperation booksEmpty = new BookOperation(new LinkedHashSet<>());
        //when
        List<Integer> result = booksEmpty.getAvailableBookIds();
        //then
        Assertions.assertEquals(expResult, result, "result is incorrect for getAvailableBookIds method");
    }
}
