package railway;

public class Train {
        private int trainNumber;
        private String trainName;
        private String source;
        private String destination;
        private double fare;

        private boolean[] seatsBooked;

        public Train(int trainNumber, String trainName,
                     String source, String destination,
                     int totalSeats, double fare) {

            this.trainNumber = trainNumber;
            this.trainName = trainName;
            this.source = source;
            this.destination = destination;
            this.fare = fare;
            this.seatsBooked = new boolean[totalSeats];
        }

        public int getTrainNumber() {
            return trainNumber;
        }

        public String getTrainName() {
            return trainName;
        }

        public String getSource() {
            return source;
        }

        public String getDestination() {
            return destination;
        }

        public double getFare() {
            return fare;
        }

        public int getTotalSeats() {
            return seatsBooked.length;
        }

        public int getAvailableSeats() {
            int count = 0;

            for (boolean booked : seatsBooked) {
                if (!booked) {
                    count++;
                }
            }

            return count;
        }

        public int bookSeat() {
            for (int i = 0; i < seatsBooked.length; i++) {
                if (!seatsBooked[i]) {
                    seatsBooked[i] = true;
                    return i + 1;
                }
            }

            return -1;
        }

        public boolean cancelSeat(int seatNumber) {
            if (seatNumber < 1 || seatNumber > seatsBooked.length) {
                return false;
            }

            if (!seatsBooked[seatNumber - 1]) {
                return false;
            }

            seatsBooked[seatNumber - 1] = false;
            return true;
        }

        public void displayTrain() {
            System.out.printf(
                    "%-8d %-22s %-15s %-15s Rs.%-9.2f %d/%d%n",
                    trainNumber,
                    trainName,
                    source,
                    destination,
                    fare,
                    getAvailableSeats(),
                    getTotalSeats()
            );
        }
}
