package sadminotaur.hospital.daoimpl;

import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.dao.SessionDao;
import sadminotaur.hospital.serviceexception.ServiceErrorCode;
import sadminotaur.hospital.serviceexception.ServiceException;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SessionDaoImpl extends DaoImplBase implements SessionDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessionDaoImpl.class);

    public SessionDaoImpl() {
    }

    @Override
    public Session add(Session session) throws ServiceException {
        LOGGER.debug("DAO insert session {}", session);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).add(session);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't insert session {} {}", session, ex);
                sqlSession.rollback();
                if (ex.getClass() == PersistenceException.class) {
                    throw new ServiceException(ServiceErrorCode.DUPLICATE_ENTITY);
                }
                throw new ServiceException(ServiceErrorCode.DATABASE_INSERT_ERROR);
            }
            sqlSession.commit();
        }
        return session;
    }

    @Override
    public Session getByCookie(String cookie) throws ServiceException {
        LOGGER.debug("DAO get session by cookie {}", cookie);
        try (SqlSession sqlSession = getSession()) {
            return getSessionMapper(sqlSession).getByCookie(cookie);
        } catch (RuntimeException ex) {
            LOGGER.info("Can't get session by cookie {} {}", cookie, ex);
            throw new ServiceException(ServiceErrorCode.DATABASE_GET_ERROR);
        }
    }

    @Override
    public void deleteByCookie(String cookie) throws ServiceException {
        LOGGER.debug("DAO delete session by user id {}", cookie);
        try (SqlSession sqlSession = getSession()) {
            try {
                getSessionMapper(sqlSession).deleteByCookie(cookie);
            } catch (RuntimeException ex) {
                LOGGER.info("Can't delete session by user id {} {}", cookie, ex);
                sqlSession.rollback();
                throw new ServiceException(ServiceErrorCode.DATABASE_DELETE_ERROR);
            }
            sqlSession.commit();
        }
    }
}
