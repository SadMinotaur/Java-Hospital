package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.serviceexception.ServiceException;

public interface SessionDao {

    Session add(Session session) throws ServiceException;

    Session getByCookie(String cookie) throws ServiceException;

    void deleteByCookie(String cookie) throws ServiceException;
}
