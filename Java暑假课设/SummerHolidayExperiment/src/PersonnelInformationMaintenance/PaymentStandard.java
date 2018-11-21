package PersonnelInformationMaintenance;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 支付标准类
 */
public class PaymentStandard {
    private double StartingPaymentStandard;
    private double UpperLimitAmount;
    private double LowerLimitAmount;
    private double FreeChargeRatio;

    public PaymentStandard(double startingPaymentStandard, double upperLimitAmount, double lowerLimitAmount, double freeChargeRatio) {
        StartingPaymentStandard = startingPaymentStandard;
        UpperLimitAmount = upperLimitAmount;
        LowerLimitAmount = lowerLimitAmount;
        FreeChargeRatio = freeChargeRatio;
    }

    public PaymentStandard() {
    }

    public double getStartingPaymentStandard() {
        return StartingPaymentStandard;
    }

    public void setStartingPaymentStandard(double startingPaymentStandard) {
        StartingPaymentStandard = startingPaymentStandard;
    }

    public double getUpperLimitAmount() {
        return UpperLimitAmount;
    }

    public void setUpperLimitAmount(double upperLimitAmount) {
        UpperLimitAmount = upperLimitAmount;
    }

    public double getLowerLimitAmount() {
        return LowerLimitAmount;
    }

    public void setLowerLimitAmount(double lowerLimitAmount) {
        LowerLimitAmount = lowerLimitAmount;
    }

    public double getFreeChargeRatio() {
        return FreeChargeRatio;
    }

    public void setFreeChargeRatio(double freeChargeRatio) {
        FreeChargeRatio = freeChargeRatio;
    }
}
