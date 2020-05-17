package me.suesslab.rogueblight.lib;

import junit.framework.TestCase;
import me.suesslab.rogueblight.aspect.HumanoidPose;
import me.suesslab.rogueblight.literary.EnumResourceWrapper;
import org.junit.Test;

public class EnumResourceWrapperTest extends TestCase {
    @Test
    public static void test() {
        EnumResourceWrapper<HumanoidPose> testWrapper = new EnumResourceWrapper<>(HumanoidPose.class);
        assert(!testWrapper.getString(HumanoidPose.DABBING).equals("[STRING NOT IN ENUM LOL]"));
    }
}