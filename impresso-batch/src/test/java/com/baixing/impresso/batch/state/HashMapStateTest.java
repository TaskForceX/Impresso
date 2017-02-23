package com.baixing.impresso.batch.state;

import com.google.common.collect.ImmutableList;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

/**
 * Created by onesuper on 22/02/2017.
 */
public class HashMapStateTest {
    @Test
    public void testMultiUpdate() throws Exception {
        HashMapState<String, Integer> hmState = new HashMapState<String, Integer>();

        hmState.multiPut(ImmutableList.of("a", "b", "c"),
                ImmutableList.of(1, 2, 3));

        List<Integer> olds = hmState.multiUpdate(ImmutableList.of("a", "c"),
                ImmutableList.of(new ValueUpdater<Integer>() {
                    @Override
                    public Integer update(Integer stored) {
                        return stored * 2;
                    }
                }, new ValueUpdater<Integer>() {
                    @Override
                    public Integer update(Integer stored) {
                        return stored * 3;
                    }
                }));

        Assert.assertEquals(hmState.multiGet(ImmutableList.of("a", "b", "c")),
                ImmutableList.of(2, 2, 9));
        Assert.assertEquals(olds, ImmutableList.of(1, 3));
    }

    @Test
    public void testMultiGet() throws Exception {
        HashMapState<String, Integer> hmState = new HashMapState<String, Integer>();

        hmState.multiPut(ImmutableList.of("a", "b", "c"),
                ImmutableList.of(1, 2, 3));

        Assert.assertEquals(hmState.multiGet(ImmutableList.of("a", "b", "c")),
                ImmutableList.of(1, 2, 3));

        Assert.assertEquals(hmState.multiGet(ImmutableList.of("b", "c")),
                ImmutableList.of(2, 3));

        Assert.assertEquals(hmState.multiGet(ImmutableList.of("b")),
                ImmutableList.of(2));


        Object a = new Integer(45);
        System.out.println(new JSONObject().put("a", 1L).put("b", a).toString());
    }

}