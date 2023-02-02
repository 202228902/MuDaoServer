package MuDaoServer.api;

import MuDaoServer.Controller.Annotations;
import java.io.*;
import java.util.Arrays;

@Annotations("/api/listUser")
public class listUser {
    public String service() throws IOException {
        File file = new File("E:\\Users");
        File[] files = file.listFiles();
        String value =Arrays.toString(files);
        System.out.println(value);
        return value;
    }

}
