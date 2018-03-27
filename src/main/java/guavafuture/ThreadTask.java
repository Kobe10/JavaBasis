package guavafuture;

public class ThreadTask {
    private String taskName;

    public ThreadTask() {
    }

    public ThreadTask(String taskName) {
        this.taskName = taskName;
    }

    public String getExcuteResult() {
        System.out.println(String.format("%s: start to get result", taskName));
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("%s: get result success", taskName);
    }
}
