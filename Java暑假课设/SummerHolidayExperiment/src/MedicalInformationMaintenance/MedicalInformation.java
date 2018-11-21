package MedicalInformationMaintenance;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.Serializable;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 药品类
 */

public class MedicalInformation implements Serializable {
    //必要属性
    private SimpleStringProperty DrugCode,DrugName;
    private double MaxPrice;
    private String DrugUnit,ChargeItemGrade,HospitalGrade;
    private int Num = 0;
    private double SumPrice = Num * MaxPrice ;

    //构造器
    public MedicalInformation(String drugCode, String drugName, double maxPrice, String drugUnit, String chargeItemGrade, String hospitalGrade) {
        DrugCode = new SimpleStringProperty(drugCode);
        DrugName = new SimpleStringProperty(drugName);
        MaxPrice = maxPrice;
        DrugUnit = drugUnit;
        ChargeItemGrade = chargeItemGrade;
        HospitalGrade = hospitalGrade;
    }

    public MedicalInformation(String drugCode, String drugName) {
        DrugCode = new SimpleStringProperty(drugCode);
        DrugName = new SimpleStringProperty( drugName);
    }

    public MedicalInformation(String drugCode, int num, double sumPrice) {
        DrugCode = new SimpleStringProperty(drugCode);
        Num = num;
    }

    public MedicalInformation() {
    }

    public String getDrugCode() {
        return DrugCode.get();
    }

    public StringProperty drugCodeProperty() {
        return DrugCode;
    }

    public void setDrugCode(String drugCode) {
        this.DrugCode = new SimpleStringProperty(drugCode);
    }

    public String getDrugName() {
        return DrugName.get();
    }

    public StringProperty drugNameProperty() {
        return DrugName;
    }

    public void setDrugName(String drugName) {
        this.DrugName = new SimpleStringProperty(drugName);
    }

    public double getSumPrice() {
        return SumPrice;
    }

    public void setSumPrice(double sumPrice) {
        SumPrice = sumPrice;
    }

    public double getMaxPrice() {
        return MaxPrice;
    }

    public void setMaxPrice(double maxPrice) {
        MaxPrice = maxPrice;
    }

    public String getDrugUnit() {
        return DrugUnit;
    }

    public void setDrugUnit(String drugUnit) {
        DrugUnit = drugUnit;
    }

    public String getChargeItemGrade() {
        return ChargeItemGrade;
    }

    public void setChargeItemGrade(String chargeItemGrade) {
        ChargeItemGrade = chargeItemGrade;
    }

    public String getHospitalGrade() {
        return HospitalGrade;
    }

    public void setHospitalGrade(String hospitalGrade) {
        HospitalGrade = hospitalGrade;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    @Override
    public String toString() {
        return  getDrugCode()+ '_' + getDrugName()+ '_' + MaxPrice+"_"
               + DrugUnit + '_' + ChargeItemGrade + '_'+ HospitalGrade;
    }
}
