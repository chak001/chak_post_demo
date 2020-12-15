package com.example.demo.javademo.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Author: Chak
 * @Date: 2020/12/11 23:09
 * 参考网址: https://thinkingcao.blog.csdn.net/article/details/108664921
 */
@SpringBootTest
@Slf4j
public class StreamTest {

    @Test
    public void test1() {
        log.info("springboot测试");
    }

    /**
     * stream，获取各种集合的stream流
     */
    @Test
    public void testCollectionStream1() {
        //List集合
        List<String> stringList = new ArrayList<>();
        //Set集合
        Set<String> stringSet = new HashSet<>();
        //Map集合
        Map<String, Object> stringObjectMap = new HashMap<>();
        //数组
        String[] stringArray = {"张三三", "李四", "王五", "王五", "赵八",};

        //通过list获取stream流
        Stream<String> streamList = stringList.stream();
        //通过set获取stream流
        Stream<String> streamSet = stringSet.stream();
        //通过map获取stream流
        Stream<String> streamMap = stringObjectMap.keySet().stream();
        //通过array获取stream流
        Stream<String> streamArray1 = Stream.of(stringArray);
    }

    @Test
    public void testCollectionStream2() {
        // 1. Individual values
        Stream stream = Stream.of("a", "b", "c");

        // 2. Arrays
        String[] strArray = new String[]{"a", "b", "c"};
        stream = Stream.of(strArray);
        stream = Arrays.stream(strArray);

        // 3. Collections
        List<String> list = Arrays.asList(strArray);
        stream = list.stream();

    }

    /**
     * 筛选过滤
     * filter 方法 ， 返回符合过滤条件的值
     */
    @Test
    public void testFilter() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.stream().filter(e -> e.contains("张")).forEach(System.out::println);
    }

    /**
     * list集合stream流式操作
     */
    @Test
    public void testStreamList() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.stream()
                .filter(e -> e.startsWith("张")) //过滤所有姓张的人
                .filter(e -> e.length() == 3) //过滤所有姓名是3个字的人
                .forEach(System.out::println); //遍历打印,System.out::println表明System.out调用println打印方法
    }

    /**
     * limit 方法 ，返回前n个元素数据值组成的Stream。
     */
    @Test
    public void testLimit() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        list.stream().limit(3).forEach(System.out::println); //取前3个
    }


    /**
     * skip方法 ，跳过前n个元素的中间流操作，返回剩下的值。
     */
    @Test
    public void testSkip() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        list.stream().skip(3).forEach(System.out::println);//跳过前3个
        log.info("===========分隔符============");
        list.stream().skip(3).limit(2).forEach(System.out::println); //skip+limit实现分页
    }

    /**
     * distinct， 返回去重的Stream
     */
    @Test
    public void testDistinct() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("李四");
        list.add("王五");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        list.stream().distinct().collect(Collectors.toList()).forEach(System.out::println);
    }

    /**
     * sorted: 返回一个排序的Stream
     */
    @Test
    public void testSorted() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("李四");
        list.add("王五");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        list.stream().distinct().sorted().collect(Collectors.toList()).forEach(System.out::println);
    }


    /**
     * 遍历map集合，截取substring(2)开始的值
     */
    @Test
    public void testMap() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        Stream<String> stream = list.stream().map(e -> e.substring(2));
        stream.forEach(System.out::println);
    }

    /**
     * forEach， ForEach流式遍历list集合
     */
    @Test
    public void testForEach() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.stream().forEach(System.out::println);
    }

    /**
     * allMatch：接收一个 Predicate 函数，当流中每个元素都符合该断言时才返回true，否则返回false
     */
    @Test
    public void testAllMatch() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("李四");
        list.add("王五");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        boolean b = list.stream().allMatch(e -> list.size() > 8);
        System.out.println("b = " + b);
    }

    /**
     * noneMatch: 接收一个 Predicate 函数，当流中每个元素都不符合该断言时才返回true，否则返回false
     */
    @Test
    public void testNoneMatch() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("李四");
        list.add("王五");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        boolean b = list.stream().noneMatch(e -> e.equals("张三三"));
        System.out.println("b = " + b);
    }

    /**
     * anyMatch：接收一个 Predicate 函数，只要流中有一个元素满足该断言则返回true，否则返回false
     */
    @Test
    public void testAnyMatch() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("李四");
        list.add("王五");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        boolean b = list.stream().anyMatch(e -> e.equals("王二麻子"));
        System.out.println("b = " + b);
    }

    /**
     * findFirst：返回流中第一个元素
     */
    @Test
    public void testFindFirsth() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("李四");
        list.add("王五");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        Optional<String> first = list.stream().findFirst();
        Optional<String> nameStr = list.stream().filter(e -> e.equals("李四")).findFirst();
        System.out.println("nameStr==>" + nameStr);
        System.out.println("first = " + first.get());
    }

    /**
     * findAny：返回流中第一个元素
     */
    @Test
    public void testFindAny() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("李四");
        list.add("王五");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        Optional<String> any = list.stream().findAny();
        System.out.println("any = " + any.get());
    }

    /**
     * count，获取List集合的长度
     */
    @Test
    public void testCount() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        long count = list.stream().count();
        System.out.println("count = " + count);
        int size = list.size();
        System.out.println("size = " + size);
    }

    /**
     * max：返回流中元素最大值
     */
    @Test
    public void testMax() {
        List<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);
        list.add(55);
        list.add(66);
        list.add(77);
        list.add(88);
        Integer integer = list.stream().max(Integer::compareTo).get();
        Optional<Integer> max = list.stream().max(Integer::compareTo);

        System.out.println("integer = " + integer);
    }

    /**
     * min：返回流中元素最小值
     */
    @Test
    public void testMin() {
        List<Integer> list = new ArrayList<>();
        list.add(11);
        list.add(22);
        list.add(33);
        list.add(44);
        list.add(55);
        list.add(66);
        list.add(77);
        list.add(88);
        Integer integer = list.stream().min(Integer::compareTo).get();
        System.out.println("integer = " + integer);
        list.stream().limit(1).forEach(System.out::println);
        Stream<Integer> limit = list.stream().limit(1).limit(2);
        List<Integer> collect = limit.collect(Collectors.toList());
        System.out.println("collect===>" + collect);
        List<Integer> collect1 = list.stream().limit(1).limit(2).distinct().skip(3).collect(Collectors.toList());
        System.out.println("collect1===>" + collect1);
        list.stream().limit(1).limit(2).distinct().skip(3).filter(f -> f.equals(55)).forEach(System.out::println);
    }

    /**
     * collect，将流转化为List集合，然后遍历集合
     */
    @Test
    public void testCollect() {
        List<String> list = new ArrayList<>();
        list.add("张三三");
        list.add("李四");
        list.add("王五");
        list.add("孙七");
        list.add("赵八");
        list.add("王二麻子");
        List<String> collect = list.stream().skip(3).limit(2).collect(Collectors.toList());
        collect.forEach(System.out::println);
    }

    /**
     * reduce： 聚合操作，用来做统计，将流中元素反复结合起来统计计算，得到一个值.
     */
    @Test
    public void testReduce() {
        //1.求集合元素只和
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8});
        Integer result = stream.reduce(0, Integer::sum);
        System.out.println(result);

        //2.求和
        Stream<Integer> stream1 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream1.reduce((i, j) -> i + j).ifPresent(System.out::println);

        //3.求最大值
        Stream<Integer> stream2 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream2.reduce(Integer::max).ifPresent(System.out::println);

        //4.求最小值
        Stream<Integer> stream3 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream3.reduce(Integer::min).ifPresent(System.out::println);

        //5.做逻辑
        Stream<Integer> stream4 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        stream4.reduce((i, j) -> i > j ? j : i).ifPresent(System.out::println);

        //6.求逻辑求乘机
        Stream<Integer> stream5 = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        int result2 = stream5.filter(i -> i % 2 == 0).reduce(1, (i, j) -> i * j);
        Optional.of(result2).ifPresent(System.out::println);
    }


    @Test
    public void testCollection() {
        Student s1 = new Student("aa", 10, 1);
        Student s2 = new Student("bb", 20, 2);
        Student s3 = new Student("cc", 10, 3);
        List<Student> list = Arrays.asList(s1, s2, s3);

        //stream转成list
        List<Integer> ageList = list.stream().map(Student::getAge).collect(Collectors.toList()); // [10, 20, 10]

        //stream转成set
        Set<Integer> ageSet = list.stream().map(Student::getAge).collect(Collectors.toSet()); // [20, 10]

        //stream转成map,注:key不能相同，否则报错
        Map<String, Integer> studentMap = list.stream().collect(Collectors.toMap(Student::getName, Student::getAge)); // {cc=10, bb=20, aa=10}

        //字符串分隔符连接
        String joinName = list.stream().map(Student::getName).collect(Collectors.joining(",", "(", ")")); // (aa,bb,cc)

        //聚合操作
        //1.学生总数
        Long count = list.stream().collect(Collectors.counting()); // 3
        //2.最大年龄 (最小的minBy同理)
        Integer maxAge = list.stream().map(Student::getAge).collect(Collectors.maxBy(Integer::compare)).get(); // 20
        //3.所有人的年龄
        Integer sumAge = list.stream().collect(Collectors.summingInt(Student::getAge)); // 40
        //4.平均年龄
        Double averageAge = list.stream().collect(Collectors.averagingDouble(Student::getAge)); // 13.333333333333334
        // 带上以上所有方法
        DoubleSummaryStatistics statistics = list.stream().collect(Collectors.summarizingDouble(Student::getAge));
        System.out.println("count:" + statistics.getCount() + ",max:" + statistics.getMax() + ",sum:" + statistics.getSum() + ",average:" + statistics.getAverage());

        //分组
        Map<Integer, List<Student>> ageMap = list.stream().collect(Collectors.groupingBy(Student::getAge));
        //多重分组,先根据类型分再根据年龄分
        Map<Integer, Map<Integer, List<Student>>> typeAgeMap = list.stream().collect(Collectors.groupingBy(Student::getType, Collectors.groupingBy(Student::getAge)));

        //分区
        //分成两部分，一部分大于10岁，一部分小于等于10岁
        Map<Boolean, List<Student>> partMap = list.stream().collect(Collectors.partitioningBy(v -> v.getAge() > 10));

        //规约
        Integer allAge = list.stream().map(Student::getAge).collect(Collectors.reducing(Integer::sum)).get(); //40　　

    }


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private class Student {
        private String name;
        private int age;
        private int type;
    }
}
