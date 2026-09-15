package core.basesyntax.service;

import core.basesyntax.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class RegistrationServiceImplTest {


    RegistrationService registrationService = new RegistrationServiceImpl();

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
}