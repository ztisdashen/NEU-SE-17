package PersonnelInformationMaintenance;

import BilingInfomation.BilingInfo;
import HospitalInfo.Hospital;
import PersonalMedcialInformationMaintenance.PersonalMedcialInformation;
import PersonalMedcialInformationMaintenance.PersonalMedcialInformationDB;
import Pre_settlementInfo.Pre_settlementInformation;
import PrescriptionDetailsMainTain.PrescriptionDetailsDB;
import PrescriptionDetailsMainTain.PrescriptionDetails;
import javafx.beans.property.SimpleStringProperty;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.zip.DataFormatException;
import java.math.*;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 人员信息类
 */
public class PersonnelInformation implements Serializable {
    private SimpleStringProperty PersonalID,Name;
    private String IDNumber,IDType,Gender;
    private String Nationality,DataOfBirth,MedicalPersonnelCategory;

    private int aaa = (int)(Math.random()*10000+1);
    private int abb = (int)(Math.random()*10000+1);
    private  Double Sum = 0.0;


    private PersonalMedcialInformationDB personalMedcialInformationDB = new PersonalMedcialInformationDB();
    private Pre_settlementInformation pre = new Pre_settlementInformation();
    private BilingInfo bi = new BilingInfo();



    //构造器
    public PersonnelInformation(String personalID, String IDType, String IDNumber, String name, String gender, String nationality,String dataOfBirth,  String medicalPersonnelCategory) {
        PersonalID = new SimpleStringProperty(personalID);
       Name = new SimpleStringProperty(name);
        this.IDNumber = IDNumber;
        this.IDType = IDType;
        Gender = gender;
        Nationality = nationality;
        DataOfBirth = dataOfBirth;
        MedicalPersonnelCategory = medicalPersonnelCategory;
    }



    //无参构造器
    public PersonnelInformation() {
    }

    public Double getSum() {
        return Sum;
    }

    public void setSum(Double sum) {
        Sum = sum;
    }

    /**
     *@描述 确保浮点数精确
     *@参数  [d]
     *@返回值 double
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public static double formatDouble(double d) {

        return Double.parseDouble(String.format("%.2f", d));
    }

    public PersonalMedcialInformationDB getPersonalMedcialInformationDB() {
        return personalMedcialInformationDB;
    }

    public void setPersonalMedcialInformationDB(PersonalMedcialInformationDB personalMedcialInformationDB) {
        this.personalMedcialInformationDB = personalMedcialInformationDB;
    }


    public String getPersonalID() {
        return PersonalID.get();
    }

    public SimpleStringProperty personalIDProperty() {
        return PersonalID;
    }

    public void setPersonalID(String personalID) {
        this.PersonalID= new SimpleStringProperty(personalID);
    }





    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public String getName() {
        return Name.get();
    }

    public SimpleStringProperty nameProperty() {
        return Name;
    }

    public void setName(String name) {
        this.Name = new SimpleStringProperty(name);
    }

    public String getIDType() {
        return IDType;
    }

    public void setIDType(String IDType) {
        this.IDType = IDType;
    }

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public String getDataOfBirth() {
        return DataOfBirth;
    }

    public void setDataOfBirth(String dataOfBirth) {
        DataOfBirth = dataOfBirth;
    }

    public String getMedicalPersonnelCategory() {
        return MedicalPersonnelCategory;
    }

    public void setMedicalPersonnelCategory(String medicalPersonnelCategory) {
        MedicalPersonnelCategory = medicalPersonnelCategory;
    }

    public Pre_settlementInformation getPre() {
        return pre;
    }

    public void setPre(Pre_settlementInformation pre) {
        this.pre = pre;
    }


    /**
     *@描述 计算结算信息和预结算信息所需要的参数
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void calculate() throws IOException, DataFormatException {

                Arguments a= Arguments.getInstance();
                a.ReadV();
                pre.setName(this.getName());
                pre.setIDtype(this.getIDType());
                pre.setIDNumber(this.getIDNumber());
                pre.setMinPaymentStandard(formatDouble(a.paymentStandards[0].getLowerLimitAmount()));
                pre.setReimbursementTopLine(formatDouble(a.PersonCategory.get(this.getMedicalPersonnelCategory())));
                double temp = 0;

                double tempYI =0;
                double tempBing =0;
                personalMedcialInformationDB.OpenFile();

                for (PersonalMedcialInformation personalMedcialInformation :personalMedcialInformationDB.getPersonalMedcialInformations()) {

                    temp = temp + personalMedcialInformation.getSum();
                    personalMedcialInformation.setSumYIBing();
                    tempYI = tempYI+ personalMedcialInformation.getSumYI();
                    tempBing = tempBing + personalMedcialInformation.getSumBing();
                    pre.setHospital(personalMedcialInformation.getHospital());
                   pre.setTime(personalMedcialInformation.getDateOfAdmission() + "-" + personalMedcialInformation.getDateOfDischarge());

                }
                setSum(temp+getSum());
                pre.setAggregateCost(formatDouble(temp));

                pre.setCategoryBItemsAtTheirOwnExpense(formatDouble(tempYI*0.5));
            if(tempYI*0.5+tempBing <= a.paymentStandards[0].getStartingPaymentStandard()){
                pre.setTheSelfPaidAmountOfA(0);
                pre.setTheSelfPaidAmountOfB(0);
                pre.setTheSelfPaidAmountOfC(0);
            }else if(tempYI*0.5+tempBing <= a.paymentStandards[0].getUpperLimitAmount()){
                pre.setTheSelfPaidAmountOfA(formatDouble((tempYI*0.5+tempBing-a.paymentStandards[0].getLowerLimitAmount())*a.paymentStandards[0].getFreeChargeRatio()));
                pre.setTheSelfPaidAmountOfB(0);
                pre.setTheSelfPaidAmountOfC(0);
            }else if(tempYI*0.5+tempBing <= a.paymentStandards[1].getUpperLimitAmount()){
                pre.setTheSelfPaidAmountOfA(formatDouble((a.paymentStandards[0].getUpperLimitAmount()-a.paymentStandards[0].getLowerLimitAmount())*a.paymentStandards[0].getLowerLimitAmount()));
                pre.setTheSelfPaidAmountOfB(formatDouble((tempYI*0.5+tempBing-a.paymentStandards[0].getUpperLimitAmount())*0.1));
                pre.setTheSelfPaidAmountOfC(0);
            }else {
                pre.setTheSelfPaidAmountOfA(formatDouble((a.paymentStandards[0].getUpperLimitAmount()-a.paymentStandards[0].getLowerLimitAmount())*a.paymentStandards[0].getLowerLimitAmount()));
                pre.setTheSelfPaidAmountOfB(formatDouble((a.paymentStandards[1].getUpperLimitAmount()-a.paymentStandards[1].getLowerLimitAmount())*a.paymentStandards[1].getLowerLimitAmount()));
                double tempC = (tempYI*0.5+tempBing-a.paymentStandards[1].getUpperLimitAmount())*a.paymentStandards[2].getFreeChargeRatio();
               if(200000-this.getSum() <(pre.getAggregateCost()-formatDouble((a.paymentStandards[0].getUpperLimitAmount()-a.paymentStandards[0].getLowerLimitAmount())*a.paymentStandards[0].getLowerLimitAmount())-formatDouble((a.paymentStandards[1].getUpperLimitAmount()-a.paymentStandards[1].getLowerLimitAmount())*a.paymentStandards[1].getLowerLimitAmount()))){
                    pre.setTheSelfPaidAmountOfC(200000+formatDouble((a.paymentStandards[0].getUpperLimitAmount()-a.paymentStandards[0].getLowerLimitAmount())*a.paymentStandards[0].getLowerLimitAmount())+formatDouble((a.paymentStandards[1].getUpperLimitAmount()-a.paymentStandards[1].getLowerLimitAmount())*a.paymentStandards[1].getLowerLimitAmount())-pre.getAggregateCost());
                }else{
                   if (200000-this.getSum() <(pre.getAggregateCost()-formatDouble((a.paymentStandards[0].getUpperLimitAmount()-a.paymentStandards[0].getLowerLimitAmount())*a.paymentStandards[0].getLowerLimitAmount())-formatDouble((a.paymentStandards[1].getUpperLimitAmount()-a.paymentStandards[1].getLowerLimitAmount())*a.paymentStandards[1].getLowerLimitAmount()))+tempC)
                    pre.setTheSelfPaidAmountOfC((pre.getAggregateCost()-formatDouble((a.paymentStandards[0].getUpperLimitAmount()-a.paymentStandards[0].getLowerLimitAmount())*a.paymentStandards[0].getLowerLimitAmount())-formatDouble((a.paymentStandards[1].getUpperLimitAmount()-a.paymentStandards[1].getLowerLimitAmount())*a.paymentStandards[1].getLowerLimitAmount()))+tempC-(200000-this.getSum()));
                   else {
                       pre.setTheSelfPaidAmountOfC(0);
                   }
                }
            }

            pre.setTheSelfPaidAmount(formatDouble(pre.getTheSelfPaidAmountOfA()+pre.getTheSelfPaidAmountOfB()+pre.getTheSelfPaidAmountOfC()));
            pre.setReimbursementAmount(formatDouble(pre.getAggregateCost() -  pre.getTheSelfPaidAmount()));
            setSum(getSum()+pre.getReimbursementAmount());

            pre.setAnnualAccumulatedReimbursementAmount(formatDouble(this.getSum()));

            bi.setName(pre.getName());
            bi.setIDtype(pre.getIDtype());
            bi.setIDNumber(pre.getIDNumber());
            bi.setHospital(pre.getHospital());
            bi.setTime(pre.getTime());
            bi.setMinPaymentStandard(pre.getMinPaymentStandard());
            bi.setPersonType(this.MedicalPersonnelCategory);
            personalMedcialInformationDB.OpenFile();
            bi.setNumberOfHospitalization(personalMedcialInformationDB.getPersonalMedcialInformations().size());
            bi.setPersonalExpenses(pre.getTheSelfPaidAmount());
            bi.setReimbursementAmount(pre.getReimbursementAmount());
            //System.out.println(pre.toString());
            //System.out.println(bi.toString());
        }


    public BilingInfo getBi() {
        return bi;
    }

    public void setBi(BilingInfo bi) {
        this.bi = bi;
    }

    public static void main(String[] args) {
        try {

            PersonnelInformation p = new PersonnelInformation("001","身份证","001","王静","女","汉","2018-07-02","享受最低保障的在职人员");
            p.calculate();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }


    }

    @Override
    public String toString() {

        return
                 getPersonalID() +'_' + IDType + '_'+ IDNumber + '_' +
                        getName() + '_' + Gender + '_' +
                 Nationality + '_'
                 + DataOfBirth + '_' +MedicalPersonnelCategory;
    }
}


