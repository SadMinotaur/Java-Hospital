package sadminotaur.hospital.daoimpl;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Component;
import sadminotaur.hospital.mappers.*;

@Component
public class DaoImplBase {

    public DaoImplBase() {
        if (MyBatisUtils.getSqlSessionFactory() == null) {
            MyBatisUtils.initSqlSessionFactory();
        }
    }

    protected SqlSession getSession() {
        return MyBatisUtils.getSqlSessionFactory().openSession();
    }

    protected SqlSession getSessionBatch() {
        return MyBatisUtils.getSqlSessionFactory().openSession(ExecutorType.BATCH);
    }

    protected AdministratorMapper getAdministratorMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(AdministratorMapper.class);
    }

    protected DoctorMapper getDoctorMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(DoctorMapper.class);
    }

    protected PatientMapper getPatientMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(PatientMapper.class);
    }

    protected RoomMapper getRoomMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(RoomMapper.class);
    }

    protected ScheduleMapper getScheduleMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(ScheduleMapper.class);
    }

    protected MedicalSpecialityMapper getMedicalSpecialityMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(MedicalSpecialityMapper.class);
    }

    protected UserMapper getUserMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(UserMapper.class);
    }

    protected SessionMapper getSessionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(SessionMapper.class);
    }

    protected TimeSlotsMapper getTimeSlotsMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(TimeSlotsMapper.class);
    }

    protected CommissionMapper getCommissionMapper(SqlSession sqlSession) {
        return sqlSession.getMapper(CommissionMapper.class);
    }
}