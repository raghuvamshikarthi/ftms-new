package com.example.ftmsnew.cloudhelper;

import java.util.Comparator
import java.util.concurrent.*


class PriorityExecutor(corePoolSize: Int, maximumPoolSize: Int, keepAliveTime: Long, unit: TimeUnit) :
    ThreadPoolExecutor(
        corePoolSize,
        maximumPoolSize,
        keepAliveTime,
        unit,
        PriorityBlockingQueue(11, PriorityTaskComparator<Important>())
    ) {

    override fun <T> newTaskFor(runnable: Runnable, value: T): RunnableFuture<T> {
        return if (runnable is Important)
            PriorityTask(runnable.priority, runnable, value)
        else
            PriorityTask(0, runnable, value)
    }

    abstract class Important : Runnable {
        internal val priority: Int
            get() = 0

        abstract override fun run()
    }

    private class PriorityTask<T> : FutureTask<T>, Comparable<PriorityTask<T>> {
        private val priority: Int

        constructor(priority: Int, tCallable: Callable<T>) : super(tCallable) {
            this.priority = priority
        }

        constructor(priority: Int, runnable: Runnable, result: T) : super(runnable, result) {
            this.priority = priority
        }

        override fun compareTo(o: PriorityTask<T>): Int {
            val diff = (o.priority - priority).toLong()
            return if (0L == diff) 0 else if (0 > diff) -1 else 1
        }
    }

    private class PriorityTaskComparator<T> : Comparator<Runnable> {
        override fun compare(left: Runnable, right: Runnable): Int {
            return (left as PriorityTask<T>).compareTo(right as PriorityTask<T>)
        }
    }
}
