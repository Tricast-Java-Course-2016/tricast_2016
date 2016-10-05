package com.tricast.database;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URL;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class IOTools {

	private static final Logger LOG = LogManager.getLogger(IOTools.class);

	private IOTools() {
		// Do not allow instances of this class.
	}

	public static int getObjectSize(Serializable serializable) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(serializable);
		oos.flush();
		oos.close();
		return baos.size();
	}

	public static String loadFile(File file) throws IOException {
		return loadFile(file.getAbsolutePath());
	}

	public static String loadFile(String filename) throws IOException {
		String line = null;
		StringWriter sw = null;
		BufferedReader br = null;

		try {
			sw = new StringWriter();
			br = new BufferedReader(new FileReader(filename));

			while ((line = br.readLine()) != null) {
				sw.write(line + System.getProperty("line.separator"));
			}

			return sw.toString();
		} finally {
			cleanup(sw);
			cleanup(br);
		}
	}

	public static String loadResource(URL url) throws IOException {
		String line = null;
		InputStream is = null;
		InputStreamReader ir = null;
		BufferedReader br = null;
		StringWriter sw = null;

		try {
			is = url.openStream();
			ir = new InputStreamReader(is, "UTF-8");
			br = new BufferedReader(ir);
			sw = new StringWriter();

			String separator = System.getProperty("line.separator");
			while ((line = br.readLine()) != null) {
				sw.write(line + separator);
			}

			return sw.toString();
		} finally {
			cleanup(sw);
			cleanup(br);
			cleanup(ir);
			cleanup(is);
		}
	}

	public static String loadStream(InputStream is) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buf = new byte[4096];

		int len;
		do {
			len = is.read(buf);
			if (len > 0) {
				baos.write(buf, 0, len);
			} else {
				break;
			}
		} while (true);

		is.close();
		baos.close();

		return new String(baos.toByteArray());
	}

	public static void writeFile(File file, String fileContent) throws IOException {
		writeFile(file, fileContent, false);
	}

	public static void writeFile(File file, String fileContent, boolean append) throws IOException {
		writeFile(file.getAbsolutePath(), fileContent, append);
	}

	public static void writeFile(String fileName, String fileContent) throws IOException {
		writeFile(fileName, fileContent, false);
	}

	public static void writeFile(String fileName, String fileContent, boolean append) throws IOException {
		BufferedWriter bw = null;

		try {
			bw = new BufferedWriter(new FileWriter(fileName, append));
			bw.write(fileContent, 0, fileContent.length());
			bw.flush();
		} finally {
			cleanup(bw);
		}
	}

	public static void cleanup(InputStream in) {
		try {
			if (in != null) {
				if (in instanceof ObjectInputStream) {
					ObjectInputStream ois = (ObjectInputStream) in;
					if (ois.markSupported()) {
						ois.reset();
					}
				}
				in.close();
				in = null;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage() + "-" + e);
		}
	}

	public static void cleanup(OutputStream out) {
		try {
			if (out != null) {
				out.flush();
				if (out instanceof ObjectOutputStream) {
					((ObjectOutputStream) out).reset();
				}
				out.close();
				out = null;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage() + "-" + e);
		}
	}

	public static void cleanup(Reader in) {
		try {
			if (in != null) {
				in.close();
				in = null;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage() + "-" + e);
		}
	}

	public static void cleanup(Writer out) {
		try {
			if (out != null) {
				out.flush();
				out.close();
				out = null;
			}
		} catch (IOException e) {
			LOG.error(e.getMessage() + "-" + e);
		}
	}

	public static void main(String[] args) throws Exception {
		System.out.println(IOTools.loadResource(new URL("file:/c:/boot.ini")));
	}
}
