package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.Administrator;
import org.apache.ibatis.annotations.*;

public interface AdministratorMapper {

    @Insert("INSERT INTO administrator (idUser, position) VALUES " +
            "( #{administrator.id}, #{administrator.position})")
    Integer insert(@Param("administrator") Administrator user);

    @Update("UPDATE administrator SET position = #{position} WHERE idUser = #{id} ")
    void update(Administrator administrator);

    @Select("SELECT * FROM user LEFT JOIN administrator ON user.id = administrator.idUser WHERE user.id = #{id}")
    Administrator getById(int id);

    @Delete("DELETE FROM administrator WHERE idUser = #{id}")
    void deleteById(int id);

    @Delete("DELETE FROM administrator")
    void deleteAll();

    @Select("SELECT COUNT(*) FROM administrator")
    int administratorCount();
}
