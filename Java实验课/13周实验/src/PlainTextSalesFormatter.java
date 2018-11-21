/**
 * @创建人 贾敬哲
 * @创建时间 2018/6/4
 * @描述 the Gourmet Coffee System
 */
public class PlainTextSalesFormatter implements SalesFormatter{
    //类的单实例
    private static PlainTextSalesFormatter singletonInstance = new PlainTextSalesFormatter();

    //构造器和方法
    private PlainTextSalesFormatter(){};

    static public PlainTextSalesFormatter getSingletonInstance(){
        return singletonInstance;
    }

    public String formatSales(Sales sales){
        int num = 0;
        String res = "";
        for(Order orders : sales){
            num++;
            res+=("------------------------\n");
            res+=("Order "+num+"\n\n");
            for(OrderItem orderItem : orders){
                res+=(orderItem.getQuantity()+" "+orderItem.getProduct().getCode()+" "
                        +orderItem.getProduct().getPrice()+"\n");
            }
            res+=("\nTotal = "+orders.getTotalCost()+"\n");
        }
        return res;
    }

}
