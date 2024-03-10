package com.modern.exec;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import static java.util.Map.entry;

public class MapProcess {
    /**
     * 자바8이후부터는 Map인터페이스에 몇가지 default메서드(인터페이스에서 작성할수있는 메서드)가 추가됨
     *
     * */

    public void mapEntryMethod(){
        Map<String,Integer> ageOfFriends = Map.of("사과",2,"딸기",3,"바나나",4,"포도",5,"체리",6);
        ageOfFriends.put("실행",3);
        for (Map.Entry<String,Integer> entry : ageOfFriends.entrySet()) {
            String fruit = entry.getKey();
            Integer cnt = entry.getValue();
            System.out.println("key : [ " + fruit+ " ] and Value : [ " + cnt + " ]" );
        }
        // 하지만 이걸 인터페이스 함수를 통해 간결하게 사용가능하다.
        // Map인터페이스는 BiConsumer(키와값을 인수로 받음) forEach메소드 지원
        ageOfFriends.forEach((fruit,cnt)->{
            System.out.println("key : [ " + fruit+ " ] and Value : [ " + cnt + " ]" );
        });
        
        // Map을 이용한 정렬 메서드
        // Entry.comapringByValue
        // Entry.comapringByKey
        Map<String,String> movies = Map.ofEntries(entry("Raphael","Star Wars"),
                entry("Cristina","Matrix"),
                entry("Olivia","James Bond"));
        movies.entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // key기준으로 정렬
                .forEach(System.out::println);
        movies.entrySet().stream()
                .sorted(Map.Entry.comparingByValue()) // value기준으로 정렬
                .forEach(System.out::println);

    }
    public void getOrDeFaultMethod(){
        /**
         * 기존에는 찾으려는 키가 존재하지 않으면 해당 키값으로 접근하는 순간부터 NullPointException이 발생했다.
         * 그래서 해당 Exception을 방지하려면 요청 결과 Null인지 먼저 확인해야 한다.(즉 key가 존재하는지 확인해야한다)
         * getOrDefault 메서드를 이용하면 첫번째 인수로 키를, 두번째 인수로 기본값을 받으며 맵에 키가 존재하지 않으면 두번째 인수로 받은
         * 기본값을 반환한다.
         *
         * */
        Map<String,String> movies = Map.ofEntries(entry("Raphael","Star Wars"),
                entry("Cristina","Matrix"),
                entry("Olivia","James Bond"));
        /* 단, 명심할건 해당 key값이 존재하고 해당 키값의 value값이 null인경우는 null을 반환한다.*/
        System.out.println(movies.getOrDefault("Olivia","Matrix"));
        System.out.println(movies.getOrDefault("JavaKing","Matrix"));
    }

    public void calculatePattern () throws NoSuchAlgorithmException {
        /**
         * 계산패턴 (CacheExample클래스 참조)
         *  1. computeIfAbsent :제공된 키에 해당하는 값이 없으면(값이 없거나 null), 키를 이용해 새값을 계산하고 맵에 추가한다.
         *  2. computeIfPresent : 제공된 키가 존재하면 새 값을 계산하고 맵에 추가한다.
         *  3. compute : 제공된 키로 새 값을 계산하고 맵에 저장한다.
         * */
        //요러 값을 저장하는 맵을 처리할때도 계산패턴을 유용하게 활용 가능 하다.
        //Map<Key,List<K,List<v>>>에 요소를 추가하려면 항목이 초기화되어 있는지 확인해야한다.
        Map<String, List<String>> friendsToMovies = new HashMap<>();
        String friend = "Raphael";
        List<String> movies = friendsToMovies.get(friend);
        if (movies == null) {
            movies = new ArrayList<>();
            friendsToMovies.put(friend,movies);
        }
        movies.add("Star Wars");

        // computeIfAbsent을 이용해 간단하게 정의한다.
        // computeIfAbsent는 키가 존재하지 않으면 값을 계산해 맵에 추가하고 키가 존재하면 기존값을 반환한다.
        friendsToMovies.computeIfAbsent("Raphael",name -> new ArrayList<>())
                .add("Star Wars");
        // 반대로 computeIfPresent 현재 키와 관련된값(내가 지정한 키값)이 맵에 존재하면 null이 아닌경우만 새값을 계산한다.
    }

    public void removePattern() {
        /**
         * 삭제패턴
         *  remove메서드는 자바8버전이전에도 존재했지만 8이후부터는 key가 특정한 값과 연관되었을때 제거하는 overroad된 메서드가 존재한다.
         * */

        String key = "Raphael1";
        String value = "Jack Reacher2";
        Map<String, List<String>> friendsToMovies = new HashMap<>();
        if (friendsToMovies.containsKey(key) &&
                Objects.equals(friendsToMovies.get(key),value)) {
            friendsToMovies.remove(key);
            System.out.println("삭제 성공");
        } else {
            System.out.println("삭제 실패");
        }
        // 다음과 값이 간결하게 제거 가능
        friendsToMovies.remove(key,value);
    }

    public void changePattern() {
        /**
         * 교체패턴
         *  1. repalceAll : BiFunction을 적용한 결과로 각항목의 값을 교체한다. List의 replaceAll메서드와 비승한 동작을 수행한다.
         *  2. Replace : 키가 존재하면 맵의 값을 바꾼다. 키가 특정 값으로 매핑되었을 때만 값을 교체하는 오버로드 버전도 존대한다.
         *
         * */

        Map<String,String> facouriteMovies = new HashMap<>();
        facouriteMovies.put("Raphael","Star Wars");
        facouriteMovies.put("Olivia","james bond");
        facouriteMovies.replaceAll((f,m)-> m.toUpperCase());
    }

    public void mergePattern(){
        /**
         * 합침
         *  replace패턴은 한개에 map에만 적용할수 있다. 두개의 맵에서 값을 합치거나 바꿔야한다면
         *  merge메서드를 이용하면된다.
         *
         * */
        // 중복된 키가 없다면 두개의 맵을 하나로 합치는데는 문제없는 코드가 된다.
        Map<String, String> family = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond"));
        Map<String, String> friends = Map.ofEntries(entry("Raphael", "Star Wars"));

        System.out.println("--> Merging the old way");
        Map<String, String> everyone = new HashMap<>(family);
        everyone.putAll(friends);
        System.out.println(everyone);
        // 좀더 유연한 방식으로 합치는 방법이 존재하는 중복된 키를 어떻게 합칠지 결정하는 BiFunction을 인수로받는다.
        // Cristina나가 둘다 존재하지만 키는 같지만 값이 다른경우를 예로 들어보겠다.
        Map<String, String> family2 = Map.ofEntries(
                entry("Teo", "Star Wars"),
                entry("Cristina", "James Bond"));
        Map<String, String> friends2 = Map.ofEntries(
                entry("Raphael", "Star Wars"),
                entry("Cristina", "Matrix"));
        // forEach와 merge를 이용해 충동을 해결한다.
        Map<String,String> everyone2 = new HashMap<>();
        friends2.forEach((k,v)->everyone2.merge(k,v,(m1,m2)->m1 + " & " + m2)); //중복된 값이 존재하는경우 두값을 연결

        /**
         * 1.merge는 널값과 관련된 복잡한 상황도 처리 가능하다.
         *  "지정된 키와 연관된 값이 없거나 값이 null이면 [merge]는 키를 null이 아닌값과 연결한다. 아니면 [merge]는 연결된 값을 주어진
         *  매핑 함수의 결과 값으로 대치하거나 결과가 null이면 [항목]을 제거한다"
         *
         * 2.merge를 사용해 초기화두 구현가능하다.
         *
         * */
         // 영화를 몇회 신청했는지 기록하는 맵이다.
        Map<String,Long> moviesToCount = new HashMap<>();
        String moviesName = "JamesBond";
        Long count = moviesToCount.get(moviesName);
        if (count == null) {
            moviesToCount.put(moviesName,1L);
        } else {
            moviesToCount.put(moviesName,count+1L);
        }

        //다음과 같이 간단하게 구현
        moviesToCount.merge(moviesName,1L,(k,c)->c + 1L);
        // 두번째 인수 1L -> 자바독에 따르면 해당 인수는 키와 연관된 기존 값에 합쳐질 널이 아닌 값 또는 값이 없거나 null값이 연관되어 있다면
        // 해당 값을 key와 연결한다. (즉,간단히 말에 moviesName라는 키값이 존재하지않으면 2번째 인수로 매핑시킨다는 말이다.초기화라 봐도 된다. )
        // 만얌 moviesName라는 키값이 존재한다면 중복이니 value값을 cnt + 1로 초기화 한다는 의미다.
        
    }


    /**
     * Map.of 메서드 key와 value를 최대 10개 까지 넣어 Map을 반환하는 메소드를 지원
     * */
    public void mapOfMethod() {
        // Map.of 는 Map.of 에는 key와 value를 최대 10개 까지 넣을 수 있는 메소드를 지원
        Map<String,Integer> mapOf = Map.of("asd",2,"xzc",3,"aqwe",4,"asadas",5,"asdas",6);
        mapOf.put("실행",3);
        // 에러 발생 Exception in thread "main" java.lang.UnsupportedOperationException
        // immutable Map 에 값을 넣을땐, List를 사용 해야한다.
        List<String> list1 = new ArrayList<>();
        list1.add("P1");
        list1.add("Q1");

        List<String> list2 = new ArrayList<>();
        list2.add("P2");
        list2.add("Q2");
        Map<Integer, List<String>> map = Map.of(111, list1, 222, list2);
        System.out.println(map);

        list1.add("R1");

        System.out.println(map);
    }

}

