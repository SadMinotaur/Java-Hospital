package sadminotaur.hospital.mappersdto;

import sadminotaur.hospital.model.Patient;
import sadminotaur.hospital.requests.PatientRegisterDTO;
import sadminotaur.hospital.response.PatientResponse;
import org.mapstruct.Mapper;

@Mapper
public interface PatientDTOMapper {

    Patient patientDTOtoPatient(PatientRegisterDTO patientRegisterDTO);

    PatientRegisterDTO patientToPatientDto(Patient patient);

    PatientResponse patientToPatientResponse(Patient patient);

    PatientResponse patientDTOToPatientResponse(PatientRegisterDTO patient);
}
