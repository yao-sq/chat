package utils;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ArgumentsTest {
    @Test
    void loadAppArgs() {
        String[] arguments = "-cca 192.168.10.250 -ccp 14001".split(" ");
        Arguments appArgs = Arguments.parse(arguments);

        assertEquals("192.168.10.250", appArgs.get("-cca"));
        assertEquals("14001", appArgs.get("-ccp"));
    }


    // a -p 1 --port 2 b
    // {"0": "a", "-p": "1", "--port": "2", "1": "b"}
    @Test
    void loadImaginaryMap(){
        String[] arguments = "a -p 1 --port 2 b".split(" ");
        Arguments args = Arguments.parse(arguments);

        assertEquals("1", args.get("-p"));
        assertEquals("2", args.get("--port"));
    }

}
