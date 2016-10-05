package com.tricast.database;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.StringTokenizer;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ResourceScanner {

	private ResourceScanner() {
		// No instances
	}

	public static List<String> getFilesInClassPath(String extension) throws IOException {
		String classpath = System.getProperty("java.class.path");
		StringTokenizer st = new StringTokenizer(classpath, File.pathSeparator);

		List<String> items = new ArrayList<String>();

		while (st.hasMoreTokens()) {
			processItem(st.nextToken(), items, extension);
		}

		return items;
	}

	private static void processItem(String item, List<String> items, String extension) throws IOException {
		if (item.endsWith(".zip")) {
			processZip(item, items, extension);
		} else if (item.endsWith(".jar")) {
			processZip(item, items, extension);
		} else {
			// Assume it's a directory
			processDirectory(item, item, items, extension);
		}
	}

	private static void processZip(String item, List<String> items, String extension) throws IOException {
		ZipFile zip = new ZipFile(item);

		Enumeration<? extends ZipEntry> enumeration = zip.entries();
		while (enumeration.hasMoreElements()) {
			ZipEntry entry = enumeration.nextElement();
			String path = entry.getName();
			if (path.endsWith(extension)) {
				items.add(entry.getName());
			}
		}

		zip.close();
	}

	private static void processDirectory(String stub, String item, List<String> items, String extension) {
		File f = new File(item);
		File[] files = f.listFiles();

		if (files == null) {
			return;
		}

		for (File file : files) {
			if (file.isDirectory()) {
				processDirectory(stub, file.getPath(), items, extension);
			} else {
				String path = file.getPath();
				if (path.endsWith(extension)) {
					items.add(path.substring(stub.length() + 1));
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		for (String s : ResourceScanner.getFilesInClassPath("sqlx")) {
			System.out.println(s);
		}
	}

}
