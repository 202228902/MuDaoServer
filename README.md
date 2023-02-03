# SeversSockrt
基于Java原生ServerSocket和I/O流进行登录注册接口实现，可部署服务端前端直接调用接口

# 可以通过使用线程池和循环来对服务端进行简单优化
# 采用get方式进行访问
# -------------------必看-------------------
# 必须修改RequestData中 //用户目录位置
```java
    private final String userPath = "E:\\Users\\";
    请修改为你自己的，否则会报错，这里以Windows系统路径测试，其它系统请使用其它系统路径格式
```
   ![](https://img.mdcvs.cn/i/2023/02/02/i8ui08.png)

    
# 用户保存位置方式
  例子一个用户 账号为123456
  那么在本地的文件保存为E:\\Users\\123456
  密码保存为E:\\Users\\123456\\password.txt
  注册的时候会通过数据分析自动创建账户和密码文件夹和文件（采用IO流）
  
  ![](https://img.mdcvs.cn/i/2023/02/02/i7jhin.png)
    
# 接口格式
  ## 登录接口
  http://localhost/api/login?user=用户名&password=密码
  ## 注册接口 
  http://localhost/api/register?user=用户名&password=密码
  可以添加其它数据，按照格式添加 &[数据名]=[数据] 例如 http://localhost/api/register?user=用户名&password=密码&name=张山
  
# 接口格式解析
  http://localhost/api/login?user=用户名&password=密码
  localhost 为服务器IP
  api 文件夹
  login 文件夹下的login类
  问号后面为提交的数据
  
# 建议
 对前后端数据进行加密,例如aes加密。对数据作过期验证。
 

 
