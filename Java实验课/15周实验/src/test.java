/**
 * @创建人 贾敬哲
 * @创建时间 2018/6/20
 * @描述 the Gourmet Coffee System
 */
public class test {
    public static void main(String[] args) {
        // 第一个不常用
        Integer a_=new Integer(123);    // a_是Integer类型

        int a=a_.intValue();                    // 转化为int类型
        System.out.println(a);

        String b = "123";    // 一个string
        int b_=Integer.parseInt(b);    // 把string转化成int
        System.out.println(b_);

        int c_=1;
        String c=String.valueOf(c_);
        System.out.println(c);
    }
}
