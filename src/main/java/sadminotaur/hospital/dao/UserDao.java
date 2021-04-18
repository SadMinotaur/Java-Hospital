package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.User;
import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.serviceexception.ServiceException;

public interface UserDao {

    User add(User user) throws ServiceException;

    void update(User user) throws ServiceException;

    User getById(int id) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    User getByLogin(LoginRequest loginRequest) throws ServiceException;

    int getUserCount() throws ServiceException;
}
