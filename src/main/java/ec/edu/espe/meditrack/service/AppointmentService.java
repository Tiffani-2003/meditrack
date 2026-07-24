package ec.edu.espe.meditrack.service;

import ec.edu.espe.meditrack.model.Appointment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class AppointmentService {

    private final List<Appointment> appointments;

    /**
     * Constructor por defecto.
     * Inicializa las 5 citas solicitadas en la práctica.
     */
    public AppointmentService() {
        this.appointments = Arrays.asList(
                new Appointment("A1", "Juan Pérez", "Cardiología", 50.0,
                        Arrays.asList("juan@gmail.com")),

                new Appointment("A2", "María López", "Pediatría", 40.0,
                        Arrays.asList("maria@gmail.com")),

                new Appointment("A3", "Carlos Ruiz", "Neurología", 0.0,
                        Arrays.asList("carlos@gmail.com")),

                new Appointment("A4", "Ana Torres", "Dermatología", 35.0,
                        Collections.emptyList()),

                new Appointment("A5", "Pedro Sánchez", "Traumatología", 60.0,
                        Arrays.asList("pedro@gmail.com"))
        );
    }

    /**
     * Constructor adicional para pruebas unitarias.
     */
    public AppointmentService(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    private Flux<Appointment> getAppointments() {
        return Flux.fromIterable(appointments);
    }

    public Flux<Appointment> getValidAppointments() {

        return getAppointments()

                // Filtra únicamente las citas válidas.
                .filter(a ->
                        a.getCostUsd() > 0 &&
                                !a.getNotifyEmails().isEmpty())

                // Convierte la especialidad a mayúsculas.
                .map(a -> new Appointment(
                        a.getId(),
                        a.getPatientName(),
                        a.getSpecialty().toUpperCase(),
                        a.getCostUsd(),
                        a.getNotifyEmails()))

                // Si no queda ninguna cita válida, emite una genérica.
                .defaultIfEmpty(
                        new Appointment(
                                "DEFAULT",
                                "Sin pacientes",
                                "GENERAL",
                                1.0,
                                Arrays.asList("default@meditrack.com")
                        ));

    }

    public Mono<Appointment> findById(String id) {

        return getAppointments()

                .filter(a -> a.getId().equals(id))

                .next()

                // Si no existe, genera un error reactivo.
                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException(
                                        "No existe la cita con id: " + id
                                )
                        )
                );
    }

}
