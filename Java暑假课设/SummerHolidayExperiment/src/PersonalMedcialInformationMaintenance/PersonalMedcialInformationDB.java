package PersonalMedcialInformationMaintenance;

import PersonnelInformationMaintenance.PersonnelInformation;
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
 * @描述 医疗保险报销系统 人员诊疗信息库类
 */
public class PersonalMedcialInformationDB {
    private ObservableList<PersonalMedcialInformation> personalMedcialInformations  = FXCollections.observableArrayList();


    public PersonalMedcialInformationDB() {
    }

    public ObservableList<PersonalMedcialInformation> getPersonalMedcialInformations() {
        return personalMedcialInformations;
    }

    public void setPersonalMedcialInformations(ObservableList<PersonalMedcialInformation> personalMedcialInformations) {
        this.personalMedcialInformations = personalMedcialInformations;
    }

    /**
                *@描述    读取文件
                *@参数  []
                *@返回值 void
                *@创建人 贾敬哲
                *@创建时间 2018/8/2
                *@其他信息
            */
    public void OpenFile() throws IOException, DataFormatException {

        File f = new File("人员就诊信息.txt");
        personalMedcialInformations.clear();
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String count = "";
        while ((count = br.readLine())!=null){

                PersonalMedcialInformation pmi = ReadPMI(count);
                if(pmi != null)personalMedcialInformations.add(pmi);
            }
    }
    /**
     *@描述 读取一行数据并切割，返回一个诊疗信息类
     *@参数  [line]
     *@返回值 PersonalMedcialInformationMaintenance.PersonalMedcialInformation
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public PersonalMedcialInformation ReadPMI(String line) throws DataFormatException {

        StringTokenizer st = new StringTokenizer(line,"_");
        if (st.countTokens()!=8) throw new DataFormatException();
        else {
                return new PersonalMedcialInformation(st.nextToken(),st.nextToken(),st.nextToken(),
                        st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken(),
                        st.nextToken());

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

        File f = new File("人员就诊信息.txt");

            PrintWriter fileOut = new PrintWriter(new FileWriter(f));
            for (PersonalMedcialInformation personalMedcialInformation :personalMedcialInformations) {
                String line = personalMedcialInformation.toString();
                fileOut.println(line);
            }
            fileOut.close();



    }

    //排序
    class SortByPersonnelID implements Comparator {
        /**
         *@描述 根据个人ID，对集合进行排序
         *@参数  [o1, o2]
         *@返回值 int
         *@创建人 贾敬哲
         *@创建时间 2018/8/2
         *@其他信息
         */public int compare(Object o1, Object o2) {

            PersonalMedcialInformation s1 = (PersonalMedcialInformation) o1;
            PersonalMedcialInformation s2 = (PersonalMedcialInformation) o2;
            return s1.getPersonnelID().compareTo(s2.getPersonnelID());
        }
    }

    //增删改查四大方法
    /**
     *@描述 查找
     *@参数  [ID]
     *@返回值 PersonalMedcialInformationMaintenance.PersonalMedcialInformation
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public PersonalMedcialInformation findPmi(String ID) {

        for (PersonalMedcialInformation personalMedcialInformation : personalMedcialInformations) {
            if (personalMedcialInformation.getPersonnelID().equals(ID)) {
                return personalMedcialInformation;
            }

        }
        return null;
    }/**
     *@描述 增加
     *@参数  [personalMedcialInformation]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
        public void AddPmi(PersonalMedcialInformation personalMedcialInformation){

            personalMedcialInformations.add(personalMedcialInformation);
            personalMedcialInformations.sort(new SortByPersonnelID());
        }
    /**
     *@描述 删除
     *@参数  [personalMedcialInformation]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
        public void delPmi(PersonalMedcialInformation personalMedcialInformation){

            personalMedcialInformations.remove(personalMedcialInformation);
        }


}
