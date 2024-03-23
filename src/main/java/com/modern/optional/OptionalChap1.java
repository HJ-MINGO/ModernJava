package com.modern.optional;

public class OptionalChap1 {
    /**
     * 1965년 토니 호어라는 영국 컴퓨터과학자가 heap에 할당되는 레코드를 사용하며 형식을 갖는 최초의 프로그래밍
     * 언어 중 하나인 "알골"을 설계하면서 처음 null참조가 등장했다.
     * 컴파일러의 자동확인 기능으로 모든 참조를 안전하게 사용할 수 있을것을 목표로 만들어졌다.
     *
     * */

    class Person {
        private Car car;
        public Car getCar(){
            return car;
        }
    }

    class Car {
        private Insurance insurance;

        public Insurance getInsurance() {
            return insurance;
        }
    }

    class Insurance{
        private String name;
        public String getName(){
            return name;
        }
    }

    //여기서 getCar()을 호출하는 순간 nullException이 발생할것이다.
    public String main1(Person person){

        return person.getCar().getInsurance().getName();
    }

    //보수적인 자세로 null처리를 하는경우 필요한 곳에 null처리 확인코드를 추가하거나
    //굳이 필요하지않은 부분까지 null처리를 하는경우도 생기게될것이다.
    public String main2(Person person) {
        if (person != null) {
            Car car = person.getCar();
            if (car != null) {
                Insurance insurance = car.getInsurance();
                if (insurance != null) {
                    return insurance.getName();
                }
            }
        }
        return "Unknown"; // 중간에 하나라도 null참조 발생시 "Unknown" 리턴
        // 이런식으로 null체크를 위한 if문이 추가되는 반복패턴을 "깊은 의심"(deep doubt)라고 부른다.
        // 중첩된if문으로 인해 들여쓰기가 증가하고 가독정도 떨어지고 코드도 엉망이된다.
    }

    // 중첩 if문을 없앴지만
    // 대신 4개의 출구가 생겨 유지보수시 매우 어려워진다.
    // 또한 상수가 아닌 똑같은 값을 3개나 그냥 문자열로 넘길시 오타가 발생할수도 있다.
    public String main3(Person person) {
        if (person == null) {
            return "Unknown";
        }
        Car car = person.getCar();
        if (car == null) {
            return "Unknown";
        }
        Insurance insurance = car.getInsurance();
        if (insurance == null) {
            return "Unknown";
        }
        return insurance.getName();
    }
    /**
     * null로인해 발생하는 문제
     *  1. 에러의 근원 : NullpointerException발생
     *  2. 아무의미가 없다 : null은 아무런 의미가 없다. 정적 형식 언어에서 값이 없음을 표현하는 방법으로 null을 쓰는건 좋지않다.
     *  3. 코드를 어지럽힌다. : main2(),main3()메서드와 같이 null확인하는 코드로 여러출구,여러 중첩코드가 발생해 가독성이 떨어진다.
     *  4. 자바철학에 위배된다. : 자바는 개발자로부터 모든 포인터를 숨겼다. 하지만 예외가 있는데 그게바로 null포인터다.
     *  5. 형식 시스템의 구멍을 만든다 : null은 무형식이며 정보를 포함하고 있지 않으므로 모든 참조형식에
     *  null을 할당 할 수 있다. 이런 식으로 null이 할당되기 시작하면서 시스템의 다른 부분으로 null이 퍼졌을때 애초에 null이 어떤
     *  의미로 사용되었는지 알수 없다.
     * */

}
