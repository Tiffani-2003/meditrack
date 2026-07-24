package ec.edu.espe.meditrack.service;

import ec.edu.espe.meditrack.model.Appointment;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.Collections;

public class AppointmentServiceTest {

    @Test
    public void getValidAppointments_debeEmitirSoloLasTresValidas() {

        // Arrange
        AppointmentService service = new AppointmentService();

        // Act
        Flux<Appointment> flujo = service.getValidAppointments();

        // Assert
        StepVerifier.create(flujo)
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    public void getValidAppointments_todasInvalidas_debeEmitirDefault() {

        // Arrange

        Appointment a1 = new Appointment(
                "A1",
                "Juan",
                "Cardiología",
                0.0,
                Arrays.asList("a@a.com")
        );

        Appointment a2 = new Appointment(
                "A2",
                "María",
                "Pediatría",
                20.0,
                Collections.emptyList()
        );

        AppointmentService service =
                new AppointmentService(
                        Arrays.asList(a1, a2)
                );

        // Act

        Flux<Appointment> flujo =
                service.getValidAppointments();

        // Assert

        StepVerifier.create(flujo)

                .expectNextMatches(
                        appointment ->
                                appointment.getId().equals("DEFAULT")
                )

                .verifyComplete();
    }

    @Test
    public void findById_idNoExiste_debeTerminarConError() {

        // Arrange

        AppointmentService service =
                new AppointmentService();

        // Act

        // Assert

        StepVerifier.create(
                        service.findById("A100")
                )

                .expectError(RuntimeException.class)

                .verify();

    }

}