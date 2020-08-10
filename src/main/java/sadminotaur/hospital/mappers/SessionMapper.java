package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.Session;
import sadminotaur.hospital.model.User;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

public interface SessionMapper {

    @Insert("INSERT INTO session (cookie, idUser) VALUES " +
            "(#{cookie}, #{user.id})")
    Integer add(Session session);

    @Select("SELECT * FROM session WHERE cookie = #{cookie}")
    @Results({
            @Result(property = "cookie", column = "cookie"),
            @Result(property = "user", column = "idUser", javaType = User.class,
                    one = @One(select = "net.thumbtack.school.hospital.mappers.UserMapper.getById", fetchType = FetchType.LAZY))
    })
    Session getByCookie(String cookie);

    @Delete("DELETE FROM session WHERE cookie = #{cookie}")
    void deleteByCookie(String cookie);

    @Delete("DELETE FROM session")
    void deleteAll();
}
