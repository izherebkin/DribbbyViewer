package com.jetruby.dribbbyviewer;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * This is main starting tests class.
 * It is important to run Gradle tasks 'app:uninstallAll' or 'app:uninstallDebug' before running it.
 * Also you can just uninstall app (if it's installed) from testing device with standard tools before each TestSuite run.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses ({
        GridViewActivityFirstLaunchTest.class,
        GridViewActivityNextLaunchTest.class
})
public class TestSuite {
}
