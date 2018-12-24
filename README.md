# GitU 开发记录
> [欢迎访问我的技术博客tech.viewv.top](https://tech.viewv.top)获取相关具体文档信息

## 前言

这是我的Java期末课程设计的作业，原本的构思是一个基于DL4J的类keras框架，可是后来发现Java在这方面实在是不行，文档简陋，难以继续，又想开发一个markdown的Java版本的编辑器，但是发现解析器很多都是基于ruby，js等等语言，几乎没有Java的相关的现成的解释器，有的也文档不全，自己写一个解释器的话目测很麻烦，自己也不喜欢文本编辑相关的工作，所以选择了这个项目，一个基于Jgit的一个Java的简单的Git-GUI客户端，能够实现基本的功能，基于eclipse基金会的开源项目Jgit，相对来说文档相对健全，也有很多例子可以参考，基于JavaFX，Java在桌面应用开发这方面私以为已经失败了，JDK10以后oracle也舍弃了JavaFx，交由开源社区开发，现在应该是叫OpenFx，在开发中感觉JavaFx没有很多人使用，相关文档和教程非常少，中文更少，但是我还是写了一点东西出来。

## 框架

这个软件最底层的就是Jgit和JavaFx，有了Jgit，用户可以不用安装Git，就可以管理库，这是有一定便利性的，在开发过程中，Java本身的界面可以说是很难看了，相比较与现在流行的前端框架，JavaFx不是很好看，虽然已经比AWT和Swing提升了许多，在搜索以后，我使用了[JFoenix](https://github.com/jfoenixadmin/JFoenix)这个开源组件来替代一些JavaFx的原声组件，这个组件可以实现一个Google Material Design的效果，是非常美观的，为了美观，我牺牲了使用系统自带的标题栏等等组件，制作了一个类似mac的前端，有很多缺点，但是看起来还不错。

![1543749587190](https://p2.cdn.img9.top/ipfs/Qme91WDrrcUTTefxRu4kDZd1fvCgRdsKNKR7Eb8ANWkgmf?2.png)
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fviewv%2FGitU.svg?type=shield)](https://app.fossa.io/projects/git%2Bgithub.com%2Fviewv%2FGitU?ref=badge_shield)

使用jdk-8开发，因为自带JavaFx，相关组件也很多，使用maven管理使用到的开源组件，在这里向Jgit，JFoenix，maven致谢，特别感谢[jgit-cookbook](https://github.com/centic9/jgit-cookbook)和[GitFx](https://github.com/jughyd/GitFx)，这两个库中的代码对我帮助很大。

## 环境

软件可以在Linux平台下，在JDK-8的Java环境下运行，由于Linux平台的广泛不保证所有发行版本均可以运行，在开发中已经考虑到跨平台，在编写时内部代码均无与系统底层相关，所以理论上也可以在其他系统上运行，软件使用字体理论上已经实现内置加载，但是考虑到平台的复杂性，如果软件字体显示效果差，请手动安装DejaVuSansMono-Bold字体保证友好的用户界面。

建议分辨率在不低于1366*768的电脑使用，目前软件没有在很多平台上测试，不确定在分辨率较低的平台运行会出现怎样的效果，建议使用1080p的屏幕使用。

## 使用

在release中下载最新的release jar文件，或者克隆本库，在啊target文件夹下找到GitU.jar包，终端中使用java -jar GitU.jar命令来运行应用，在终端中会有一些相关开发信息。

顶边栏为功能区，按钮就是所有功能。

特别说明，三个圆形按钮的功能类似mac中的红绿灯功能：

- 红色：关闭应用
- 黄色：最小化
- 绿色：最大化

最左边的一栏显示的是用户已经打开的库，点击可以切换，中部为commit message的汇总，点击以后可以在最左边栏显示这次commit的文件信息。

底边栏为快速信息查看栏目，第一个是当前Branch名字，第二个是最新一次commit的SHA信息。

## 最后

软件仍然处于开发状态，理想情况下我还会继续维护，这是一个最早的版本，只是能用水平，能够完成基础的git操作，希望未来能够覆盖到所有的操作。



## License
[![FOSSA Status](https://app.fossa.io/api/projects/git%2Bgithub.com%2Fviewv%2FGitU.svg?type=large)](https://app.fossa.io/projects/git%2Bgithub.com%2Fviewv%2FGitU?ref=badge_large)