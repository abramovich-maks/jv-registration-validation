package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) {
        if (user.getLogin() == null || user.getPassword() == null || user.getAge() == null) {
            throw new RegistrationExceptions("User data is incomplete");
        }

        if (storageDao.get(user.getLogin()) != null) {
            throw new RegistrationExceptions("User already exists");
        }

        User newUser = new User();
        if (user.getLogin().length() < 6) {
            throw new RegistrationExceptions("Login must be at least 6 characters long");
        }
        newUser.setLogin(user.getLogin());

        newUser.setPassword(user.getPassword());
        if (user.getPassword().length() < 6) {
            throw new RegistrationExceptions("Password must be at least 6 characters long");
        }

        newUser.setAge(user.getAge());
        if (user.getAge() < 18) {
            throw new RegistrationExceptions("User must be at least 18 years old");
        }
        return storageDao.add(newUser);
    }
}
