package PersonnelInformationMaintenance;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 参数类
 */
public class Arguments {
    private static Arguments instance;
    public  Map<String,Double> PersonCategory  = new HashMap<>();
    public  PaymentStandard[] paymentStandards = new PaymentStandard[5];


    private Arguments(){}
    public static  synchronized Arguments getInstance(){
        if (instance == null){
            instance = new Arguments();
        }
        return instance;
    }
    /**
     *@描述 读取参数
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void ReadV() throws IOException {

        File f = new File("医疗待遇计算参数.txt");
        FileReader fr = null;
        fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        String count = null;
        int number = 0, ccc = 0;
        while ((count = br.readLine()) != null ) {

            if (count.charAt(0) == '#' || ccc == 0) {
                ccc++;
                continue;
            } else {

                StringTokenizer st = new StringTokenizer(count,"-");
                //英文 "-" 中文 ”-“两者从外观上无法分辨
                //System.out.println(st.countTokens());
                if (st.countTokens() == 3) {
                    st.nextToken();
                    PersonCategory.put(st.nextToken(), Double.parseDouble(st.nextToken()));

                }
                if (st.countTokens() == 4) {
                    PaymentStandard ppp = new PaymentStandard();
                    ppp.setStartingPaymentStandard(Double.parseDouble(st.nextToken()));
                    ppp.setUpperLimitAmount(Double.parseDouble(st.nextToken()));
                    ppp.setLowerLimitAmount(Double.parseDouble(st.nextToken()));
                    String a = st.nextToken();
                    Double res=new Double(a.substring(0,a.indexOf("%")))/100.0;
                    ppp.setFreeChargeRatio(res);
                    paymentStandards[number] = ppp;
                    number++;

                }
            }

        }

    }

    public static void main(String[] args) {
        Arguments a = new Arguments();
        try {
            a.ReadV();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(a.paymentStandards[0].getLowerLimitAmount());
        System.out.println(a.paymentStandards[1].getUpperLimitAmount());
        System.out.println(a.paymentStandards[2].getUpperLimitAmount());
    }


}
