package io.nana.Registration.registeration;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "student_detail")
public class RegistrationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column( name = "firstname" , nullable = false)
    private String firstname;

    @Column( name = "middlename" , nullable = false)
    private String middlename;
    @Column( name = "lastname" , nullable = false)
    private String lastname;
    @Column( name = "email" , nullable = false , unique = true)
    private String email;
    @Column( name = "dob" , nullable = false)
    private LocalDate dob;
    @Column( name = "street_address" , nullable = false)
    private String streetAddress;
    @Column( name = "gender" , nullable = false)
    private String gender;
    @Column( name = "city" , nullable = false)
    private String city;
    @Column( name = "state" , nullable = false)
    private String state;
    @Column( name = "postal_code" , nullable = false)
    private String postalCode;
    @Column( name = "phone_no" , nullable = false)
    private String phoneNo;
    @Column( name = "course" , nullable = false)
    private String course;


    public RegistrationModel(String firstname, String middlename, String lastname, LocalDate dob, String email, String postalCode, String city, String streetAddress, String course, String gender, String phoneNo, String state) {
    }
}
