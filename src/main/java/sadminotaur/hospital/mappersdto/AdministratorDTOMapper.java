package sadminotaur.hospital.mappersdto;

import sadminotaur.hospital.model.Administrator;
import sadminotaur.hospital.requests.AdministratorRegisterDTO;
import sadminotaur.hospital.requests.AdministratorUpdateDTO;
import sadminotaur.hospital.response.AdministratorResponse;
import org.mapstruct.Mapper;

@Mapper
public interface AdministratorDTOMapper {

    AdministratorRegisterDTO adminToAdminDTO(Administrator administrator);

    Administrator adminDTOToAdmin(AdministratorRegisterDTO administratorRegisterDTO);

    AdministratorResponse adminToAdminResponse(Administrator administrator);

    AdministratorResponse adminDTOToAdminResponse(AdministratorRegisterDTO administratorResponse);

    Administrator administratorUpdateDTOtoAdministrator(AdministratorUpdateDTO administratorUpdateDTO);
}
