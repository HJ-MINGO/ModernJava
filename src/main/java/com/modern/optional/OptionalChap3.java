package com.modern.optional;

import javax.print.DocFlavor;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

public class OptionalChap3 {
    /**
     * 디폴트 액션과 Optional 언랩
     * Optional 인스턴스에 포함된 값을 읽는 다양한 방법
     * */
    public void optionalGet(){
        /**
         * get() 메서드는 값을 읽는 가장 간단한 방법임과 동시에 가장 안전하지 않은 메서드다.
         * get() 메서드는 래핑된 값이 있으면 해당 값을 반환하고 값이 없으면 NoSuchElementException 을 발생시킨다.
         * 따라서 Optional 에 값이 반드시 있다고 가정할 수 있는 상황이 아니면 get()메서드를 사용하지 않는 것이 바람직하다.
         * 결국 중첩된 null 체크를 하는 상황과 크게 다를게없다.
         * */
        Optional<String> name = Optional.empty();
        name.get(); // Optional 에 값이 반드시 있다고 가정할 수 있는 상황에서만 사용
    }

    public void optionalOrElse(){
        /**
         * orElse(T other)메서드는 Optional 에 값이 포함하지 않을때 기본값을 제공 할수 있다.
         * */
        Optional<String> name = Optional.empty();
        name.orElse("UnKnown");

    }

    public void optionalOrElseGet(){
        /**
         * orElseGet(Supplier< ? extends T> other)는 orElse 메서드에 대응하는 게으른 버전의메서드다.
         * Optional 에 값이 없을 때만 Supplier가 실행되기 때문이다.디폴트 메서드를 만드는 데 시간이 걸리거나
         * (효율성때문에) Optional 이 비어있을 때만 기본값을 생성하고 싶다면(기본값이 반드시 필요한 상황)
         * orElseGet(Supplier< ? extends T> other)를 사용해야 한다.
         *
         * */
        Optional<String> name = Optional.empty();
        name.orElseGet(String::new);
    }

    public void optionalOrElseThrow() {
        /**
         * orElseGet(Supplier< ? extends T> exceptionSupplier)는 Optional 이 비어있을때 예외를 발생시킨다는
         * 점에서 get() 메서드와 비슷하다.하지만 이 메서드는 발생시킬 예외의 종류를 선택할 수 있다.
         *
         * */

    }

    public void optionalIfPresent() {
        /**
         * ifPresent(Consumer< ? super T> consumer)를 이용하면 값이 존재할 대 인수로 넘겨준 동작을 실행 할 수 있다.
         * 값이 없으면 아무일도 일어나지 않는다
         *
         * */
    }

    public void optionalIfPresentOrElse() {
        /**
         * ifPresentOrElse(Consumer< ? super T > action, Runnable emptyAction)이 메서드는 Optional 이 비었을때
         * 실행할 수 있는 Runnable 을 인수로 받는다는 점만 ifPresent와 다르다. 
         *
         * */
    }

    public void getOptionalValue(){
        /**
         * 예를들어  Map의 get 메서드는 요청한 키에 대응하는 값을 찾지 못했을 때 null 을 반환한다.
         * 지금까지 살펴본걸로 치면 그냥 null을 반환하는것보다 Optional로 감싸서 반환하는게 더 바람직하다.
         * Map의 get()메서드의 시그니처는 우리가 고칠수없지만 get()메서드의 반환값을 Optional 로 감쌀수 있다.
         * */
        Map<String,Object> map = new HashMap<>();
        Object value = map.get("key"); //여기서 "key"라는 key값의 해당하는 값이 없을경우 null을 반환한다.\

        //Optional 을 이용한 안전한 방법
        Optional<Object> value2 = Optional.ofNullable(map.get("key"));
    }
}
