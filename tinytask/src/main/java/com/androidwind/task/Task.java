package com.androidwind.task;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public abstract class Task<T> extends TaskRunnable {
    public Task() {
    }

    public Task(Priority priority) {
        super(priority);
    }

    public abstract T doInBackground();

    public abstract void onSuccess(T t);

    public abstract void onFail(Throwable throwable);

    @Override
    public void run() {
        // System.out.println("[Task] compare: priority = " + getPriority() + ", taskName = " + getTaskName());
        try {
            final T t = doInBackground();
            TinyTaskExecutor.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    onSuccess(t);
                }
            });
        } catch (final Throwable throwable) {
            TinyTaskExecutor.getMainThreadHandler().post(new Runnable() {
                @Override
                public void run() {
                    onFail(throwable);
                }
            });
        }
    }
}