package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    public static final int MIN_LOGIN_LENGTH = 6;
    public static final int MIN_PASSWORD_LENGTH = 6;
    public static final int MIN_AGE = 18;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null) {
            throw new RegistrationExceptions("Login can't be null");
        }
        if (user.getPassword() == null) {
            throw new RegistrationExceptions("Password can't be null");
        }
        if (user.getAge() == null) {
            throw new RegistrationExceptions("Not valid age: " + user.getAge()
                    + ". Min allowed age is " + MIN_AGE);
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationExceptions("User already exists");
        }

        final User newUser = createNewUser(user);
        return storageDao.add(newUser);
    }

    private static User createNewUser(final User user) {
        User newUser = new User();
        if (user.getLogin().length() < MIN_LOGIN_LENGTH) {
            throw new RegistrationExceptions("Login must be at least 6 characters long");
        }
        newUser.setLogin(user.getLogin());

        if (user.getPassword().length() < MIN_PASSWORD_LENGTH) {
            throw new RegistrationExceptions("Password must be at least 6 characters long");
        }
        newUser.setPassword(user.getPassword());

        if (user.getAge() < MIN_AGE) {
            throw new RegistrationExceptions("User must be at least 18 years old");
        }
        newUser.setAge(user.getAge());
        return newUser;
    }
}
