package com.thebiginternatianalcompanu.app;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import junit.framework.TestCase;

import org.junit.Test;

public class MyAppTest extends TestCase
{
	@Test
	public void testLoadData() throws Exception
	{
		TextFileDataConnection data = new TextFileDataConnection(
				MyAppTest.class.getResourceAsStream("in.txt"));

		TextFileOutputDataConnection output = new TextFileOutputDataConnection(
				new File("out.txt"));

		MyApp app = new MyApp(1990, 2020, data, output);
		app.process();
		output.close();

		FileInputStream fis = new FileInputStream(new File("out.txt"));
		try
		{
			assertTrue(compareStreams(
					MyAppTest.class.getResourceAsStream("out.txt"),
					new FileInputStream(new File("out.txt"))));
		}
		finally
		{
			fis.close();
		}
	}

	private boolean compareStreams(InputStream in1, InputStream in2)
			throws Exception
	{
		while (true)
		{
			int i = in1.read(), j = in2.read();
			if (i != j)
				return false;
			if (i == -1)
				return true;
		}
	}

}
