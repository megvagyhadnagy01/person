package com.dpd.person.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
//import javax.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    private String name;

    @Past(message = "Birth date must be in the past")
    private Date birthDate;

    @NotBlank(message = "Birth place is mandatory")
    private String birthPlace;

    @NotBlank(message = "Mother's maiden name is mandatory")
    private String motherMaidenName;

    @NotBlank(message = "Social Security Number is mandatory")
    @Pattern(regexp = "^[0-9]{9}$", message = "Social Security Number must be 9 digits")
    private String socialSecurityNumber;

    @NotBlank(message = "Tax ID is mandatory")
    @Pattern(regexp = "^[0-9]{10}$", message = "Tax ID must be 10 digits")
    private String taxId;

    @Email(message = "Email should be valid")
    @NotBlank(message = "Email is mandatory")
    private String email;


}
