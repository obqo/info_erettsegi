package _2019oktober;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class Eutazas {

    private final List<Passanger> passangers = new ArrayList<>();

    private void readFile() {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/java/_2019oktober/utasadat.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                passangers.add(new Passanger(splitLine[0], splitLine[1], splitLine[2], splitLine[3], splitLine[4]));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file!");
        }
    }

    private int countOfInvalidTickets() {
        int counter = 0;
        for (Passanger passanger : passangers) {
            if (isNotValidTicket(passanger)) {
                counter++;
            }
        }
        return counter;
    }

    private boolean isNotValidTicket(Passanger passanger) {
        String validation = passanger.getValidation();
        return validation.equals("0") || (validation.length() == 8 && passanger.getDateTime().substring(0, 8).compareTo(validation) > 0);
    }

    private int getStationWithMaxGetUp() {
        int station = 0;
        for (int i = 1; i < 30; i++) {
            if (getNrOfPassangerByStation()[station] < getNrOfPassangerByStation()[i]) {
                station = i;
            }
        }
        return station;
    }

    private int[] getNrOfPassangerByStation() {
        int[] getOn = new int[30];
        for (Passanger passanger : passangers) {
            getOn[Integer.parseInt(passanger.getStation())]++;
        }
        return getOn;
    }

    private void getNrOfFreeOrDiscountTickets() {
        int free = 0;
        int discount = 0;
        for (Passanger passanger : passangers) {
            if (!isNotValidTicket(passanger)) {
                if ("NYPRVSGYK".contains(passanger.getTicketsType())) {
                    free++;
                }
                if ("TABNYB".contains(passanger.getTicketsType())) {
                    discount++;
                }
            }
        }
        System.out.println("Ingyenesen utazók száma: " + free + " fő");
        System.out.println("A kedvezményesen utazók száma: " + discount + " fő");
    }

    private int napokszama(int e1, int h1, int n1, int e2, int h2, int n2) {
        h1 = (h1 + 9) % 12;
        e1 = e1 - h1 / 10;
        int d1 = 365 * e1 + e1 / 4 - e1 / 100 + e1 / 400 + (h1 * 306 + 5) / 10 + n1 - 1;
        h2 = (h2 + 9) % 12;
        e2 = e2 - h2 / 10;
        int d2 = 365 * e2 + e2 / 4 - e2 / 100 + e2 / 400 + (h2 * 306 + 5) / 10 + n2 - 1;
        return d2 - d1;
    }

    private void saveFile() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of("src/main/java/_2019oktober/figyelmeztetes.txt"))) {
            processPassangers(writer);
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not write file!");
        }
    }

    private void processPassangers(BufferedWriter writer) throws IOException {
        for (Passanger passanger : passangers) {
            String valid = passanger.getValidation();
            if (valid.length() == 8 && !isNotValidTicket(passanger)) {
                int e2 = Integer.parseInt(valid.substring(0, 4));
                int h2 = Integer.parseInt(valid.substring(4, 6));
                int n2 = Integer.parseInt(valid.substring(6, 8));
                String getUp = passanger.getDateTime();
                int e1 = Integer.parseInt(getUp.substring(0, 4));
                int h1 = Integer.parseInt(getUp.substring(4, 6));
                int n1 = Integer.parseInt(getUp.substring(6, 8));
                if (napokszama(e1, h1, n1, e2, h2, n2) <= 3) {
                    writer.write(passanger.getTicketsNr() + " " + e2 + "-" + valid.substring(4, 6) +"-" +valid.substring(6, 8) +"\n");
                }
            }
        }
    }

    public static void main(String[] args) {
        int i = 2;
        //1. feladat
        Eutazas eutazas = new Eutazas();
        eutazas.readFile();

        System.out.println(i++ + ".feladat");
        System.out.println("A buszra " + eutazas.passangers.size() + " utas akart felszállni.");

        System.out.println(i++ + ".feladat");
        System.out.println("A buszra " + eutazas.countOfInvalidTickets() + " utas nem szállhatott fel.");

        System.out.println(i++ + ".feladat");
        int max = eutazas.getStationWithMaxGetUp();
        System.out.println("A legtöbb utas (" + eutazas.getNrOfPassangerByStation()[max] + " fő) a " + max + ". megállóban próbált felszállni.");

        System.out.println(i + ".feladat");
        eutazas.getNrOfFreeOrDiscountTickets();

        //6. feladat    napokszama

        //7. feladat
        eutazas.saveFile();
    }
}

