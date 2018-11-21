/**
 * @创建人 贾敬哲
 * @创建时间 2018/6/4
 * @描述 the Gourmet Coffee System
 */
public class XMLSalesFormatter implements SalesFormatter{
    //单一实例
    private static XMLSalesFormatter singletonInstance = new XMLSalesFormatter();
    //构造器和方法
    static public XMLSalesFormatter getSingletonInstance(){
        return singletonInstance;
    }
    private XMLSalesFormatter(){};

    @Override
    public String formatSales(Sales sales) {
        String res = "<Sales>\n";
        for(Order orders : sales){
            res+=" <Order total=\""+orders.getTotalCost()+"\">\n";
            for(OrderItem orderItem : orders){
                res+="     <OrderItem quantity=\""+orderItem.getQuantity()
                        +"\" price=\""+orderItem.getProduct().getPrice()+"\">"+orderItem.getProduct().getCode()+"</OrderItem>\n";
            }
            res+=("  </Order>");
        }
        return res;
    }
}
