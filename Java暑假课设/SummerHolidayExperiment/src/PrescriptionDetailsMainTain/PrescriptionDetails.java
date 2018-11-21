package PrescriptionDetailsMainTain;

import HospitalInfo.Hospital;
import HospitalInfo.HospitalDB;
import MedicalInformationMaintenance.MedicalInformation;
import MedicalInformationMaintenance.MedicalInformationDB;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


import javax.management.loading.PrivateClassLoader;
import java.io.Serializable;
import java.util.ArrayList;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 处方信息类
 */
public class PrescriptionDetails implements Serializable {
     private  SimpleStringProperty OutpatientNumber;
     private  SimpleStringProperty DrugCode;
     private  SimpleStringProperty  price ;
     private SimpleStringProperty num ;
     private  SimpleStringProperty sumPrice ;

    public PrescriptionDetails(String outpatientNumber, String drugCode, Double price, Integer num) {
        OutpatientNumber = new SimpleStringProperty(outpatientNumber);
        DrugCode = new SimpleStringProperty(drugCode);
        this.price = new SimpleStringProperty(String.valueOf(price));
        this.num = new SimpleStringProperty(String.valueOf(num));
        this.sumPrice = new SimpleStringProperty(String.valueOf(num * price));
    }

    public PrescriptionDetails() {
    }

    public String getOutpatientNumber() {
        return OutpatientNumber.get();
    }

    public SimpleStringProperty outpatientNumberProperty() {
        return OutpatientNumber;
    }

    public void setOutpatientNumber(String outpatientNumber) {
        this.OutpatientNumber=new SimpleStringProperty(outpatientNumber);
    }

    public String getDrugCode() {
        return DrugCode.get();
    }

    public SimpleStringProperty drugCodeProperty() {
        return DrugCode;
    }

    public void setDrugCode(String drugCode) {
        this.DrugCode =new SimpleStringProperty(drugCode);
    }

    public String getPrice() {
        return price.get();
    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price =new SimpleStringProperty(price);
    }

    public String getNum() {
        return num.get();
    }

    public SimpleStringProperty numProperty() {
        return num;
    }

    public void setNum(String num) {
        this.num=new SimpleStringProperty(num);
    }

    public String getSumPrice() {
        return sumPrice.get();
    }

    public SimpleStringProperty sumPriceProperty() {
        return sumPrice;
    }

    public void setSumPrice(String sumPrice) {
        this.sumPrice=new SimpleStringProperty(sumPrice);
    }

    @Override
    public String toString() {
        String sss = "";
        return  getOutpatientNumber()+ '_'+
                 getDrugCode() +'_'+
              getPrice() +'_'+
             getNum()+'_'+
              getSumPrice();
    }
}
