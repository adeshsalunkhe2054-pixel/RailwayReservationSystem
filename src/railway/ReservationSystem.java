package railway;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class ReservationSystem {
        private ArrayList<Train> trains = new ArrayList<>();
        private HashMap<Integer, Ticket> bookings = new HashMap<>();

        private Scanner sc = new Scanner(System.in);
        private Random random = new Random();

        public ReservationSystem() {
            // Sample trains with 5 seats each for easy testing.
            trains.add(new Train(101, "Deccan Express",
                    "Pune", "Mumbai", 5, 250.00));

            trains.add(new Train(102, "Intercity Express",
                    "Mumbai", "Pune", 5, 250.00));

            trains.add(new Train(103, "Jaipur Express",
                    "Delhi", "Jaipur", 5, 400.00));

            trains.add(new Train(104, "Chennai Express",
                    "Chennai", "Bengaluru", 5, 350.00));
        }

        public void start() {
            int choice;

            do {
                displayMenu();
                choice = readInt("Enter your choice: ");

                switch (choice) {
                    case 1:
                        viewAllTrains();
                        break;

                    case 2:
                        searchTrain();
                        break;

                    case 3:
                        bookTicket();
                        break;

                    case 4:
                        viewTicket();
                        break;

                    case 5:
                        cancelTicket();
                        break;

                    case 6:
                        viewAllBookings();
                        break;

                    case 7:
                        checkSeats();
                        break;

                    case 8:
                        System.out.println("Thank you for using the system!");
                        break;

                    default:
                        System.out.println("Invalid choice. Enter 1 to 8.");
                }

            } while (choice != 8);
        }

        private void displayMenu() {
            System.out.println("\n========================================");
            System.out.println("       RAILWAY RESERVATION SYSTEM");
            System.out.println("========================================");
            System.out.println("1. View All Trains");
            System.out.println("2. Search Train");
            System.out.println("3. Book Ticket");
            System.out.println("4. View Ticket by PNR");
            System.out.println("5. Cancel Ticket");
            System.out.println("6. View All Bookings");
            System.out.println("7. Check Available Seats");
            System.out.println("8. Exit");
            System.out.println("========================================");
        }

        private int readInt(String message) {
            while (true) {
                System.out.print(message);
                String input = sc.nextLine().trim();

                try {
                    return Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid whole number.");
                }
            }
        }

        private String readText(String message) {
            while (true) {
                System.out.print(message);
                String input = sc.nextLine().trim();

                if (!input.isEmpty()) {
                    return input;
                }

                System.out.println("Input cannot be empty.");
            }
        }

        private Train findTrainByNumber(int number) {
            for (Train train : trains) {
                if (train.getTrainNumber() == number) {
                    return train;
                }
            }

            return null;
        }

        private void printTrainHeader() {
            System.out.printf("%-8s %-22s %-15s %-15s %-12s %s%n",
                    "Number", "Train Name", "Source",
                    "Destination", "Fare", "Seats");
            System.out.println(
                    "-------------------------------------------------------------------------------");
        }

        public void viewAllTrains() {
            System.out.println("\nAVAILABLE TRAINS");
            printTrainHeader();

            for (Train train : trains) {
                train.displayTrain();
            }
        }

        public void searchTrain() {
            String source = readText("Enter source: ");
            String destination = readText("Enter destination: ");

            boolean found = false;

            printTrainHeader();

            for (Train train : trains) {
                if (train.getSource().equalsIgnoreCase(source)
                        && train.getDestination()
                        .equalsIgnoreCase(destination)) {
                    train.displayTrain();
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No train found for this route.");
            }
        }

        public void bookTicket() {
            viewAllTrains();

            int trainNumber = readInt("\nEnter train number: ");
            Train train = findTrainByNumber(trainNumber);

            if (train == null) {
                System.out.println("Invalid train number.");
                return;
            }

            if (train.getAvailableSeats() == 0) {
                System.out.println("Sorry, no seats are available.");
                return;
            }

            String name = readText("Enter passenger name: ");
            int age = readInt("Enter passenger age: ");

            if (age < 1 || age > 120) {
                System.out.println("Age must be between 1 and 120.");
                return;
            }

            int seatNumber = train.bookSeat();

            if (seatNumber == -1) {
                System.out.println("Sorry, no seats are available.");
                return;
            }

            int pnr;
            do {
                pnr = 100000 + random.nextInt(900000);
            } while (bookings.containsKey(pnr));

            Passenger passenger = new Passenger(name, age);
            Ticket ticket = new Ticket(pnr, passenger, train, seatNumber);

            bookings.put(pnr, ticket);

            System.out.println("\nTicket booked successfully!");
            ticket.displayTicket();
        }

        public void viewTicket() {
            int pnr = readInt("Enter PNR number: ");

            Ticket ticket = bookings.get(pnr);

            if (ticket == null) {
                System.out.println("Ticket not found.");
            } else {
                ticket.displayTicket();
            }
        }

        public void cancelTicket() {
            int pnr = readInt("Enter PNR number to cancel: ");

            Ticket ticket = bookings.get(pnr);

            if (ticket == null) {
                System.out.println("Ticket not found.");
                return;
            }

            if (ticket.isCancelled()) {
                System.out.println("This ticket is already cancelled.");
                return;
            }

            boolean released = ticket.getTrain()
                    .cancelSeat(ticket.getSeatNumber());

            if (released) {
                ticket.cancel();
                System.out.println("Ticket cancelled successfully.");
                System.out.println("PNR Number: " + pnr);
                System.out.println("Seat " + ticket.getSeatNumber()
                        + " is now available.");
            } else {
                System.out.println("Unable to cancel this ticket.");
            }
        }

        public void viewAllBookings() {
            if (bookings.isEmpty()) {
                System.out.println("No bookings available.");
                return;
            }

            System.out.println("\nALL BOOKINGS");

            for (Map.Entry<Integer, Ticket> entry
                    : bookings.entrySet()) {
                entry.getValue().displayTicket();
            }
        }

        public void checkSeats() {
            int trainNumber = readInt("Enter train number: ");
            Train train = findTrainByNumber(trainNumber);

            if (train == null) {
                System.out.println("Invalid train number.");
                return;
            }

            System.out.println("Train: " + train.getTrainName());
            System.out.println("Total seats: " + train.getTotalSeats());
            System.out.println("Available seats: "
                    + train.getAvailableSeats());
            System.out.println("Booked seats: "
                    + (train.getTotalSeats() - train.getAvailableSeats()));
        }
}


