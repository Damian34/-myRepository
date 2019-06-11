package operation;

import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class IsnbCheckerTest {

    private static IsnbChecker instance;

    @BeforeAll
    public static void setup() {
        instance = new IsnbChecker();
    }

    @Test
    public void shouldReturnTrueWhenIsbn10HaveCorrectNumber() {
        //given
        String isbn10 = "0-306-40615-2";
        //when
        boolean result = instance.checkIsbn10(isbn10);
        //then
        Assertions.assertTrue(result, "Isbn is incorrect for CheckIsbn10 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn10HaveIncorrectChecksum() {
        //given
        String isbn = "0-306-40615-9";
        //when
        boolean result = instance.checkIsbn10(isbn);
        //then
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn10 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn10GetNumberWithSizeDifferentThan10() {        
        //given
        String isbn13 = "978-3-16-148410-0";
        //when
        boolean result = instance.checkIsbn10(isbn13);
        //then
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn10 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn10HaveNumbersAndLetters() {                
        //given
        String isbn = "0-A06-F0615-8";
        //when
        boolean result = instance.checkIsbn10(isbn);
        //then
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn10 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn10HaveEmptyString() {              
        //given
        String isbn = "";
        //when
        boolean result = instance.checkIsbn10(isbn);
        //then
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn10 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn10HaveNull() {          
        //given
        String isbn = null;
        //when
        boolean result = instance.checkIsbn10(isbn);
        //then
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn10 method");
    }

    @Test
    public void shouldReturnTrueWhenIsbn13HaveCorrectNumber() {        
        //given
        String isbn13 = "978-3-16-148410-0";
        //when
        boolean result = instance.checkIsbn13(isbn13);
        //then
        Assertions.assertTrue(result, "Isbn is incorrect for CheckIsbn13 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn13HaveIncorrectChecksum() {  
        //given
        String isbn = "978-3-16-148410-6";
        //when
        boolean result = instance.checkIsbn13(isbn);
        //then        
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn13 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn13GetNumberWithSizeDifferentThan13() {
        //given
        String isbn = "0-306-40615-2";
        //when
        boolean result = instance.checkIsbn13(isbn);
        //then        
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn13 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn13HaveNumbersAndLetters() {
        //given
        String isbn = "978-3-A6-148410-6";
        //when
        boolean result = instance.checkIsbn13(isbn);
        //then        
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn13 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn13HaveEmptyString() {
        //given
        String isbn = "";
        //when
        boolean result = instance.checkIsbn13(isbn);
        //then        
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn13 method");
    }

    @Test
    public void shouldReturnFalseWhenIsbn13HaveNull() {
        //given
        String isbn = null;
        //when
        boolean result = instance.checkIsbn13(isbn);
        //then        
        Assertions.assertFalse(result, "Isbn is correct for CheckIsbn13 method");
    }
}
