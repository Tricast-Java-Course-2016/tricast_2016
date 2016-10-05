package com.tricast.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * Think of this as a cache of sql statements. You pass it a sql file name and
 * it returns the sql statement in that file. It remembers sql files previously
 * used so it doesn't load from disk every time you want a sql statement. The
 * other thing it does is automatically convert a genuine oracle sql statement
 * that has :param entries to a JDBC compliant sql statement that uses the ? as
 * a parameter instead.
 */
public class SqlManager {

	private static SqlManager sqlManager;
	public static final String schema = "CALENDAR";

	private final static Logger LOG = LogManager.getLogger(SqlManager.class);
	private ClassLoader classLoader;
	private String sqlFolderName;
	private Map<String, SQLXFile> cache = new ConcurrentHashMap<String, SQLXFile>();

	public static SqlManager getInstance() {
		if (sqlManager == null) {
			sqlManager = new SqlManager(SqlManager.class.getClassLoader(), "sql");
		}
		return sqlManager;
	}

	public SqlManager(ClassLoader classLoader, String sqlFolderName) {
		this.classLoader = classLoader;
		this.sqlFolderName = sqlFolderName;
	}

	public String get(String sqlFile) throws IOException {
		// I've not bothered with synchronization.
		// Who cares if two people call this and both threads
		// decide to load the sql file. It won't break anything
		// if that happens - I think!

		if (sqlFile.contains(":")) {
			String[] parts = sqlFile.split(":");
			return get(parts[0], parts[1]);
		}

		// don't ever use the cache (useful when developing)
		URL url = classLoader.getResource(sqlFolderName + "/" + sqlFile);
		// check all the things like /*=SCHEMA*/

		if (url == null) {
			throw new FileNotFoundException(String.format("Cannot find file %s", sqlFile));
		}
		String parsedSql = parseSql(IOTools.loadResource(url));
		return parsedSql;
	}

	private String buildParamList(Map.Entry<String, Integer> entry) {
		StringBuilder sb = new StringBuilder("?");
		for (int i = entry.getValue(); i > 1; i--) {
			sb.append(",?");
		}
		return sb.toString();
	}

	/**
	 * Parses the sql statement and changes all the oracle :param entries into
	 * JDBC compatible ? symbols. Its nice to have the sql oracle compaticlbe as
	 * it can run in TOAD unchanged at any time and it also means that our build
	 * scripts can test sql statements.
	 *
	 * @param sql
	 *            The SQL statement to parse
	 * @return returns a JDBC compliant version of the sql string
	 */

	public static String parseSql(String sql) {

		List<SQLParameter> parameters = new ArrayList<SQLParameter>();

		sql = replaceParametersWithQuestionMarks(sql, parameters);
		sql = replaceABPMarkers(sql);

		return sql;
	}

	public static String parseSql(String sql, List<SQLParameter> parameter) {

		sql = replaceParametersWithQuestionMarks(sql, parameter);
		sql = replaceABPMarkers(sql);
		sql = sql.replaceAll("\\s*--.*", "");

		return sql;
	}

	static String replaceParametersWithQuestionMarks(String sql, List<SQLParameter> parameters) {
		getParametersForSQL(sql, parameters);

		StringBuilder sb = new StringBuilder(sql);

		for (int i = parameters.size() - 1; i >= 0; i--) {
			SQLParameter p = parameters.get(i);
			sb.replace(p.getStartPos(), p.getEndPos(), "?");
		}

		return sb.toString();
	}

	public static String replaceABPMarkers(String sql) {
		sql = sql.replaceAll("/\\*\\s*=\\s*SCHEMA\\s*\\*/", schema + ".");
		sql = sql.replaceAll("@SCHEMA@", schema + ".");

		return sql;
	}

	public static void getParametersForSQL(String sql, List<SQLParameter> parameters) {

		// As the regex stuff seems to be line based, we add a \n to the end of
		// our string to guarantee that
		// there will always a terminating CR.
		sql = sql + '\n';

		// Get comment boundaries
		// String sql for '/*' or '--' to mark start of comment

		// DO NOT CHANGE THE REGULAR EXPRESSION BELOW. HERE'S A BACKUP JUST IN
		// CASE.
		// Pattern findCommentsPattern =
		// Pattern.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?:--.*)|(?:'.*')|(?:\".*\")");

		// Look for multiline comments /* ... */ or single line comments -- ....
		// \n or string literals '....' "...."
		Pattern findCommentsPattern = Pattern
				.compile("(?:/\\*(?:[^*]|(?:\\*+[^*/]))*\\*+/)|(?:--.*)|(?:'.*')|(?:\".*\")");
		Matcher matcher = findCommentsPattern.matcher(sql);

		// Now we look for all occurrences of :xxxxxx (Oracle parameter) and
		// check to make sure it is not within any of
		// our commented text...
		Pattern findParameterPattern = Pattern.compile("(?:\\:\\w+)");
		Matcher matcher2 = findParameterPattern.matcher(sql);

		while (matcher2.find()) {
			// Check this parameter match is NOT in a comment...
			boolean inComment = false;
			matcher.find(0);
			while (matcher.hitEnd() == false) {
				inComment = false;

				if (matcher2.start() <= matcher.end() && (matcher2.end() - 1) >= matcher.start()) {
					// System.out.format("Found hit of [%s] (%d,%d) in comment
					// [%s] (%d,%d)\n", matcher2.group(),
					// matcher2.start(), (matcher2.end() -1), matcher.group(),
					// matcher.start(), (matcher.end() -1));
					inComment = true;
					break;
				}

				matcher.find();
			}

			// if (inComment) {
			// System.out.format("Match found IN COMMENT [%s] between %d and
			// %d\n", matcher2.group(), matcher2.start(),
			// (matcher2.end() -1));
			// } else {
			// System.out.format("Match found [%s] between %d and %d\n",
			// matcher2.group(), matcher2.start(),
			// (matcher2.end() -1));
			// }

			if (!inComment) {
				SQLParameter p = new SQLParameter(matcher2.group(), matcher2.start(), matcher2.end());
				parameters.add(p);
			}
		}
	}

	public String getSQLDescription(String fileContents, int position) {
		return extractMetadata("DESCRIPTION", fileContents, position);
	}

	public String getSQLComment(String fileContents, int position) {
		return extractMetadata("COMMENT", fileContents, position);
	}

	public String getSQLReportBuilder(String fileContents, int position) {
		return extractMetadata("REPORTBUILDER", fileContents, position);
	}

	public String getSQLLabel(String fileContents, int position) {
		return extractMetadata("LABEL", fileContents, position);
	}

	public String getSQLAttr(String fileContents, int position) {
		return extractMetadata("ATTR", fileContents, position);
	}

	public boolean getSQLPanoramixValidation(String fileContents, int position) {
		boolean panoramixValidation = false;
		if ("true".equalsIgnoreCase(extractMetadata("PANORAMIX_VALIDATION", fileContents, position))) {
			panoramixValidation = true;
		}
		return panoramixValidation;
	}

	public String getSQLPDFBuilder(String fileContents, int position) {
		return extractMetadata("PDF_BUILDER", fileContents, position);
	}

	protected String extractMetadata(String metadata, String fileContents, int occurrence) {
		Pattern findCommentsPattern = Pattern
				.compile("(?:/\\*\\s*=\\s*" + metadata + "\\s*=\\s*(?:[^*]|(?:\\*+[^*/]))*\\*+/)");
		Matcher matcher = findCommentsPattern.matcher(fileContents);
		int matches = 0;
		while (matcher.find()) {
			matches++;
			if (matches == occurrence) {
				String label = matcher.group();
				int lastEqualPos = label.lastIndexOf('=');
				int lastStarPos = label.lastIndexOf('*');

				return matcher.group().substring(lastEqualPos + 1, lastStarPos).trim();
			}
		}
		return null;
	}

	public String getSQLDataQuery(String fileContents, int occurrence) {
		Pattern findCommentsPattern = Pattern.compile("(?:/\\*\\s*=\\s*DATA\\s*=\\s*(?:[^*]|(?:\\*+[^*/]))*\\*+/)");
		Matcher matcher = findCommentsPattern.matcher(fileContents);
		int matches = 0;
		while (matcher.find()) {
			matches++;
			if (matches == occurrence) {
				String label = matcher.group();
				int lastEqualPos = label.lastIndexOf("DATA=");
				int lastStarPos = label.lastIndexOf('*');
				return matcher.group().substring(lastEqualPos + 5, lastStarPos).trim();
			}
		}
		return null;
	}

	// Take a file name and load it from the sql folder in the classpath.
	// Then parse the file and build up a list of all SQL statements.
	SQLXFile getSQLXFile(String name) throws IOException {
		SQLXFile file = cache.get(name);
		if (file != null) {
			return file;
		}

		URL url = classLoader.getResource(sqlFolderName + "/" + name);

		String fileContents = IOTools.loadResource(url);

		// RegEx explanation:
		// Find a word boundary
		// Followed by the word "define"
		// Followed by an optional "sql" sequence - GROUP 1
		// Followed by a word boundary
		// Followed by 1 or more characters (lazy) - GROUP 2
		// Followed by 1 or more whitespace characters (lazy)
		// Followed by a "{"
		// Followed by 0, 1 or more characters (lazy) - GROUP 3
		// Followed by a "}"
		Pattern p = Pattern.compile("\\bdefine(sql)*?\\b(.+?)\\s+?\\{(.*?)\\}",
				Pattern.MULTILINE | Pattern.DOTALL | Pattern.COMMENTS);
		Matcher m = p.matcher(fileContents);

		while (m.find()) {
			if (file == null) {
				file = new SQLXFile();
			}
			String key = m.group(2).trim();
			String value = m.group(3).trim();

			if (m.group(1) != null) {
				file.sql_items.put(key, value);
			} else {
				file.items.put(key, value);
			}
		}

		return file;
	}

	// Given the name of an SQLX file, get the parsed, named item.
	public String get(String filename, String name) throws IOException {
		return get(filename, name, true);
	}

	public String get(String filename, String name, boolean replaceParameters) throws IOException {
		// don't ever use the cache (useful when developing)
		SQLXFile file = getSQLXFile(filename);
		return file.getItem(name, replaceParameters);
	}

	public String get(String sqlxFile, String statementName, Map<String, Integer> inSubstitutions) throws IOException {

		SQLXFile file = getSQLXFile(sqlxFile);
		String sql = file.getItem(statementName, false);
		for (Map.Entry<String, Integer> entry : inSubstitutions.entrySet()) {
			sql = sql.replace(entry.getKey(), buildParamList(entry));
		}
		return parseSql(sql);

	}

	public String getPartial(String filename, String name) throws IOException {
		SQLXFile file = getSQLXFile(filename);
		return file.getPartial(name, true);
	}

	public class SQLXFile {

		Map<String, String> items = new ConcurrentHashMap<String, String>();
		Map<String, String> sql_items = new ConcurrentHashMap<String, String>();

		@Override
		public String toString() {
			return items.toString();
		}

		public String getItem(String name) {
			return getItem(name, true);
		}

		public String getItem(String name, boolean replaceParameters) {
			String item = sql_items.get(name);
			if (item == null) {
				return null;
			}

			// Substitute all variables...
			Pattern p = Pattern.compile("\\b?\\$.+?\\b", Pattern.COMMENTS);
			while (true) {
				Matcher m = p.matcher(item);
				if (m.find()) {
					String key = m.group().substring(1);
					String replacement = getPartial(key, replaceParameters);
					if (replacement == null) {
						replacement = getItem(key, replaceParameters);
						if (replacement == null) {
							throw new RuntimeException("Could not find value for variable " + m.group());
						}
					}
					item = m.replaceFirst(replacement);
				} else {
					break;
				}
			}

			if (replaceParameters) {
				item = parseSql(item);
			}
			return item;
		}

		public String getPartial(String name, boolean replaceParameters) {
			String item = items.get(name);
			if (item == null) {
				return null;
			}

			// Substitute all variables...
			Pattern p = Pattern.compile("\\b?\\$.+?\\b", Pattern.COMMENTS);
			while (true) {
				Matcher m = p.matcher(item);
				if (m.find()) {
					String key = m.group().substring(1);
					String replacement = getPartial(key, replaceParameters);
					if (replacement == null) {
						replacement = getItem(key, replaceParameters);
						if (replacement == null) {
							throw new RuntimeException("Could not find value for variable " + m.group());
						}
					}
					item = m.replaceFirst(replacement);
				} else {
					break;
				}
			}

			if (replaceParameters) {
				item = parseSql(item);
			}
			return item;
		}

		public Map<String, String> getAllSql() {
			return sql_items;
		}

	}

	public void generateSQLFilesForBuild(String outpath) throws IOException {
		for (String file : ResourceScanner.getFilesInClassPath("sqlx")) {
			// Strip off the leading "sql/" as the SQLXFile assumes all sqlx
			// files are in the sql folder...
			String filePath = null;
			try {
				file = file.substring(4);
				LOG.debug("generateSQLFilesForBuild : Processing file " + file + " outpath " + outpath);

				SQLXFile sqlx = getSQLXFile(file);
				Map<String, String> map = sqlx.getAllSql();
				for (String key : map.keySet()) {
					filePath = outpath + "/" + file + "." + key + ".sql";

					File fileFile = new File(filePath);
					File directory = fileFile.getParentFile();
					if (!directory.exists()) {
						LOG.debug("generateSQLFilesForBuild : Making directory " + directory);
						directory.mkdirs();
					} else {
						LOG.debug("generateSQLFilesForBuild : Directory {} exists " + directory);
					}

					LOG.debug("generateSQLFilesForBuild : Writing file " + filePath);
					PrintWriter out = new PrintWriter(new File(filePath));
					out.println(sqlx.getItem(key, false));
					out.flush();
					out.close();
				}
			} catch (RuntimeException ex) {
				LOG.error("generateSQLFilesForBuild : Error when processing file: " + file, ex);
				throw ex;
			}
		}

	}

	// used by ANT script so dont remove as it will break CI.
	public static void main(String[] args) throws Exception {
		// Test that the class is thread safe to do get & reset.
		final SqlManager sqlManager = new SqlManager(SqlManager.class.getClassLoader(), "sql");
		// sqlManager.getSQLXFile("settlement.sqlx").print();

		sqlManager.generateSQLFilesForBuild(System.getProperty("dest.dir"));

	}

}
