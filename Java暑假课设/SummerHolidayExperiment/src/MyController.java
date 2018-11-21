
/**
 * @创建人 贾敬哲
 * @创建时间 2018/8/2
 * @描述 医疗保险报销系统
 */
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.zip.DataFormatException;

import BilingInfomation.BilingInfo;
import HospitalInfo.Hospital;
import MedicalInformationMaintenance.MedicalInformation;
import MedicalInformationMaintenance.MedicalInformationDB;
import PersonalMedcialInformationMaintenance.PersonalMedcialInformation;
import PersonalMedcialInformationMaintenance.PersonalMedcialInformationDB;
import PersonnelInformationMaintenance.PersonnelInformation;
import PersonnelInformationMaintenance.PersonnelInformationDB;
import Pre_settlementInfo.Pre_settlementInformation;
import PrescriptionDetailsMainTain.PrescriptionDetails;
import PrescriptionDetailsMainTain.PrescriptionDetailsDB;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import javax.annotation.PostConstruct;

public class MyController implements Initializable {
    //LocalDate格式
    /**
     *@描述 确定时间格式
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public static final DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private String resYU,resJie ;
    //初始化ComboxBox的选项
    /**
     *@描述  初始化，combobox的选项
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void chushihu(){

        IDtype.getItems().addAll("身份证","医保卡" );
        Gender.getItems().addAll("男","女");
        MedicalPersonKind.getItems().addAll("在职职工" ,
                "退休人员" ,
                "享受最低保障的在职人员" ,
                "享受最低保障的退休人员");
        DrugUnit.getItems().addAll("g（克）","mg（毫克）",
                "μg（微克）","L（升）","ml（毫升）","kg（公斤）");
        ChargeGrade.getItems().addAll("甲","乙","丙");
        DrugHospitalGrade.getItems().addAll("一","二","三");
        PMIHospitalGrade.getItems().addAll("一","二","三");
    }
    /**
     *@描述 为TableView添加监听事件，实现模糊搜索，以及选择匹配
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void refushTable(){

        // Initialize the person table with the two columns.
        PersonIDColumn.setCellValueFactory(cellData -> cellData.getValue().personalIDProperty());
        PersonNameColum.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        FilteredList<PersonnelInformation> personData = new FilteredList<>(piol, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        PersonFindField.textProperty().addListener((observable, oldValue, newValue) -> {
            personData.setPredicate(personnelInformation -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            if (personnelInformation.getIDNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                return true; // Filter matches first name.
            } else if (personnelInformation.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                return true; // Filter matches last name.
            }
            return false; // Does not match.
        });
    });

    // 3. Wrap the FilteredList in a SortedList.
    SortedList<PersonnelInformation> sortedpersonData = new SortedList<>(personData);
    // 4. Bind the SortedList comparator to the TableView comparator.
    // 	  Otherwise, sorting the TableView would have no effect.
        sortedpersonData.comparatorProperty().bind(PersonTable.comparatorProperty());
        PersonTable.getSelectionModel().selectedItemProperty().addListener(
            // 选中某一行
                new ChangeListener<PersonnelInformation>() {
        /**
         * This method needs to be provided by an implementation of
         * {@code ChangeListener}. It is called if the value of an
         * {@link ObservableValue} changes.
         * <p>
         * In general is is considered bad practice to modify the observed value in
         * this method.
         *
         * @param observable The {@code ObservableValue} which value changed
         * @param oldValue   The old value
         * @param newValue
         */
        @Override
        public void changed(ObservableValue<? extends PersonnelInformation> observable, PersonnelInformation oldValue, PersonnelInformation newValue) {

            String xxx = newValue.getPersonalID();
            PersonID.setText(xxx);
            //System.out.println(newValue.getIDType());
            IDtype.setValue(newValue.getIDType());
            IDNumber.setText(newValue.getIDNumber());
            Name.setText(newValue.getName());
            LocalDate ldt = LocalDate.parse(newValue.getDataOfBirth(),df);
            BirthDay.setValue(ldt);
            Gender.setValue(newValue.getGender());
            MedicalPersonKind.setValue(newValue.getMedicalPersonnelCategory());
            Nationality.setText(newValue.getNationality());
        }
    });
    // 5. Add sorted (and filtered) data to the table.

        PersonTable.setItems(sortedpersonData);


        TableDrugCode.setCellValueFactory(cellData -> cellData.getValue().drugCodeProperty());
        TableDrugName.setCellValueFactory(cellData -> cellData.getValue().drugNameProperty());

    FilteredList<MedicalInformation> DrugData = new FilteredList<>(miol, p -> true);

    // 2. Set the filter Predicate whenever the filter changes.
        DrugFindField.textProperty().addListener((observable, oldValue, newValue) -> {
        DrugData.setPredicate(mdicalInformation -> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }

            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            if (mdicalInformation.getDrugName().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true; // Filter matches first name.
            } else if (mdicalInformation.getDrugCode().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                return true; // Filter matches last name.
            }
            return false; // Does not match.
        });
    });

    // 3. Wrap the FilteredList in a SortedList.
    SortedList<MedicalInformation> sortedData = new SortedList<>(DrugData);

    // 4. Bind the SortedList comparator to the TableView comparator.
    // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(DrugTable.comparatorProperty());
        DrugTable.getSelectionModel().selectedItemProperty().addListener(// 选中某一行
                new ChangeListener<MedicalInformation>() {
        /**
         * This method needs to be provided by an implementation of
         * {@code ChangeListener}. It is called if the value of an
         * {@link ObservableValue} changes.
         * <p>
         * In general is is considered bad practice to modify the observed value in
         * this method.
         *
         * @param observable The {@code ObservableValue} which value changed
         * @param oldValue   The old value
         * @param newValue
         */
        @Override
        public void changed(ObservableValue<? extends MedicalInformation> observable, MedicalInformation oldValue, MedicalInformation newValue) {
            String xx = newValue.getDrugCode();
            DrugID.setText(xx);
            DrugName.setText(newValue.getDrugName());
            DrugPrice.setText(String.valueOf(newValue.getMaxPrice()));
            DrugUnit.setValue(newValue.getDrugUnit());
            ChargeGrade.setValue(newValue.getChargeItemGrade());
            DrugHospitalGrade.setValue(newValue.getHospitalGrade());
        }
    });
    // 5. Add sorted (and filtered) data to the table.

        DrugTable.setItems(sortedData);
    // TODO (don't really need to do anything here).
        TablePMIID.setCellValueFactory(cellData -> cellData.getValue().personnelIDProperty());

    FilteredList<PersonalMedcialInformation> PMIData = new FilteredList<>(pmiol, p -> true);
    // 2. Set the filter Predicate whenever the filter changes.
        PMIFInd.textProperty().addListener((observable, oldValue, newValue) -> {
        PMIData.setPredicate( PersonalMedcialInformation-> {
            // If filter text is empty, display all persons.
            if (newValue == null || newValue.isEmpty()) {
                return true;
            }
            // Compare first name and last name of every person with filter text.
            String lowerCaseFilter = newValue.toLowerCase();

            if (PersonalMedcialInformation.getPersonnelID().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                return true; // Filter matches first name.
            }
            return false; // Does not match.

        });
        });
        SortedList<PersonalMedcialInformation> SortedPMIData = new SortedList<>(PMIData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        SortedPMIData.comparatorProperty().bind(PMITable.comparatorProperty());
        PMITable.getSelectionModel().selectedItemProperty().addListener(// 选中某一行
                new ChangeListener<PersonalMedcialInformation>() {
                    /**
                     * This method needs to be provided by an implementation of
                     * {@code ChangeListener}. It is called if the value of an
                     * {@link ObservableValue} changes.
                     * <p>
                     * In general is is considered bad practice to modify the observed value in
                     * this method.
                     *
                     * @param observable The {@code ObservableValue} which value changed
                     * @param oldValue   The old value
                     * @param newValue
                     */
                    @Override
                    public void changed(ObservableValue<? extends PersonalMedcialInformation> observable, PersonalMedcialInformation oldValue, PersonalMedcialInformation newValue) {

                        PMIPersonID.setText(newValue.getPersonnelID());
                        PMIMenzhenhao.setText(newValue.getHospitailizationNumber());
                        PMIHospitalName.setText(newValue.getHospital().getName());
                        PMIHospitalCode.setText(newValue.getHospital().getCode());
                        PMIHospitalGrade.setValue(newValue.getHospital().getGrade());
                        LocalDate in = LocalDate.parse(newValue.getDateOfAdmission(),df);
                        LocalDate out = LocalDate.parse(newValue.getDateOfDischarge(),df);
                        PMIInputHospital.setValue(in);
                        PMIOutputHospital.setValue(out);
                        PMIReason.setText(newValue.getReasonOfDischarge());
                    }
                });

        PMITable.setItems(SortedPMIData);
        // Initialize the person table with the two columns.
        ZhuyuanHaoColumn.setCellValueFactory(cellData -> cellData.getValue().outpatientNumberProperty());
        PreDrugCodeColumn.setCellValueFactory(cellData -> cellData.getValue().drugCodeProperty());
        PreDrugNumColumn.setCellValueFactory(cellData -> cellData.getValue().numProperty());
        PreDrugPriceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
        PreDrugSumColum.setCellValueFactory(cellData -> cellData.getValue().sumPriceProperty());
        FilteredList<PrescriptionDetails> PreData = new FilteredList<>(pol, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        PreFind.textProperty().addListener((observable, oldValue, newValue) -> {
            PreData.setPredicate(prescriptionDetails -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (prescriptionDetails.getOutpatientNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                    return true; // Filter matches first name.
                } else if (prescriptionDetails.getDrugCode().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });
        SortedList<PrescriptionDetails> SortedPreData = new SortedList<>(PreData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        SortedPreData.comparatorProperty().bind(PreTable.comparatorProperty());
        PreTable.getSelectionModel().selectedItemProperty().addListener(// 选中某一行
                new ChangeListener<PrescriptionDetails>() {
                    /**
                     * This method needs to be provided by an implementation of
                     * {@code ChangeListener}. It is called if the value of an
                     * {@link ObservableValue} changes.
                     * <p>
                     * In general is is considered bad practice to modify the observed value in
                     * this method.
                     *
                     * @param observable The {@code ObservableValue} which value changed
                     * @param oldValue   The old value
                     * @param newValue
                     */
                    @Override
                    public void changed(ObservableValue<? extends PrescriptionDetails> observable, PrescriptionDetails oldValue, PrescriptionDetails newValue) {
                        PreDrugCode.setText(newValue.getDrugCode());
                        PreDrugNum.setText(newValue.getNum());
                        PreDrugPrice.setText(newValue.getPrice());
                        PreMenzhenhao.setText(newValue.getOutpatientNumber());
                        PreSumPrice.setText(newValue.getSumPrice());

                    }
                });

        PreTable.setItems(SortedPreData);

        // Initialize the person table with the two columns.
        ReTableID.setCellValueFactory(cellData -> cellData.getValue().personalIDProperty());
        ReTableName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        FilteredList<PersonnelInformation> ReData = new FilteredList<>(piol, p -> true);
        // 2. Set the filter Predicate whenever the filter changes.
        ReFind.textProperty().addListener((observable, oldValue, newValue) -> {
            ReData.setPredicate(personnelInformation -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (personnelInformation.getIDNumber().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                    return true; // Filter matches first name.
                } else if (personnelInformation.getName().toLowerCase().indexOf(lowerCaseFilter) != -1) {

                    return true; // Filter matches last name.
                }
                return false; // Does not match.
            });
        });

        // 3. Wrap the FilteredList in a SortedList.
        SortedList<PersonnelInformation> sortedReData = new SortedList<>(ReData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedpersonData.comparatorProperty().bind(PersonTable.comparatorProperty());
        ReTable.getSelectionModel().selectedItemProperty().addListener(
                // 选中某一行
                new ChangeListener<PersonnelInformation>() {
                    /**
                     * This method needs to be provided by an implementation of
                     * {@code ChangeListener}. It is called if the value of an
                     * {@link ObservableValue} changes.
                     * <p>
                     * In general is is considered bad practice to modify the observed value in
                     * this method.
                     *
                     * @param observable The {@code ObservableValue} which value changed
                     * @param oldValue   The old value
                     * @param newValue
                     */
                    @Override
                    public void changed(ObservableValue<? extends PersonnelInformation> observable, PersonnelInformation oldValue, PersonnelInformation newValue) {

                        try {

                            newValue.calculate();
                            resYU = newValue.getPre().toString();
                            resJie = newValue.getBi().toString();
                            res.setText(newValue.getPre().toString()+"\n\r"+newValue.getBi().toString());
                        } catch (IOException e) {
                            ReState.setText("参数文件读入失败");
                        } catch (DataFormatException e) {
                            ReState.setText("参数文件格式错误");
                        }
                    }
                });
        // 5. Add sorted (and filtered) data to the table.

        ReTable.setItems(sortedReData);
    }

    //人员基本信息维护
    @FXML
    private TextField PersonFindField;
    @FXML
    /**
     *@描述 人员信息的删除按钮操作
     *@参数  [e]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPIDelete(ActionEvent e){
        PersonnelInformation ppp = new PersonnelInformation();
        ppp.setPersonalID(PersonID.getText());
        pidb.DeletePi(pidb.FindPi(ppp.getPersonalID()));
        PersonTable.refresh();
    }
    @FXML
    private Button PIDelete;
    @FXML
    /**
     *@描述 人员信息的添加按钮操作
     *@参数  [e]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void onPIAdd(ActionEvent e){
        PersonnelInformation ppp = new PersonnelInformation();
        ppp.setPersonalID(PersonID.getText());
        ppp.setIDType(IDtype.getSelectionModel().getSelectedItem().toString());
        ppp.setIDNumber(IDNumber.getText());
        ppp.setName(Name.getText());
        ppp.setGender(Gender.getSelectionModel().getSelectedItem().toString());
        ppp.setNationality(Nationality.getText());
        //ppp.setDataOfBirth(String.valueOf(BirthDay.getControlCssMetaData()));
        String localTime = df.format(BirthDay.getValue());
        ppp.setDataOfBirth(localTime);
        System.out.println(localTime);
        ppp.setMedicalPersonnelCategory(MedicalPersonKind.getSelectionModel().getSelectedItem().toString());

        if (pidb.FindPi(ppp.getPersonalID()) == null){
            pidb.AddPI(ppp);
            PersonTable.refresh();
        }else{
            PersonDaochuState.setText("人员信息重复");

        }

    }
    @FXML
    private Button PIAdd;
    @FXML
    /**
     *@描述 人员信息的修改按钮操作
     *@参数  [e]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void onPIChange(){
        PersonnelInformation ppp = new PersonnelInformation();
        ppp.setPersonalID(PersonID.getText());
        //System.out.println(IDtype.getSelectionModel().getSelectedItem().toString());
        ppp.setIDType(IDtype.getSelectionModel().getSelectedItem().toString());
        ppp.setIDNumber(IDNumber.getText());
        ppp.setName(Name.getText());
        ppp.setGender(Gender.getSelectionModel().getSelectedItem().toString());
        ppp.setNationality(Nationality.getText());
        //System.out.println(BirthDay.getValue().toString());
        ppp.setDataOfBirth(BirthDay.getValue().toString());
        ppp.setMedicalPersonnelCategory(MedicalPersonKind.getSelectionModel().getSelectedItem().toString());
        pidb.DeletePi(pidb.FindPi(ppp.getPersonalID()));
        pidb.AddPI(ppp);

        PersonTable.refresh();

    }
    @FXML
    private Button PIChange;
    @FXML
    /**
     *@描述 人员信息的导出按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onDaochu(){
        try {
            pidb.WriteFile();
            PersonDaochuState.setText("录入成功");
        } catch (IOException e) {
            PersonDaochuState.setText("录入失败");
        }
    }
    @FXML
    private Button PIDaoChu;
    @FXML
    private TableView<PersonnelInformation> PersonTable;

    @FXML
    private TableColumn<PersonnelInformation,String > PersonIDColumn;
    @FXML
    private TableColumn<PersonnelInformation,String> PersonNameColum;
    @FXML
    private TextField PersonDaochuState;
    @FXML
    private TextField PersonID;
    @FXML
    private TextField IDNumber;
    @FXML
    private TextField Nationality;
    @FXML
    private TextField Name;
    @FXML
    private DatePicker BirthDay;
    @FXML
    private ComboBox IDtype;
    @FXML
    private ComboBox Gender;
    @FXML
    private ComboBox MedicalPersonKind;


    //药品基本信息维护
    @FXML
    private TextField DrugFindField;
    @FXML
    private Button MIDelete;
    @FXML
    /**
     *@描述 药品信息的删除按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onMIDelete(){

        MedicalInformation mmm = new MedicalInformation();
        mmm.setDrugCode(DrugID.getText());
        midb.DeleteMi(midb.FindMI(mmm.getDrugCode()));
        DrugTable.refresh();

    }
    @FXML
    private Button MIAdd;
    @FXML
    /**
     *@描述 药品信息的添加按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onMIAdd(){
        MedicalInformation mmm = new MedicalInformation();
        mmm.setDrugCode(DrugID.getText());
        mmm.setDrugName(DrugName.getText());
        mmm.setMaxPrice(Double.parseDouble(DrugPrice.getText()));
        mmm.setChargeItemGrade(ChargeGrade.getSelectionModel().getSelectedItem().toString());
        mmm.setDrugUnit(DrugUnit.getSelectionModel().getSelectedItem().toString());
        mmm.setHospitalGrade(DrugHospitalGrade.getSelectionModel().getSelectedItem().toString());

        if (midb.FindMI(mmm.getDrugCode()) == null){

            midb.AddMi(mmm);
            DrugTable.refresh();
            PersonTable.refresh();
        }else{
            DrugState.setText("药品信息重复");

        }
    }
    @FXML
    private Button MIChange;
    @FXML
    /**
     *@描述 药品信息的修改按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onMIChange(){
        MedicalInformation mmm = new MedicalInformation();
        mmm.setDrugCode(DrugID.getText());
        mmm.setDrugName(DrugName.getText());
        mmm.setMaxPrice(Double.parseDouble(DrugPrice.getText()));
        mmm.setChargeItemGrade(ChargeGrade.getSelectionModel().getSelectedItem().toString());
        mmm.setDrugUnit(DrugUnit.getSelectionModel().getSelectedItem().toString());
        mmm.setHospitalGrade(DrugHospitalGrade.getSelectionModel().getSelectedItem().toString());

        midb.DeleteMi(midb.FindMI(mmm.getDrugCode()));
        midb.AddMi(mmm);
        DrugTable.refresh();
    }
    @FXML
    private Button MIDaochu;
    @FXML
    /**
     *@描述 药品信息的导出按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onMIDaochu(){
        try {
            midb.WriteFile();
            DrugState.setText("录入成功");
        } catch (IOException e) {
            DrugState.setText("录入失败");
        }
    }
    @FXML
    private TextField MIState;
    @FXML
    private TextField DrugID;
    @FXML
    private TextField DrugName;
    @FXML
    private TextField DrugState;
    @FXML
    private TextField DrugPrice;

    @FXML
    private TableView<MedicalInformation> DrugTable;
    @FXML
    private TableColumn<MedicalInformation, String> TableDrugCode;
    @FXML
    private TableColumn<MedicalInformation, String> TableDrugName;
    @FXML
    private ComboBox DrugUnit;
    @FXML
    private ComboBox ChargeGrade;
    @FXML
    private ComboBox DrugHospitalGrade;


    //人员就诊信息维护
    @FXML
    private  TextField PMIFInd;
    @FXML
    private Button PMIDelete;
    @FXML
    /**
     *@描述 人员就诊信息的删除按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPMIDelete(){

        PersonalMedcialInformation ppp = new PersonalMedcialInformation();

        ppp.setPersonnelID(PMIPersonID.getText());

        pmidb.delPmi(pmidb.findPmi(ppp.getPersonnelID()));
        PMITable.refresh();
    }
    @FXML
    private Button PMIAdd;
    @FXML
    /**
     *@描述 人员就诊信息的添加按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPMIAdd(){
        PersonalMedcialInformation ppp = new PersonalMedcialInformation();
        ppp.setPersonnelID(PMIPersonID.getText());
        ppp.setHospitailizationNumber(PMIMenzhenhao.getText());
        ppp.setHospital(new Hospital(PMIHospitalName.getText(),PMIHospitalGrade.getSelectionModel().getSelectedItem().toString(),PMIHospitalCode.getText()));
        String inTime = df.format(PMIInputHospital.getValue());
        ppp.setDateOfAdmission(inTime);
        String outTime = df.format(PMIOutputHospital.getValue());
        ppp.setDateOfDischarge(outTime);
        ppp.setReasonOfDischarge(PMIReason.getText());
        pmidb.AddPmi(ppp);
        PMITable.refresh();
        }

    @FXML
    private Button PMIChange;
    @FXML
    /**
     *@描述 人员就诊信息的修改按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPMIChange(){
        PersonalMedcialInformation ppp = new PersonalMedcialInformation();
        ppp.setPersonnelID(PMIPersonID.getText());
        ppp.setHospital(new Hospital(PMIHospitalName.getText(),PMIHospitalGrade.getSelectionModel().getSelectedItem().toString(),PMIHospitalCode.getText()));
        String inTime = df.format(PMIInputHospital.getValue());
        ppp.setDateOfAdmission(inTime);
        String outTime = df.format(PMIOutputHospital.getValue());
        ppp.setDateOfDischarge(outTime);
        ppp.setReasonOfDischarge(PMIReason.getText());
        pmidb.delPmi(pmidb.findPmi(ppp.getPersonnelID()));
        pmidb.AddPmi(ppp);
        PMITable.refresh();

    }
    @FXML
    private Button PMIDaochu;
    @FXML
    /**
     *@描述 人员就诊信息的导出按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPMIDAochu(){
        try {
            pmidb.WriteFile();
           PMIState.setText("录入成功");
        } catch (IOException e) {
            PMIState.setText("录入失败");
        }
    }
    @FXML
    private TableView<PersonalMedcialInformation> PMITable;
    @FXML
    private TableColumn<PersonalMedcialInformation,String> TablePMIID;

    @FXML
    private  TextField  PMIPersonID;
    @FXML
    private  TextField  PMIMenzhenhao;
    @FXML
    private  TextField  PMIHospitalCode;
    @FXML
    private  TextField  PMIHospitalName;
    @FXML
    private  TextField  PMIReason;
    @FXML
    private TextField  PMIState;
    @FXML
    private  DatePicker PMIInputHospital;
    @FXML
    private  DatePicker PMIOutputHospital;
    @FXML
    private ComboBox PMIHospitalGrade;

    //处方信息
    @FXML
    private TextField PreFind;
    @FXML
    private Button PreDelete;
    @FXML
    /**
     *@描述 处方信息的删除按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPreDelete(){
        PrescriptionDetails pp = new PrescriptionDetails();

        pp.setOutpatientNumber(PreMenzhenhao.getText());
        pp.setDrugCode(PreDrugCode.getText());
        pdb.DeletePD(pdb.FindPD(pp.getOutpatientNumber()));
        PreTable.refresh();
    }
    @FXML
    private Button PreAdd;
    @FXML
    /**
     *@描述 处方信息的添加按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void  onPreAdd(){
        PrescriptionDetails ppp = new PrescriptionDetails();
        ppp.setDrugCode(PreDrugCode.getText());
        ppp.setNum(PreDrugNum.getText());
        ppp.setOutpatientNumber(PreMenzhenhao.getText());
        ppp.setPrice(PreDrugPrice.getText());
        String res = String.valueOf(Integer.parseInt(ppp.getNum()) * Double.parseDouble(ppp.getPrice()));
        ppp.setSumPrice(res);
        PreSumPrice.setText(ppp.getSumPrice());
        pdb.AddPD(ppp);
        PreTable.refresh();


    }
    @FXML
    private Button PreChange;
    @FXML
    /**
     *@描述 处方信息的修改按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPreChange(){
        PrescriptionDetails ppp = new PrescriptionDetails();
        ppp.setDrugCode(PreDrugCode.getText());
        ppp.setNum(PreDrugNum.getText());
        ppp.setOutpatientNumber(PreMenzhenhao.getText());
        ppp.setPrice(PreDrugPrice.getText());
        String res = String.valueOf(Integer.parseInt(ppp.getNum()) * Double.parseDouble(ppp.getPrice()));
        ppp.setSumPrice(res);
        PreSumPrice.setText(ppp.getSumPrice());
        pdb.DeletePD(pdb.FindPD(ppp.getOutpatientNumber()));
        pdb.AddPD(ppp);
        PreTable.refresh();
    }
    @FXML
    private Button PreDaochu;
    @FXML
    /**
     *@描述 处方信息的导出按钮操作
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void onPreDaochu(){

        try {
            pdb.WriteFile();
            PreState.setText("录入成功");
        } catch (IOException e) {
            PreState.setText("录入失败");
        }
    }
    @FXML
    private TextField PreState;
    @FXML
    private TextField PreMenzhenhao;
    @FXML
    private TextField PreSumPrice;
    @FXML
    private TextField PreDrugCode;
    @FXML
    private TextField PreDrugPrice;
    @FXML
    private TextField PreDrugNum;

    @FXML
    private TableColumn<PrescriptionDetails,String> ZhuyuanHaoColumn;
    @FXML
    private TableView<PrescriptionDetails> PreTable;
    @FXML
    private TableColumn<PrescriptionDetails,String> PreDrugCodeColumn;
    @FXML
    private TableColumn<PrescriptionDetails,String > PreDrugPriceColumn;
    @FXML
    private TableColumn<PrescriptionDetails,String> PreDrugNumColumn;
    @FXML
    private TableColumn<PrescriptionDetails,String>PreDrugSumColum;


    //结算
    @FXML
    private TextField ReFind;
    @FXML
    private TableView<PersonnelInformation> ReTable;
    @FXML
    private TableColumn<PersonnelInformation,String> ReTableID;
    @FXML
    private TableColumn<PersonnelInformation,String> ReTableName;
    @FXML
    private TextArea res;
    @FXML
    private TextField ReState;
    @FXML
    private Button YuJIE;
    @FXML/**
     *@描述 导出预结算和结算信息
     *@参数  []
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    private void  onYuJIE(){

        PrintWriter fileOutYU = null;
        try {
            fileOutYU = new PrintWriter(new FileWriter("预结算信息.txt"));
            String line = resYU;
            fileOutYU.println(line);

        } catch (IOException e) {
            ReState.setText("写入预结算信息失败");
        }
        fileOutYU.close();
        PrintWriter fileOutJIE = null;
        try {
            fileOutJIE = new PrintWriter(new FileWriter("结算信息.txt"));
            String line = resJie;
            fileOutJIE.println(line);
            fileOutJIE.close();
            ReState.setText("写入成功");
        } catch (IOException e) {
            ReState.setText("写入结算信息失败");
        }


    }
    @FXML
    private Button ReDaochu;


    private PersonnelInformationDB pidb = new PersonnelInformationDB();
    private ObservableList<PersonnelInformation> piol  = FXCollections.observableArrayList();
    private MedicalInformationDB midb = new MedicalInformationDB();
    private ObservableList<MedicalInformation> miol = FXCollections.observableArrayList();
    private PersonalMedcialInformationDB pmidb = new PersonalMedcialInformationDB();
    private ObservableList<PersonalMedcialInformation> pmiol = FXCollections.observableArrayList();
    private PrescriptionDetailsDB pdb = new PrescriptionDetailsDB();
    private ObservableList<PrescriptionDetails> pol = FXCollections.observableArrayList();
    public MyController() {
    }
    @Override
    @PostConstruct
    /**
     *@描述 默认运行
     *@参数  [location, resources]
     *@返回值 void
     *@创建人 贾敬哲
     *@创建时间 2018/8/2
     *@其他信息
     */
    public void initialize(URL location, ResourceBundle resources) {

        try {
            pidb.OpenFIle();
            piol = pidb.getPersonnelInformations();
        } catch (IOException e) {
            PersonDaochuState.setText("无法打开文件");
        }
        try {
            midb.OPenFile();
            miol = midb.getMedicalInformations();
        } catch (IOException e) {
            DrugState.setText("无法打开文件");
        } catch (DataFormatException e) {
            DrugState.setText("文件内信息格式错误");
        }
        try {
            pmidb.OpenFile();
            pmiol = pmidb.getPersonalMedcialInformations();
        } catch (IOException e) {
            PMIState.setText("无法打开文件");
        } catch (DataFormatException e) {
            PMIState.setText("文件内信息格式错误");
        }
        try {
            pdb.OpenFile();
            pol = pdb.getPrescriptionDetails();
        } catch (IOException e) {
            PreState.setText("无法打开文件");
        } catch (DataFormatException e) {

            PreState.setText("文件内信息格式错误");
        }
        chushihu();
        refushTable();
        // TODO (don't really need to do anything here).

    }
}