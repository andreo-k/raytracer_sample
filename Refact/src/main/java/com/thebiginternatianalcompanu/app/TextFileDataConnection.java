package com.thebiginternatianalcompanu.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextFileDataConnection implements DataConnection
{
	private InputStream fis;
	private BufferedReader reader;

	public TextFileDataConnection(InputStream input) throws Exception
	{
		fis = input;
		reader = new BufferedReader(new InputStreamReader(fis));
	}
	
	public TextFileDataConnection(File file) throws Exception
	{
		this(new FileInputStream(file));
	}

	public String[] readRecord()
	{
		try
		{
			String line = reader.readLine();
			if (line == null)
				return null;

			String[] columns = line.split("\t");

			return columns;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public void close()
	{
		try
		{
			fis.close();
		}
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}
	}

	public void writeRecord(String[] record)
	{
		throw new RuntimeException("NOT IMPLEMENTED");
	}
}
