package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.Administrator;
import sadminotaur.hospital.dao.AdministratorDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class AdministratorDaoImpl extends DaoImplBase implements AdministratorDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdministratorDaoImpl.class);

    public AdministratorDaoImpl() {
    }

    @Override
    public Administrator add(Administrator administrator) throws ServiceException {
        LOGGER.debug("DAO insert administrator {}", administrator);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).add(administrator);
                getAdministratorMapper(sqlSession).insert(administrator);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert administrator {} {}", administrator, ex);
                sqlSession.rollback();
                if (ex.getClass() == PersistenceException.class) {
                    throw new ServiceException(ServiceErrorCode.DUPLICATE_ENTITY);
                }
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return administrator;
    }

    @Override
    public void update(Administrator administrator) throws ServiceException {
        LOGGER.debug("DAO update administrator {}", administrator);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).update(administrator);
                getAdministratorMapper(sqlSession).update(administrator);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update administrator {} {}", administrator, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public Administrator getById(int id) throws ServiceException {
        LOGGER.debug("DAO get administrator by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getAdministratorMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get administrator by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOGGER.debug("DAO delete administrator by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete administrator by id {} {}", id, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public int getCount() throws ServiceException {
        LOGGER.debug("DAO get administrator count");
        try (SqlSession sqlSession = getSession()) {
            return getAdministratorMapper(sqlSession).administratorCount();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get administrator count", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }
}
