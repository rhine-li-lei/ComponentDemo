# GradlePluginDemo

---

用于学习gradle插件开发的Hello world 版代码。

以下是开发中的要点：
1. 配置插件的 build.gradle，这里配置的是的本地仓库
    

    仓库说明：
      1. flatDir
      2. ivy仓库
      3. maven仓库
      4. Bintray的JCenter仓库
      5. mavenCentral，这个类似jcenter
      6. mavenLocal，这个是一个默认的本地仓库，具体位置可以进行配置,如果没有配置默认是在(user)/.m2/repository
    
同时在这里需要配置 group 和 version ，都是project属性

2. 编写插件
3. 编写入口地址
4. 使用运行uploadArchives这个task把我们的插件打包成jar，记住是执行两次task，才会成功上传到本地
5. 在项目的build.gradle里面配置插件
6. app.gradle里面配置
    

学习参考链接：
1. [Gradle插件开发](https://www.jianshu.com/p/3c59eded8155)
2. [Android 自定义Gradle插件基础](https://www.jianshu.com/p/eda0bfd692e6)



