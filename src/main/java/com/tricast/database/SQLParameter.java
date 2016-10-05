package com.tricast.database;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

public class SQLParameter {

	private String name;
	private int startPos;
	private int endPos;
	private String label;
	private String type;
	boolean optional;
	boolean dateAsString;
	private int daysAdded;

	public SQLParameter(String name, int startPos, int endPos) {

		this.startPos = startPos;
		this.endPos = endPos;

		// Parameter names are of format ":PARAMETERNAME_TYPE_O_UTC_PLUS_X"
		// where:
		// _O is optional I'll scan as being keyword for 'optional'
		// _UTC is optional and I'll scan as being keyword for 'forceUTC'
		// _PLUS_X is optional and it adds X days to a date
		LinkedList<String> fragments = new LinkedList<>(Arrays.asList(name.split("_")));

		StringBuilder sb = new StringBuilder();
		for (Iterator<String> it = fragments.iterator(); it.hasNext();) {
			String fragment = it.next();
			switch (fragment) {
			case "O":
				this.optional = true;
				it.remove();
				break;
			case "DS":
				this.dateAsString = true;
				it.remove();
				break;
			case "PLUS":
				it.remove();
				this.daysAdded = Integer.parseInt(it.next());
				it.remove();
				break;
			default:
				if (sb.length() > 0) {
					sb.append('_');
				}
				sb.append(fragment);
			}
		}

		this.type = fragments.getLast();
		this.name = sb.toString();
	}

	public int getEndPos() {
		return endPos;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		int typePos = name.lastIndexOf("_");
		return name.substring(1, typePos);
	}

	public int getStartPos() {
		return startPos;
	}

	public String getLabel() {
		return label;
	}

	public String getType() {
		return type;
	}

	public boolean isOptional() {
		return optional;
	}

	public boolean isDateAsString() {
		return dateAsString;
	}

	public void setDateAsString(boolean dateAsString) {
		this.dateAsString = dateAsString;
	}

	public int getDaysAdded() {
		return daysAdded;
	}

	@Override
	public String toString() {
		return "SQLParameter [name=" + name + ", startPos=" + startPos + ", endPos=" + endPos + ", label=" + label
				+ ", type=" + type + ", optional=" + optional + ", dateAsString=" + dateAsString + ", daysAdded="
				+ daysAdded + "]";
	}
}
