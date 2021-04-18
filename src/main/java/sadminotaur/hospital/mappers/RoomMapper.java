package sadminotaur.hospital.mappers;

import sadminotaur.hospital.model.Room;
import org.apache.ibatis.annotations.*;

public interface RoomMapper {

    @Select("SELECT id, name FROM room WHERE id = #{id}")
    Room getById(int id);

    @Select("SELECT id, name FROM room WHERE name = #{name}")
    Room getByName(String name);

}
