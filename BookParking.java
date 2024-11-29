import java.util.Scanner;

public class BookParking {
    private static final int NUM_MALLS = 2;
    private static final int NUM_FLOORS = 2;
    private static final int NUM_SLOTS = 6;
    private static final Scanner scanner = new Scanner(System.in);

    private static boolean[][][] parkingStatus = new boolean[NUM_MALLS][NUM_FLOORS][NUM_SLOTS];
    private static String[][][] parkedCars = new String[NUM_MALLS][NUM_FLOORS][NUM_SLOTS];

    private static final String[] MALLS = {
            "Mall Malang Town Square",
            "Mall Of Garden Malang"
    };

    public static void main(String[] args) {
        while (true) {
            int option = displayMainMenu();

            switch (option) {
                case 1:
                    performCheckIn();
                    break;
                case 2:
                    performCheckOut();
                    break;
                case 3:
                    exitSystem();
                    return;
                default:
                    System.out.println("Pilihan tidak valid. Silakan coba lagi.");
            }
        }
    }

    private static int displayMainMenu() {
        System.out.println("===================================");
        System.out.println("Selamat datang di sistem E-parkir!");
        System.out.println("===================================");
        System.out.println("1. Check-in");
        System.out.println("2. Check-out");
        System.out.println("3. Keluar");
        System.out.print("Pilih opsi: ");
        return scanner.nextInt();
    }

    private static int selectMall() {
        System.out.println("\nPilih mall untuk parkir:");
        for (int i = 0; i < MALLS.length; i++) {
            System.out.printf("%s. %s\n", (i + 1), MALLS[i]);
        }
        System.out.print("Pilihan Anda: ");
        return scanner.nextInt() - 1;
    }

    private static int selectFloor() {
        System.out.println("\nPilih lantai parkir:");
        System.out.println("1. Lantai 1");
        System.out.println("2. Lantai 2");
        System.out.print("Pilihan Anda: ");
        return scanner.nextInt() - 1;
    }

    public static void performCheckIn() {
        scanner.nextLine();

        System.out.print("\nMasukkan jenis mobil Anda: ");
        String carType = scanner.nextLine();
        System.out.print("Masukkan no plat mobil Anda: ");
        String carNumber = scanner.nextLine();

        int mall = selectMall();
        int floor = selectFloor();

        System.out.println("\nStatus parkir:");
        displayParking(parkingStatus[mall][floor]);

        int slot = selectParkingSlot(mall, floor);

        parkingStatus[mall][floor][slot] = true;
        parkedCars[mall][floor][slot] = carType + " - " + carNumber;

        displayParkingConfirmation(carType, carNumber, mall, floor, slot);
    }

    private static int selectParkingSlot(int mall, int floor) {
        int slot;
        while (true) {
            System.out.print("Pilih nomor parkir (1-6): ");
            slot = scanner.nextInt() - 1;

            if (parkingStatus[mall][floor][slot]) {
                System.out.println("Nomor parkir ini sudah terisi. Silakan pilih yang lain.");
            } else {
                return slot;
            }
        }
    }

    public static void displayParkingConfirmation(String carType, String carNumber, int mall, int floor, int slot) {
        System.out.println("Parkir berhasil untuk mobil " + carType + " di " +
                MALLS[mall] + ", Lantai " + (floor + 1) + ", Nomor " + (slot + 1));

        System.out.println("===============");
        System.out.println("Invoice parkir\n");
        displayParking(parkingStatus[mall][floor]);
        displayInvoice(carType, carNumber, MALLS[mall], (floor + 1), (slot + 1));
        System.out.println("===============\n");
    }

    public static void performCheckOut() {
        int mall = selectMall();
        int floor = selectFloor();

        System.out.println("\nStatus parkir:");
        displayParking(parkingStatus[mall][floor]);

        int slot = selectCheckOutSlot(mall, floor);

        processCheckOut(mall, floor, slot);
    }

    private static int selectCheckOutSlot(int mall, int floor) {
        int slot;
        while (true) {
            System.out.print("Masukkan nomor parkir Anda (1-6): ");
            slot = scanner.nextInt() - 1;

            if (parkingStatus[mall][floor][slot]) {
                return slot;
            } else {
                System.out.println("Nomor parkir ini kosong. Silakan masukkan nomor yang benar.");
            }
        }
    }

    public static void processCheckOut(int mall, int floor, int slot) {
        parkingStatus[mall][floor][slot] = false;
        String carDetails = parkedCars[mall][floor][slot];
        parkedCars[mall][floor][slot] = null;

        System.out.println("\nCheck-out berhasil!");
        System.out.println("Detail mobil keluar:");
        System.out.println("Mobil\t: " + carDetails.split(" - ")[0]);
        System.out.println("Plat\t: " + carDetails.split(" - ")[1]);
        System.out.println("Mall\t: " + MALLS[mall]);
        System.out.println("Lantai\t: " + (floor + 1));
        System.out.println("Nomor\t: " + (slot + 1));

        System.out.println("\nStatus parkir setelah update:");
        displayParking(parkingStatus[mall][floor]);
    }

    public static void exitSystem() {
        System.out.println("Terima kasih telah menggunakan layanan E-parkir!");
        scanner.close();
    }

    public static void displayParking(boolean[] floor) {
        System.out.println("+-----------+");
        for (int i = 0; i < floor.length; i++) {
            if (i % 3 == 0 && i != 0) {
                System.out.println("|");
            }
            System.out.print("| ");
            if (floor[i]) {
                System.out.print("\u001B[31m" + (i + 1) + "\u001B[0m "); // Red for occupied
            } else {
                System.out.print("\u001B[32m" + (i + 1) + "\u001B[0m "); // Green for available
            }
        }
        System.out.println("|");
        System.out.println("+-----------+");
    }

    public static void displayInvoice(String carType, String carNumber, String mall, int floor, int slot) {
        System.out.println("Mobil\t: " + carType);
        System.out.println("Plat\t: " + carNumber);
        System.out.println("Mall\t: " + mall);
        System.out.println("Lantai\t: " + floor);
        System.out.println("Nomor\t: " + slot);
    }
}