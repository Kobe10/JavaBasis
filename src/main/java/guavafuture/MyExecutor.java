package guavafuture;

import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

public class MyExecutor {
    private ExecutorService threadPool;
    
    public MyExecutor(){
        threadPool= Executors.newFixedThreadPool(10);
    }

    
    public void testJdkFuture() throws ExecutionException, InterruptedException {
        final String taskName="jdkFuture";
        Future<String> future=threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                ThreadTask threadTask=new ThreadTask(taskName);
                return threadTask.getExcuteResult();
            }
        });

        System.out.println(String.format("%s: 获取结果前----",taskName));
        System.out.println(future.get());
        System.out.println(String.format("%s: 获取结果后----",taskName));
    }

    /**
     * 方法1：将Future装换成LisenableFuture
     */
    public void testGuavaLisenableFuture1(){

        final String taskName1="guavaTask1";
        Future<String> future=threadPool.submit(new Callable<String>(){

            @Override
            public String call() throws Exception {
                ThreadTask threadTask=new ThreadTask(taskName1);
                return threadTask.getExcuteResult();
            }
        });

        ListenableFuture<String> listenableFuture=JdkFutureAdapters.listenInPoolThread(future);
        System.out.println(String.format("%s: callback执行之前----",taskName1));
        Futures.addCallback(listenableFuture, new FutureCallback<String>() {

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        System.out.println(String.format("%s: callback执行之后----",taskName1));
    }

    /**
     * 将线程池装饰成可监听的线程池，从中获取可监听的Future
     */
    public void testGuavaLisenableFuture2(){
        final String taskName2="guavaTask2";

        ListeningExecutorService listeningThreadPool=MoreExecutors.listeningDecorator(threadPool);
        ListenableFuture<String> listenableFuture=listeningThreadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                ThreadTask threadTask=new ThreadTask(taskName2);
                return threadTask.getExcuteResult();
            }
        });
        System.out.println(String.format("%s: callback执行之前----",taskName2));
        //获取结果方法1：可以在结果返回的时候使用listener线程获取结果
        listenableFuture.addListener(new Runnable() {
            @Override
            public void run() {
                try {
                    String result=listenableFuture.get();
                    System.out.println(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        },MoreExecutors.directExecutor());


        //获取结果方法2：使用callback获取结果和处理异常
        Futures.addCallback(listenableFuture,new FutureCallback<String>(){

            @Override
            public void onSuccess(String result) {
                System.out.println(result);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
            }
        });
        System.out.println(String.format("%s: callback执行之后----",taskName2));
    }

    /**
     * 测试Futretask
     */
    public void testFutureTask() throws ExecutionException, InterruptedException {
        FutureTask<String> futureTask=new FutureTask<String>(new Callable<String>(){

            @Override
            public String call() throws Exception {
                Thread.sleep(100);
                return "futureTask result!";
            }
        });
        threadPool.submit(futureTask);
        String result=futureTask.get();
        System.out.println(result);
    }

    public void shutdownThreadPool(){
        threadPool.shutdown();
    }
}
