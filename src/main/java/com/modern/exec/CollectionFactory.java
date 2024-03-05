package com.modern.exec;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionFactory {
    public static void main(String[] args) {

    }

    public List<String> friendsLst(){
        List<String> friends = new ArrayList<>();
        friends.add("Raphel");
        friends.add("Olival");
        friends.add("Thibaut");
        return friends;
    }
    /**
     * Arrays.asList() => 고정크기의 리스트를 만들어서 set()함수를 이용해 요소를 갱싱가능하지만
     * 요소를 삭제,추가 할수없다. 그럴경우 Unsupported OperationException 발생
     * Unsupported OperationException예외가 발생하는 이유는 내부적으로 고정된 크기의 변환 할수 있는
     * 배열로 구현되었기 때문이다.
     */
    public List<String> friendsArraysLst(){
        List<String> friends = Arrays.asList("Raphel","Olival","Thibaut");
        friends.set(0,"Richard");
        friends.add("Thibaut");
        return friends;
    }

    /**
     * Collectio의 또다른 메서든 Set<>의 경우 list를 인수(매개변수)로받는 HashSet<>()생성자를 사용가능
     * List, Arrays.asList()와 다르게 순서가 존재하지않아(인덱스가 존재하지 않는다)
     * 값의 중복을 허용하지 않는다.
     */
    public Set<String> friendsSetLst(){
        /* Collectio의 또다른 메서든 Set<>의 경우 list를 인수(매개변수)로받는 HashSet<>()생성자를 사용가능 */
        Set<String> friends = new HashSet<>(Arrays.asList("Raphel","Olival","Thibaut"));

        /* 스트림 API를 통해 구현도가능 */
        Set<String> frinendsStream = Stream.of("Raphel","Olival","Thibaut")
                .collect(Collectors.toSet());
        return friends;
    }
    /**
     * List.of() => 고정된크기의 변하지않는 리스트를 만든다.
     * set(), add()사용시 UnsupportedOperationException예외발생
     * 컬렉션이 의도치않게 변하는것을 막기위할때 사용한다.(단,요소자체가 변하는것을 막을수 있는 방법은 없다.)
     *
     * */
    public List<String> friendsLstOf() {
        List<String> friends = List.of("Raphel","Olival","Thibaut");
        return friends;
    }

    /**
     * Set.of() => 고정된크기의 변하지않는 리스트를 만든다.
     * 생성시 중복된 값을 넣게 되는경우 IllegalArgumentException이 발생한다.
     *
     * */
    public Set<String> friendsSetOf() {
        Set<String> friends = Set.of("Raphel","Olival","Thibaut");
        return friends;
    }
}
