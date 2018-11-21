package HospitalInfo;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 医院类
 */
public class Hospital {
    private String name;
    private String grade;
    private String code;

    public Hospital(String name, String grade, String code) {
        this.name = name;
        this.grade = grade;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return  name + '_' +grade + '_' +
                 code;
    }
}
