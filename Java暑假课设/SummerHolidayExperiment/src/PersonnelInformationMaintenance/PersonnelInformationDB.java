package PersonnelInformationMaintenance;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 人员信息库类
 */
public class PersonnelInformationDB {
    private ObservableList<PersonnelInformation> personnelInformations = FXCollections.observableArrayList();

    public ObservableList<PersonnelInformation> getPersonnelInformations() {
        return personnelInformations;
    }

    public void setPersonnelInformations(ObservableList<PersonnelInformation> personnelInformations) {
        this.personnelInformations = personnelInformations;
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
    public void OpenFIle() throws IOException {

        File f= new File("人员信息.txt");
        personnelInformations.clear();
            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String count = "";
            while ((count = br.readLine())!=null){
                try {
                    PersonnelInformation pi = ReadPI(count);
                    if(pi != null)personnelInformations.add(pi);
                } catch (DataFormatException e) {
                    e.printStackTrace();
                }
            }

    }/**
     *@描述 读取一行，
     *@参数  [count]
     *@返回值 PersonnelInformationMaintenance.PersonnelInformation
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public PersonnelInformation ReadPI(String count) throws DataFormatException {

        StringTokenizer st = new StringTokenizer(count,"_");
        if(st.countTokens() != 8) throw  new DataFormatException();
        else{
            return new PersonnelInformation(st.nextToken(),st.nextToken(),st.nextToken()
                ,st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken());
        }
    }
    public void WriteFile() throws IOException {
            PrintWriter fileOut = new PrintWriter(new FileWriter("人员信息.txt"));
            for (PersonnelInformation personnelInformation :personnelInformations) {
                String line = personnelInformation.toString();
                fileOut.println(line);
            }
            fileOut.close();

    }


    /**
     *@描述 根据人员ID对集合类进行排序
     *@参数  [o1, o2]
     *@返回值 int
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    class SortByPersonalID implements Comparator{

        public int compare(Object o1,Object o2){

            PersonnelInformation s1 = (PersonnelInformation)o1;
            PersonnelInformation s2 = (PersonnelInformation)o2;
            return  s1.getPersonalID().compareTo(s2.getPersonalID());
        }
    }
    //增删改查保存
    /**
     *@描述 增加
     *@参数  [pi]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void AddPI(PersonnelInformation pi){

        personnelInformations.add(pi);
        personnelInformations.sort(new SortByPersonalID());
    }/**
     *@描述 删除
     *@参数  [pi]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void DeletePi(PersonnelInformation pi){

        personnelInformations.remove(pi);
    } /**
     *@描述 查找
     *@参数  [CodeOrName]
     *@返回值 PersonnelInformationMaintenance.PersonnelInformation
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public PersonnelInformation FindPi(String CodeOrName){

        for (PersonnelInformation personnelInformation :personnelInformations) {
            if(personnelInformation.getPersonalID().equals(CodeOrName) || personnelInformation.getName().equals(CodeOrName)){
                return personnelInformation;
            }

        }
        return null;
    }


}
