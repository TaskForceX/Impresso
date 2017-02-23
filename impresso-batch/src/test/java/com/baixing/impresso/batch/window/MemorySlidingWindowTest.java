package com.baixing.impresso.batch.window;

import com.google.common.collect.ImmutableList;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by onesuper on 06/02/2017.
 */
public class MemorySlidingWindowTest {
    @Test
    public void testSlide() throws Exception {

        MemorySlidingWindow<Integer> slidingWindow = new MemorySlidingWindow<>(
                ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).iterator(), 5);

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(0), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(0), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(0, 1), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(1), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(0, 1, 2), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(2), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(0, 1, 2, 3), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(3), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(0, 1, 2, 3, 4), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(4), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getRetired());


        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(1, 2, 3, 4, 5), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(0), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(5), slidingWindow.getAdmitted());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(2, 3, 4, 5, 6), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(1), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(6), slidingWindow.getAdmitted());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(3, 4, 5, 6, 7), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(2), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(7), slidingWindow.getAdmitted());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(4, 5, 6, 7, 8), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(3), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(8), slidingWindow.getAdmitted());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(5, 6, 7, 8, 9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(4), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(9), slidingWindow.getAdmitted());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(6, 7, 8, 9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(5), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(7, 8, 9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(6), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(8, 9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(7), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getAdmitted());
        Assert.assertEquals(ImmutableList.of(8), slidingWindow.getRetired());

        slidingWindow.slide();
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(9), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getAdmitted());

        Assert.assertFalse(slidingWindow.canSlide());
    }

    @Test
    public void testSlideBy2() throws Exception {

        MemorySlidingWindow<Integer> slidingWindow = new MemorySlidingWindow<>(
                ImmutableList.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9).iterator(), 5);

        slidingWindow.slideBy(2);
        Assert.assertEquals(ImmutableList.of(0, 1), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(0, 1), slidingWindow.getAdmitted());

        slidingWindow.slideBy(2);
        Assert.assertEquals(ImmutableList.of(0, 1, 2, 3), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(2, 3), slidingWindow.getAdmitted());

        slidingWindow.slideBy(2);
        Assert.assertEquals(ImmutableList.of(1, 2, 3, 4, 5), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(0), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(4, 5), slidingWindow.getAdmitted());

        slidingWindow.slideBy(2);
        Assert.assertEquals(ImmutableList.of(3, 4, 5, 6, 7), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(1, 2), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(6, 7), slidingWindow.getAdmitted());

        slidingWindow.slideBy(2);
        Assert.assertEquals(ImmutableList.of(5, 6, 7, 8, 9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(3, 4), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(8, 9), slidingWindow.getAdmitted());

        slidingWindow.slideBy(2);
        Assert.assertEquals(ImmutableList.of(7, 8, 9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(5, 6), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getAdmitted());

        slidingWindow.slideBy(2);
        Assert.assertEquals(ImmutableList.of(9), slidingWindow.getActive());
        Assert.assertEquals(ImmutableList.of(7, 8), slidingWindow.getRetired());
        Assert.assertEquals(ImmutableList.of(), slidingWindow.getAdmitted());
    }

}