# 706. Design HashMap
- 해시 테이블 디자인하기
    - MyHashMap() 생성자
    - void put(int key, int value)
    - int get(int key)
    - void remove(int key)
    - https://leetcode.com/problems/design-hashmap/description/

<br>

### 풀이 방법
- 우선 책에서 나오는 자바 기존 구현 방식인 개별 체이싱 방법으로 구현 예정
    - key에 대한 중복 value 값을 연결리스트로 연결하는 방식
- 연결리스트로 구현해야하기 때문에 노드에 key와 value 변수가 필요
    - 키가 존재하지않다면 연결리스트를 새로 생성
- 해싱 알고리즘으로 매직넘버 31와 임의의 해시 코드값을 사용 예정
    - 키를 찾을 때 해당 값으로 변환된 키를 탐색
    - 키가 이미 존재한다면 기존 연결리스트에 추가
- 개인적으로 생각은 키가 있는지 확인하고 있다면 값을 업데이트해야 하는데 연결리스트일 필요가 있는지 궁금했는데 생각해보니 키에 대한 연결리스트가 아니라 버킷에 대한 연결리스트임
    - 해시 인덱싱을 통해 특정 버킷에 접근하여 연결리스트를 사용하지만, 키가 같지 않다면 연결 리스트 중 키가 같은 것을 찾을 때 까지 탐색

<br>

### 풀이 코드
```java
class MyHashMap {
    static class Node {
        int key, val;
        Node next;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }

    final Node[] nodes;
    final int magicNumber = 31;

    public MyHashMap() {
        nodes = new Node[1000000];
    }

    public void put(int key, int value) {
        // 해싱 결과를 인덱스로 지정
        int index = key * magicNumber % nodes.length;

        // 해당 인덱스에 노드가 없다면 신규 생성 후 종료
        if(nodes[index] == null) {
            nodes[index] = nes Node(key, value);
            return;
        }

        // 해당 인덱스에 노드가 존재한다면 연결리스트로 처리
        Node node = nodes[index];
        while (node != null) {
            // 동일한 키가 없다면 값을 업데이트하고 종료
            if (node.key == key) {
                node.val = value;
                return;
            }

            // 다음 노드가 없다면 종료
            if (node.next == null) {
                break;
            }

            // 다음 노드로 진행
            node = node.next;
        }

        // 마지막 노드 다음으로 연결
        node.next = new Node(key, value);
    }

    public int get(int key) {
        // 해싱 결과를 인덱스로 지정
        int index = key * magicNumber % nodes.length;

        // 인덱스에 노드가 존재하지 않으면 -1
        if (nodes[index] == null) {
            return -1;
        }

        // 인덱스에 노드가 존재한다면 일치하는 키 탐색
        Node node = nodes[index];

        while (node != null) {
            // 동일한 키가 있다면 값을 리턴
            if (node.key == key) {
                return node.val;
            }

            // 다음 노드로 진행
            node = node.next;
        }

        // 인덱스를 모두 순회해도 일치하는 키가 없다면 -1
        return -1;
    }

    public void remove(int key) {
        // 해싱 결과를 인덱스로 지정
        int index = key * magicNumber % nodes.length;

        // 인덱스에 노드가 없다면 종료
        if (nodes[index] == null) {
            return;
        }

        // 첫 번째 노드일 때의 삭제처리
        Node node = nodes[index];

        if (node.key == key) {
            // 다음 노드가 없으면 해당 인덱스는 null 처리
            if(node.next == null) {
                nodes[index] = null;
            } else {
                // 다음 노드가 있다면 다음 노드를 해당 인덱스로 지정
                nodes[index] = node.next;
            }
        }

        // 연결 리스트 노드일 때의 삭제 처리
        Node prev = node;

        while (node != null) {
            // 일치하는 키가 있다면
            if (node.key == key) {
                // 이전 노드의 다음에 현재 노드의 다음을 연결하고 리턴
                prev.next = node.next;
                return;
            }

            // 노드 한 칸씩 이동
            prev = node;
            node = node.next;
        }
    }
}

/**
 * Your MyHashMap object will be instantiated and called as such:
 * MyHashMap obj = new MyHashMap();
 * obj.put(key,value);
 * int param_2 = obj.get(key);
 * obj.remove(key);
 */
```