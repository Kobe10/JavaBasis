package forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ForkjoinTest {
    public static void main(String[] args){
        int[] integers={1,2,3,4,5,6,7,8,9,10};
        ForkJoinPool forkJoinPool=new ForkJoinPool(4);
        ForkJoinTask<Integer> task=new CountTask(integers,0,9);
        int sum=forkJoinPool.invoke(task);
        System.out.println(sum);
    }
}
