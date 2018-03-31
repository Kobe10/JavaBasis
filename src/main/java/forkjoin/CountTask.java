package forkjoin;

import java.util.List;
import java.util.concurrent.RecursiveTask;

public class CountTask extends RecursiveTask<Integer>{
    private static int THRESHOLD=3;
    private int[] integers;
    private int start;
    private int end;

    public CountTask(int[] integers,int start,int end){
        this.integers=integers;
        this.start=start;
        this.end=end;
    }

    @Override
    protected Integer compute() {
        int len=end-start+1;
        boolean isOverThreshold=len>THRESHOLD;
        int sum=0;
        if(!isOverThreshold){
           for(int i=start;i<=end;i++){
               sum+=integers[i];
           }
        }else{
            int mid=(start+end)/2;
            CountTask leftTask=new CountTask(this.integers,start,mid);
            CountTask rightTask=new CountTask(this.integers,mid+1,end);
            invokeAll(leftTask,rightTask);
            //fork的作用是将当前任务放到workerThread里面去做
            //invokeAll是将其中一个放在本线程做，其他的调用fork
            int leftResult=leftTask.join();
            int rightResult=rightTask.join();
            sum=leftResult+rightResult;

        }
        return sum;
    }
}
