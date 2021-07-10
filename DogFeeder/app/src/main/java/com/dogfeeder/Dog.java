package com.dogfeeder;

public class Dog {

    private String type;
    private String age;
    private String weight;

    public Dog(String type, String age, String weight) {
        this.type = type;
        this.age = age;
        this.weight = weight;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
