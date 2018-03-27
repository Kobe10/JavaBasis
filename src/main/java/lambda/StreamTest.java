package lambda;

import java.security.acl.Permission;
import java.security.cert.PKIXRevocationChecker.Option;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.plaf.basic.BasicTableHeaderUI;

public class StreamTest {
	public static void main(String[] args) {
		
		//map
		//将所有的字母变为大写字母
		Stream<String> stream=Stream.of("abc","def","ghi","jkl","opq");
		List<String> output=stream.map(String::toUpperCase).collect(Collectors.toList());
		//output.forEach(System.out::println);
		//所有的数变成平方
		List<Integer> nums=Arrays.asList(1,2,3,4,5);
		List<Integer> squareNums=nums.stream().map(n->n*n).collect(Collectors.toList());
		//squareNums.forEach(System.out::println);
		
		//flatMap
		//多组stream合并,填充流
		Stream<List<Integer>>input1=Stream.of(Arrays.asList(1,5),Arrays.asList(2,4),Arrays.asList(3));
		Stream<Integer>outputStream=input1.flatMap(childList->childList.stream());
		List<Integer>output1=outputStream.map(n->n*n).collect(Collectors.toList());
		//output1.forEach(System.out::println);
		
		//filter
		Integer[] sixNums= {1,2,3,4,5,6};
		Integer[] evens=Stream.of(sixNums).filter(n->n%2==0).toArray(Integer[]::new);
		//Arrays.asList(evens).forEach(System.out::println);
		//findFirst
		Optional<Integer>first=Stream.of(sixNums).findFirst();
		//first.ifPresent(System.out::println);//如果不为null则执行
		
		//reduce
		String concat=Stream.of("A","B","C").reduce("start-", String::concat);
		//System.out.println(concat);//start-ABC
		
		//limit/skip
		List<Person> persons=new ArrayList<>();
		for(int i=1;i<=5;i++) {
			Person person=new Person(i);
			persons.add(person);
		}
		List<String>outperson1=persons.stream().map(Person::getName)
				.limit(10).skip(3).collect(Collectors.toList());
		//outperson1.forEach(System.out::println);
		/*outperson1
		 name4
		name5
		name6
		name7
		name8
		name9
		name10
		 */
		
		//sort
		List<Person>outperson2=persons.stream().limit(2).sorted((p1,p2)->p1.getName().compareTo(p2.getName()))
				.collect(Collectors.toList());
		//outperson2.forEach(p->System.out.println(p.getName()));
		//name1, name2
		
		//min,max,distinct
		String[] wordList2= {"","may","hello","may","word","he","he","splendid"};
		int maxlength=Stream.of(wordList2).mapToInt(he->he.length()).max().getAsInt();
		//System.out.println(maxlength);
		//out:8
		int minlength=Stream.of(wordList2).mapToInt(String::length).min().getAsInt();
		//System.out.println(minlength);
		//out:2
		List<String> words=Stream.of(wordList2).filter(word->word.length()>0)
		.map(String::toLowerCase).distinct().sorted().collect(Collectors.toList());
		//System.out.println(words);
		//out:[he, hello, may, splendid, word]
		
		//allMatch,anyMatch, noneMatch
		List<Person> persons2=new ArrayList<>();
		persons2.add(new Person(1, 10));
		persons2.add(new Person(2, 21));
		persons2.add(new Person(3, 34));
		persons2.add(new Person(4, 6));
		persons2.add(new Person(5, 55));
		boolean isAllAdult=persons2.stream().allMatch(p->p.getAge()>18);
		//System.out.println(" are all adult?:"+isAllAdult);
		boolean isThereAnyChild=persons2.stream().anyMatch(p->p.getAge()<12);
		//System.out.println("Is there any child?:"+isThereAnyChild);
		//out: are all adult?:false
		//Is there any child?:true
		
		//iterate
		List<Integer> num3=Stream.iterate(0, n->n+3).limit(6).collect(Collectors.toList());
		//System.out.println(num3);//output:[0, 3, 6, 9, 12, 15]
		
		//Collectors groupBy
		List<Person> persons3=new ArrayList<>();
		persons3.add(new Person(1, 10));
		persons3.add(new Person(2, 6));
		persons3.add(new Person(3, 34));
		persons3.add(new Person(4, 6));
		persons3.add(new Person(5, 55));
		persons3.add(new Person(6, 10));
		
		Map<Integer,List<Person>>groups=persons3.stream().collect(Collectors.groupingBy(Person::getAge));

	/*	Iterator<Entry<Integer, List<Person>>>it=groups.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Integer, List<Person>> entry=it.next();
			System.out.println(entry.getKey()+":"+entry.getValue().stream().map(person->person.getName()+",").reduce(String::concat).get());
		}*/
		/*output:
		34:name3,
		6:name2,name4,
		55:name5,
		10:name1,name6,
		 */
		
		//parting
		Map<Boolean, List<Person>>groups2=persons3.stream().collect(Collectors.partitioningBy(p->p.getAge()<18));
		System.out.println("未成年人：");
		groups2.get(true).forEach(p->System.out.println(p.getName()+":"+p.getAge()));
		System.out.println("成年人：");
		groups2.get(false).forEach(p->System.out.println(p.getName()+":"+p.getAge()));
		
		
	}

}
