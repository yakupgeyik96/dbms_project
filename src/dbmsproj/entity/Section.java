package dbmsproj.entity;

public class Section {
    private int sectionNo;
    private String name;
    private String description;

    public Section(int sectionNo, String name, String description) {
        this.sectionNo = sectionNo;
        this.name = name;
        this.description = description;
    }

    public int getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(int sectionNo) {
        this.sectionNo = sectionNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Section{" +
                "sectionNo=" + sectionNo +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
