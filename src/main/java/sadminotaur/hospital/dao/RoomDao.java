package sadminotaur.hospital.dao;

import sadminotaur.hospital.model.Room;
import sadminotaur.hospital.serviceexception.ServiceException;

public interface RoomDao {
    Room getById(int id) throws ServiceException;

    Room getByName(String name) throws ServiceException;
}
