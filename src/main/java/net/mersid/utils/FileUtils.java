package net.mersid.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
}
