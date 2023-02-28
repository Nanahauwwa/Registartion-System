package io.nana.Registration.registeration;

import java.util.List;

public interface RegistrationService {
    List<RegistrationModel> getAllStudent();
    void save(RegistrationDTO student);

    RegistrationModel getById(Long id);
    RegistrationModel getByEmail(String email);

    void deleteViaId(Long id);
}
