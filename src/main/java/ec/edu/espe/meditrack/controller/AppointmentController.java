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

    /**
     * Inyección de dependencias mediante constructor.
     */
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    /**
     * Devuelve únicamente las citas válidas.
     * El retorno es Flux porque puede emitir varias citas.
     */
    @GetMapping
    public Flux<Appointment> getAppointments() {
        return appointmentService.getValidAppointments();
    }

    /**
     * Busca una cita por su identificador.
     * El retorno es Mono porque solamente puede existir una cita.
     */
    @GetMapping("/{id}")
    public Mono<Appointment> getAppointmentById(@PathVariable String id) {
        return appointmentService.findById(id);
    }
}