package ec.edu.espe.meditrack.service;

import ec.edu.espe.meditrack.model.Appointment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Collections;

@Service
public class AppointmentService {

    /**
     * Devuelve todas las citas en memoria.
     * Se utiliza Flux.just() porque trabajamos con una secuencia de datos
     * sin necesidad de una base de datos.
     */
    private Flux<Appointment> getAppointments() {

        return Flux.just(

                new Appointment(
                        "A1",
                        "Juan Pérez",
                        "Cardiología",
                        50.0,
                        Arrays.asList("juan@gmail.com")
                ),

                new Appointment(
                        "A2",
                        "María López",
                        "Pediatría",
                        40.0,
                        Arrays.asList("maria@gmail.com")
                ),

                // Inválida (costo = 0)

                new Appointment(
                        "A3",
                        "Carlos Ruiz",
                        "Neurología",
                        0.0,
                        Arrays.asList("carlos@gmail.com")
                ),

                // Inválida (sin correos)

                new Appointment(
                        "A4",
                        "Ana Torres",
                        "Dermatología",
                        35.0,
                        Collections.emptyList()
                ),

                new Appointment(
                        "A5",
                        "Pedro Sánchez",
                        "Traumatología",
                        60.0,
                        Arrays.asList("pedro@gmail.com")
                )

        );
    }

    /**
     * Devuelve únicamente las citas válidas.
     */
    public Flux<Appointment> getValidAppointments() {

        return getAppointments()

                /*
                 filter()
                 Permite dejar pasar únicamente las citas válidas.
                 Una cita es válida cuando:
                 - costUsd > 0
                 - notifyEmails no está vacío
                 */

                .filter(appointment ->
                        appointment.getCostUsd() > 0 &&
                                !appointment.getNotifyEmails().isEmpty()
                )

                /*
                 map()
                 Transforma cada objeto emitido.
                 En este caso convertimos la especialidad a MAYÚSCULAS.
                 */

                .map(appointment ->

                        new Appointment(

                                appointment.getId(),

                                appointment.getPatientName(),

                                appointment.getSpecialty().toUpperCase(),

                                appointment.getCostUsd(),

                                appointment.getNotifyEmails()

                        )

                )

                /*
                 defaultIfEmpty()
                 Si después del filtro no queda ninguna cita,
                 Reactor emitirá una cita genérica.
                 */

                .defaultIfEmpty(

                        new Appointment(

                                "DEFAULT",

                                "Sin pacientes",

                                "GENERAL",

                                1.0,

                                Arrays.asList("default@meditrack.com")

                        )

                );

    }

    /**
     * Busca una cita por su identificador.
     */
    public Mono<Appointment> findById(String id) {

        return getAppointments()

                .filter(appointment -> appointment.getId().equals(id))

                .next()

                /*
                 switchIfEmpty()
                 Si la cita no existe,
                 Reactor genera un error sin bloquear el flujo.
                 */

                .switchIfEmpty(

                        Mono.error(

                                new RuntimeException(
                                        "No existe la cita con id: " + id
                                )

                        )

                );

    }

}
