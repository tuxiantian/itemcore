remote repository address:https://github.com/tuxiantian/itemcore.git  
模仿人工审核项目后端工程rgshcore建的项目  
com.tuxt.itemcore.util.thread包下面的类引入线程池处理数据库表中数据，为避免重复处理数据引入了带返回值的线程。 

BusiException的使用场景  
服务类S的a方法调用b方法（b方法是公共方法），b方法中抛出的异常，可以使用BusiException，将抛出的异常信息传递给a.  
在a的catch块中拿到异常信息塞到OutputObject对象的returnMessage中。exceptionId对应OutputObject对象的returnCode。