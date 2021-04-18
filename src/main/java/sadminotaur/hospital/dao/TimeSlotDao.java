package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.model.enums.TimeSlotState;
import sadminotaur.hospital.serviceexception.ServiceException;

import java.util.List;

public interface TimeSlotDao {

    TimeSlot add(TimeSlot timeSlot, int scheduleId) throws ServiceException;

    void batchInsert(List<TimeSlot> timeSlots, int schedule) throws ServiceException;

    void update(TimeSlot timeSlot) throws ServiceException;

    void updateBusySlots(List<TimeSlot> timeSlots, TimeSlotState timeSlotState) throws ServiceException;

    TimeSlot getByTicket(String ticket) throws ServiceException;

    List<TimeSlot> getByPatient(int idPatient) throws ServiceException;

    List<TimeSlot> getAll(int schedule) throws ServiceException;

    int getDoctorTimeslotsCount(int doctorId) throws ServiceException;

    int getBusyCount() throws ServiceException;

    int getPatientBusyCount(int id) throws ServiceException;

    void deleteByTicket(String ticket) throws ServiceException;
}
