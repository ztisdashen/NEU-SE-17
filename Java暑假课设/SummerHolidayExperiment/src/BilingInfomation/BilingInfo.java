package BilingInfomation;

import HospitalInfo.Hospital;

/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 结算类
 */
public class BilingInfo {
    private String name,IDtype,IDNumber;
    private Hospital hospital;
    private String time,PersonType;
    private double MinPaymentStandard,PersonalExpenses,ReimbursementAmount;
    private int NumberOfHospitalization;

    public BilingInfo(String name, String IDtype, String IDNumber, Hospital hospital, String time, String personType, double minPaymentStandard, double personalExpenses, double reimbursementAmount, int numberOfHospitalization) {
        this.name = name;
        this.IDtype = IDtype;
        this.IDNumber = IDNumber;
        this.hospital = hospital;
        this.time = time;
        PersonType = personType;
        MinPaymentStandard = minPaymentStandard;
        PersonalExpenses = personalExpenses;
        ReimbursementAmount = reimbursementAmount;
        NumberOfHospitalization = numberOfHospitalization;
    }

    public BilingInfo() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIDtype() {
        return IDtype;
    }

    public void setIDtype(String IDtype) {
        this.IDtype = IDtype;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPersonType() {
        return PersonType;
    }

    public void setPersonType(String personType) {
        PersonType = personType;
    }

    public double getMinPaymentStandard() {
        return MinPaymentStandard;
    }

    public void setMinPaymentStandard(double minPaymentStandard) {
        MinPaymentStandard = minPaymentStandard;
    }

    public double getPersonalExpenses() {
        return PersonalExpenses;
    }

    public void setPersonalExpenses(double personalExpenses) {
        PersonalExpenses = personalExpenses;
    }

    public double getReimbursementAmount() {
        return ReimbursementAmount;
    }

    public void setReimbursementAmount(double reimbursementAmount) {
        ReimbursementAmount = reimbursementAmount;
    }

    public int getNumberOfHospitalization() {
        return NumberOfHospitalization;
    }

    public void setNumberOfHospitalization(int numberOfHospitalization) {
        NumberOfHospitalization = numberOfHospitalization;
    }

    @Override
    public String toString() {
        return
                "姓名：" + name + '\t' +
                        " 证件类型：" + IDtype + '\t' +
                        " 证件号码：" + IDNumber + '\t' +
                        " 医院:" + hospital.getName() +
                        " 就诊时段" + time  +
                        "\r\n 人员类别'" + PersonType +
                        "\r\n 起付标准=" + MinPaymentStandard +
                        "元\r\n 个人自费费用=" + PersonalExpenses +
                        "元\r\n 报销费用=" + ReimbursementAmount +
                        "元\r\n 住院次数=" + NumberOfHospitalization +
                        "\r\n";
    }

}
