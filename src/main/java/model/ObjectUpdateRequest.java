package model;

public class ObjectUpdateRequest {
    public String name;
    public Data data;

    public ObjectUpdateRequest(String name, Data data) {
        this.name = name;
        this.data = data;
    }

    public static class Data {
        public int year;
        public double price;
        public String cpu_model;
        public String hard_disk_size;
        public String capacity;
        public String screen_size;
        public String color;

        public Data(int year, double price, String cpu_model, String hard_disk_size,
                    String capacity, String screen_size, String color) {
            this.year = year;
            this.price = price;
            this.cpu_model = cpu_model;
            this.hard_disk_size = hard_disk_size;
            this.capacity = capacity;
            this.screen_size = screen_size;
            this.color = color;
        }
    }
}