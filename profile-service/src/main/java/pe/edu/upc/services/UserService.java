package pe.edu.upc.services;

import pe.edu.upc.aggregates.User;
import pe.edu.upc.valueobject.UserInformation;

public interface UserService extends CrudService<User,Long>  {

    UserInformation createdUser(User user);
    User getUserByEmail(String email);

    UserInformation login(User user);

}
