package ru.geometrylib;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ru.geometrylib.datamodel.GeometryUtilsTest;
import ru.geometrylib.datamodel.RayTest;
import ru.geometrylib.datamodel.RayTracerTests;
import ru.geometrylib.datamodel.SceneLoaderTest;

@RunWith(Suite.class)
@SuiteClasses({ SceneLoaderTest.class, RayTest.class, GeometryUtilsTest.class, RayTracerTests.class })
public class AllTests
{

}
