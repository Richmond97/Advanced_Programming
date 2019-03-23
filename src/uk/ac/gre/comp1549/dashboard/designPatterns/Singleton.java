package uk.ac.gre.comp1549.dashboard.designPatterns;

public class Singleton { //Singleton design pattern class
    private static Singleton instance = null;
    
    private Singleton(){}
    
    public static synchronized Singleton getInstance(){
     if (instance == null){
         instance = new Singleton();
     }   
     return instance;
    }
    
    
    
    
    
}
