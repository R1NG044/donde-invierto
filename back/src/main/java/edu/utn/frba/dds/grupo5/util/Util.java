package edu.utn.frba.dds.grupo5.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class Util {
	
	public static <T> List<T> filterByPredicate(List<T>list, Predicate<? super T> p){
		return list.stream().filter(p).collect(Collectors.toList());
	}
	
	public static <T> boolean allElementsMatch(List<T>list, Predicate<? super T> p){
		return list.stream().allMatch(p);
	}
	
	public static <R, T> List<R> map(List<T>list, Function<T,R> f){
		return list.stream().map(f).collect(Collectors.toList());
	}
	
	public static <T> List<T> sort(List<T>list, Comparator<? super T> comparator){
		return list.stream().sorted(comparator).collect(Collectors.toList());
	}
	
	public static List<String> substringsBetween(String string,String open,String close){
		String[] subs = StringUtils.substringsBetween(string, open, close);
		
		if(subs==null)
			return new ArrayList<String>();
		
		return Arrays.asList(subs);
		
	}
}
