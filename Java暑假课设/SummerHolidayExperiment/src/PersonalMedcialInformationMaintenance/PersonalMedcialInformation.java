package PersonalMedcialInformationMaintenance;

import HospitalInfo.Hospital;
import MedicalInformationMaintenance.MedicalInformation;
import MedicalInformationMaintenance.MedicalInformationDB;
import PrescriptionDetailsMainTain.PrescriptionDetailsDB;
import PrescriptionDetailsMainTain.PrescriptionDetails;
import javafx.beans.property.SimpleStringProperty;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.zip.DataFormatException;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 人员诊疗类
 */
public class PersonalMedcialInformation implements Serializable {
    private SimpleStringProperty PersonnelID;
    private String HospitailizationNumber;
    private Hospital hospital;
    private String DateOfAdmission,DateOfDischarge;
    private String ReasonOfDischarge;
    private double sum ;
    private double SumYI = 0,SumBing = 0;

    private MedicalInformationDB mdb = new MedicalInformationDB();
    private PrescriptionDetailsDB pdb = new PrescriptionDetailsDB();
    public PersonalMedcialInformation(String personnelID, String hospitailizationNumber, String code,String name,String grade, String dateOfAdmission, String dateOfDischarge, String reasonOfDischarge) {
        PersonnelID = new SimpleStringProperty(personnelID);
        HospitailizationNumber = hospitailizationNumber;
        this.hospital = new Hospital(name,grade,code);
        DateOfAdmission = dateOfAdmission;
        DateOfDischarge = dateOfDischarge;
        ReasonOfDischarge = reasonOfDischarge;
    }

    public PersonalMedcialInformation() {
    }
    /**
     *@描述   读取文件，计算出该个人诊疗信息的乙类药品和丙类药品的总和
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void setSumYIBing() throws IOException, DataFormatException {


        mdb.OPenFile();
        pdb.OpenFile();
        for (PrescriptionDetails details : pdb.getPrescriptionDetails()) {
            //System.out.println(details.getOutpatientNumber()+"++++"+this.getHospitailizationNumber());
            if(details.getOutpatientNumber().equals(this.HospitailizationNumber)){
                for (MedicalInformation mi :mdb.getMedicalInformations()) {
                    if(details.getDrugCode().equals(mi.getDrugCode())){
                    if(hospital.getGrade().equals( "一")){

                        if(mi.getChargeItemGrade().equals("乙")){
                            SumYI += Double.parseDouble(details.getSumPrice());
                        }
                        if(mi.getChargeItemGrade( ).equals("丙")){
                            SumBing += Double.parseDouble(details.getSumPrice());
                        }
                    }
                    else if(hospital.getGrade().equals( "三")){
                        if(mi.getHospitalGrade().equals("三")){
                            if(mi.getChargeItemGrade().equals("乙")){
                                SumYI += Double.parseDouble(details.getSumPrice());
                            }
                            if(mi.getChargeItemGrade( ).equals("丙")){
                                SumBing += Double.parseDouble(details.getSumPrice());
                            }
                        }
                    }
                    else if(hospital.getGrade().equals("二")){
                        if (mi.getHospitalGrade().equals("二")||mi.getHospitalGrade().equals("三")){
                            if(mi.getChargeItemGrade().equals("乙")){
                                SumYI += Double.parseDouble(details.getSumPrice());
                            }
                            if(mi.getChargeItemGrade( ).equals("丙")){
                                SumBing += Double.parseDouble(details.getSumPrice());
                            }
                        }
                    }
                }
                }
        }

        }
    }

    public double getSumYI() {
        return SumYI;
    }

    public void setSumYI(double sumYI) {
        SumYI = sumYI;
    }

    public double getSumBing() {
        return SumBing;
    }

    public void setSumBing(double sumBing) {
        SumBing = sumBing;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }


    public String getPersonnelID() {
        return PersonnelID.get();
    }

    public SimpleStringProperty personnelIDProperty() {
        return PersonnelID;
    }


    public String getHospitailizationNumber() {
        return HospitailizationNumber;
    }

    public void setHospitailizationNumber(String hospitailizationNumber) {
        HospitailizationNumber = hospitailizationNumber;
    }

    public void setPersonnelID(String personnelID) {
        this.PersonnelID = new SimpleStringProperty(personnelID);
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getDateOfAdmission() {
        return DateOfAdmission;
    }

    public void setDateOfAdmission(String dateOfAdmission) {
        DateOfAdmission = dateOfAdmission;
    }

    public String getDateOfDischarge() {
        return DateOfDischarge;
    }

    public void setDateOfDischarge(String dateOfDischarge) {
        DateOfDischarge = dateOfDischarge;
    }

    public String getReasonOfDischarge() {
        return ReasonOfDischarge;
    }

    public void setReasonOfDischarge(String reasonOfDischarge) {
        ReasonOfDischarge = reasonOfDischarge;
    }
    /**
     *@描述   获得该诊疗信息的费用总额
     *@参数  []
     *@返回值 double
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public  double getSum() throws IOException, DataFormatException {

        sum = 0.0;
        pdb.OpenFile();
        for (PrescriptionDetails details :pdb.getPrescriptionDetails()) {
            if(details.getOutpatientNumber().equals(this.HospitailizationNumber))sum+=Double.parseDouble(details.getSumPrice());
        }
        return  sum;

    }

    public static void main(String[] args) {
        PersonalMedcialInformation m = new PersonalMedcialInformation();
        m.setHospitailizationNumber("001");
        m.setPersonnelID("001");
        m.setHospital(new Hospital("1","一","001"));
        try {
            m.setSumYIBing();
            System.out.println(m.getSumYI());
            System.out.println(m.getSumBing());
            System.out.println(m.getSum());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DataFormatException e) {
            e.printStackTrace();
        }
    }
        @Override
    public String toString() {

        return
                getPersonnelID() +'_'
                 + HospitailizationNumber + '_'

                 + hospital.getCode() + '_'
               + hospital.getName()+ '_'+hospital.getGrade()+'_'+
             DateOfAdmission + '_'
                 + DateOfDischarge + '_'

                 + ReasonOfDischarge
               ;
    }


}
