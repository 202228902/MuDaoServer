package MuDaoServer.api;

import MuDaoServer.Controller.Annotations;
import MuDaoServer.RequestData;
import java.io.*;

@Annotations("/api/login")
public class login {
    public String service() throws IOException {
        String[] split = RequestData.data.split("&");
        String user = split[0].split("=")[1];
        String pass = split[1].split("=")[1];
        File file = new File("E:\\Users");
        String json = "";
        if (!file.exists()) {
            file.mkdirs();
            json="{\"code\":\"-1\",\"msg\":\"提交参数不完整\"}";
        }else {
            File file1 = new File("E:\\Users\\"+user);
            if (!file1.exists()) {
                json="{\"code\":\"-1\",\"msg\":\"用户不存在\"}";
            }else {
                BufferedReader bufferedReader = new BufferedReader(new FileReader("E:\\Users\\"+user+"\\password.txt"));
                if (pass.equals(bufferedReader.readLine())){
                    json="{\"code\":\"200\",\"msg\":\"登录成功\"}";
                }else {
                    json="{\"code\":\"-1\",\"msg\":\"密码错误\"}";
                }
            }
        }
        return json;
    }

}
