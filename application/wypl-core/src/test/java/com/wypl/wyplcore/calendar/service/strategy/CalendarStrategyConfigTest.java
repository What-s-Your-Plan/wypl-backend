package com.wypl.wyplcore.calendar.service.strategy;

import com.wypl.wyplcore.schedule.data.CalendarType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalendarStrategyConfigTest {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void testRegisterBean(){
        assertNotNull(applicationContext.getBean(CalendarStrategyConfig.class));
        assertTrue(applicationContext.containsBean("calendarStrategyMap"));

        Map<?, ?> instance = (Map<?, ?>) applicationContext.getBean("calendarStrategyMap");
        for (Map.Entry<?, ?> entry : instance.entrySet()) {
            assertThat(entry.getKey()).isInstanceOf(CalendarType.class);
            assertThat(entry.getValue()).isInstanceOf(CalendarStrategy.class);
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }

    @Test
    void testStrategyFlyweight() {

        // Flyweight(CalendarStrategy)의 공유 역할을 하는 calendarStrategyMap이 싱글톤으로 관리되는 지 확인
        Map<?, ?> instance = (Map<?, ?>) applicationContext.getBean("calendarStrategyMap");
        Map<?, ?> instance2 = (Map<?, ?>) applicationContext.getBean("calendarStrategyMap");
        assertEquals(instance, instance2);

        // 여러 번 호출 시 같은 전략 인스턴스가 호출되는 지 확인
        assertEquals(instance.get("DAY"), instance2.get("DAY"));
    }
}

