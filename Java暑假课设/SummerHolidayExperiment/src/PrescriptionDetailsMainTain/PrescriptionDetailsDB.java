package PrescriptionDetailsMainTain;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.awt.image.SampleModel;
import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;
import java.util.zip.DataFormatException;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 处方信息库类
 */
public class PrescriptionDetailsDB {

    private ObservableList<PrescriptionDetails> prescriptionDetails = FXCollections.observableArrayList();


    public PrescriptionDetailsDB(ObservableList<PrescriptionDetails> prescriptionDetails) {
        this.prescriptionDetails = prescriptionDetails;
    }

    public ObservableList<PrescriptionDetails> getPrescriptionDetails() {
        return prescriptionDetails;
    }

    public void setPrescriptionDetails(ObservableList<PrescriptionDetails> prescriptionDetails) {
        this.prescriptionDetails = prescriptionDetails;
    }

    public PrescriptionDetailsDB() {
    }



    //File I/O
    /**
     *@描述 读取文件
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void OpenFile() throws IOException, DataFormatException {

        prescriptionDetails.clear();
        FileReader fr = new FileReader("处方明细信息.txt");
        BufferedReader br = new BufferedReader(fr);
        String Count = "";
        while ((Count = br.readLine())!=null ){
            PrescriptionDetails pd = ReadPD(Count);
            if (pd != null) prescriptionDetails.add(pd); }
    }
    public PrescriptionDetails ReadPD(String line ) throws DataFormatException {
        StringTokenizer st = new StringTokenizer(line,"_");
        if (st.countTokens()!=5){
            throw new DataFormatException();
        }
        else{
                return new PrescriptionDetails(st.nextToken(),st.nextToken(),
                        Double.parseDouble(st.nextToken()),Integer.parseInt(st.nextToken())
                        );
        }
    }

    /**
     *@描述 写入文件
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void WriteFile() throws IOException {

            PrintWriter fileOut = new PrintWriter(new FileWriter("处方明细信息.txt"));
            for (PrescriptionDetails details :prescriptionDetails) {
                String line = details.toString();
                fileOut.println(line);
            }
            fileOut.close();


    }
    /**
     *@描述 根据门诊号进行排序
     *@参数  [o1, o2]
     *@返回值 int
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    class  SortByPersonalId implements Comparator{
        public int compare(Object o1,Object o2){

            PrescriptionDetails s1 = (PrescriptionDetails)o1;
            PrescriptionDetails s2 = (PrescriptionDetails)o2;
            return s1.getOutpatientNumber().compareTo(s2.getOutpatientNumber());
        }

    }
    /**
     *@描述 增加
     *@参数  [pd]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void AddPD(PrescriptionDetails pd){

        prescriptionDetails.add(pd);
        prescriptionDetails.sort(new SortByPersonalId());
    }/**
     *@描述 删除
     *@参数  [pd]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void DeletePD(PrescriptionDetails pd){

        prescriptionDetails.remove(pd);
    }

    public PrescriptionDetails FindPD(String code,String menzhen){
        for (PrescriptionDetails details :prescriptionDetails) {
            if (details.getOutpatientNumber().equals(menzhen)&&details.getDrugCode().equals(code)){
                return details;
            }

        }
        return null;

    }/**
     *@描述 查找
     *@参数  [menzhen]
     *@返回值 PrescriptionDetailsMainTain.PrescriptionDetails
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public PrescriptionDetails FindPD(String menzhen){

        for (PrescriptionDetails details :prescriptionDetails) {
            if (details.getOutpatientNumber().equals(menzhen)){
                return details;
            }

        }
        return null;

    }





}
