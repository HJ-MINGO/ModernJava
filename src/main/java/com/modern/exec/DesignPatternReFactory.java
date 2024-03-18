package com.modern.exec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.UnaryOperator;

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

    /**
     * ===================================================
     *  3. 옵저버
     * ===================================================
     * */


    /**
     * 옵저버 패턴
     * 어떤 이벤트가 발생했을때ㅣ 한 객체(주제)가 다른 객체 리스트(옵저버)에 자동으로 알림을 보내야하는 상황에 사용
     * 일반적으로 GUI 애플리케이션에서 옵저버 패턴이 자주등장한다.
     * 버튼 같은 GUI 컴포넌트에 옵저버를 설정할 수 있다. 그리고 사용자가 버튼을 클릭하면 옵저버에 알림이 전달되고
     * 정해진 동작이 수행된다. 꼭 GUI에서만 옵저버를 사용하는것은 아니다. 예를들어 주식의 가격(주제) 변동에 반응하는
     * 다수의 거래자(옵저버) 예제에세도 옵저버 패턴이 사용된다.
     *
     * 예를 들어 트위터 같은 커스텀마이즈된 알림시스템을 설계하고 구현할수 있다.
     * 다양한 신문매체에서 내가 구독한 뉴스트위,또는 특정키워드가 포함된 트윗이 등록되면 알림을 받는 형식
     *
     * */

    interface Observer{
        // 다양한 옵저버를 그룹화할 인터페이스
        // 해당 인터페이스는 새로운 트윗이 있을때 주제가 호출될수 있도록 하나의 메서드를 제공
        void notify(String tweet);
    }
    
    // 내가 설정한 키워드들마다 동작할수있는 옵저버를 정의
    class NYTimes implements Observer {
        public void notify(String tweet){
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in Ny" +  tweet);
            }
        }
    }

    class Guardian implements Observer {
        public void notify(String tweet){
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Breaking news in Guardian" +  tweet);
            }
        }
    }

    class LeMond implements Observer {
        public void notify(String tweet){
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Breaking news in LeMond" +  tweet);
            }
        }
    }

    interface Subject {
        void registerObserver(Observer o);
        void notifyObserver(String tweet);
    }

    // 주제는 registerObserver 메서드로 새로운 옵저를 등록하고 notifyObserver메서드로 트윗의 옵저버에 이를 알린다.
    class Feed implements Subject {
        // 이제 Feed는 트윗을 받았을때 알림을 보낼 옵저버 리스트를 유지한다.
        // 이제 이렇게 구성함으로서 주제와 옵저버를 연결하는 데모 애플리케이션을 만들수있다.
        private final List<Observer> observerList = new ArrayList<>();

        public void registerObserver(Observer o) {
            this.observerList.add(o);
        }

        public void notifyObserver(String tweet) {
            observerList.forEach(o->o.notify(tweet));
        }
    }

    public void ObserverUseMethod(){
        Feed feed = new Feed();
        feed.registerObserver(new NYTimes());
        feed.registerObserver(new Guardian());
        feed.registerObserver(new LeMond());
        // 이럼으로 이제 가디언도 우리의 트윗을 받아볼수있다.
        feed.notifyObserver("The queen said her Favourite book is Modern Java In Action");

    }

    public void RamdaObserverUseMethod() {
        // 3개의 NYTimes,Guardian,LeMond 명시적으로 인스턴스화 하지 않고
        // 람다표현식으로 직접 전달해서 사용한다.
        Feed feed = new Feed();
        feed.registerObserver((String tweet)->{
            if (tweet != null && tweet.contains("money")) {
                System.out.println("Breaking news in Ny" +  tweet);
            }
        });

        feed.registerObserver((String tweet)->{
            if (tweet != null && tweet.contains("queen")) {
                System.out.println("Breaking news in Guardian" +  tweet);
            }
        });

        feed.registerObserver((String tweet)->{
            if (tweet != null && tweet.contains("wine")) {
                System.out.println("Breaking news in LeMond" +  tweet);
            }
        });
        // 물론 비교적 동작이 쉬울경우는 이렇게 사용할수 있다지만 , 옵저버가 상태를 가지고
        // 여러 메서드를 정의하는 복잡한 구조가된다고하면 이러한 람다표현식 보다 클래스 구현방식이 더 현명하다.
    }


    /**
     * ===================================================
     *  4. 의무체인
     * ===================================================
     * */


    /**
     * 의무체인 패턴
     *  작업 처리 객체의 체인(동작 체인 등)을 만들 때는 의무체인 패턴을 사용한다.
     *  한 객체가 어떤 작업을 처리한 다음에 다른 객체로 결과를 전달하고, 다른객체도 해야 할 작업을 처리한
     *  다음에 또 다른 객체로 전달하는 방식이다.
     *  일반 적으로 다음으로 처리할 객체 정보를 유지하는 필드를 포함하는 작업처리 추상 클래스로 의무체인을 구성한다.
     *  작업 처리 객체가 자신의 작업을 끝냈으면 다음 작업 처리 객체로 결과를 전달한다.
     *
     * */
    /**         의무체인 패턴 UML
     * 
     *              ConcreteProcessingObject
     *                      ◇       │   
     *                      │       │
     *                      │       │
     *                      │       │
     *                      ▼       ▼   
     *                  ProcessingObject  ◀────── 클라이언트
     *                     + handle()
     * 템플릿 메서드 디자인 패턴이 사용된걸 확인 가능하다.
     *
     * */
    public abstract class ProcessingObject<T> {
        protected ProcessingObject<T> successor;
        public void setSuccessor(ProcessingObject<T> successor) {
            this.successor = successor;
        }
        public T handle(T input) { // 일부 작업을 어떻게 처리해야할지 전체적으로 기술
            T r =handleWork(input);
            if (successor != null) {
                return successor.handle(r);
            }
            return r;
        }
        // 이제 ProcessingObject클래스를 상속받아 handleWork메서드를 구현하연 다양한 종류의
        // 작업처리 객체를 만들 수잇따.
        abstract protected T handleWork(T input);
    }

    //텍스트를 처리하는 2개의 클래스
    class HeaderTextProcessing extends ProcessingObject<String> {
        public String handleWork(String text) {
            return "From Raoul , Mario and Alan: " + text;
        }
    }
    class SpellCheckerProcessing extends ProcessingObject<String> {
        public String handleWork(String text) {
            return text.replaceAll("labda","lambda");
        }
    }
    public void jobChain(){
        ProcessingObject<String> p1 = new HeaderTextProcessing();
        ProcessingObject<String> p2 = new SpellCheckerProcessing();
        p1.setSuccessor(p2); // HeaderTextProcessing 와 SpellCheckerProcessing 두작업처리를 연결
        String result = p1.handle("Aren't labdas really sexy?!!");
        System.out.println(result);
    }

    /**
     * 위에 jobChain메서드를 람다표현식으로 리팩터링
     * 해당 패턴은 함수체인(함수조합)과 비슷하다.
     * 작업 처리 객체를 Function<String,String>, 더 정확하게 표현하자면 UnaryOperator<String> 형식의 인스턴스로
     * 표현 할 수 있다. andThen 메서드로 이들 함수를 조합해 체인을 만들 수있다.
     *
     * */
    public void jobChainLamda(){
        UnaryOperator<String> headerProcessing =
                (String text)-> "From Raoul , Mario and Alan: " + text; // 첫번째 작업 처리 객체
        UnaryOperator<String> SpellCheckerProcessing =
                (String text)-> text.replaceAll("labda","lambda");// 두번째 작업 처리 객체
        Function<String , String> pipeline =
                headerProcessing.andThen(SpellCheckerProcessing); // 동작체인으로 두작업(함수)를 조합
        String result = pipeline.apply("Aren't labdas really sexy?!!");
    }
}
