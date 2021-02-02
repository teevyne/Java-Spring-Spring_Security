package com.wise.multidb;

import com.wise.multidb.admission.Admission;
import com.wise.multidb.admission.AdmissionRepository;
import com.wise.multidb.appointment.Appointment;
import com.wise.multidb.appointment.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@RestController
public class MultidbApplication {

    @Autowired
    private AdmissionRepository admissionRepository;

    @Autowired
    private AppointmentRepository appointmentRepository;

    /******************************************************************************
     * ENDPOINTS FOR APPOINTMENTS SERVICE
     ******************************************************************************/
    @PostConstruct
    public void addAppointmentsData() {
        appointmentRepository.saveAll(Stream.of(
                new Appointment(101, "Teh first appointment ever"),
                new Appointment(102, "Appointment with a Dentist"))
                .collect(Collectors.toList()));
    }
    @GetMapping("/getAppointments")
    public List<Appointment> getAppointments(){

        return appointmentRepository.findAll();
    }

    /******************************************************************************
     * ENDPOINTS FOR ADMISSIONS SERVICE
     ******************************************************************************/
    @PostConstruct
    public void addAdmissionsData() {
        admissionRepository.saveAll(Stream.of(
                new Admission(1, "Faculty level admission"),
                new Admission(2, "Department level admission"))
                .collect(Collectors.toList()));
    }
    @GetMapping("/getAdmissions")
    public List<Admission> getAdmissions(){

        return admissionRepository.findAll();
    }

    public static void main(String[] args) {
        SpringApplication.run(MultidbApplication.class, args);
    }
}