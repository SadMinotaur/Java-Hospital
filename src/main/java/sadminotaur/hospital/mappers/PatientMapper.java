package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.Patient;
import org.apache.ibatis.annotations.*;

public interface PatientMapper {

    @Insert("INSERT INTO patient (idUser, phone, address, email) VALUES " +
            "(#{patient.id}, #{patient.phone}, #{patient.address}, #{patient.email})")
    Integer add(@Param("patient") Patient patient);

    @Update("UPDATE patient SET phone = #{phone}, address = #{address}, email = #{email}" +
            " WHERE idUser = #{id} ")
    void update(Patient patient);

    @Delete("DELETE FROM patient WHERE idUser = #{id}")
    void deleteById(int id);

    @Select("SELECT * FROM user LEFT JOIN patient ON user.id = patient.idUser WHERE user.id = #{id}")
    Patient getById(int id);

    @Select("SELECT COUNT(*) FROM patient")
    int patientCount();
}
