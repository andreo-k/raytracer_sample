package com.thebiginternatianalcompanu.app;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyApp
{
	private int startYear;
	private int endYear;
	private int sumsByYears[];
	private int countsByYears[];
	private DataConnection data;
	private DataConnection output;

	public MyApp(int startYear, int endYear, DataConnection data,
			DataConnection output)
	{
		this.startYear = startYear;
		this.endYear = endYear;
		this.data = data;
		this.output = output;
		sumsByYears = new int[endYear - startYear + 1];
		countsByYears = new int[endYear - startYear + 1];		
	}

	public static void main(String[] args) throws Exception
	{
		if (args.length < 2)
		{
			System.out.println("input and output files must be specified");
			return;
		}

		TextFileDataConnection data = new TextFileDataConnection(new File(
				args[0]));

		TextFileOutputDataConnection output = new TextFileOutputDataConnection(
				new File(args[1]));

		try
		{			
			MyApp app = new MyApp(1990, 2020, data, output);
			app.process();
			System.out.println("gotovo");
		}
		catch (Exception e)
		{
			System.out.println("oshibka!");
			e.printStackTrace();
		}
		finally
		{
			data.close();
			output.close();
		}
	}

	public void process()
	{
		loadData();
		for (int i = startYear; i < endYear; ++i)
		{
			double qq = 0.0d;
			int sum = sumsByYears[i-startYear]; 
			if (sum > 0)
			{
				qq = sum / (double) countsByYears[i-startYear];
				System.out.println(i + " " + qq);
			}
			output.writeRecord(String.valueOf(0) /* COUNT1 был всегда 0 */,
					String.valueOf(i), String.valueOf(qq));
		}
	}

	public void loadData()
	{
		String[] record = data.readRecord();
		Pattern pattern = Pattern.compile("\\d{4}");
		while (record != null)
		{
			Matcher matcher = pattern.matcher(record[2]);
			// ищем год в колонке 2
			if (matcher.find())
			{
				int year = Integer.parseInt(matcher.group());
				if (startYear <= year && year < endYear)
				{
					sumsByYears[year - startYear] += Integer
							.parseInt(record[3]);
					countsByYears[year - startYear] += 1;
				}
			}

			record = data.readRecord();
		}
	}
}
