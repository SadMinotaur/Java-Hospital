package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.Patient;
import sadminotaur.hospital.serviceexception.ServiceException;

public interface PatientDao {
    Patient add(Patient patient) throws ServiceException;

    void update(Patient patient) throws ServiceException;

    Patient getById(int id) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    int getCount() throws ServiceException;
}
