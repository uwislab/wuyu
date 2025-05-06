package statistical.Model;

public class Student {
    private Long id;
    private String name;
    private Integer gender; // 0表示女性，1表示男性

    // 构造函数、Getter和Setter方法等
    public Student() {
    }

    public Student(String name, Integer gender) {
        this.name = name;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }
}