public class Main {
    public static void main(String[] args) throws Exception {
        Management management = new Management("Dharmsthala Superstore");
        management.readFile();
        management.runApp();
        management.writeFile();
    }
}

