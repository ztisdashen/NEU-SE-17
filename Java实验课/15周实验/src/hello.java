/**
 * @创建人 贾敬哲
 * @创建时间 2018/6/26
 * @描述 the Gourmet Coffee System
 */

public class hello {
    static int t ;
    public static void main(String[] args) {
        System.out.println("****计算开始***");
        try {
            String i = args[0];
            String j = args[2];
            int x = Integer.valueOf(i);
            int y = Integer.parseInt(j);
            t = x/y;
        }catch (ArrayIndexOutOfBoundsException e1){
            System.out.println("sajdoiasjdoiajdoiajdoiajdoisajdoisajd");
            //e1.printStackTrace();
        }catch (ArithmeticException e2){
            //e2.printStackTrace();
        }catch (Exception e3){
            System.out.println("sadmsadsa");
        }

        System.out.println("res = "+t);
    }
}
