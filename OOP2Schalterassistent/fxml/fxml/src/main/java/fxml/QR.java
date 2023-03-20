package fxml;

public class QR {
    private String title;
    private String orderNumber;
    private String name;
    private String footer;

    public QR(String title, String orderNumber, String name, String footer) {
        this.title = title;
        this.orderNumber = orderNumber;
        this.name = name;
        this.footer = footer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFooter() {
        return footer;
    }

    public void setFooter(String footer) {
        this.footer = footer;
    } 
}
