package ec.edu.espe.meditrack.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Clase totalmente inmutable.
 * No posee setters.
 * Todos los atributos son final.
 */
public final class Appointment {

    private final String id;
    private final String patientName;
    private final String specialty;
    private final Double costUsd;
    private final List<String> notifyEmails;

    public Appointment(String id,
                       String patientName,
                       String specialty,
                       Double costUsd,
                       List<String> notifyEmails) {

        this.id = id;
        this.patientName = patientName;
        this.specialty = specialty;
        this.costUsd = costUsd;

        /*
         Copia defensiva.
         Se crea una nueva lista para evitar que el objeto original
         pueda modificar el estado interno.
        */
        this.notifyEmails = new ArrayList<>(notifyEmails);
    }

    public String getId() {
        return id;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getSpecialty() {
        return specialty;
    }

    public Double getCostUsd() {
        return costUsd;
    }

    /*
      El getter devuelve una lista de solo lectura.
      Nadie podrá modificar el contenido interno.
     */
    public List<String> getNotifyEmails() {
        return Collections.unmodifiableList(
                new ArrayList<>(notifyEmails)
        );
    }
}