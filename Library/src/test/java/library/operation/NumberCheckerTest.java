package library.operation;

import operation.NumberChecker;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class NumberCheckerTest {

    private static NumberChecker instance;

    @BeforeAll
    public static void setup() {
        instance = new NumberChecker();
    }

    @Test
    public void shouldReturnTrueWhenCheckNumberGetNonNegativeInteger() {
        //given
        String number = "12";
        //when
        boolean result = instance.checkNumber(number);
        //then
        Assertions.assertTrue(result, "12 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnFalseWhenCheckNumberGetNonNegativeFloat() {
        //given
        String number = "12.5";
        //when
        boolean result = instance.checkNumber(number);
        //then
        Assertions.assertFalse(result, "12.5 is correct for checkNumber method");
    }

    @Test
    public void shouldReturnFalseWhenCheckNumberGetNegativeInteger() {
        //given
        String number = "-12";
        //when
        boolean result = instance.checkNumber(number);
        //then
        Assertions.assertFalse(result, "-12 is correct for checkNumber method");
    }

    @Test
    public void shouldReturnFalseWhenCheckNumberGetNegativeFloat() {
        //given
        String number = "-12.2";
        //when
        boolean result = instance.checkNumber(number);
        //then
        Assertions.assertFalse(result, "-12.2 is correct for checkNumber method");
    }

    @Test
    public void shouldReturn10WhenCheckGetString10() {
        //given
        String number = "10";
        int expResult = 10;
        //when
        int result = instance.check(number);
        //then
        Assertions.assertEquals(expResult, result, "10 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnNegative1WhenCheckGetStringWithFloat10_1() {
        //given
        String number = "10.1";
        int expResult = -1;
        //when
        int result = instance.check(number);
        //then
        Assertions.assertEquals(expResult, result, "10.1 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnNegative1WhenCheckGetStringWithNegative10() {
        //given
        String number = "-10";
        int expResult = -1;
        //when
        int result = instance.check(number);
        //then
        Assertions.assertEquals(expResult, result, "-10 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnNegative1WhenCheckGetRandomString() {
        //given
        String number = "AF#G";
        int expResult = -1;
        //when
        int result = instance.check(number);
        //then
        Assertions.assertEquals(expResult, result, "AF#G is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturn25WhenCheckBetween1And105ForString25() {
        //given
        String number = "25";
        int expResult = 25;
        int from = 1;
        int to = 105;
        //when
        int result = instance.check(from, to, number);
        //then
        Assertions.assertEquals(expResult, result, "25 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnNegative1WhenCheckBetween1And5ForString6() {
        //given
        String number = "6";
        int expResult = -1;
        int from = 1;
        int to = 5;
        //when
        int result = instance.check(from, to, number);
        //then
        Assertions.assertEquals(expResult, result, "6 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnNegative1WhenCheckBetween1And5ForRandomString() {
        //given
        String number = "AF#G";
        int expResult = -1;
        int from = 1;
        int to = 5;
        //when
        int result = instance.check(from, to, number);
        //then
        Assertions.assertEquals(expResult, result, "AF#G is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturn2WhenCheckHaveListFrom1To5ForString2() {
        //given
        String number = "2";
        int expResult = 2;
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        //when
        int result = instance.check(list, number);
        //then
        Assertions.assertEquals(expResult, result, "2 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnNegative1WhenCheckHaveListFrom1To5ForString0() {
        //given
        String number = "0";
        int expResult = -1;
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        //when
        int result = instance.check(list, number);
        //then
        Assertions.assertEquals(expResult, result, "0 is incorrect for checkNumber method");
    }

    @Test
    public void shouldReturnNegative1WhenCheckHaveListFrom1To5ForRandomString() {
        //given
        String number = "AF#G";
        int expResult = -1;
        List<Integer> list = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));
        //when
        int result = instance.check(list, number);
        //then        
        Assertions.assertEquals(expResult, result, "AF#G is incorrect for checkNumber method");
    }
}
