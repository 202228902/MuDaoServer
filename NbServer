package MuDaoServer;

import java.net.ServerSocket;
import java.net.Socket;

public class NbServer extends ServerMethod{
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(80);
        Socket socket = serverSocket.accept();
        //处理前端数据
        getClientData(socket);
        //反射获得需要输出的数据
        String val = reflex(RequestData.FullName);
        //输出数据
        ServerMethod.outBrowse(socket,val);
        socket.close();
    }
}
