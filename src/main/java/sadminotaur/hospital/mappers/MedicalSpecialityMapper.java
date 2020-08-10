package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.MedicalSpeciality;
import org.apache.ibatis.annotations.*;

public interface MedicalSpecialityMapper {

    @Select("SELECT id, name FROM medicalSpeciality WHERE id = #{id}")
    MedicalSpeciality getById(int id);

    @Select("SELECT id, name FROM medicalSpeciality WHERE name = #{name}")
    MedicalSpeciality getByName(String speciality);

}
