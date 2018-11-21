package Pre_settlementInfo;

import HospitalInfo.Hospital;
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统 结算类
 */
public class Pre_settlementInformation {
    private String name,IDtype,IDNumber;
    private Hospital hospital;
    private String time;
    private double MinPaymentStandard,ReimbursementTopLine;
    private double AggregateCost,CategoryBItemsAtTheirOwnExpense;
    private double TheSelfPaidAmountOfA,TheSelfPaidAmountOfB,TheSelfPaidAmountOfC;
    private double TheSelfPaidAmount,ReimbursementAmount,AnnualAccumulatedReimbursementAmount;


    public Pre_settlementInformation() {
    }

    public Pre_settlementInformation(String name, String IDtype, String IDNumber, Hospital hospital, String time, double minPaymentStandard, double reimbursementTopLine, double aggregateCost, double categoryBItemsAtTheirOwnExpense, double theSelfPaidAmountOfA, double theSelfPaidAmountOfB, double theSelfPaidAmountOfC, double theSelfPaidAmount, double reimbursementAmount, double annualAccumulatedReimbursementAmount) {
        this.name = name;
        this.IDtype = IDtype;
        this.IDNumber = IDNumber;
        this.hospital = hospital;
        this.time = time;
        MinPaymentStandard = minPaymentStandard;
        ReimbursementTopLine = reimbursementTopLine;
        AggregateCost = aggregateCost;
        CategoryBItemsAtTheirOwnExpense = categoryBItemsAtTheirOwnExpense;
        TheSelfPaidAmountOfA = theSelfPaidAmountOfA;
        TheSelfPaidAmountOfB = theSelfPaidAmountOfB;
        TheSelfPaidAmountOfC = theSelfPaidAmountOfC;
        TheSelfPaidAmount = theSelfPaidAmount;
        ReimbursementAmount = reimbursementAmount;
        AnnualAccumulatedReimbursementAmount = annualAccumulatedReimbursementAmount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIDtype() {
        return IDtype;
    }

    public void setIDtype(String IDtype) {
        this.IDtype = IDtype;
    }

    public String getIDNumber() {
        return IDNumber;
    }


    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public Hospital getHospital() {
        return hospital;
    }

    public void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getMinPaymentStandard() {
        return MinPaymentStandard;
    }

    public void setMinPaymentStandard(double minPaymentStandard) {
        MinPaymentStandard = minPaymentStandard;
    }

    public double getReimbursementTopLine() {
        return ReimbursementTopLine;
    }

    public void setReimbursementTopLine(double reimbursementTopLine) {
        ReimbursementTopLine = reimbursementTopLine;
    }

    public double getAggregateCost() {
        return AggregateCost;
    }

    public void setAggregateCost(double aggregateCost) {
        AggregateCost = aggregateCost;
    }

    public double getCategoryBItemsAtTheirOwnExpense() {
        return CategoryBItemsAtTheirOwnExpense;
    }

    public void setCategoryBItemsAtTheirOwnExpense(double categoryBItemsAtTheirOwnExpense) {
        CategoryBItemsAtTheirOwnExpense = categoryBItemsAtTheirOwnExpense;
    }

    public double getTheSelfPaidAmountOfA() {
        return TheSelfPaidAmountOfA;
    }

    public void setTheSelfPaidAmountOfA(double theSelfPaidAmountOfA) {
        TheSelfPaidAmountOfA = theSelfPaidAmountOfA;
    }

    public double getTheSelfPaidAmountOfB() {
        return TheSelfPaidAmountOfB;
    }

    public void setTheSelfPaidAmountOfB(double theSelfPaidAmountOfB) {
        TheSelfPaidAmountOfB = theSelfPaidAmountOfB;
    }

    public double getTheSelfPaidAmountOfC() {
        return TheSelfPaidAmountOfC;
    }

    public void setTheSelfPaidAmountOfC(double theSelfPaidAmountOfC) {
        TheSelfPaidAmountOfC = theSelfPaidAmountOfC;
    }

    public double getTheSelfPaidAmount() {
        return TheSelfPaidAmount;
    }

    public void setTheSelfPaidAmount(double theSelfPaidAmount) {
        TheSelfPaidAmount = theSelfPaidAmount;
    }

    public double getAnnualAccumulatedReimbursementAmount() {
        return AnnualAccumulatedReimbursementAmount;
    }

    public void setAnnualAccumulatedReimbursementAmount(double annualAccumulatedReimbursementAmount) {
        AnnualAccumulatedReimbursementAmount = annualAccumulatedReimbursementAmount;
    }

    public double getReimbursementAmount() {
        return ReimbursementAmount;
    }

    public void setReimbursementAmount(double reimbursementAmount) {
        ReimbursementAmount = reimbursementAmount;
    }

    @Override
    public String toString() {
        return "姓名：" + name + '\t' +
                "证件ID：" + IDNumber + '\t' +
                "证件类型：" + IDtype + '\t' +
                "日期：" + time + "\r\n" +
                " 起付标准=" + MinPaymentStandard +
                "元\r\n 报销封顶线=" + ReimbursementTopLine +
                "元\r\n 费用金额=" + AggregateCost +
                "元\r\n 乙类项目自费金额=" + CategoryBItemsAtTheirOwnExpense +
                "元\r\n 分段计算中自费金额_A段=" + TheSelfPaidAmountOfA +
                "元\r\n 分段计算中自费金额_B段=" + TheSelfPaidAmountOfB +
                "元\r\n 分段计算中自费金额_C段=" + TheSelfPaidAmountOfC +
                "元\r\n 自费金额=" + TheSelfPaidAmount +
                "元\r\n 报销金额=" + ReimbursementAmount +
                "元\r\n 年度累计报销金额=" + AnnualAccumulatedReimbursementAmount+"元";

    }
}
