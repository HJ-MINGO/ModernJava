package com.modern.exec;

import java.util.function.Consumer;

public class DesignPatternReFactory {

    /**
     * 람다로 객체지향 디자인패턴 리팩터링 하기
     * 1. 전략 패턴
     * 2. 템플릿 메서드 패턴
     * 3. 옵저버 패턴
     * 4. 의무체인 패턴
     * 5. 팩토리 패턴
     *
     * */


    /**
     *
     * 전략 패턴
     *  전략패턴은 한 유형의 알고리즘을 보유한 상태에서 런타임에 적절한 알고리즘을 선택하는 기법 이다.
     *
     *                                ┏━━━━━━━━ ConcreteStrategyB
     *  클라이언트 -----> 전략 [execute()] ━━━━━━━ ConcreteStrategyA
     *
     *  ● 알고리즘을 나타내는 인터페이스 (Strategy 인터페이스)
     *  ● 다양한 알고리즘을 나타내는 한 개 이상의 인터페이스 구현(ConcreteStrategyA,ConcreteStrategyB)
     *  ● 전략 객체를 사용하는 한개 이상의 클라이언트
     *
     * */
    
    /**
     * ===================================================
     *  1. 전략 패턴
     * ===================================================
     * */
    private final ValidationStrategy va;

    public DesignPatternReFactory(ValidationStrategy va) {
        this.va = va;
    }

    public boolean  validate(String s) {
        return va.execute(s);
    }

    // 일단 입력받는 텍스트를 검증하기 위한 인터페이스 작성
    interface ValidationStrategy {
        boolean execute(String s);
    }
    // ValidationStrategy인터페이스를 구현한 조건 클래스 구현 [오직 소문자로만 이루어지는 조건]
    class IsAllLowerCase implements ValidationStrategy {
        public boolean execute(String s){
            return s.matches("[a-z]+");
        }
    }
    // ValidationStrategy인터페이스를 구현한 조건 클래스 구현 [오직 숫자만 되야하는 조건]
    class IsNumeric implements ValidationStrategy{
        public boolean execute(String s) {
            return s.matches("\\d+");
        }
    }

    public void strategyPattern(){
        // 오직 소문자 또는 숫자로 이루어져야하는 등 텍스트 입력값이 앞서 언급한 내용이 아니더라도 다양한 조건에 맞게 포맷되어야한다.
        // 전략패턴 사용 (실제론 Validate클래스를 따로 작성해햐하지만 귀찮아서...)
        DesignPatternReFactory va = new DesignPatternReFactory(new IsAllLowerCase());
        boolean b = va.validate("aaaaa");
        DesignPatternReFactory va2 = new DesignPatternReFactory(new IsNumeric());
        boolean b2 = va.validate("bbbbb");

        // 람다를 이용한 전략 패턴을 빠르게 리팩터링 -> 구현클래스를 직접 전달하니 자잘한 코드가 사라진다.
        DesignPatternReFactory va3 = new DesignPatternReFactory((String s)->s.matches("[a-z]+]"));
        boolean b3 = va3.validate("aaaaa");
        DesignPatternReFactory va4 = new DesignPatternReFactory((String s)->s.matches("\\d+"));
        boolean b4 = va4.validate("bbbbb");
    }

    /**
     * ===================================================
     *  2. 템플릿 메서드
     * ===================================================
     * */


    /**
     *
     * 템플릿 메서드 패턴
     *  알고리즘의 개요를 제시한 다음에 알고리즘의 일부를 ㄹ고칠 수 있는 유연함을 제공해야 할 때 템플릿
     *  메서드 디자인 패턴을 사용한다.
     *  간단하게 말하자면 '이 알고리즘을 사용하고싶은데 그대로는 안되고 조금 수정이 필요한 상황'에 적합하다.
     *
     * */
    //온라인 뱅킹 애플리케이션 동작을 정의하는 추상 클래스
    abstract class OnlineBanking {
        //1. 해당 processCustomer메서드는 온라인 뱅킹 알고리즘이 해야 할일을 보여준다.
        public void processCustomer(int id) {
            Customer c = Database.getCustomerWithId(id); //2. 고객의 id(pk)를 가져온다.
            makeCustomerHappy(c);//3. 그리고 해당 고객id를 이용해 고객이 행복해하는 동작을 수행해야한다.
        }
        // 4. 이제 여러 은행들은 OnlineBanking클래스를 상속받아 makeCustomerHappy()메서드를
        // 구현해 각 은행에 맞는 동작시스템을 만들어야 한다.
        abstract void makeCustomerHappy(Customer c);
    }
    class lamdaOnlineBanking {
        // 이제 람다형태로 바꿔보자
        // makeCustomerHappy의 추상 메서드 시그니처와 일치하도록
        // Consumer<Customer>형식을 갖는 두번째 인수를 해당메서드에 추가한다.
        public void processCustomer(int id, Consumer<Customer> makeCustomerHappy) {
            Customer c = Database.getCustomerWithId(id);
            makeCustomerHappy.accept(c);
        }
    }

    static private class Customer{} // 예제용 dumy 클래스
    static private class Database { // 예제용 dumy Database 클래스

        static Customer getCustomerWithId(int id) {
            return new Customer();
        }

    }


    public void templateMethod() {
        // 온라인 뱅킹애플리케이션이 있다고 가정하자.
        // 사용자가 고객 ID를 애플리케이션에 입력하면 은행 데이터베이스에서 고객정보를 가져오고 고객이 원하는 서비스를 제공
        // 고객계좌에 보너스를 입금한다고 가정하면 은행마다 온라인 뱅킹 시스템(동작방식)도 다 다르다.

        // 그럼 이제 은행들은 OnlineBanking클래스를 상속받지 않고도 직접 람다표현식을 전달해서 다양한 동작을 추가할수잇다.
        new lamdaOnlineBanking().processCustomer(1337,(Customer c)-> System.out.println("Hello "));
    }
}
