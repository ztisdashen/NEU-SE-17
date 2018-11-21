package Page;
public class Commodity {
    private int ID;
    private String Name;
    private String Factory;
    private String Kind;
    private String Field;
    private String modelNumber;
    private String Description;

    private String imgPath;
    //无参构造器

    public Commodity() {
    }

    public Commodity(int ID, String name, String factory, String kind, String field, String modelNumber, String description, String imgPath) {
        this.ID = ID;
        Name = name;
        Factory = factory;
        Kind = kind;
        Field = field;
        this.modelNumber = modelNumber;
        Description = description;
        this.imgPath = imgPath;
    }
    //set||get

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getFactory() {
        return Factory;
    }

    public void setFactory(String factory) {
        Factory = factory;
    }

    public String getKind() {
        return Kind;
    }

    public void setKind(String kind) {
        Kind = kind;
    }

    public String getField() {
        return Field;
    }

    public void setField(String field) {
        Field = field;
    }

    public String getModelNumber() {
        return modelNumber;
    }

    public void setModelNumber(String modelNumber) {
        this.modelNumber = modelNumber;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
