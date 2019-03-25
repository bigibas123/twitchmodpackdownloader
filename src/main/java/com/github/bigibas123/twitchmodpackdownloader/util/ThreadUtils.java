package com.github.bigibas123.twitchmodpackdownloader.util;

import com.github.bigibas123.twitchmodpackdownloader.Log;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class ThreadUtils{

    public static <T extends Runnable> void dosedRunAwait(ArrayList<T> ar, int concurrency){
        ArrayList<ArrayList<RunnableContainer<T>>> masterlist = new ArrayList<>();
        ArrayList<RunnableContainer<T>> current = new ArrayList<>();
        for (T t:ar){
            if(current.size()>=concurrency){
                masterlist.add(current);
                current = new ArrayList<>();
            }
            current.add(new RunnableContainer<>(t));
        }
        Log.getLog().debug("Created arraylist size:" + masterlist.size());
        Log.getLog().debug("Has sublists of size:" + masterlist.get(0).size());
        for(ArrayList<RunnableContainer<T>> tlist: masterlist){
            for(RunnableContainer<T> t:tlist){
                t.start();
            }
            for (RunnableContainer<T> t:tlist){
                t.await();
            }

        }
    }

    public static class RunnableContainer<T extends Runnable> extends Thread{

        private final Runnable runnable;
        private final CountDownLatch latch;

        public RunnableContainer(T runnable) {
            this.runnable = runnable;
            this.latch = new CountDownLatch(1);
        }

        @Override
        public void run() {
            this.runnable.run();
            this.latch.countDown();
        }

        public void await(){
            try {
                this.latch.await();
            } catch (InterruptedException e) {
                Log.printException(e);
            }
        }
    }
}
