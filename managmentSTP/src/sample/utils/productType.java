package sample.utils;

public enum productType {
    Type1,Type2,Type3,Type4;

    private productType(){}

    public String value(){
        return name();
    }

    public static productType fromValue(String v){
        return valueOf(v);
    }
}
