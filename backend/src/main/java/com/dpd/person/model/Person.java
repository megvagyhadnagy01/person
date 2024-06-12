package com.dpd.person.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

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

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Address> addresses;

    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<PhoneNumber> phoneNumbers;
}
