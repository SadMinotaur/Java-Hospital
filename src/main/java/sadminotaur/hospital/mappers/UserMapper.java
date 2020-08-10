package sadminotaur.hospital.mappers;

import sadminotaur.hospital.requests.LoginRequest;
import sadminotaur.hospital.model.User;
import org.apache.ibatis.annotations.*;

public interface UserMapper {

    @Insert("INSERT INTO user (firstname, lastname, patronymic, login, password, userType) VALUES " +
            "( #{user.firstname}, #{user.lastname}, #{user.patronymic}, #{user.login}, #{user.password}, #{user.userType})")
    @Options(useGeneratedKeys = true, keyProperty = "user.id")
    Integer add(@Param("user") User user);

    @Select("SELECT id, firstname, lastname, patronymic, login, password, userType FROM user " +
            "WHERE id = #{id}")
    User getById(int id);

    @Select("SELECT id, firstname, lastname, patronymic, login, password , userType FROM user " +
            "WHERE LOWER(login) LIKE #{login} AND password = #{password}")
    User getByLogin(LoginRequest loginRequest);

    @Update("UPDATE user SET " +
            "firstname = #{firstname}, " +
            "lastname = #{lastname}, " +
            "patronymic = #{patronymic}, " +
            "password = #{password}, " +
            "userType = #{userType}" +
            " WHERE id = #{id} ")
    void update(User user);

    @Delete("DELETE FROM user WHERE id > 1")
    void deleteAll();

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteById(int id);

    @Select("SELECT COUNT(*) FROM user")
    int userCount();
}
