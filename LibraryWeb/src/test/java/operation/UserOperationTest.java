package operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import operation.table.User;
import operation.table.UserLogin;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserOperationTest {

    private static Set<User> users;
    private static UserOperation instance;

    @BeforeAll
    public static void setup() {
        users = new LinkedHashSet<>(Arrays.asList(new User(1, "name", "surname",new UserLogin("login1","haslo1"))
                , new User(2, "name2", "surname2",new UserLogin("login2","haslo2"))));
        instance = new UserOperation(users);
    }

    @Test
    public void shouldReturnListWithNumber1And2WhenListOfUsersGiven() {
        //given
        List<Integer> expResult = new ArrayList<>(Arrays.asList(1, 2));
        //when
        List<Integer> result = instance.getUsersId();
        //then        
        Assertions.assertEquals(expResult, result, "result is incorrect for getUsersId method");
    }

    @Test
    public void shouldReturnEmptyListWhenEmptyListOfUsersGiven() {
        //given
        List<Integer> expResult = new ArrayList<>();
        UserOperation usersEmpty = new UserOperation(new LinkedHashSet<>());
        //when
        List<Integer> result = usersEmpty.getUsersId();
        //then        
        Assertions.assertEquals(expResult, result, "result is incorrect for getUsersId method");
    }
}
