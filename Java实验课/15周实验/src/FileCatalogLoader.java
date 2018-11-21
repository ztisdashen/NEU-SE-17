

import  java.util.*;
import java.io.*;


public class FileCatalogLoader implements CatalogLoader {
    static final String DELIM = "_";

    //Coffee
    private Coffee readCoffee(String line) throws DataFormatException {
        StringTokenizer st = new StringTokenizer(line,DELIM);
        if (st.countTokens()!= 10 ) throw new DataFormatException(line);
        else{
            try {
                String prefix = st.nextToken();
                return  new Coffee(st.nextToken(),st.nextToken(),Double.parseDouble(st.nextToken()),st.nextToken(),st.nextToken(),
                        st.nextToken(),st.nextToken(),st.nextToken(),st.nextToken());
            }catch (NumberFormatException nfe){
                throw  new DataFormatException(line);
            }
        }
    }

    //coffee brewer
    private CoffeeBrewer readCoffeeBrewer(String line) throws DataFormatException {

        StringTokenizer st = new StringTokenizer(line, DELIM, false);
        if(st.countTokens()!=7){throw  new DataFormatException(line);}
        else{
            try {
                String prefix = st.nextToken();
                return new CoffeeBrewer(st.nextToken(),st.nextToken(),Double.parseDouble(st.nextToken()),
                        st.nextToken(),st.nextToken(),Integer.parseInt(st.nextToken()));
            }catch (NumberFormatException nfe){
                throw  new DataFormatException(line);
            }
        }
    }
    //Product
    private Product readProduct(String line) throws DataFormatException {
        StringTokenizer st = new StringTokenizer(line, DELIM, false);
        if (st.countTokens() != 4){throw new DataFormatException(line);}
        else {
            try {
                String prefix = st.nextToken();
                return new Product(st.nextToken(),st.nextToken(),Double.parseDouble(st.nextToken()));
            }catch (NumberFormatException nfe){
                throw  new DataFormatException(line);
            }
        }
    }

    @Override
    public Catalog loadCatalog(String fileName) throws FileNotFoundException, IOException, DataFormatException {
        Catalog catalog  = new Catalog();
        Product product = null;
       // fileName = "F:\\作业\\Java\\15周实验\\src\\"+fileName;
        DataInputStream in = new DataInputStream(new FileInputStream(fileName));
        BufferedReader d  = new BufferedReader(new InputStreamReader(in));
        String count ="";
        while((count = d.readLine()) != null){
            if(count.startsWith("Product")) product = readProduct(count);
            else if (count.startsWith("Coffee")) product = readCoffee(count);
            else if (count.startsWith("Brewer"))product = readCoffeeBrewer(count);
            else{
                throw  new DataFormatException(count);
            }
            catalog.addProduct(product);
        }
        d.close();

        return catalog;
    }
}