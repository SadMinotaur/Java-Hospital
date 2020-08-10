package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.Administrator;
import sadminotaur.hospital.serviceexception.ServiceException;

public interface AdministratorDao {

    Administrator add(Administrator administrator) throws ServiceException;

    void update(Administrator administrator) throws ServiceException;

    Administrator getById(int id) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    int getCount() throws ServiceException;
}
