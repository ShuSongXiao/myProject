package thread.executorService;

import com.xss.util.A;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import static com.xss.util.A.lists;

/**
 * Created by Administrator on 2017/8/2 0002
 */
public class ExecutorServiceTest {

    public static void main(String [] str){
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<Future<String>> futureList = lists();

        //创建任务并执行
        for(int i = 1;i < 10 ; i ++){
            Future<String> result = executorService.submit(new TaskWikiResult(i));
            futureList.add(result);
        }

        executorService.shutdown();
        for(Future future : futureList){
            try{
                System.out.println(future.get());
            }catch (InterruptedException e){
                e.printStackTrace();
            } catch (ExecutionException e){
                executorService.shutdown();
                e.printStackTrace();
                return;
            }
        }
    }



}

class TaskWikiResult implements Callable<String> {

    private int id;

    public TaskWikiResult(int id){
        this.id = id;
    }

    @Override
    public String call() throws Exception {
        System.out.println("call()方法被执行："+Thread.currentThread().getName());
        if(new Random().nextBoolean()){
            throw new TaskException("发生错误："+Thread.currentThread().getName());
        }
        // 一个模拟耗时的操作
        for (int i = 999999999; i > 0; i--)
            ;
        return "call()方法被自动调用，任务的结果是：" + id + "    " + Thread.currentThread().getName();
    }
}

class TaskException extends Exception{

    public TaskException(String message){
        super(message);
    }
}