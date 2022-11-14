import java.util.LinkedHashMap;
import java.util.Set;
import java.util.TreeSet;

public class Singleton{
    private static volatile Singleton instance = null;
    private Singleton(){
    }
    public static Singleton getInstance(){
        if(instance == null){
            synchronized (Singleton.class){
                instance = new Singleton();
            }
        }
        return instance;
    }
    public static void main(String[] args){
        System.out.print(Singleton.getInstance());
    }
}
