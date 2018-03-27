package guavafuture;

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

    public void shutdownThreadPool(){
        threadPool.shutdown();
    }
}
