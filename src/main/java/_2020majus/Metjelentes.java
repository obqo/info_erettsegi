package _2020majus;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Metjelentes {

    private final List<Station> stations = new ArrayList<>();

    private void readFile() {
        try (BufferedReader reader = Files.newBufferedReader(Path.of("src/main/java/_2020majus/tavirathu13.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splitLine = line.split(" ");
                stations.add(new Station(splitLine[0], splitLine[1], splitLine[2], Integer.parseInt(splitLine[3])));
            }
        } catch (IOException ioe) {
            throw new IllegalStateException("Can not read file!");
        }
    }

    private String readFromConsole() {
        System.out.print("Adja meg egy település kódját! Település: ");
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private String getLastTimeByName(String name) {
        for (int i = stations.size() - 1; i >= 0; i--) {
            if (stations.get(i).getName().equals(name)) {
                return stations.get(i).getTime();
            }
        }
        return null;
    }

    private String convertTime(String time) {
        return time.substring(0, 2) + ":" + time.substring(2, 4);
    }

    private List<Station> orderedListByTemp() {
        List<Station> copyList = new ArrayList<>(stations);
        copyList.sort(Comparator.comparingInt(Station::getTemp));
        return copyList;
    }

    private String noWind() {
        StringBuilder sb = new StringBuilder();
        for (Station station : stations) {
            if (station.getWind().equals("00000")) {
                sb.append("\n").append(station.getName()).append(" ").append(convertTime(station.getTime()));
            }
        }
        if (sb.length() == 0) {
            return "Nem volt szélcsend a mérések idején.";
        }
        return sb.toString();
    }

    private Set<String> getNameSet() {
        Set<String> result = new TreeSet<>();
        for (Station item : stations) {
            result.add(item.getName());
        }
        return result;
    }

    private Set<String> getAvgTimeSetByName(String name) {
        Set<String> result = new TreeSet<>();
        for (Station item : stations) {
            if (item.getName().equals(name) && List.of("01", "07", "13", "19").contains(item.getTime().substring(0, 2))) {
                result.add(item.getTime().substring(0, 2));
            }
        }
        return result;
    }

    private List<Integer> getTempByAvgTimeAndName(String name) {
        List<Integer> result = new ArrayList<>();
        for (Station item : stations) {
            if (item.getName().equals(name) && List.of("01", "07", "13", "19").contains(item.getTime().substring(0, 2))) {
                result.add(item.getTemp());
            }
        }
        return result;
    }

    private List<Integer> getTempByName(String name) {
        List<Integer> result = new ArrayList<>();
        for (Station item : stations) {
            if (item.getName().equals(name)) {
                result.add(item.getTemp());
            }
        }
        return result;
    }

    private String getAvgTempByName(String name) {
        String result;
        if (getAvgTimeSetByName(name).size() != 4) {
            result = " NA";
        } else {
            double sum = 0;
            for (int item : getTempByAvgTimeAndName(name)) {
                sum += item;
            }
            sum += 0.5 * getTempByAvgTimeAndName(name).size();
            result = " Középhőmérséklet: " + (int) sum / getTempByAvgTimeAndName(name).size();
        }
        return result;
    }

    private void getTempAvgAndRange() {
        for (String item : getNameSet()) {
            List<Integer> sortedTemp = new ArrayList<>(getTempByName(item));
            int min = Collections.min(sortedTemp);
            int max = Collections.max(sortedTemp);
            String range = "; Hőmérséklet-ingadozás: " + (max - min);
            System.out.println(item + getAvgTempByName(item) + range);
        }
    }

    private void saveFile() {
        for (String name : getNameSet()) {
            try (BufferedWriter writer = Files.newBufferedWriter(Path.of("src/main/java/_2019oktober/" + name +".txt"))) {
                for (Station item : stations) {
                    if (item.getName().equals(name)) {
                        writer.write(convertTime(item.getTime()) + " " + "#".repeat(Integer.parseInt(item.getWind().substring(3,5)))+"\n");
                    }
                }
            } catch (IOException ioe) {
                throw new IllegalStateException("Can not write file!");
            }
        }
    }

    public static void main(String[] args) {
        int i = 2;
        //1. feladat
        Metjelentes metjelentes = new Metjelentes();
        metjelentes.readFile();

        System.out.println(i++ + ".feladat");
        String name = metjelentes.readFromConsole();
        String time = metjelentes.convertTime(metjelentes.getLastTimeByName(name));
        System.out.println("Az utolsó mérési adat a megadott településről " + time + "-kor érkezett.");

        System.out.println(i++ + ".feladat");
        Station minTempStation = metjelentes.orderedListByTemp().get(0);
        Station maxTempStation = metjelentes.orderedListByTemp().get(metjelentes.stations.size() - 1);
        System.out.println("A legalacsonyabb hőmérséklet: " +
                minTempStation.getName() + " " + metjelentes.convertTime(minTempStation.getTime()) + " " +
                minTempStation.getTemp() + " fok.");
        System.out.println("A legmagasabb hőmérséklet: " +
                maxTempStation.getName() + " " + metjelentes.convertTime(maxTempStation.getTime()) + " " +
                maxTempStation.getTemp() + " fok.");

        System.out.print(i++ + ".feladat");
        System.out.println(metjelentes.noWind());

        System.out.println(i + ".feladat");
        metjelentes.getTempAvgAndRange();

        //6.feladat
        metjelentes.saveFile();
    }
}
