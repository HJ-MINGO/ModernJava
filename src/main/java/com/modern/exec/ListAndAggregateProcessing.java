package com.modern.exec;

import javax.sql.rowset.spi.TransactionalWriter;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListAndAggregateProcessing {
    /**
     * list, Set 인터페이스에 다음과 같은 메서드가 추가됐다.
     * 1. removelf : 프레디케이트를 만족하는 요소를 제거한다. List,Set을 구현하거나 그 구현을 상속받은 모든 클래스에서 이용할 수 있다.
     * 2. replaceAll : 리스트에서 이용할 수 있는 기능으로 UnaryOperator 함수를 이용해 요소를 바꾼다.
     * 3. sort : List인터페이스에서 제공하는 기능으로 리스트를 정렬한다.
     * 위 3개 메소드는 해당메소드를 호출한 기존 컬렉션의 영향을 준다.
     *
     * */

    public void transactionReferenceRemove () {
        /* 일반적이 forEach구문 */
        List<String> list = Arrays.asList("1","2","3","4","5");
        for (String s : list) {
            if (Character.isDigit(s.charAt(0))) {
                list.remove(s);
            }
        }
        /* 위에 작성한 forEach문의 실제 내부구조 */
        /**
         * for-each루프는 내부적으로 Iterator객체를 사용하기 때문에 아래와 같이 해석된다.
         * iterator객체와 원본객체 2개의 객체가 컬렉션을 관리하고 있어 서로 동기화 되자않는다.
         * */
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
            String s = iterator.next(); // 작업하는건 원복객체를 통해 만든 iterator
            if (Character.isDigit(s.charAt(0))) {
                list.remove(s); // 근데 실제 컬렉션에서 원본객체에 존재했던 String이 아닌 iterator로 만든 String 객체로 
                                // 원본 객체를통해 제거
            }
        }
        /* 위 문제를개선한 코드*/
        for (Iterator<String> iterator = list.iterator(); iterator.hasNext();) {
            String s = iterator.next(); // 작업하는건 원복객체를 통해 만든 iterator
            if (Character.isDigit(s.charAt(0))) {
                iterator.remove(); // 근데 실제 컬렉션에서 원본객체에 존재했던 String이 아닌 iterator로 만든 String 객체로
                // 원본 객체를통해 제거
            }
        }
        /**
         * 이제 위에 코드를 자바8부터 등장한 removeIf메서들 통해 간소화 할수 있다.
         * removeIf메서드는 삭제할 요소를 가리키는 프리디케이트(조건)을 인수로 받는다 (즉 해당하는 조건에 요소를 삭제)
         * */
        list.removeIf(s-> Character.isDigit(s.charAt(0)));
    }

    public void listReplaceAll() {
        // replaceAll메서드를 이용해 요소를 바꿀수있다.
        List<String> referenceCodes = Arrays.asList("a12", "C14", "b13");
        /* 스트림을 이용해 요소를 바꾸는 코드
        *  하지만 이런경우 원본 컬렉션이 아닌 스트림을 통해 만들었으니 새로운 컬렉션이 만들어지고 원본에는 변환가 없다.
        * */
        referenceCodes.stream()
                .map(code -> Character.toUpperCase(code.charAt(0)) + code.substring(1))
                .collect(Collectors.toList())
                .forEach(System.out::println);
        /*
        * ListIterator객체를 이용해 set으로 요소를 바꿀수있는 방법도 있지만 이것또한 복잡
        * */
        for (ListIterator<String> iterator = referenceCodes.listIterator(); iterator.hasNext(); ) {
            String code = iterator.next();
            iterator.set(Character.toUpperCase(code.charAt(0)) + code.substring(1));
        }
        /* 간단하게 replaceAll사용 */
        referenceCodes.replaceAll(r-> Character.toUpperCase(r.charAt(0)) + r.substring(1));
    }
}
