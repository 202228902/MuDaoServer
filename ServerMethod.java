package MuDaoServer;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.net.URL;
import java.util.Objects;
//导入api文件夹下面的所有类，用于获取路径
import MuDaoServer.Controller.Annotations;
import MuDaoServer.api.*;

public class ServerMethod extends RequestData{
    //获取用户客户端信息
    public static void getClientData(Socket socket) throws IOException {
        //获取客户端内容
        InputStream getBrowserData = socket.getInputStream();
        //将字节流转换为字符流
        InputStreamReader change = new InputStreamReader(getBrowserData);
        BufferedReader reader = new BufferedReader(change);
        String val;
        //获取第一行，用于解析用户使用了哪个文件，提交了哪些数据
        String line1 = reader.readLine();
        RequestData.firstLineData=line1;
        //将第一行以空格进行分割分别得到提交方法，提交路径数据，http协议版本  GET / HTTP/1.1，这里我们只需要路径
        RequestData.data = line1.split(" ")[1];
        //判断获取的路径是否包含/api/数据，不包含则返回错误json,目前只提供api文件夹下的文件
        if (RequestData.data.contains("/api/")){
            //将问号前面的数据放到RequestData中的path中
            RequestData.path=RequestData.data.split("\\?")[0];
            //判断请求的类是否存在 GET /api/login?user=202228902&password=QWERTY HTTP/1.1
            String className = RequestData.data.split("\\?")[0].split("/")[2];//获取类名
            String path = ServerMethod.class.getResource("./") +"api/"+className+".class";
            path = path.substring(6);
            path = path.replaceAll("/","\\\\\\\\");
            //判断文件是否存在
            if (fileExists(path)){
                //把全限定名放到数据中
                String[] split = ServerMethod.class.getResource("./").toString().split("/");
                String fullName = split[split.length - 1]+".api."+className;
                RequestData.FullName=fullName;
                System.out.println("全限定"+fullName);
            }else {
                val = "{\"code\":\"-1\",\"msg\":\"该接口不存在\"}";
                outBrowse(socket,val);
            }
        }else {
            val = "{\"code\":\"-1\",\"msg\":\"该接口不存在\"}";
            outBrowse(socket,val);
        }
    }
    //反射
    public static String reflex(String fullName) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clazz = Class.forName(fullName);
        String invoke = "{\"code\":\"-1\",\"msg\":\"反射出错\"}";;
        //判断是否使用了注解
        if (clazz.isAnnotationPresent(Annotations.class)) {
            Annotations annotation = clazz.getAnnotation(Annotations.class);
            //获取注解的value
            String value = annotation.value();
            //判断注解中路径是否正确，
            if (value.equals(RequestData.path)) {
                //通过反射创建该servlet对象，然后调用该对象的service方法
                Object instance = clazz.getDeclaredConstructor().newInstance();
                Method method = clazz.getDeclaredMethod("service");
                //把类型强转为字符串
                invoke = (String) method.invoke(instance);
            }
        }
        return invoke;
    }
    //通用输出到网页
    public static void outBrowse(Socket socket,String value) throws IOException {
        String stringBuilder = "HTTP/1.1" + " " + 200 + " " +
                "ok" + "\n" +
                "Content-Type" + ":" + "text/html;charset=UTF-8" + "\n" +
                "\n" + value;
        OutputStream outputStream = socket.getOutputStream();
        PrintWriter output = new PrintWriter(outputStream);
        output.println(stringBuilder);
        output.flush();
        outputStream.close();
    }
    //判读文件或者文件夹是否存在
    public static boolean fileExists(String path) {
        File file = new File(path);
        return file.exists();
    }
    //创建一个文件
    public static boolean createFile(String path) throws IOException {
        File file = new File(path);
        return file.createNewFile();
    }
    //获取api文件夹下的类路径
    public static String classPath(String className) {

        URL url = login.class.getResource("./");
        return "";
       
    }
}
