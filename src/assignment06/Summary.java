package assignment06;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * @author Nikita Neveditsin
 * @author Gianni Belizaire (A00487024)
 */
public class Summary {

    public static final String URL = "https://people.math.sc.edu/"
            + "Burkardt/datasets/csv/biostats.csv";

    /**
     * Downloads a file from the specified URL and saves it to the given output
     * file.
     *
     * @param link The URL of the file to be downloaded.
     * @param outFile The path where the downloaded file should be saved.
     * @throws IOException If an I/O error occurs while reading from the URL or
     * writing to the file.
     * @throws InterruptedException If the operation is interrupted while
     * waiting for the HTTP response.
     */
    public static void downloadFile(String link, String outFile)
            throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(link))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            try (PrintWriter outputStream = new PrintWriter(outFile)) {
                outputStream.print(response.body());
            }
        } else {
            throw new IOException("Failed to download file, "
                    + "HTTP response code: " + response.statusCode());
        }
    }

    /**
     * Reads lines from a file and returns an ArrayList of type Person
     *
     * @param path The path of the file to be read
     * @return ArrayList of type Person containing the people in file
     */
    public static ArrayList<Person> readFile(String path) {
        ArrayList<Person> people = new ArrayList<>();

        try {
            File file = new File(path);
            Scanner fileReader = new Scanner(file);
            boolean first = true;

            while (fileReader.hasNextLine()) {
                if (first) {
                    fileReader.nextLine();
                    first = false;
                    continue;
                }

                String line = fileReader.nextLine();
                String[] parts = line.split(",");

                if (parts.length >= 5) {
                    String name = parts[0].trim().substring(1, parts[0].length() - 1);
                    String sex = parts[1].trim().replaceAll("^\"|\"$", "");
                    int age = Integer.parseInt(parts[2].trim());
                    int height = Integer.parseInt(parts[3].trim());
                    int weight = Integer.parseInt(parts[4].trim());

                    people.add(new Person(name, sex, age, height, weight));
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Invalid file path " + path);
            System.exit(0);
        }

        return people;
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Person> people;

        switch (args[0]) {
            case "download": {
                try {
                    downloadFile(URL, args[1]);
                } catch (InterruptedException ex) {
                    System.out.println(ex);
                }
            }

            System.out.println("Downloaded successully: " + args[1]);
            break;

            case "summary":
                people = readFile(args[1]);

                int total = people.size();

                Collections.sort(people, Person.BY_AGE);

                Person youngest = people.get(0);
                Person oldest = people.get(total - 1);

                int totalAge = 0;

                for (Person person : people) {
                    totalAge += person.getAge();
                }

                double averageAge = (double) totalAge / total;

                Collections.sort(people, Person.BY_HEIGHT);

                Person tallest = people.get(total - 1);
                Person shortestFemale = null;

                for (Person person : people) {
                    if (person.getSex().equalsIgnoreCase("F")) {
                        if (shortestFemale == null || person.getHeight() < shortestFemale.getHeight()) {
                            shortestFemale = person;
                        }
                    }
                }

                System.out.println("Summary:");
                System.out.println("Total number of records: " + total);
                System.out.println("Youngest person: " + youngest.getName() + " (" + youngest.getAge() + " years old)");
                System.out.println("Oldest person: " + oldest.getName() + " (" + oldest.getAge() + " years old)");
                System.out.format("Average age: %.2f\n", averageAge);
                System.out.println("Tallest person: " + tallest.getName() + " (" + tallest.getHeight() + " inches)");
                System.out.println("Shortest female: " + (shortestFemale != null ? shortestFemale.getName() : "N/A"));

                break;

            case "print":
                people = readFile(args[1]);

                if (args.length > 2) {
                    String sortBy = args[2];

                    if (sortBy.equalsIgnoreCase("n")) {
                        Collections.sort(people, Person.BY_NAME);
                    } else if (sortBy.equalsIgnoreCase("a")) {
                        Collections.sort(people, Person.BY_AGE);
                    } else if (sortBy.equalsIgnoreCase("h")) {
                        Collections.sort(people, Person.BY_HEIGHT);
                    } else {
                        System.out.println("Invalid sort option");
                        System.exit(0);
                    }
                }

                if (args.length > 3 && Boolean.parseBoolean(args[3].toLowerCase())) {
                    Collections.reverse(people);
                }

                for (Person person : people) {
                    String name = person.getName();
                    String sex = person.getSex();
                    int age = person.getAge();
                    int height = person.getHeight();
                    int weight = person.getWeight();

                    String personLine = String.format("%-10s %-6s %6d %6d %6d%n", name, sex, age, height, weight);
                    System.out.print(personLine);
                }

                break;
            default:
                System.out.println("Unknown command");
        }
    }
}
