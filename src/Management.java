import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;
import java.util.Scanner;

public class Management implements Serializable {

    private ArrayList<Record> masterRecords;
    private ArrayList<Record> records;
    private double netProfit;
    static Scanner sc = new Scanner(System.in);
    private int counter = 0;

    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    private File file = new File("manifest.ims");


    public Management() {
        masterRecords = new ArrayList<>();
        records = new ArrayList<>();
    }
    public void updateProfit(){
        Record temp;
        for (int i =  counter; i < masterRecords.size(); i++) {
            temp = masterRecords.get(counter);
            if (temp.getType().equals("Buy")){
                netProfit -= (temp.getPurchaseRate() * temp.getQuantity());
            }
            else {
                netProfit += (temp.getSellingRate() * temp.getQuantity());
            }
        counter++;
        }

    }

    public void displayRecords(boolean master){
        Record temp;
        beautify();
        System.out.println("Particular\t\t\t\tDate\t\t\t\tTime\t\t\t\tQuantity\t\tRate\t\t\t  Type");
        beautify();
        int size = ((master)? masterRecords.size(): records.size());
        for (int i = 0; i < size; i++) {
            temp = ((master)?masterRecords:records).get(i);
            System.out.println(temp.getName()+"\t\t\t\t\t"+temp.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE)+"\t\t\t"+temp.getDateTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))+"\t\t\t"+temp.getQuantity()+"\t\t\t"+temp.getSellingRate()+"\t\t\t  "+temp.getType());
            beautify();
        }
    }

    public void displayRecord(String name){
        Record temp;
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if ((name.toUpperCase()).equals(records.get(i).getName())){
                temp = records.get(i);
                System.out.println("Name : "+temp.getName());
                System.out.println("Selling price :" + temp.getSellingRate());
                System.out.println("Stock : "+temp.getQuantity());
                found = true;
            }
        }
        if (!found){
            System.out.println("No records found!");
        }
    }

    private ArrayList<Record> purchase(boolean isSale){
        int input = -1;
        ArrayList<Record> temp = new ArrayList<>();
        String name, type;
        double quantity, purchaseRate, sellingRate;
        System.out.println("Enter '0' as name when done with order.");
        beautify();
        while (true) {
            System.out.println("Enter Name : ");
            name = sc.next();
            beautify();
            if (name.equals("0")){
                break;
            }
            System.out.println("Enter Quantity : ");
            quantity = sc.nextDouble();
            beautify();
            System.out.println("Enter Purchase rate : ");
            purchaseRate = sc.nextDouble();
            beautify();
            System.out.println("Enter Selling Rate : ");
            sellingRate = sc.nextDouble();
            beautify();
            if (isSale){
                type = "Sell";
            }
            else {
                type = "Buy";
            }
            temp.add(new Record(name, type, quantity, purchaseRate, sellingRate));
        }
        return temp;
    }

    public boolean updateRecords(ArrayList<Record> records, boolean isSale){
        Record temp1,temp2;
        boolean updated = false;
        for (int i = 0; i < this.records.size(); i++) {
            for (int j = 0; j < records.size(); j++) {
                temp1 = this.records.get(i);
                temp2 = records.get(j);
                if (temp1.getName().equals(temp2.getName().toUpperCase())){
                    if (!isSale){
                        temp1.setQuantity(temp1.getQuantity() + temp2.getQuantity());
                        temp1.setDateTime(LocalDateTime.now());
                        updated = true;
                    }else {
                        temp1.setQuantity(temp1.getQuantity() - temp2.getQuantity());
                        temp1.setDateTime(LocalDateTime.now());
                        updated = true;
                    }

                }
            }

        }
        return updated;
    }

    public boolean containsRecord(String name){
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if (name.toUpperCase().equals(records.get(i).getName())){
                found = true;
            }
        }
        return found;
    }
    public void addRecords(ArrayList<Record> temp){
        Record temp1;
        for (int i = 0; i < temp.size(); i++) {
            temp1 = temp.get(i);
            if (!(containsRecord(temp1.getName()))){
                records.add(temp1);
            }
        }
    }


    public void purchaseValidator(){
        ArrayList<Record> temp;
        boolean isSale = false;
        System.out.println("Is it a sale?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter the choice code : ");
        int choice = sc.nextInt();
        if (choice == 1){
            isSale = true;
        }
        temp = purchase(isSale);
        System.out.println("Approve this order?");
        System.out.println("1. Yes");
        System.out.println("2. No");
        System.out.print("Enter the choice code : ");
        choice = sc.nextInt();
        if (choice == 1){
            updateMasterRecords(temp);
            updateRecords(temp, isSale);
            addRecords(temp);
            updateProfit();
        }
        else {
            System.out.println("Your order has been cancelled");
        }
    }

    private void beautify(){
        System.out.println("-----------------------------------------------------------------------------------------------------------");
    }


    public void runApp(){
        int input = -1;
        String name;
        System.out.println("--------------------------------------------- N&S Tally Suite ---------------------------------------------");
        System.out.println("----------------------------------- A complete Inventory Management System --------------------------------");
        while (input != 0){
            System.out.println("1. Show all records");
            System.out.println("2. Lookup a record");
            System.out.println("3. Create a Purchase Order");
            System.out.println("4. Show Net Profit");
            input = sc.nextInt();
            switch (input){
                case 1:
                    System.out.println("1. Master Record");
                    System.out.println("2. Updated Record");
                    input = sc.nextInt();
                    if (input == 1){
                        displayRecords(true);
                    }
                    else {
                        displayRecords(false);
                    }
                    break;
                case 2 :
                    System.out.print("Enter the name of item : ");
                    name = sc.next();
                    displayRecord(name);
                    break;
                case 3:
                    purchaseValidator();
                    break;
                case 4:
                    System.out.println("Net profit is : "+ netProfit);
                    break;
            }
        }
    }

    private void updateMasterRecords(ArrayList<Record> records){
        Record temp;
        for (int i = 0; i < records.size(); i++) {
            temp = records.get(i);
            masterRecords.add(new Record(temp.getName(), temp.getType(), temp.getQuantity(), temp.getPurchaseRate(), temp.getSellingRate()));
        }
    }

    public void writeFile() throws IOException {
        oos = new ObjectOutputStream(new FileOutputStream(file,false));
        oos.writeObject(masterRecords);
        oos.writeObject(records);
        oos.close();
    }
    public void readFile() throws Exception {
        ois = new ObjectInputStream(new FileInputStream(file));
        masterRecords = (ArrayList<Record>) ois.readObject();
        records = (ArrayList<Record>) ois.readObject();
        ois.close();
    }

}



