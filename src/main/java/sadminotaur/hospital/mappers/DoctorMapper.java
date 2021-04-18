package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.MedicalSpeciality;
import sadminotaur.hospital.model.Room;
import sadminotaur.hospital.model.Doctor;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;
import java.util.Set;

public interface DoctorMapper {

    @Insert("INSERT INTO doctor (idUser, idRoom, duration, idSpeciality) VALUES " +
            "( #{doctor.id}, #{doctor.room.id}, #{doctor.duration}, #{doctor.medicalSpeciality.id})")
    Integer add(@Param("doctor") Doctor doctor);

    @Update("UPDATE doctor SET idUser = #{id}, idRoom = #{room.id}, idSpeciality = #{medicalSpeciality.id}, duration = #{duration} WHERE idUser = #{id} ")
    void update(Doctor doctor);

    @Select("SELECT * FROM user LEFT JOIN doctor ON user.id = doctor.idUser WHERE user.id = #{id}")
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
    Doctor getById(int id);

    @Delete("DELETE FROM doctor WHERE idUser = #{id}")
    void deleteById(int id);

    @Delete("DELETE FROM doctor")
    void deleteAll();

    @Select("SELECT * FROM doctor LEFT JOIN user ON user.id = doctor.idUser")
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
    List<Doctor> getDoctors();

    @Select("SELECT * FROM user LEFT JOIN doctor ON user.id = doctor.idUser WHERE user.id IN " +
            "( SELECT idDoctor FROM schedule WHERE id IN ( SELECT idSchedule FROM timeSlot WHERE idPatient = #{patientId}))")
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
    List<Doctor> getDoctorsByPatient(int patientId);

    @Select({"<script>",
            "SELECT * FROM doctor LEFT JOIN user ON user.id = doctor.idUser ",
            "WHERE idUser IN",
            "<foreach item='doctor' index='index' collection='list'",
            "open='(' separator=',' close=')'>",
            "#{doctor}",
            "</foreach>",
            "</script>"
    })
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
    List<Doctor> getDoctorsInRange(@Param("list") Set<Integer> doctorsIds);

    @Select("SELECT * FROM doctor INNER JOIN user ON user.id = doctor.idUser WHERE doctor.idSpeciality = #{idSpeciality}")
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
    List<Doctor> getDoctorsBySpeciality(int speciality);

    @Select("SELECT COUNT(*) FROM doctor")
    int doctorCount();
}
