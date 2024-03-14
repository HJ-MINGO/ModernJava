package com.modern.exec;

public class ReFactory {
    
    /**
     * 코드의 가독성을 높이려면 코드의 문서화를 잘하고, 표준 코딩 규칙을 준수하는 등의 노력을 기울여야 한다.
     * */

    /**
     * 람다를 이용한 리팩터링
     *  1. 익명 클래스를 람다 표현시긍로 리팩터링 하기
     * */
    public void reFactory(){
        Runnable r1 = new Runnable() { // -> 익명 클래스를 사용해 작성
            @Override
            public void run() {
                System.out.println("1단계");
            }
        };
        
        Runnable r2 = ()-> System.out.println("2단계"); // 람다표현식으로 리팩터링
    }

    public void reFactory2() {
        /**
         * ※주의※
         * [모든 익명 클래스를 람다 표현식으로 변환할수는 없다.]
         *
         * 1. 익명 클래스에서 사용한 this와 super는 람다 표현식에서 다른의미를 갖는다.
         * 익명 클래스에서 this는 익명클래스 자신을 가리키지만 람다에서 this는 람다를 감싸는 클래스를 가리킨다.
         * 2. 익명 클래스는 감싸고 있는 클래스의 변수를 가릴수 있다.(섀도우 변수)
         * */
        //2번에 예시 익명 클래스는 감싸고 있는 클래스의 변수를 가릴수 있지만 람다는 변수를 가릴수 없다.
        int a =10;
        Runnable r1 = ()->{
//            int a =2; // 컴파일 에러( 변수 _ 가 이미 메소드 ___ 에서 정의되었다. 라는 오류 발생) => 람다는 변수를 가릴수 없다.
            System.out.println(a);
        };

        Runnable r2 = new Runnable() {
            @Override
            public void run() {
                int a = 2; // 작 작동됨 => 익명 클래스는 감싸고 있는 클래스의 변수를 가릴수 있다.
                System.out.println(a);
            }
        };

        /**
         * 3. 익명 클래스를 람다 표현식으로 바꾸면 콘텍스트 오버로딩에 따른 모호함이 초래된다.
         *  익명클래스는 인스턴스화 할때 명시적으로 형식이 정해지는 반면 람다의 형식은
         *  "콘텍스트"에따라 달라진다.
         * */
        // 오버로딩 된 2개의 doSomething이 있다고 가정하자.
        // 여기서 doSomething의 매개변수 Task를 익명함수로 구현해서 던진다고 가정해보자
        // 이러면 doSomething의 매개변수로 Task를 구현하는 익명클래스로 매개변수로 던지기 때문에
        // 어떠한 시그니처의 doSomething인지가 명확해진다.
        doSomething(new Task() {
            @Override
            public void excute() {
                System.out.println("익명으로 구현");
            }
        });
        
        // 반면, 람다로 익명클래스를 사용할경우는 매개변수로 함수형 인터페이스가
        // 어떤시그니처를 갖는 함수형 인터페이스를 구현한건지 ,Task 인지지, Runable인지 알수가 없다.
        // doSomething(Runable) vs doSomething(Task) 이러한 모호함이 생기게된다.
//        doSomething(()->System.out.println("Hello")); // -> 에러발생
        /* 명시적 형변환을 통해 모호함을 제거할수있다.*/

        doSomething((Task)()-> System.out.println("Hello"));

    }
    interface Task {
        public void excute();
    }
    public static void doSomething(Runnable r){r.run();} // Runable을 시그니처로 갖는 함수형 인터페이스
    public static void doSomething(Task a){a.excute();} // Task를 시그니처로 갖는 함수형 인터페이스

}
