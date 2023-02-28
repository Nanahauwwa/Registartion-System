package io.nana.Registration.registeration;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationRepository extends JpaRepository<RegistrationModel, Long> {
RegistrationModel findByEmail(String email);
Boolean existsByEmail(String email);

}
