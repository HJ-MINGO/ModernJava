package com.modern.exec;

public class ConcurrentHashMap {
    /**
     * ConcurrentHashMap 클래스는 동시성 친화적이며 최신기술이 반영된 HashMap이다.
     * ConcurrentHashMap은 내부 자료구조의 특정 부분만 잠권 동시 추가,갱신 작업을 허용한다.
     * 따라서 동기화된 HashTable 버전에 비해 읽기 쓰기 연산 성능이 월등하다(참고로 표준 HashMap은 비동기로 작동한다.)
     * */

    public void reduceAndSearch(){
        /**
         * ConcurrentHashMap은 3가지 연산을 지원한다.(키값으로 연산)
         *  1. forEach : 각 (key,value) 쌍에 주어진 액션을 실행
         *  2. reduce : 모든 (key,value) 쌍을 제공된 reduce함수를 이용해 결과로 합칩(merge)
         *  3. search : null이 아닌 값을 반환할때까지 각 (key,value) 쌍에 함수를 적용
         * 인수를 활용한 4가지 연산 형탱
         *  1. key,value로 연산 (forEach,reduce,search)
         *  2. 키로 연산 (forEachKey,reduceKey,searchKey)
         *  3. 값으로 연산 (forEachValue,reduceValue,searchValue)
         *  4. Map.Entry연산
         * */

    }
}
