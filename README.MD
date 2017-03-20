remote repository address:https://github.com/tuxiantian/itemcore.git  
模仿人工审核项目后端工程rgshcore建的项目  
com.tuxt.itemcore.util.thread包下面的类引入线程池处理数据库表中数据，为避免重复处理数据，为一条待处理数据设计了3种状态：0表示未处理，1表示处理中，2表示已处理。 
数据入表状态为0，预占为处理中状态为1，处理完成状态为2。若让处理过的数据再次处理只需更新状态为0即可。为避免系统重启后，表中留有处理中的数据，先将处理中的数据标记为未处理。  
BusiException的使用场景  
服务类S的a方法调用b方法（b方法是公共方法），b方法中抛出的异常，可以使用BusiException，将抛出的异常信息传递给a.  
在a的catch块中拿到异常信息塞到OutputObject对象的returnMessage中。exceptionId对应OutputObject对象的returnCode。