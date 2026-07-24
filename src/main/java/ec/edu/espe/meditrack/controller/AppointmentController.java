package ec.edu.espe.meditrack.controller;

import ec.edu.espe.meditrack.model.Appointment;
import ec.edu.espe.meditrack.service.AppointmentService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public Flux<Appointment> getAppointments() {
        return appointmentService.getValidAppointments();
    }

    @GetMapping("/{id}")
    public Mono<Appointment> getAppointmentById(@PathVariable String id) {
        return appointmentService.getValidAppointments()
                .filter(appointment -> appointment.getId().equalsIgnoreCase(id))
                .next()
                .switchIfEmpty(Mono.error(new RuntimeException("No existe la cita con id: " + id)));
    }
}