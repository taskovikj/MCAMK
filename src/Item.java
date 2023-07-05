class Item {
    private String name;
    private boolean domestic;
    private double price;
    private int weight;
    private String description;

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public Item(String name, boolean domestic, int weight, double price, String description) {
        this.name = name;
        this.domestic = domestic;
        this.price = price;
        this.description = description;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "\t.."+name+"..\n \tPrice: $"+ price +"\n"+"\t"+
                description.substring(0,Math.min(description.length(), 10))+"...\n"
                +"\tWeight: "+(weight > 0 ? weight + "g":"N/A\n");
    }


    public boolean isDomestic() {
        return domestic;
    }


    public double getPrice() {
        return price;
    }


    public String getDescription() {
        return description;
    }

}