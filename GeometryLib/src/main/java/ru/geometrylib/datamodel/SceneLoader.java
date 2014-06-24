package ru.geometrylib.datamodel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class SceneLoader
{
	public static Scene Load(File f) throws Exception
	{
		InputStream input = new FileInputStream(f);
		try
		{
			return Load(input);
		}
		finally
		{
			input.close();
		}
	}

	public static Scene Load(InputStream input) throws Exception
	{
		JAXBContext jc = JAXBContext.newInstance("ru.geometrylib.datamodel");

		Unmarshaller unmarshaller = jc.createUnmarshaller();
		return (Scene) unmarshaller.unmarshal(input);
	}

}
