package MuDaoServer.api;

import MuDaoServer.Controller.Annotations;
import MuDaoServer.RequestData;
import java.io.*;
import java.util.HashMap;

import static MuDaoServer.ServerMethod.fileExists;

@Annotations("/api/register")
public class register {
    public String service() throws IOException {
        String json = "";
        //获取data的数据
        String data = RequestData.data.split("\\?")[1];
        System.out.println("data:"+data);
        //对data数据进行分析
        HashMap<String, String> dataMap = new HashMap<>();//存放get请求的数据
        String[] split = data.split("&");
        for (String s : split){
            String[] split1 = s.split("=");
            dataMap.put(split1[0],split1[1]);
        }
        //判断必填数据是否存在
        if (dataMap.containsKey("user") && dataMap.containsKey("password")){
            //判断用户是否存在
            RequestData requestData = new RequestData();
            if (!fileExists(requestData.getUserPath()+dataMap.get("user"))){
                String path1 = requestData.getUserPath()+dataMap.get("user");
                File file = new File(path1);
                //创建用户文件夹
                boolean mkdirs = file.mkdirs();
                if (mkdirs){
                    //创建文件
                    FileOutputStream out = new FileOutputStream(path1 + "\\\\\\\\password.txt");
                    //写入文件
                    out.write(dataMap.get("password").getBytes());
                    out.close();
                    json = "{\"code\":\"200\",\"msg\":\"账号注册成功\"}";
                }
            }else {
                json = "{\"code\":\"-1\",\"msg\":\"该账号已存在\"}";
            }
        }else {
            json = "{\"code\":\"-1\",\"msg\":\"提交参数不完整\"}";
        }
        return json;
    }

}
