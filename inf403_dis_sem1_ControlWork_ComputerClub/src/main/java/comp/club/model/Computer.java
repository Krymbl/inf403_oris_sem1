package comp.club.model;

public class Computer {

    private int id;
    private String name;
    private String processor;
    private int ram;
    private String videoCard;
    private boolean isAvailable;
    private double pricePerHour;

    public Computer() {}

    public Computer(int id, String name, String processor, int ram, String videoCard, boolean isAvailable, double pricePerHour) {
        this.id = id;
        this.name = name;
        this.processor = processor;
        this.ram = ram;
        this.videoCard = videoCard;
        this.isAvailable = isAvailable;
        this.pricePerHour = pricePerHour;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProcessor() {
        return processor;
    }

    public void setProcessor(String processor) {
        this.processor = processor;
    }

    public int getRam() {
        return ram;
    }

    public void setRam(int ram) {
        this.ram = ram;
    }

    public String getVideoCard() {
        return videoCard;
    }

    public void setVideoCard(String videoCard) {
        this.videoCard = videoCard;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }
    public double getPricePerHour() {
        return pricePerHour;
    }

    public void setPricePerHour(double pricePerHour) {
        this.pricePerHour = pricePerHour;
    }
}
