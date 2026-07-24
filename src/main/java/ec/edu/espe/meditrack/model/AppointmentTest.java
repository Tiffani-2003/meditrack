package ec.edu.espe.meditrack.model;

import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class AppointmentTest {

    @Test
    public void constructor_getters_debeRetornarValoresCorrectos() {
        // Arrange
        List<String> emails = new ArrayList<>();
        emails.add("juan@gmail.com");

        Appointment appointment = new Appointment(
                "A1",
                "Juan Pérez",
                "Cardiología",
                50.0,
                emails
        );

        // Act
        String id = appointment.getId();
        String patient = appointment.getPatientName();
        String specialty = appointment.getSpecialty();
        Double cost = appointment.getCostUsd();

        // Assert
        assertEquals("A1", id);
        assertEquals("Juan Pérez", patient);
        assertEquals("Cardiología", specialty);
        assertEquals(50.0, cost);
    }

    @Test
    public void copiaDefensiva_modificarListaOriginal_noDebeModificarObjeto() {
        // Arrange
        List<String> emails = new ArrayList<>();
        emails.add("juan@gmail.com");

        Appointment appointment = new Appointment(
                "A1",
                "Juan Pérez",
                "Cardiología",
                50.0,
                emails
        );

        // Act
        emails.add("otro@gmail.com");

        // Assert
        assertEquals(1, appointment.getNotifyEmails().size());
        assertNotSame(emails, appointment.getNotifyEmails());
    }
}