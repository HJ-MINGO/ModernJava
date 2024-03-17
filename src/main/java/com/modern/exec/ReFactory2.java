package com.modern.exec;

import com.modern.dto.Dish;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ReFactory2 {
    /**
     * 스트림은 멀티코어 아키텍처를 활용할 수 있는 지름길을 제공 한다.
     *
     * */
    public void disReFactory(){
        List<String> dishNames = new ArrayList<>();
        for (Dish dish : Dish.menu) {
            if (dish.getCalories()>300) {
                dishNames.add(dish.getName());
            }
        }
        // 스트림 사용
        // 더욱 직접적으로 기술 가능하면 쉽게 병렬처리가 가능하다.
        Dish.menu.parallelStream()
                .filter(d -> d.getCalories()>300)
                .map(Dish::getName)
                .collect(Collectors.toList());

    }

    /**
     * 람다 표현식을 이용하려면 함수형 인터페이스를 적용해야 한다.
     * 함수형 인터페이스를 코드에 추가해야한다.
     *  1. 조건부 연기 실행
     *  2. 실행 어라운드
     * */
    /* 조건부 연기 실행 */
    public void conditionalDeferredExecution(){
        Logger logger = Logger.getLogger("ReFactory2");
        if (logger.isLoggable(Level.FINER)) {
            logger.finer("Problem: ");
        }

        logger.log(Level.FINER,"Problem: ");
    }
}
