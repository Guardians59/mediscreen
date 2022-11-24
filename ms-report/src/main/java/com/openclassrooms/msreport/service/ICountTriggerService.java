package com.openclassrooms.msreport.service;

import java.io.IOException;
import java.util.HashMap;

/**
 * L'interface ICountTriggerService est le service de calcul des occurences du diabète.
 * 
 * @author Dylan
 *
 */
public interface ICountTriggerService {

    /**
     * La méthode numberOfTriggerById permet de compter les occurences relevées dans les notes
     * médicales d'un patient via son id.
     * @param patientId l'id du patient.
     * @return int le nombre d'occurence.
     * @throws IOException si une erreur est rencontrée lors du calcul.
     */
    public int numberOfTriggerById(int patientId) throws IOException;

    /**
     * La méthode numberOfTriggerByName permet de compter les occurences relevées dans les notes
     * des patients d'une même famille.
     * @param lastName le nom de famille.
     * @return HasMap avec l'id du patient du patient en key, et le nombre d'occurence en valeur.
     * @throws IOException si une erreur est rencontrée lors du calcul.
     */
    public HashMap<Integer, Integer> numberOfTriggerByName(String lastName) throws IOException;

}
