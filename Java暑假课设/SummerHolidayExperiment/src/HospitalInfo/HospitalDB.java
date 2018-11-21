package HospitalInfo;

import MedicalInformationMaintenance.MedicalInformation;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.zip.DataFormatException;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 医院库类
 */
public class HospitalDB {
    private ArrayList<Hospital>hospitals = new ArrayList<>();

    public HospitalDB(ArrayList<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public ArrayList<Hospital> getHospitals() {
        return hospitals;
    }

    public void setHospitals(ArrayList<Hospital> hospitals) {
        this.hospitals = hospitals;
    }

    public HospitalDB() {
    }
    /**
     *@描述   读取文件
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    //File I/O
    public void OPenFile()  {

        File f = new File("医院信息.txt");
        hospitals.clear();
        try {

            FileReader fr = new FileReader(f);
            BufferedReader br = new BufferedReader(fr);
            String count = "";
            while ((count = br.readLine())!=null){
                try {
                    System.out.println(count);
                    Hospital hh = ReadH(count);
                    if(hh!=null)hospitals.add(hh);
                }catch (DataFormatException e){
                    e.printStackTrace();
                }
            }

        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     *@描述   读取一行字符串，并将其分割，返回hospital类；
     *@参数  [line]
     *@返回值 HospitalInfo.Hospital
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public Hospital ReadH(String line) throws DataFormatException {

        StringTokenizer st = new StringTokenizer(line,"_");
        if (st.countTokens()!=3) throw  new DataFormatException();
        else {
            try {
                return new Hospital(st.nextToken(),st.nextToken(),st.nextToken()
                        );
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        return null;
    }
    /**
     *@描述   写入文件
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void WriteFile(){

        File f = new File("医院信息.txt");

        try {
            PrintWriter fileOut = new PrintWriter(new FileWriter(f));
            for (Hospital hospital :hospitals) {
                String line = hospital.toString();
                fileOut.println(line);

            }
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
