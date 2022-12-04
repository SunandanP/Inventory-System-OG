public class Main {
    public static void main(String[] args) throws Exception {
        Management management = new Management("123 Superstore");
        if(management.getFile().exists())
            management.readFile();
        management.runApp();
        management.writeFile();
    }
}
