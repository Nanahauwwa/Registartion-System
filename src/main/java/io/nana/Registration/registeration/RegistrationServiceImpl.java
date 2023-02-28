package io.nana.Registration.registeration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository regRepo;

    @Autowired
    private MailSender mailSender;

    public Boolean emailExist(String email){
        return regRepo.existsByEmail(email);
    }
    public void sendRegistrationConfirmationEmail(String email, String lastname) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("no-reply@siteworx.ng");
        message.setTo(email);
        message.setSubject("Successfully Registered For C-STEMP Free Digital Skills Training");
        message.setText("Congrats! " + lastname + ",\n\nYour course has been registered successfully!");

        mailSender.send(message);
    }


    @Override public List<RegistrationModel> getAllStudent() {
        return regRepo.findAll();
    }
    @Override public void save(RegistrationDTO regDTO){
        RegistrationModel regModel = new RegistrationModel();
        regModel.setFirstname(regDTO.getFirstname());
        regModel.setMiddlename(regDTO.getMiddlename());
        regModel.setLastname(regDTO.getLastname());
        regModel.setEmail(regDTO.getEmail());
        regModel.setCity(regDTO.getCity());
        regModel.setStreetAddress(regDTO.getStreetAddress());
        regModel.setGender(regDTO.getGender());
        regModel.setState(regDTO.getState());
        regModel.setCourse(regDTO.getCourse());
        regModel.setPostalCode(regDTO.getPostalCode());
        regModel.setDob(LocalDate.parse(regDTO.getDob(), DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        regModel.setPhoneNo(regDTO.getPhoneNo());

        regRepo.save(regModel);

        sendRegistrationConfirmationEmail(regDTO.email, regDTO.lastname);

    }
    @Override
    public RegistrationModel getByEmail(String email) {
        return regRepo.findByEmail(email);
    }

    @Override public RegistrationModel getById(Long id){
        Optional<RegistrationModel> optional = regRepo.findById(id);
        RegistrationModel registrationModel = null;
        if (optional.isPresent())
            registrationModel = optional.get();
        else
            throw new RuntimeException(
                    "Student not found for id : " +id);
        return registrationModel;
    }

    @Override
    public void deleteViaId(Long id) {
        regRepo.deleteById(id);

    }
    public List<RegistrationModel> getAlldata(){
       List<RegistrationModel> entities = regRepo.findAll();
       return entities;
//        return entities.stream().map(entity -> new RegistrationModel(entity.getFirstname(),entity.getMiddlename(), entity.getLastname(),
//                entity.getDob(), entity.getEmail(),entity.getPostalCode(), entity.getCity(), entity.getStreetAddress(),
//                entity.getCourse(), entity.getGender(), entity.getPhoneNo(), entity.getState())).collect(Collectors.toList());

    }
}
