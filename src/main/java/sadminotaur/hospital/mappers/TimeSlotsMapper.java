package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.TimeSlot;
import sadminotaur.hospital.model.Patient;
import sadminotaur.hospital.model.enums.TimeSlotState;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalTime;
import java.util.List;

public interface TimeSlotsMapper {

    @Insert("INSERT INTO timeSlot (ticket, idSchedule, timeStart, timeEnd, state) VALUES " +
            "(#{timeSlot.ticket}, #{schedule}, #{timeSlot.timeStart}, " +
            "#{timeSlot.timeEnd}, #{timeSlot.timeSlotState})")
    Integer addWithoutPatient(@Param("timeSlot") TimeSlot timeSlot, @Param("schedule") int schedule);

    @Update("UPDATE timeSlot SET " +
            "ticket = #{ticket}, " +
            "idPatient = #{patient.id}," +
            "timeStart = #{timeStart}," +
            "timeEnd = #{timeEnd}," +
            "state = #{timeSlotState} " +
            "WHERE ticket = #{ticket}")
    void update(TimeSlot timeSlot);

    @Update("UPDATE timeSlot SET " +
            "state = #{state} " +
            "WHERE " +
            "idSchedule = #{schedule} AND " +
            "(#{timeStart} BETWEEN timeStart AND timeEnd OR " +
            "#{timeEnd} BETWEEN timeStart AND timeEnd)")
    int setSlotsState(@Param("schedule") int schedule,
                      @Param("timeStart") LocalTime timeStart,
                      @Param("timeEnd") LocalTime timeEnd,
                      @Param("state") TimeSlotState state
    );

    @Select("SELECT ticket, idPatient, timeStart, timeEnd, state FROM timeSlot WHERE ticket = #{ticket}")
    @Results({
            @Result(property = "ticket", column = "ticket"),
            @Result(property = "patient", column = "idPatient", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mappers.PatientMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "timeStart", column = "timeStart"),
            @Result(property = "timeEnd", column = "timeEnd"),
            @Result(property = "timeSlotState", column = "state"),
    })
    TimeSlot getByTicket(String ticket);

    @Select("SELECT ticket, idPatient, timeStart, timeEnd, state FROM timeSlot WHERE idPatient = #{id}")
    @Results({
            @Result(property = "ticket", column = "ticket"),
            @Result(property = "timeStart", column = "timeStart"),
            @Result(property = "patient", column = "idPatient", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mappers.PatientMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "timeEnd", column = "timeEnd"),
            @Result(property = "timeSlotState", column = "state"),
    })
    List<TimeSlot> getByPatientId(int id);

    @Select("SELECT ticket, idPatient, timeStart, timeEnd, state FROM timeSlot WHERE idSchedule = #{schedule} ORDER BY timeStart")
    @Results({
            @Result(property = "ticket", column = "ticket"),
            @Result(property = "patient", column = "idPatient", javaType = Patient.class,
                    one = @One(select = "net.thumbtack.school.hospital.mappers.PatientMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "timeStart", column = "timeStart"),
            @Result(property = "timeEnd", column = "timeEnd"),
            @Result(property = "timeSlotState", column = "state"),
    })
    List<TimeSlot> getByScheduleId(int schedule);

    @Delete("DELETE FROM timeSlot WHERE ticket = #{ticket}")
    void deleteByTicket(String ticket);

    @Delete("DELETE FROM timeSlot")
    void deleteAll();

    @Select("SELECT COUNT(*) FROM timeSlot WHERE state = 'BUSY' AND idSchedule IN (SELECT id FROM schedule WHERE idDoctor = #{id})")
    int doctorBusyCount(int id);

    @Select("SELECT COUNT(*) FROM timeSlot WHERE state = 'BUSY'")
    int busyCount();

    @Select("SELECT COUNT(*) FROM timeSlot WHERE idPatient = #{id}")
    int patientBusyCount(int id);
}
