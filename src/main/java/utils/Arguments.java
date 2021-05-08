package utils;

import java.util.LinkedHashMap;

public class Arguments extends LinkedHashMap<String, String> {

    private String prefix = "-";

    public Arguments(){
    }

    public Arguments(String prefix){
        this.prefix = prefix;
    }

    public static Arguments parse(String... args){
        Arguments arguments = new Arguments();
        arguments.load(args);
        return arguments;
    }

    public void load(String... args){
        String key = null;
        int keyPosition = 0;
        for (String arg: args) {
            if (arg.startsWith(prefix)){
                if ( key != null && this.get(key) == null){
                    this.put(key, null);
                }
                key = arg;;
            } else {
                this.put( key != null ? key: String.valueOf(keyPosition++), arg);
            }
        }
    }
}
