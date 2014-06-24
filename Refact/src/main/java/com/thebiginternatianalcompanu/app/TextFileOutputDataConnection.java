package com.thebiginternatianalcompanu.app;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class TextFileOutputDataConnection implements DataConnection
{
	private OutputStream os;
	private BufferedWriter w;

	public TextFileOutputDataConnection(File file) throws Exception
	{
		os = new FileOutputStream(file);
		w = new BufferedWriter(new OutputStreamWriter(os));
	}

	public String[] readRecord()
	{
		throw new RuntimeException("NOT IMPLEMENTED");
	}

	public void writeRecord(String ... record)
	{
		try
		{
			for (int i = 0; i < record.length; ++i)
			{
				w.write(record[i]);
				if (i < record.length - 1)
					w.write("\t");
			}
			w.newLine();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}

	public void close()
	{
		try
		{
			w.flush();
			os.close();
		}
		catch (Throwable e)
		{
			throw new RuntimeException(e);
		}
	}

}
