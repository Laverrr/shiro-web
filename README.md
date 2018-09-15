web.xml里面，对于每一段的配置都有详细的注释。

从spring-xml开始，一步一步看配置，再去看里面自定义的bean和property

logback.xml是slf4j的配置文件。

启动项目前，要先启动redis，spring-redis.xml是redis的配置文件。redis默认端口是6379。

spring-context-shiro.xml 是将shiro配置从spring.xml里提取出来，本项目中并没有这么做，所以不用理会。

//todo shiro验证的整个流程
