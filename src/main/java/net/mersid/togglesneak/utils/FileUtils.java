package net.mersid.togglesneak.utils;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FileUtils {

	/**
	 * Writes text to a file. If the file already exists, it will be removed first.
	 * @param text The text to write to the file.
	 * @param path The path of the file.
	 */
	public static void write(String text, Path path)
	{
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(path.toString()));
			writer.write(text);
			writer.close();
		} catch (IOException e)
		{
			System.out.println("Error while saving!");
			e.printStackTrace();
		}
	}

	public static String read(Path path)
	{
		try
		{
			byte[] bytes = Files.readAllBytes(path);
			return new String(bytes);
		}
		catch (IOException e)
		{
			System.out.println("Error while reading file!");
			e.printStackTrace();
		}
		return "";
	}
}
