package com.portfolio.TriviaApp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RouterLink;

public class Utils {

	public static String convertTime(long time) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yy hh.mm aa");
		
		return dateFormat.format(new Date(time));
	}
	
	public static void printAll(Collection<?> c){
		System.out.println("Utils.printAll() called");
		for(Object o : c) {
			System.out.println(o.toString());
		}
		System.out.println("Utils.printAll() end");
	}
	
	public static final String lorem = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, "
			+ "sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. "
			+ "Ut enim ad minim veniam, "
			+ "quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. "
			+ "Duis aute irure dolor in reprehenderit in "
			+ "voluptate velit esse cillum dolore eu fugiat nulla pariatur. "
			+ "Excepteur sint occaecat cupidatat non proident, sunt in "
			+ "culpa qui officia deserunt mollit anim id est laborum.";
	
	public static Div routerLink(String routerName, Class navClass) {
    	Div menu = new Div();
    	
    	menu.add(new RouterLink(routerName,navClass));
    	return menu;
    }
	
	//TODO make this efficent with StringBuilder
	public static String replaceHtml(String inputString) {
		String strippedText = inputString
				.replaceAll("&quot;", "\"")
				.replaceAll("&#039;", "'");
		
		return strippedText;
	}
	
	public static ArrayList<Integer> toArrayList(int[] inputArray){
		ArrayList<Integer> resultArrayList = new ArrayList<>();
		
		for(int i : inputArray) {
			resultArrayList.add(i);
		}
		return resultArrayList;
	}
	
	public static ArrayList<String> toArrayList(String[] inputArray){
		ArrayList<String> resultArrayList = new ArrayList<>();
		
		for(String i : inputArray) {
			resultArrayList.add(i);
		}
		return resultArrayList;
	}
}