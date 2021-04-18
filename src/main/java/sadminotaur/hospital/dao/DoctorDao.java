package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.serviceexception.ServiceException;

import java.util.List;
import java.util.Set;


public interface DoctorDao {
    Doctor add(Doctor doctor) throws ServiceException;

    void update(Doctor doctor) throws ServiceException;

    Doctor getById(int id) throws ServiceException;

    List<Doctor> getDoctors() throws ServiceException;

    List<Doctor> getDoctors(Set<Integer> doctors) throws ServiceException;

    List<Doctor> getDoctors(int speciality) throws ServiceException;

    List<Doctor> getDoctorsByPatientId(int patient) throws ServiceException;

    void deleteById(int id) throws ServiceException;

    int getCount() throws ServiceException;
}
