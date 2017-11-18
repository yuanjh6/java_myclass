自己的常用类
com.yuan.common:
DBase：单例模式的数据库connection，将数据库结果ResultSet转为map形式
MConfig：从配置文件路径得到配置文件，读取配置文件的内容得到配置信息，集中系统所有配置信息 文件格式为 key=value。单例模式。
MMap:带有计数功能的map，可以统计key被put了几次（好像是计算tf-idf时使用到的），通过维护一个count计数map实现
MMap0:功能同上，但是实现不同，通过新建一个vo类作为value值保存在map的value字段实现。扩展性更好

com.yuan.demo:
multiThreadTest:对webservice接口做测试，需测试高并发接口是否会以异常数据返回，编写了如下demo，有类似需求的可以参考.
@link:http://blog.csdn.net/u011331731/article/details/78567249
rmi:RMI远程接口调用的demo
simulationLoginOn：使用httpclient实现模拟网站登录的功能。
socket：socker的server和client样例
SVM:svm的癌症预测算法，调用使用libsvm实现。
video：提取视频的第一帧，视频基本信息（长度，时分秒，长宽），视频最后一帧，视频特定桢提取
DTree：决策时的简单demo
new String[] { "大于1.2", "是", "否", "是" },
new String[] { "小于0.8", "否", "是", "是" },

httpUtil：HttpUnit是一个可以自动化测试工具，可以获取网页js执行后的内容
ID3：ID3算法
Json：json数据的使用demo
JsoupTest：jsoup是一个分析xml的工具，用来分析网页避免书写正则表达式，可用js的选择器语法进行书写。这个是使用demo
miniBrowser：小型浏览器，调用eclipse的图形组件swt
multiThread：多线程demo
romeTest：rome是解析rss的工具包
SplitWords：分词工具

specialUse：特定工具
baidujsAD：获取百度的广告

tools：顾名思义即可
dom4jtool
function:map和str互转，字符串判空，计算字符串相关度等，url格式化，计算欧式距离，cos距离
htmlUnitTool：
HttpClientTool：
ImageTools：图像灰度矩阵，一位灰度直方图，灰度直方图距离
JsoupTools：
ReferOrder：参考排序，使用一个list的顺序对另一个list排序，不记得使用场景了
Webtool：探测网页字符集，获取title等
xmltool：

web：
proxy：代理服务器
Mregexp：正则表达式相关
MWebpage；传入charset, headTag, footTag, splitTag, regexp, regexpInfo信息，提取网页的正则表达式匹配信息（典型的应用在搜索引擎的条条型，目录型网页上）




