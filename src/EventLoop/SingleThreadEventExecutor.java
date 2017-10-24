package EventLoop;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadFactory;
import EventLoopGroup.EventLoopGroup;
/**
 * Created by jrj on 17-5-9.
 */
public abstract class SingleThreadEventExecutor {
    protected LinkedBlockingQueue<Runnable> taskQueue;
    abstract public void run();
    Thread thread;
    EventLoopGroup parent;
    ThreadFactory threadFactory;
    private boolean addTaskWakesUp;
    protected SingleThreadEventExecutor(
            EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
        this.parent = parent;
        this.addTaskWakesUp = addTaskWakesUp;
        this.threadFactory = threadFactory;
        taskQueue = new LinkedBlockingQueue<>(16);
        //thread在这里赋值与在后面用thread = Thread.currentThread();得到的结果一样吗？
        startThread();
    }

    private boolean inExecutorLoop(){
        if (thread.equals(Thread.currentThread())){
            return true;
        }else{
            return false;
        }
    }

    public void startThread(){
        thread = threadFactory.newThread(new Runnable() {
            @Override
            public void run() {
                //thread = Thread.currentThread();
                try{
                    SingleThreadEventExecutor.this.run();
                }catch(Exception t){
                    t.printStackTrace();
                    System.out.println("Unexpected exception from an event executor");
                }
            }
        });
    }

    public void execute(){
        if (inExecutorLoop()){
            try {
                Runnable curTask = taskQueue.take();
                curTask.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }else{
            //这里应该做什么？
        }
    }

    public void addTask(Runnable task){
        taskQueue.add(task);
    }
}
