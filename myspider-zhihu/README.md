## It's a simple spider for zhihu!

## feature
在[配置文件](https://github.com/flysall/projects-for-training/blob/master/myspider-zhihu/config.properties)中可做如下配置：
1. 支持将用户信息保存到本地文件，否则保存到数据库中，并在控制台打印用户信息；
2. 若将用户信息保存至数据库，请先在数据库中创建[user](https://github.com/flysall/projects-for-training/blob/master/myspider-zhihu/createtable.sql)表；
3. 通过持久化cookie使得第一次登录之后， 以后再次运行程序可免登录；
4. 可限制爬取的用户数目（在配置文件中配置）;