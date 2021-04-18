package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.Room;
import sadminotaur.hospital.dao.RoomDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RoomDaoImpl extends DaoImplBase implements RoomDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoomDaoImpl.class);

    public RoomDaoImpl() {
    }

    @Override
    public Room getById(int id) throws ServiceException {
        LOGGER.debug("DAO get room by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getRoomMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get room by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public Room getByName(String name) throws ServiceException {
        LOGGER.debug("DAO get room by name {}", name);
        try (SqlSession sqlSession = getSession()) {
            return getRoomMapper(sqlSession).getByName(name);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get room by name {} {}", name, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }
}
