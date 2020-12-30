package dbmsproj.entity;

public class Stand {
    private int standNumber;
    private int area;
    private int exposedSides;
    private int sectionNo;

    public Stand() {
    }

    public int getStandNumber() {
        return standNumber;
    }

    public void setStandNumber(int standNumber) {
        this.standNumber = standNumber;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getExposedSides() {
        return exposedSides;
    }

    public void setExposedSides(int exposedSides) {
        this.exposedSides = exposedSides;
    }

    public int getSectionNo() {
        return sectionNo;
    }

    public void setSectionNo(int sectionNo) {
        this.sectionNo = sectionNo;
    }

    @Override
    public String toString() {
        return "Stand{" +
                "standNumber=" + standNumber +
                ", area=" + area +
                ", exposedSides=" + exposedSides +
                ", sectionNo=" + sectionNo +
                '}';
    }
}
