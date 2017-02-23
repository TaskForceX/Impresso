package com.baixing.impresso.batch.window;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;

/**
 * Created by onesuper on 06/02/2017.
 */
public class MemorySlidingWindow<T> implements SlidingWindow {

    private final Logger logger = LoggerFactory.getLogger(MemorySlidingWindow.class);

    private Iterator<T> it;
    private Deque<T> active = new ArrayDeque<T>();
    private Deque<T> retired = new ArrayDeque<T>();
    private Deque<T> admitted = new ArrayDeque<T>();

    private final int windowSize;

    public MemorySlidingWindow(Iterator<T> generator, int windowSize) {
        this.it = generator;
        this.windowSize = windowSize;
    }

    public boolean canSlide() {
        return it.hasNext() || active.size() != 0;
    }

    /**
     * Returns an iterable collection of elements which are active till last slide
     */
    public Iterable<T> getActive() {
        return active;
    }

    /**
     * Returns an iterable collection of elements which are going out of the window till last slide
     */
    public Iterable<T> getRetired() {
        return retired;
    }

    /**
     * Returns an iterable collection of elements which are coming into the window till last slide
     */
    public Iterable<T> getAdmitted() {
        return admitted;
    }

    public void slideBy(int step) {

        admitted = new ArrayDeque<T>();
        retired = new ArrayDeque<T>();

        for (int i = 0; i < step; i++) {
            // Filling an incomplete window
            if (active.size() < windowSize && it.hasNext()) {

                T e = it.next();
                logger.trace("Fresh " + e);
                admitted.add(e);
                active.add(e);
                continue;
            }

            // Continuously sliding on the window
            if (active.size() == windowSize && it.hasNext()) {
                T e = it.next();
                logger.trace("Purge " + active.peekFirst() + ", Fresh ", e);
                admitted.add(e);
                retired.add(active.pop());
                active.add(e);
                continue;
            }

            // Purging an element from window
            if (active.size() > 0 && !it.hasNext()) {
                logger.trace("Purge " + active.peekFirst());
                retired.add(active.pop());
            }
        }

        if (logger.isDebugEnabled()) {
            logger.debug("Active: {}", active.toString());
        }
    }

    public void slide() {
        slideBy(1);
    }
}
