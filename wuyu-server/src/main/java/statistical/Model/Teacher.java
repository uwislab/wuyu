package statistical.Model;

public class Teacher {
    private Long id;
    private String name;
    private Integer gender; // 0表示女性，1表示男性
    private String title;

    // 构造函数、Getter和Setter方法等
    public Teacher() {
    }

    public Teacher(String name, Integer gender, String title) {
        this.name = name;
        this.gender = gender;
        this.title = title;
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}