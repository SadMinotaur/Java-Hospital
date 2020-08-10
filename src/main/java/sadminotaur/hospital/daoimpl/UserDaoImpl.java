package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.model.User;
import sadminotaur.hospital.dao.UserDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserDaoImpl extends DaoImplBase implements UserDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserDaoImpl.class);

    public UserDaoImpl() {
    }

    @Override
    public User add(User user) throws ServiceException {
        LOGGER.debug("DAO insert user {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).add(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert user {} {}", user, ex);
                sqlSession.rollback();
                if (ex.getClass() == PersistenceException.class) {
                    throw new ServiceException(ServiceErrorCode.DUPLICATE_ENTITY);
                }
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return user;
    }

    @Override
    public void update(User user) throws ServiceException {
        LOGGER.debug("DAO update user {}", user);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).update(user);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't update user {} {}", user, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_UPDATE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public User getById(int id) throws ServiceException {
        LOGGER.debug("DAO get user by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getById(id);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get user by id {} {}", id, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public User getByLogin(LoginRequest loginRequest) throws ServiceException {
        LOGGER.debug("DAO get user by login and password {} ", loginRequest);
        loginRequest.setLogin(loginRequest.getLogin().toLowerCase());
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).getByLogin(loginRequest);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get user by login and password {} {}", loginRequest, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteById(int id) throws ServiceException {
        LOGGER.debug("DAO delete patient by Id {}", id);
        try (SqlSession sqlSession = getSession()) {
            try {
                getUserMapper(sqlSession).deleteById(id);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete user by id {} {}", id, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }

    @Override
    public int getUserCount() throws ServiceException {
        LOGGER.debug("DAO get users count");
        try (SqlSession sqlSession = getSession()) {
            return getUserMapper(sqlSession).userCount();
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get users count", ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }
}
