package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.Schedule;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleMapper {

    @Insert("INSERT INTO schedule (idDoctor, date, workingHoursStart, workingHoursEnd) VALUES " +
            "(#{doctor}, #{schedule.date}, #{schedule.workingHoursStart}, #{schedule.workingHoursEnd})")
    @Options(useGeneratedKeys = true, keyProperty = "schedule.id")
    Integer add(@Param("schedule") Schedule schedule, @Param("doctor") int doctor);

    @Update("UPDATE schedule SET " +
            "date = #{date}," +
            "workingHoursStart = #{workingHoursStart}," +
            "workingHoursEnd = #{workingHoursEnd}" +
            "WHERE id = #{id}")
    void update(Schedule schedule);

    @Select("SELECT id, date, workingHoursStart, workingHoursEnd FROM schedule WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "workingHoursStart", column = "workingHoursStart"),
            @Result(property = "workingHoursEnd", column = "workingHoursEnd"),
            @Result(property = "timeSlots", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mappers.TimeSlotsMapper.getByScheduleId", fetchType = FetchType.LAZY))
    })
    Schedule getById(int id);

    @Select("SELECT id, date, workingHoursStart, workingHoursEnd FROM schedule WHERE idDoctor = #{idDoctor} ORDER BY date")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "date", column = "date"),
            @Result(property = "workingHoursStart", column = "workingHoursStart"),
            @Result(property = "workingHoursEnd", column = "workingHoursEnd"),
            @Result(property = "timeSlots", column = "id", javaType = List.class,
                    many = @Many(select = "net.thumbtack.school.hospital.mappers.TimeSlotsMapper.getByScheduleId", fetchType = FetchType.LAZY))
    })
    List<Schedule> getByDoctorId(int idDoctor);

    @Delete("DELETE FROM schedule WHERE id = #{id}")
    void deleteById(int id);

    @Delete("DELETE FROM schedule")
    void deleteAll();

    @Delete("DELETE FROM schedule WHERE date >= #{date} AND idDoctor = #{id}")
    void deleteAfterDate(@Param("date") LocalDate date, @Param("id") int id);

    @Delete("DELETE FROM schedule WHERE date >= #{start} AND date <= #{end} AND idDoctor = #{id}")
    void deleteBetweenDates(@Param("start") LocalDate start, @Param("end") LocalDate end, @Param("id") int id);
}
