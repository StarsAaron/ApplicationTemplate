package com.aaron.thread;

import android.os.Handler;
import android.os.Message;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * 每一个任务在线程池开启一个线程处理
 *
 * 注意：依赖ThreadExecutorFactory.java
 */
public class TaskThreadPool {

    /**
     * 单例对象 The http pool.
     */
    private static TaskThreadPool TaskThreadPool = null;

    /**
     * 线程执行器.
     */
    private static Executor mExecutor = null;

    /**  存放返回的任务结果. */
    private static HashMap<Integer,List<Object>> result;

    /** 下载完成后的消息句柄. */
    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            TaskItem item = (TaskItem)msg.obj;
            item.finished(result.get(item.getTag()));
            result.remove(item.getTag());
        }
    };

    /**
     * 构造线程池.
     */
    private TaskThreadPool() {
        result = new HashMap<Integer,List<Object>>();
        mExecutor = ThreadExecutorFactory.getExecutor();
    }

    /**
     * 单例
     */
    public static TaskThreadPool getInstance() {
        if (TaskThreadPool == null) {
            TaskThreadPool = new TaskThreadPool();
        }
        return TaskThreadPool;
    }

    /**
     * 开启线程执行任务
     * @param item
     */
    public void execute(final TaskItem item) {
        mExecutor.execute(new Runnable() {
            public void run() {
                try {
                    if (item != null) {
                        result.put(item.getTag(), item.execute());
                        //交由UI线程处理
                        Message msg = handler.obtainMessage();
                        msg.obj = item;
                        handler.sendMessage(msg);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
