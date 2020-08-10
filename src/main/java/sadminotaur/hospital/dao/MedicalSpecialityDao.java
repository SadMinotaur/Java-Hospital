package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.MedicalSpeciality;
import sadminotaur.hospital.serviceexception.ServiceException;

public interface MedicalSpecialityDao {

    MedicalSpeciality getById(int id) throws ServiceException;

    MedicalSpeciality getByName(String speciality) throws ServiceException;
}
