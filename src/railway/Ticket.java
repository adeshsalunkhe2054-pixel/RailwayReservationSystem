package railway;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Ticket {
        private int pnr;
        private Passenger passenger;
        private Train train;
        private int seatNumber;
        private LocalDateTime bookingTime;
        private boolean cancelled;

        public Ticket(int pnr, Passenger passenger,
                      Train train, int seatNumber) {
            this.pnr = pnr;
            this.passenger = passenger;
            this.train = train;
            this.seatNumber = seatNumber;
            this.bookingTime = LocalDateTime.now();
            this.cancelled = false;
        }

        public int getPnr() {
            return pnr;
        }

        public Train getTrain() {
            return train;
        }

        public int getSeatNumber() {
            return seatNumber;
        }

        public boolean isCancelled() {
            return cancelled;
        }

        public void cancel() {
            cancelled = true;
        }

        public void displayTicket() {
            DateTimeFormatter formatter =
                    DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

            System.out.println("\n========== TICKET DETAILS ==========");
            System.out.println("PNR Number     : " + pnr);
            System.out.println("Passenger Name : " + passenger.getName());
            System.out.println("Passenger Age  : " + passenger.getAge());
            System.out.println("Train Number   : " + train.getTrainNumber());
            System.out.println("Train Name     : " + train.getTrainName());
            System.out.println("Source         : " + train.getSource());
            System.out.println("Destination    : " + train.getDestination());
            System.out.println("Seat Number    : " + seatNumber);
            System.out.printf("Ticket Fare    : Rs. %.2f%n", train.getFare());
            System.out.println("Booking Time   : " +
                    bookingTime.format(formatter));
            System.out.println("Status         : " +
                    (cancelled ? "CANCELLED" : "CONFIRMED"));
            System.out.println("====================================");
        }
}


