
import java.time.LocalDateTime;

public class Record {

    private String name, type;
    private double quantity, purchaseRate, sellingRate;

    private LocalDateTime dateTime;

    public Record(String name, String type, double quantity, double purchaseRate, double sellingRate) {
        this.name = name.toUpperCase();
        this.type = type;
        this.quantity = quantity;
        this.purchaseRate = purchaseRate;
        this.sellingRate = sellingRate;
        this.dateTime = LocalDateTime.now();
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getPurchaseRate() {
        return purchaseRate;
    }

    public double getSellingRate() {
        return sellingRate;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }


}
