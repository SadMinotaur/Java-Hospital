package sadminotaur.hospital.mappers;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;
import sadminotaur.hospital.model.*;

import java.util.List;

public interface CommissionMapper {

    @Insert("INSERT INTO commission (ticket, idPatient, idRoom, date, timeStart, timeEnd) VALUES " +
            "(#{commission.ticket}, #{commission.patient.id}, #{commission.room.id}, #{commission.date}, #{commission.timeStart}, " +
            "#{commission.timeEnd})")
    @Options(useGeneratedKeys = true, keyProperty = "commission.id")
    Integer add(@Param("commission") Commission commission);

    @Insert({"<script>",
            "INSERT INTO doctorCommission (idCom, idDoctor) VALUES ",
            "<foreach item='doctor' index='index' collection='doctors' separator=','>",
            "( #{commission.id}, #{doctor.id} )",
            "</foreach>",
            "</script>"
    })
    Integer addDoctors(@Param("commission") Commission commission, @Param("doctors") List<Doctor> doctor);

    @Select("SELECT * FROM user LEFT JOIN doctor ON user.id = doctor.idUser WHERE id IN (SELECT idDoctor FROM doctorCommission WHERE idCom = #{id})")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "firstname", column = "firstname"),
            @Result(property = "lastname", column = "lastname"),
            @Result(property = "patronymic", column = "patronymic"),
            @Result(property = "login", column = "login"),
            @Result(property = "password", column = "password"),
            @Result(property = "duration", column = "duration"),
            @Result(property = "room", column = "idRoom", javaType = Room.class,
                    one = @One(select = "sadminotaur.hospital.mappers.RoomMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "medicalSpeciality", column = "idSpeciality", javaType = MedicalSpeciality.class,
                    one = @One(select = "sadminotaur.hospital.mappers.MedicalSpecialityMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "schedule", column = "id", javaType = List.class,
                    many = @Many(select = "sadminotaur.hospital.mappers.ScheduleMapper.getByDoctorId", fetchType = FetchType.LAZY))
    })
    List<Doctor> getDoctors(int id);

    @Select("SELECT * FROM commission WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ticket", column = "ticket"),
            @Result(property = "patient", column = "idPatient", javaType = Patient.class,
                    one = @One(select = "sadminotaur.hospital.mappers.PatientMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "room", column = "idRoom", javaType = Room.class,
                    one = @One(select = "sadminotaur.hospital.mappers.RoomMapper.getById")),
            @Result(property = "date", column = "date"),
            @Result(property = "timeStart", column = "timeStart"),
            @Result(property = "timeEnd", column = "timeEnd"),
            @Result(property = "timeslotState", column = "timeslotState"),
            @Result(property = "doctors", column = "id", javaType = List.class,
                    many = @Many(select = "sadminotaur.hospital.mappers.CommissionMapper.getDoctors"))
    })
    Commission getById(int id);

    @Select("SELECT * FROM commission WHERE ticket = #{ticket}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ticket", column = "ticket"),
            @Result(property = "patient", column = "idPatient", javaType = Patient.class,
                    one = @One(select = "sadminotaur.hospital.mappers.PatientMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "room", column = "idRoom", javaType = Room.class,
                    one = @One(select = "sadminotaur.hospital.mappers.RoomMapper.getById")),
            @Result(property = "date", column = "date"),
            @Result(property = "timeStart", column = "timeStart"),
            @Result(property = "timeEnd", column = "timeEnd"),
            @Result(property = "timeslotState", column = "timeslotState"),
            @Result(property = "doctors", column = "id", javaType = List.class,
                    many = @Many(select = "sadminotaur.hospital.mappers.CommissionMapper.getDoctors"))
    })
    Commission getByTicket(String ticket);

    @Select("SELECT * FROM commission WHERE idPatient = #{patient}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "ticket", column = "ticket"),
            @Result(property = "patient", column = "idPatient", javaType = Patient.class,
                    one = @One(select = "sadminotaur.hospital.mappers.PatientMapper.getById", fetchType = FetchType.LAZY)),
            @Result(property = "room", column = "idRoom", javaType = Room.class,
                    one = @One(select = "sadminotaur.hospital.mappers.RoomMapper.getById")),
            @Result(property = "date", column = "date"),
            @Result(property = "timeStart", column = "timeStart"),
            @Result(property = "timeEnd", column = "timeEnd"),
            @Result(property = "timeslotState", column = "timeslotState"),
            @Result(property = "doctors", column = "id", javaType = List.class,
                    many = @Many(select = "sadminotaur.hospital.mappers.CommissionMapper.getDoctors"))
    })
    List<Commission> getByPatient(int patient);

    @Update("UPDATE commission SET " +
            "ticket = #{ticket}, " +
            "idPatient = #{patient.id}, " +
            "idRoom = #{room.id}, " +
            "date = #{date}, " +
            "timeStart = #{timeStart}, " +
            "timeEnd = #{timeEnd} " +
            "WHERE ticket = #{ticket}")
    void update(Commission commission);

    @Delete("DELETE FROM doctorCommission WHERE idCom = #{id}")
    void removeDoctorsById(int id);

    @Delete("DELETE FROM commission WHERE ticket = #{ticket}")
    int deleteByTicket(String ticket);

    @Delete("DELETE FROM commission")
    void deleteAll();
}
