package utils;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {

    // a -p 1 --port 2 b
    // {"0": "a", "-p": "1", "--port": "2", "1": "b"}
    @Test
    void loadAppArgs() {
        String[] arguments = "-cca 192.168.10.250 -ccp 14001".split(" ");
        Arguments appArgs = Arguments.parse(arguments);
        String hostname = appArgs.get("-cca");
        String port = appArgs.get("-ccp");

        System.out.println( appArgs);

        assertEquals("192.168.10.250", hostname);
        assertEquals("14001", port);
    }

    @Test
    void loadImaginaryMap(){
        String[] arguments = "a -p 1 --port 2 b".split(" ");
        for (String s: arguments) {
            System.out.println(s);
        }

        System.out.println(Arrays.stream(arguments).count());
        Arguments args = Arguments.parse(arguments);
        System.out.println( args);
    }

}
