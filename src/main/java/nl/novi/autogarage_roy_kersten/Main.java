package nl.novi.autogarage_roy_kersten;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
/**
    public static void main(String[] args) throws IOException {
        // write your code here

        Customer customer = new Customer(1,"Roy", "Kersten", "061234567","roy.kersten@gmail.nl");

         /**Als ik eerst een service instantieer dan kan ik geen serviceLine als attribuut invullen
         * Als ik eerst een serviceLine instantieer dan kan ik geen Service als attribuut invullen
         * nu opgelost door een tweede constructor aan te maken in Isnpection zonder attrubuut ServiceLine


        Inspection createInspection = new Inspection(10, "2021", "voltooid", customer, "geen issues");
        ServiceLine serviceLine = new ServiceLine(20, 1, 45, createInspection);
        Inspection inspection = new Inspection(10, "2021", "voltooid", customer, serviceLine,"geen issues");
        //Invoice inspectionInvoice = new InspectionInvoice(40, customer,inspection, serviceLine);      //indien inspectionInvoice rechstreeks communiceerd met serviceLine
        Invoice inspectionInvoice = new InspectionInvoice(40, customer,inspection);


        //Add carPapers via pathName which is declared as String

        Car car = new Car(1, "VW", "Polo", "2021", "56-HTJ-8", customer);
        String pathName="/Users/roykersten/Documents/Persoonlijk/NOVI/Eindopdracht/Integrale eindopdracht Backend 1.02 (1).pdf";
        car.addCarPapers(pathName);


        System.out.println(inspectionInvoice.getIdInvoice());
        System.out.println(inspectionInvoice.getCustomer());
        System.out.println(inspectionInvoice);

    }
 **/
}
