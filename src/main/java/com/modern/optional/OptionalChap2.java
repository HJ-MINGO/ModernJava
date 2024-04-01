package com.modern.optional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalChap2 {
    /**
     * Optional 클래스
     *  선택형값을 캡슐화 하는 클래스다.
     *  값이 존재하면 Optional 클래스는 값을 감싼다. 반면 값이 없으면 Optional.empty 메서드로
     *  Optional 을 반환한다. Optional.empty 메서드는 Optional 의 특별한 싱클턴 인스턴스를 반환하는 정적팩토리 메서드다.
     *
     * ※ null 참조   VS  Optional.empty() ※
     *  의미적으로는 비슷하지만 실제로 차이점이 존재한다.
     *  null 을 참조하려하면 우리가 흔히 알듯이 NullPointException 이 발생하지만
     *  Optional.empty()는 Optional 객체이므로 이를 다양한 방식으로 활요잉 가능하다.
     *
     * */
    // 기존 Person, Car 클래스는 OptionalChap1의 재정의
    // Optional 로 재정의
    class Person {
        private Optional<Car> car;
        public Optional<Car> getCar(){
            return car;
        }
    }
    // Optional 로 재정의
    class Car {
        private Optional<Insurance> insurance;
        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    class Insurance{
        /**
         * 조건 : 해당 객체의 name 은 반드시 존재해야하는 필드변수이다.
         * 해당 객체의 name 은 Optional 로 감싸져있지않은데 이는 곧 NullPointException 이 발생할수 있을 알수있다.
         * 그럼에도 Optional 로 감싸지 않은이유는 Optional 로 감싸서 문제를 숨기는게 아니라
         * 왜 값이 존재하지 않는지 이유를 밟혀 문제를 해결해야한다.
         * 그렇기 때문에 모든 null 참조 를 Optional 로 감싸는것은 옳지 않은 행동이다.
         * */
        private String name;
        public String getName(){
            return name;
        }
    }

    /* Optional 적용패턴 */

    // Optional 객체 만들기
    public void main1(){

        Optional<Car> optCar = Optional.empty(); // 정적팩토리 메서드 Optional.empty()로 빈 Optional 객체를 얻는다.

        // 정적팩토리 메서드 Optional.of()로 null 이 아닌 값을 포함하는 Optional 을 만들수 있다.
        Car car = new Car();
        Optional<Car> optCar2 = Optional.of(car); // car 가 null 이라면 즉시 NullPointException 이 발생한다.
        // 만약 Optional 을 사용하지 않았다면 car 의 프로퍼티에 접근하려는 순간부터 에러가 발생햇을것이다.

        //Optional.ofNullable()로 null 값을 저장할 수있는 Optional 을 만들수있다.
        // 만약 car 가 null 이면 빈 Optional 객체가 반환된다.
        Optional<Car> optCar3 = Optional.ofNullable(car);

    }

    public void main2(){
        Insurance insurance = new Insurance();

        // 일반적인 null 체크
        String name = null;
        if (insurance != null) {
            name = insurance.getName();
        }

        // Optional 의 map()메서드를 이용한 insurance null 체크
        Optional<Insurance> insurance2 = Optional.ofNullable(insurance);
        Optional<String> name2 = insurance2.map(Insurance::getName);
        // Optional 의 map()에 값이 존재하면 해당 타입형태로 바꾼뒤에 다시 옵셔널로 감싸서 반환한다
    }

    // main2()의 과정을 flatMap으로 정의
    public void main3(){
        Person person = new Person();
        // person 을 Optional 로 생성
        Optional<Person> optPerson = Optional.of(person);
//        Optional<String> name = optPerson.map(Person::getCar)
//                .map(Car::getInsurance)
//                // .map(Car::getInsurance) 에러발생
//                // 에러 발생 이유: optPerson 인스턴스는 Optional<Person> 형태이기때문에 map 메서드를 사용가능하다
//                // 하지만 optPerson 인스턴스를 이용해 map 메서드를 사용해 getCar()메서드를 하게되면
//                // Optional<Car> 형식의 객체를 반환해준다.
//                // 그렇기 때문에 2번쨰 map 메서드에 전달되는 값은 Optional<Optional<Car>> 형식이기 때문에 오류가 발생하는것이다.
//
//                .map(Insurance::getName );
    // 그래서 위에 같은 방식을 해결하고자 flatMap 을 사용해 생선된 각각의 스트림에서 콘텐츠만 남겨 전달하도록 한다,.
        Optional<String> name = optPerson.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName);
    /**
     * 결론 : Optional flatMap 메서드로 전달된 함수는 Optional 에 Person 에 저장된 Car 로 바꾼다.
     * map 메서드였다면 Optional 내부에 다른 Optional 그리고 그 내부에 Car 로 저장되었겠지만 (Optional<Optional<Car>> 형식)
     * flatMap 메서드 덕분에 이차원 Optional 이 하나의 Car 를 포함하는 하나의 Optional 로 바꾼다.
()     * */
    }

    public String getCarInsuranceName(Optional<Person> person){
        return person.flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("NoName"); //Optional 이 비어있으면 기본값 "NoName" 리턴
    }

    public Set<String> getCarInsuranceNames(List<Person> persons) {
        return persons.stream()
                .map(Person::getCar) // 1번째 map()메서드에서는 getCar()메서드는 Optional<Car> 를 반환 (사람이 차를 가지지 않을수도 있는 상황을 반환)
                //그래서 여기선 1번째 연산이 끝나고 Stream<Optional<Car>> 값이 2번째 map 으로 전달
                .map(optCar-> optCar.flatMap(Car::getInsurance))
                //2번째 map에서는 Optional<Car>를 Optional<Insurance>로 변환한다.
                .map(optInsurance-> optInsurance.map(Insurance::getName))
                //3번째 map에서는 각각을 Optional<String> 으로 변환 시킨다.
                .flatMap(Optional::stream)
                // 그결과 flatMap 에는 3번째 map()으로 받은 Stream<Optional<String>>를 얻게되기 때문에 flatMap 을 사용하는것이다.
                // 이결과 NULL 걱정없이 안전하게 값을 처리할순 있지만 마지막결과를 얻으려면 빈 Optional 을 제거하고 값을
                // 언랩해야(감싸고있는 Optional 을 제거) 사용가능하기때문에 기회비용이 많이 든다.
                .collect(Collectors.toSet());
    }

    /**
     * Optional 클래스의 stream() 메서드를 이용하면 한번의 연산으로 같은결과를 얻을수 있다.
     * 해당 메서드는 각 Optional 이 비어있는지 아닌지에 따라 Optional 을 0개이상의 항목을 포함하는 스트림으로 변환한다.
     * 해당메서드는 참조를 스트림의 한 요소에서 다른 스트림으로 적용하는 함수로 볼 수 있으며 이를 원래 스트림에 호출하는
     * flatMap 메서드로 전달 할 수 있다.
     * */
    public Set<String> getCarInsuranceNames2(Stream<Optional<String>> stream) {
        return stream.filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }
}
