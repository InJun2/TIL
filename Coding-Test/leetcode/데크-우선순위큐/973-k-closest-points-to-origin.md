# 973. K Closest Points to Origin
- point[][] 이중 배열의 x, y 값이 원점에 가장 가까운 점을 k개 반환
    - XY 평면의 두 점 사이의 거리는 유클리드 거리 √(x1 - x2)^2 + (y1 - y2)^2
    - 가까운 대로 k개의 배열을 반환하되 순서는 상관하지 않음
    - https://leetcode.com/problems/k-closest-points-to-origin/description/

<br>

### 풀이 방법
- k개 만큼 결과 배열을 생성하고 유클리드 거리를 재는 함수를 구현하여 최소 값 순서대로 배열에 추가
    - 최소 값을 저장하기 위해 우선순위 큐를 사용. 해당 사용방법 GPT 참조

<br>

### 첫번째 코드
```java
import java.util.PriorityQueue;
import java.lang.Math;

class Solution {
    public int[][] kClosest(int[][] points, int k) {
        int[][] result = new int[k][2];
        PriorityQueue<int[]> pq = new PriorityQueue<>((p1, p2) 
            -> Double.compare(getEuclidean(p1), getEuclidean(p2)));

        for(int[] point : points) {
            pq.add(point);
        }

        for (int i = 0; i < k; i++) {
            result[i] = pq.poll();
        }
        
        return result;
    }

    private double getEuclidean(int point[]) {
        return Math.sqrt(point[0] * point[0] + point[1] * point[1]);
    }
}
```

- runtime : 30ms
- memory : 47.24mb

<br>

### 풀이 코드
- 책의 풀이는 Point 객체를 생성하는 것과 Comparator.comparingDouble(p -> p.distance)로 우선순위 지정하는 법만 다름

```java
import java.util.PriorityQueue;
import java.lang.Math;
import java.util.Comparator;

class Solution {
    static class Point {
        double distance;
        int[] point;

        public Point(double distance, int[] point) {
            this.distance = distance;
            this.point = point;
        }
    }

    public int[][] kClosest(int[][] points, int k) {
        int[][] result = new int[k][2];
        PriorityQueue<Point> pq = new PriorityQueue<>(
            Comparator.comparingDouble(p -> p.distance));

        for(int[] point : points) {
            double distance = getEuclidean(point);
            pq.add(new Point(distance, point));
        }

        for (int i = 0; i < k; i++) {
            result[i] = pq.poll().point;
        }
        
        return result;
    }

    private double getEuclidean(int point[]) {
        return Math.sqrt(point[0] * point[0] + point[1] * point[1]);
    }
}
```

- runtime : 29ms
- memory : 53.59mb