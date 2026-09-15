package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

public class RegistrationServiceImplTest {

    private final RegistrationService registrationService = new RegistrationServiceImpl();

    @Test
    void register_validUser_Ok() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
        User expected = registrationService.register(user);
        assertNotNull(expected);
        assertEquals("validLogin", expected.getLogin());
    }

    @Test
    void register_userExistsInTheStorage_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(20);
        Storage.people.add(user);
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_userLoginLessThanSixCharacters_notOk() {
        User user = new User();
        user.setLogin("zzz");
        user.setPassword("validPassword");
        user.setAge(20);
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_userPasswordLessThanSixCharacters_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("123");
        user.setAge(20);
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_userAgeLess18Years_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        user.setAge(17);
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullAge_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setPassword("validPassword");
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullLogin_notOk() {
        User user = new User();
        user.setPassword("validPassword");
        user.setAge(20);
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_nullPassword_notOk() {
        User user = new User();
        user.setLogin("validLogin");
        user.setAge(20);
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }

    @Test
    void register_createUserWithLoginAndPasswordLengthEqualsSixAndAge18_Ok() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("123456");
        user.setAge(18);
        User expected = registrationService.register(user);
        assertEquals(expected.getLogin(), user.getLogin());
        assertEquals(expected.getPassword(), user.getPassword());
        assertEquals(expected.getAge(), user.getAge());
    }

    @Test
    void register_createUserWithNegativeAge_notOk() {
        User user = new User();
        user.setLogin("abcdef");
        user.setPassword("123456");
        user.setAge(-10);
        assertThrows(RegistrationExceptions.class, () -> registrationService.register(user));
    }
}
