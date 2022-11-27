public class Main {
    public static void main(String[] args) throws Exception {

        Management management = new Management();
        management.readFile();
        management.runApp();
        management.writeFile();
    }
}

