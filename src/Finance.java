import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

public class Finance {
    private double netPrice;
    private ArrayList<Record> records;


    public Finance() {
        records = new ArrayList<>();
        records.add(new Record("Kimchi", "Import", 20, 60, 70));
        records.add(new Record("milk", "Import", 80, 52, 60));
    }


    public void displayRecords(){
        Record temp;
        System.out.println("Particular\t\t\t\tDate\t\t\t\tTime\t\t\t\tQuantity\t\tRate\t\t\t  Type");
        for (int i = 0; i < records.size(); i++) {
            temp = records.get(i);
            System.out.println(temp.getName()+"\t\t\t\t\t"+temp.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE)+"\t\t\t"+temp.getDateTime().format(DateTimeFormatter.ofLocalizedTime(FormatStyle.MEDIUM))+"\t\t\t"+temp.getQuantity()+"\t\t\t"+temp.getSellingRate()+"\t\t\t   "+temp.getType());
        }
    }

    public void displayRecord(String name){
        Record temp;
        boolean found = false;
        for (int i = 0; i < records.size(); i++) {
            if ((name.toLowerCase()).equals(records.get(i).getName().toLowerCase())){
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




}



