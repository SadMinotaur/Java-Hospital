package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.Commission;
import sadminotaur.hospital.model.Doctor;
import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.serviceexception.ServiceException;

import java.util.List;

public interface CommissionDao {

    Commission add(Commission commission, List<Doctor> doctors) throws ServiceException;

    Commission getById(int id) throws ServiceException;

    Commission getByTicket(String ticket) throws ServiceException;

    List<Commission> getByPatient(int patient) throws ServiceException;

    void deleteByTicket(String ticket, List<TimeSlot> timeSlots) throws ServiceException;
}
