package MedicalInformationMaintenance;

import PersonnelInformationMaintenance.PersonnelInformation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.StringTokenizer;
import java.util.zip.DataFormatException;

/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 药品库类
 */
public class MedicalInformationDB {
    private ObservableList<MedicalInformation> medicalInformations = FXCollections.observableArrayList();

    public MedicalInformationDB(ObservableList<MedicalInformation> medicalInformations) {
        this.medicalInformations = medicalInformations;
    }

    public ObservableList<MedicalInformation> getMedicalInformations() {
        return medicalInformations;
    }

    public void setMedicalInformations(ObservableList<MedicalInformation> medicalInformations) {
        this.medicalInformations = medicalInformations;
    }

    public MedicalInformationDB() {
    }

    //File I/O
    //读取文件
    /**
     *@描述 读取药品信息文件
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void OPenFile() throws IOException, DataFormatException {

        File f = new File("药品信息.txt");
        medicalInformations.clear();
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String count = "";
            while ((count = br.readLine())!=null){
                    if (count.charAt(0)=='#'){
                        continue;
                    }
                    else {
                    //System.out.println(count);
                    MedicalInformation mi = ReadMi(count);
                    if(mi!=null)medicalInformations.add(mi);}
            }
    }
    /**
     *@描述   读取一行字符串，并进行切割，返回一个药品对象
     *@参数  [line]
     *@返回值 MedicalInformationMaintenance.MedicalInformation
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public MedicalInformation ReadMi(String line) throws DataFormatException {
        StringTokenizer st = new StringTokenizer(line,"_");
        if (st.countTokens()!=6) throw  new DataFormatException();
        else {
                return new MedicalInformation(st.nextToken(),st.nextToken(),Double.parseDouble(st.nextToken()),
                        st.nextToken(),st.nextToken(),st.nextToken());
        }
    }

    /**
     *@描述   写入文件
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void WriteFile() throws IOException {
        File f = new File("药品信息.txt");
           PrintWriter fileOut = new PrintWriter(new FileWriter(f));
            for (MedicalInformation medicalInformation :medicalInformations) {
                String line = medicalInformation.toString();
                fileOut.println(line);
            }
            fileOut.close();
    }


    //排序
    class SortByDrugCode implements Comparator {
        /**
         *@描述   根据Drugcode对集合类进行排序
         *@参数  [o1, o2]
         *@返回值 int
         *@创建人 贾敬哲
         *@创建时间 2018/8/2
         *@其他信息
         */
        public int compare(Object o1,Object o2){

            MedicalInformation s1 = (MedicalInformation) o1;
            MedicalInformation s2 = (MedicalInformation) o2;
            return  s1.getDrugCode().compareTo(s2.getDrugCode());
        }
    }
    //增删改查保存
    /**
     *@描述 增加
     *@参数  [mi]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void AddMi(MedicalInformation mi){

        medicalInformations.add(mi);
        medicalInformations.sort(new SortByDrugCode());
    }
    /**
     *@描述 删除
     *@参数  [mi]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void DeleteMi(MedicalInformation mi){
        medicalInformations.remove(mi);
    }

    /**
     *@描述 查找
     *@参数  [Code]
     *@返回值 MedicalInformation
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public MedicalInformation FindMI(String Code){
        for (MedicalInformation medicalInformation :medicalInformations) {
            if (medicalInformation.getDrugCode().equals(Code)){
                return medicalInformation;
            }
        }
        return null;
    }

}
