package com.modern.optional;

import java.util.Optional;

public class OptionalChap2 {
    /**
     * Optional클래스
     *  선택형값을 캡슐화 하는 클래스다.
     *  값이 존재하면 Optional클래스는 값을 감싼다. 반면 값이 없으면 Optional.empty 메서드로
     *  Optional을 반환한다. Optional.empty메서드는 Optional의 특별한 싱클턴 인스턴스를 반환하는 정적팩토리 메서드다.
     *
     * ※ null참조   VS  Optional.empty() ※
     *  의미적으로는 비슷하지만 실제로 차이점이 존재한다.
     *  null을 참조하려하면 우리가 흔히 알듯이 NullPointerException이 발생하지만
     *  Optional.empty()는 Optional객체이므로 이를 다양한 방식으로 활요잉 가능하다.
     *
     * */
    // Optional로 재정의
    class Person {
        private Optional<Car> car;
        public Optional<Car> getCar(){
            return car;
        }
    }
    // Optional로 재정의
    class Car {
        private Optional<Insurance> insurance;
        public Optional<Insurance> getInsurance() {
            return insurance;
        }
    }

    class Insurance{
        /**
         * 조건 : 해당 객체의 name은 반드시 존재해야하는 필드변수이다.
         * 해당 객체의 name은 Optional로 감싸져있지않은데 이는 곧 NullpointException이 발생할수 있을 알수있다.
         * 그럼에도 Optional로 감싸지 않은이유는 Optinal로 감싸서 문제를 숨기는게 아니라
         * 왜 값이 존재하지 않는지 이유를 밟혀 문제를 해결해야한다.
         * 그렇기 때문에 모든 null참조를 Optional로 감싸는것은 옳지 않은 행동이다.
         * */
        private String name;
        public String getName(){
            return name;
        }
    }

    /* Optional 적용패턴 */

    // Optional객체 만들기
    public void main1(){

        Optional<Car> optCar = Optional.empty(); // 정적팩토리 메서드 Optional.empty()로 빈 Optional객체를 얻는다.

        // 정적팩토리 메서드 Optional.of()로 null이 아닌 값을 포함하는 Optional을 만들수 있다.
        Car car = new Car();
        Optional<Car> optCar2 = Optional.of(car); // car가 null이라면 즉시 NullPoniterException이 발생한다.
        // 만약 Optional을 사용하지 않았다면 car의 프로퍼티에 접근하려는 순간부터 에러가 발생햇을것이다.

        //Optional.ofNullable()로 null값을 저장할 수있는 Optional을 만들수있다.
        // 만약 car가 null이면 빈 Optional객체가 반환된다.
        Optional<Car> optCar3 = Optional.ofNullable(car);

    }

    public void main2(){
        Insurance insurance = new Insurance();

        // 일반적인 null체크
        String name = null;
        if (insurance != null) {
            name = insurance.getName();
        }

        // Optinal의 map()메서드
        Optional<Insurance> insurance2 = Optional.ofNullable(insurance);
        Optional<String> name2 = insurance2.map(Insurance::getName);
    }




}
