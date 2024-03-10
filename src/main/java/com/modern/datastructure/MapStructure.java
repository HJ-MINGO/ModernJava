package com.modern.datastructure;

import java.util.*;
import java.util.stream.Collectors;

public class MapStructure {
    /**
     *  Map 인터페이스
     *  Map 인터페이스는 키(key)와 값(value)을 하나의 쌍으로 묶어서 저장하는 컬렉션 클래스를 구현하는데 사용된다.
     *  즉, 키 와 값으로 구성된 객체를 저장하는 구조이며 이러한 key,value으로 구성된 객체가 Entry객체이다.
     *
     *  키는 중복될 수 없지만 값은 중복을 허용한다. 만약 중복된 키와 값을 저장한다면 기존의 값은 없어지고, 저장된 값이 남는다.
     *  순서는 유지되지 않으며, 키는 중복을 허락하지 않는다.
     *  Map인터페이스를 Implements(구현)한 클래스로는 TreeMap, HashTable, HashMap등이 있다.
     *
     *  HashMap 클래스
     *  HashMap 은 Map Interface 를 Implements(구현) 한 클래스로서 중복을 허용하지 않는다.
     *  Map 의 특징인 Key 와 Value 의 쌍으로 이루어지며, key 또는 value 값으로써 null 을 허용한다.
     *
     *  TreeMap 클래스
     *  TreeMap 역시 중복을 허용하지 않으며, Key 와 Value 의 쌍으로 이루어져 있다.
     *  HashMap 과 다른 점은 SortedMap을 상속하였으며, Key 값들에 대한 정렬이 이루어진다는 점이다.
     *
     *  HashTableMap 클래스
     *  HashTable Map , key 또는 value 값으로써 null 을 허용하지 않는다. ( HashMap 과 차이점 )
     *
     *  Map과 HashMap 차이점
     *  1. HashMap : HashTable을 이용해서 키-값 관계를 유지
     *  2. Map : red-black-tree 알고리즘 이용
     *
     *
     * */

    public void entryOfMapInnerInterFace(){
        /**
         * Map.Entry 인터페이스
         *  Map 인터페이스의 내부 인터페이스(inner interface) 이다.
         *  Map에 저장되는 key-value 쌍을 다루기 위해 내부적으로 Entry 인터페이스를 정의해 놓았다.
         *  Map인터페이스를 구현하는 클래스 에서는 Map.Entry 인터페이스도 함께 구현해야 한다.
         *  맵에 저장되는 엔트리의 조작을 위한 메소드가 정의되어있다.
         *  1. boolean equals(Object o) : 동일한 Entry 인지 비교한다.
         *  2. Object getKey() : Entry의 key 객체를 반환한다.
         *  3. Object getValue() : Entry 의 value 객체를 반환한다.
         *  4. int hashCode() : Entry 의 해시코드를 반환한다.
         *  5. Object setValue(Object value) : Entry의 value 객체를 지정된 객체로 변경한다.
         *
         * */
        Map<String,Integer> getEntry = Map.of("나해준",1,
                "장훈",2,
                "신익수",3);
        // entrySet() 메서드를 호출하는 경우, Map.Entry객체를 Set에 담아서 리턴한다.
        // 즉 Map인터페이스 구현체에 포함되어 있는 [Key, Value] 쌍 전체를 포함하는 Set 컬렉션을 반환받을 수 있다.
        Set<Map.Entry<String,Integer>> entries = getEntry.entrySet();
        // Map.Entry<K, V> 해당 Map의 항목에 대한 참조로써 사용될 수 있다고 한다.
        Map<String, Integer> map = new HashMap<>();
        map.put("딸기",1);
        List<Map.Entry<String,Integer>> entries2 = map.entrySet().stream().collect(Collectors.toList());
        entries2.get(0).setValue(3);
        /**
         * Map.Entry의 장점
         *  1. Map.Entry를 사용하면 각각의 key와 value가 연결되어 하나의 객체로 표현되어 서로 연관되어 있다는 것을 명시할 수 있다는 장점이 있다.
         *  2. 동적으로 key에 대한 value값을 편하게 수정할 수 있다. Map.entry를 사용하지 않고 key의 value값을 수정하려면 key값을
         *     얻어오고 수정하고 다시 저장해주는 작업을 해줘야하는데 그러지 않고 한번에 setValue() 메서드를 이용해 수정할 수 있다.
         * */

        // Map.Entry<K, V>의 구현체인 AbstractMap.SimpleEntry<K, V> 의 생성자를 이용해 직접 생성
        List<Map.Entry<String ,Integer>> constructer = List.of(
                new AbstractMap.SimpleEntry<>("나해준",1),
                new AbstractMap.SimpleEntry<>("장훈",2),
                new AbstractMap.SimpleEntry<>("신익수",3)
        );


    }
}
