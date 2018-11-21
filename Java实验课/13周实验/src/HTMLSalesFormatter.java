/**
 * @创建人 贾敬哲
 * @创建时间 2018/6/4
 * @描述 the Gourmet Coffee System
 */
public class HTMLSalesFormatter implements SalesFormatter {
    //HTMLSalesFormatter类的单实例
    private static HTMLSalesFormatter  singletonInstance = new HTMLSalesFormatter();
    //构造器和方法
    static public HTMLSalesFormatter getSingletonInstance(){
        return singletonInstance;
    }
    private HTMLSalesFormatter(){};

    @Override
    public String formatSales(Sales sales) {
        String res = "<html>\n" +
                "  <body>\n";
        int num = 0;
        for(Order orders : sales){
            num++;
            res+="    <center><h2>"+num+"</h2></center>\n";
            res+=("<hr>\n" +
                    "    <h4>Total = "+orders.getTotalCost()+"</h4>\n");
            for(OrderItem orderItem : orders){
                res+="      <p>\n" +
                        "        <b>code:</b> "+orderItem.getProduct().getCode()+"<br>\n" +
                        "        <b>quantity:</b> "+orderItem.getQuantity()+"<br>\n" +
                        "        <b>price:</b> "+orderItem.getProduct().getPrice()+"\n" +
                        "     </p>\n"+"</hr>\n";
            }

        }
        res+=("  </body>\n" +
                "</html>");
        return res;
    }
}
