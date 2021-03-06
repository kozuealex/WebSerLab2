package se.iths.springlab.dtos;

public class MonkeyDto {

    private int id;
    private String name;
    private String type;
    private long weight;
    private String gender;

    public MonkeyDto(int id, String name, String type, long weight, String gender) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.weight = weight;
        this.gender = gender;
    }

    public MonkeyDto() {

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public long getWeight() {
        return weight;
    }

    public void setWeight(long weight) {
        this.weight = weight;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
