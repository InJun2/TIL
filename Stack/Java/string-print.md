# Java 문자열 출력

### String Print 별 성능
- String 문자열을 + 로 연결하여 한번에 출력하기
- StringBuilder 에 문자열을 연결하여 한번에 출력하기
- printf 출력
    - 해당 포매팅만큼 문자열을 여러번 출력하기
- String.format()을 통한 포매팅 후 StringBuilder에 연결하여 한번에 출력하기

<br>

```java
import java.io.IOException;

public class Solution {

    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    public void solution() throws IOException {
        Long time1;
        Long time2;
        Long time3;
        Long time4;

        // maxArea를 미리 설정 (테스트를 위해)
        int maxArea = 100;  // 임의의 값 설정

        // 1. String + 연산 후 1번 출력
        long startTime = System.nanoTime();
        String result1 = "";
        for (int i = 0; i < 1000; i++) {
            result1 += "#Test " + i + " " + maxArea + "\n";  // String + 연산
        }
        System.out.println(result1);
        long endTime = System.nanoTime();
        time1 = endTime - startTime;

        // 2. StringBuilder 사용 후 1번 출력
        startTime = System.nanoTime();
        StringBuilder result2 = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            result2.append("#Test ").append(i).append(" ").append(maxArea).append("\n");  // StringBuilder append 사용
        }
        System.out.println(result2);
        endTime = System.nanoTime();
        time2 = endTime - startTime;

        // 3. printf 사용 하여 여러번 출력 (출력할 타입을 기입해야하므로)
        startTime = System.nanoTime();
        for (int i = 0; i < 1000; i++) {
            System.out.printf("#Test %d %d\n", i, maxArea);  // 출력을 많이 써서 다른 것과 비교해서 성능이 매우 안좋음. 출력 자체 횟수를 줄이는게 Best
        }
        endTime = System.nanoTime();
        time3 = endTime - startTime;

        // 4. String.format과 StringBuilder 함께 사용하여 한번 출력
        startTime = System.nanoTime();
        StringBuilder result4 = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            result4.append(String.format("#Test %d %d\n", i, maxArea));  // String.format 사용 후 StringBuilder에 추가
        }
        System.out.println(result4);
        endTime = System.nanoTime();
        time4 = endTime - startTime;

        System.out.println("Time 1 : " + time1 + " ns");
        System.out.println("Time 2 : " + time2 + " ns");
        System.out.println("Time 3 : " + time3 + " ns");
        System.out.println("Time 4 : " + time4 + " ns");
    }
}

/*

Time 1 : 14330300 ns
Time 2 : 1242701 ns
Time 3 : 53078000 ns
Time 4 : 9181101 ns

*/
```

<br>

### 성능 분석 및 이유

| Method                      | Time (ns)    | 설명 |
|-----------------------------|--------------|------|
| **String + 연산**            | 14,330,300   | - 문자열 `+` 연산은 **불변 객체**인 `String`을 계속 생성하므로, 문자열이 커질수록 **새로운 객체**를 매번 생성하게 됨 <br> - 이로 인해 메모리 소비가 커지고, 많은 GC(Garbage Collection) 작업이 발생해 시간이 오래 걸림. |
| **StringBuilder 사용**       | 1,242,701    | - `StringBuilder`는 가변 객체로, 문자열을 **변경하는 과정에서 새로운 객체를 생성하지 않기 때문에** 매우 효율적. <br> - 반복적으로 문자열을 추가하는 작업에 최적화되어 있어 **가장 빠른 성능**을 보임. |
| **printf 사용**              | 53,078,000   | - `System.out.printf`는 형식을 지정하여 출력을 할 수 있지만, 내부적으로는 출력 스트림을 처리하는 비용이 크기 때문에 가장 **비효율적**. <br> - 이 메서드는 I/O 작업에 많은 시간이 소요되므로 성능이 매우 느림. |
| **String.format + StringBuilder 사용** | 9,181,101    | - `String.format`은 형식 문자열을 사용해 새로운 문자열을 생성하지만, 내부적으로 **문자열 처리에 비교적 많은 연산**이 필요. <br> - 그럼에도 결과를 `StringBuilder`에 추가하면서 객체 생성을 최소화하여 성능이 **String + 연산보다는 빠르고** 순수 `StringBuilder` 사용보다는 느린 결과를 보임. |

<br>

- 최대한 출력 횟수를 줄이는 것이 제일 중요
- 또한, 매번 새로운 객체를 생성하여 합친 문자열을 반환하는 String + 연산은 지양하자

<br>

### String '+'' 연산 동작
- String이 **불변 객체(immutable)**이기 때문에 한 번 생성되면 값을 변경할 수 없음
- String result = "a" + "b";을 수행하면 내부적으로는 새로운 String 객체 "ab"가 만들어짐
- 이 과정이 반복될 때마다 기존 문자열과 새로 추가된 부분을 합친 새로운 객체가 만들어지므로 문자열을 많이 연결할 경우, 매번 새로운 메모리를 할당하여 전체 문자열을 재구성하는 비효율적인 메커니즘을 사용하게 됨
- 결과적으로, 많은 문자열을 더하면 더할수록 매번 새로운 객체가 생성되고, GC(Garbage Collection) 작업도 빈번해지면서 성능이 저하

<br>

### StringBuilder 연산 동작
- StringBuilder는 가변(mutable) 문자열 객체로, String과 달리 한 번 생성된 이후에도 내부의 문자열 데이터를 변경할 수 있음
- StringBuilder는 내부적으로 변경 가능한 버퍼를 가지고 있어, 문자열을 더할 때마다 새로운 객체를 생성하지 않고 기존 버퍼에 추가함
- 메모리가 부족할 경우에만 버퍼 크기를 증가시키며, 추가 작업을 계속해서 수행

<br>

| 특징                        | String + 연산                                      | StringBuilder                                   |
|-----------------------------|---------------------------------------------------|-------------------------------------------------|
| **객체 생성**                | 매번 새로운 `String` 객체를 생성                   | 동일한 `StringBuilder` 객체에 문자열을 추가      |
| **성능**                     | 문자열 결합이 많아질수록 성능 저하가 심해짐         | 대량의 문자열 처리 시에도 성능 저하가 적음       |
| **불변성**                   | `String`은 불변 객체, 값을 변경할 수 없음          | `StringBuilder`는 가변 객체로 값을 변경 가능     |
| **사용 목적**                | 적은 횟수의 문자열 결합에 적합                     | 반복적이고 대량의 문자열 처리에 적합             |

<br>

### 추가적으로 System.out.print() vs BufferedWriter
- 해당 기준은 StringBuilder()에 넣어두고 각각 한번씩 출력했을 경우 기준

```java
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class Solution {

    public static void main(String[] args) throws IOException {
        new Solution().solution();
    }

    public void solution() throws IOException {
        Long time1;
        Long time2;
        Long time3;
        Long time4;

        // maxArea를 미리 설정 (테스트를 위해)
        int maxArea = 100;  // 임의의 값 설정

        // 1. System.out.print() 한번 출력 
        long startTime = System.nanoTime();
        StringBuilder result1 = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            result1.append("#Test ").append(i).append(" ").append(maxArea).append("\n"); 
        }
        System.out.println(result1);
        long endTime = System.nanoTime();
        time1 = endTime - startTime;
        
        // 2. BufferedWriter 생성 후 한번 출력
        startTime = System.nanoTime();
        StringBuilder result2 = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            result2.append("#Test ").append(i).append(" ").append(maxArea).append("\n");
        }
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        bw.write(result2.toString());
        endTime = System.nanoTime();
        time2 = endTime - startTime;
        
        // 3. System.out.print() 매번 출력 
        startTime = System.nanoTime();
        StringBuilder result3;
        for (int i = 0; i < 1000; i++) {
        	result3 = new StringBuilder();
            result3.append("#Test ").append(i).append(" ").append(maxArea).append("\n");
            System.out.println(result3);
        }
        endTime = System.nanoTime();
        time3 = endTime - startTime;
        
        // 4. BufferedWriter 매번 출력
        startTime = System.nanoTime();
        StringBuilder result4;
        BufferedWriter bw2 = new BufferedWriter(new OutputStreamWriter(System.out));
        for (int i = 0; i < 1000; i++) {
        	result4 = new StringBuilder();
            result4.append("#Test ").append(i).append(" ").append(maxArea).append("\n");
            bw2.write(result4.toString());
        }
        endTime = System.nanoTime();
        time4 = endTime - startTime;


        System.out.println("Time 1 : " + time1 + " ns");
        System.out.println("Time 2 : " + time2 + " ns");
        System.out.println("Time 3 : " + time3 + " ns");
        System.out.println("Time 4 : " + time4 + " ns");
    }
}

/*

Time 1 : 2023900 ns
Time 2 : 1137700 ns
Time 3 : 8980600 ns
Time 4 : 536500 ns

*/
```

<br>

### 정리
- StringBuilder를 사용해서 한번씩만 출력한다면 약 1.9배 정도 속도 차이가 발생
    - System.out.print() : 2023900 ns
    - BufferedWriter() : 1137700 ns
- 물론 문자열을 매번 System.out.print()를 출력하는 경우가 가장 느림
    - System.out.print() * 1000 : 8980600 ns
- 근데 StringBuilder로 한번에 출력 보다 한번 생성했던 BufferedWriter를 여러번 출력하는게 더 빨랐음
    - bw * 1000 : 536500 ns
    - BufferedWriter는 출력될 내용을 메모리에 버퍼링한 후, 내부적으로 적절한 크기에서 출력 스트림으로 내보내므로 데이터 전송 비용이 저렴
    - StringBuilder에 계속해서 문자열을 추가하고, 한 번에 모아서 출력을 하게 되면 그 사이에 많은 문자열 객체가 생성되고 메모리를 점유하게 됨. 이로 인해 자바의 Garbage Collector가 실행되어 메모리 관리를 하면서 성능에 영향을 줄 수 있음

<br>

### 정리

| 특징                        | System.out.print()                                | BufferedWriter()                                |
|-----------------------------|---------------------------------------------------|-------------------------------------------------|
| **출력 방식**                | 매번 출력할 때마다 스트림을 호출                  | 메모리에 버퍼링한 후, 적절한 시점에 출력 스트림으로 전송 |
| **성능**                     | 출력 호출이 빈번할수록 성능 저하가 큼              | 효율적인 버퍼링으로 출력 호출 횟수를 줄여 성능 개선 |
| **처리 효율**                | 출력할 때마다 새로운 작업을 처리해야 함            | 버퍼에 모아 둔 데이터를 한 번에 출력하여 효율적  |
| **메모리 사용**              | 비교적 적은 메모리 사용 (단일 출력마다 해소됨)     | 메모리 내 버퍼를 사용하여 일부 메모리 사용 증가  |
| **Garbage Collection (GC) 영향** | 메모리 관리가 상대적으로 덜 영향을 받음           | 버퍼가 크고, 한 번에 출력할 때 메모리 점유로 GC 영향 발생 가능 |
| **사용 목적**                | 적은 양의 출력 작업에 적합                        | 대량의 데이터를 처리할 때 적합                   |
