# I/O Stream

### I/O
- 데이터의 입력 (Input)과 출력 (Output)
- 데이터는 한쪽에서 주고 한쪽에서 받는 구조로 되어있음
    - 이 때 입력과 출력의 끝단 : 노드 (Node)
    - 두 노드를 연결하고 데이터를 전송할 수 있는 개념 : 스트림 (Stream)
    - 뭏의 흐름이나 전기의 흐름과 같은 개념
- 스트림은 단방향으로만 통신이 가능하며 하나의 스트림으로 입력과 출력을 같이처리할 수 없음

```
노드의 종류
- 키보드, 콘솔, 메모리, 파일, 데이터베이스, 네트워크

노드 --- 노드 스트림 --- > 프로그램 --- 노드 스트림 --- > 노드
```

<br>

### Node 스트림의 종류와 네이밍
- Node Steam : 노드에 연결되는 스트림
- 데이터 타입에 따라
    - XXStream -> InputStream, OutputStream
    - XXer -> Reader, Writer

<br>

### InputStream 주요 메서드
- read()
    - read() : byte 하나를 읽어서 int로 반환. 더 이상 읽을 값이 없으면 -1 리턴
    - read(byte[] b) : 데이터를 읽어서 b를 채웅고 바이트의 개수를 리턴. 0이 리턴되면 더 이상 읽을 값이 없는 상황, b의 길이가 0인 경우 0, 더 이상 읽을 데이터가 없는 경우 -1
    - read(byte[] b, int offset, int len) : 최대 len 만큼 데이터를 읽어서 b의 offset 부터 b에 저장하고 읽은 바이트 개수를 리턴
- close()
    - close() : 스트림을 종료해서 자원을 반납

<br>

### Reader의 주요 메서드
- read()
    - read() : char 하나를 읽어서 int로 반환. 더 이상 읽을 값이 없으면 -1 리턴
    - read(char[] cbuf) : 데이터를 읽어서 cbuf를 채우고 읽은 문자의 개수를 리턴. 0이 되면 더 이상 읽을 값이 없는 상황
    - read(char[] cbuf, int off, int len) : 최대 len 만큼 데이터를 읽어서 cbuf의 offset부터 cbuf에 저장하고 읽은 char 개수를 리턴. 따라서 len + off는 cbuf 크기 이하여야 함
    - read(java.nio.CharBuffer targer) : 데이터를 읽어서 target에 저장. target은 cbuf를 대체
- close()
    - close() : 스트림을 종료해서 자원을 반납

<br>

### OutputStream의 주요 메서드
- write()
    - write(int b) : b의 내용을 byte로 출력
    - write(byte[] b) : b를 문자열로 변환해서 출력
    -  write(byte[] b, int off, int len) : b의 off 부터 off + len - 1 만큼 문자열로 변환해서 출력
- close()
    - close() : 스트림을 종료해서 자원을 반남. close()는 내부적으로 flush 호출
- flush()
    - flush() : 버퍼가 있는 스트림에서 버퍼의 내용을 출력하고 버퍼를 지움

<br>

### Writer의 주요 메서드
- write()
    - write(int c) : b의 내용을 char로 출력
    - write(char[] cbuf) : cbuf를 문자열로 변환해서 출력
    - write(char[] cbuf, int off, int len) : cbuf의 off 부터 off+len-1 만큼을 문자열로 변환해서 출력
    - write(String str) : str 을 출력
    - write(String str, int off, int len) : str의 off 부터 off+len-1 만큼을 출력
- append()
    - append(CharSequence scq) : csq를 출력하고 Writer를 리턴
    - append(CharSequence scq, int start, int end) : csq의 start부터 end까지를 출력하고 Writer 리턴
- close()
    - close() : 스트림을 종료해서 자원을 반납. close()는 내부적으로 flush() 호출
- flush()
    - flush() : 버퍼가 있는 스트림에서 버퍼의 내용을 출력하고 버퍼를 비움

<br>

### File의 주요 메서드
- 가장 기본적인 입출력 장치 중 하나로 파일과 디렉터리를 다루는 클래스
- File() 생성자
    - File(String pathname) : pathname에 해당하는 파일 생성. 경로 없이 파일을 생성하면 어플리케이션을 시작한 경로가 됨
    - File(File parent, String child) : parent 경로 아래 child를 생성
    - File(URI uri) : file로 시작하는 URI 객체를 이용해 파일을 생성
- createNewFile()
    - createNewFile() : 새로운 물리적인 파일 생성
- mkdir()
    - mkdir() : 새로운 디렉토리를 생성
- mkdirs()
    - mkdirs() : 경로상에 없는 모든 디렉토리를 생성
- delete()
    - delete() : 파일 또는 디렉토리를 삭제
- getName()
    - getName() : 파일 이름을 리턴
- getPath()
    - getPath() : 파일 경로를 리턴
- getAbsolutePath()
    - getAbsolutePath() : 파일의 절대 경로를 리턴
- getCanonicalPath()
    - getCanonicalPath() : 파일의 정식 경로를 리턴. 정식 경로는 절대 경로이며 경로 내에 . 혹은 ..의 상대 경로가 없는 경로
- isDirectory()
    - isDirectory() : 파일이 디렉토리인지 리턴
- isFile()
    - isFile() : 파일이 파일인지 리턴
- length()
    - length() : 파일의 길이를 리턴
- listFiles()
    - listFiles() : 파일이 디렉토리인 경우 자식 파일들을 File[] 형태로 리턴

<br>

### FileInpuStream, FileOutputStream
- FileInputStream 생성자
    - FileInputStream(String name) : name 경로에 파일을 읽는 FileInputStream을 생성
- FileOutputStream 생성자   
    - FileOutputStream(String name) : name 경로의 파일에 출력하는 FileOutputStream을 생성
    - FileOUtputStream(String name, boolean append) : name 경로의 파일에 출력하는 FileOutputStream을 생성. 기존에 파일이 있다면 뒤에 이어씀
- String name 대신 File 객체 사용가능

<br>

### FileReader, FileWriter
- FileReader 생성자
    - FileReader(String name) : name 경로에 있는 파일을 읽는 FileReader를 생성
- FileWriter 생성자
    - FileWriter(String name) : name 경로의 파일에 출력하는 FileWriter를 생성
    - FileWriter(String name, boolean append) : name 경로의 파일에 출력하는 FileWriter를 생성. 기존에 파일이 있다면 뒤에 이어씀
- 마찬가지로 String name 대신 File 객체 사용 가능

<br>

### 보조 스트림
- 보조 스트림은 다른 스트림에 부가적인 기능을 제공하는 스트림
- 스트림 체이닝(Stream Chaining)
    - 필요에 따라 여러 보조 스트림을 연결해서 사용 가능
- 보조 스트림 생성은 이전 스트림을 생성자의 파라미터에 연결
    - new BufferedInputStream(System.in);
    - new ObjectInputStream(new BufferedInputStream(new FileInputStream()));
- 보조 스트림의 close()를 호출하면 노드 스트림의 close() 까지 호출 됨

```
노드 --- 노드 스트림 ---> --- 보조 스트림 ---> 프로그램
프로그램 --- 보조 스트림 ---> --- 노드 스트림 ---> 노드
```

<br>

### 보조 스트림의 종류
|기능|byte 기반|char 기반|
|:---:|:---:|:---:|
|byte 스트림을 char 스트림으로 변환|InputStreamReader, OutputStreamReader||
|버퍼링을 통한 속도 향상|BufferedInputStream, BufferedOutputStream|BufferedReader, BufferedWriter|
|객체 전송|ObjectInpuStream, ObjectOutputStream||

<br>

### 사용할 스트림의 결정 과정
- 노드가 무엇인가 -> 타입은 문자열인가? 바이트인가? -> 방향이 무엇인가? -> 추가 기능이 필요한가?
    - 노드가 무엇인지 ~~ 방향이 무엇인지 : 노드 스트림 구성
    - 추가 기능이 필요한지 : 보조 스트림 구성

<br>

### 객체 직렬화 (Serialization)
- 객체를 파일 등에 저장하거나 네트워크로 전송하기 위해 연속적인 데이터로 변환하는 것
- 반대의 경우는 역직렬화(Deserialization) 라고함
- 직렬화 되기 위한 조건은 다음과 같음
    - Serialization 인터페이스를 구현할 것
    - 클래스의 모든 멤버가 Serialization 인터페이스를 구현해야 함
    - 직렬화에서 제외하려는 멤버는 transient 선언

```java
Class Person implements Serializable {
    private String name;
    private Info info;
    private transient String uuid;
}
```

<br>

### serialVersionUID
- 클래스의 변경 여부를 파악하기 위한 유일 키
- 직렬화 할 때의 UID와 역 직렬화 할 때의 UID가 다를 경우 예외 발생
- 직렬화 되는 객체에 UID가 설정되지 않았을 경우 컴파일러가 자동 생성
    - 멤버 변경으로 인한 컴파일 시마다 변경 -> InvalidException 초래
-  직렬화되는 객체에 대해서 serialVersionUID 설정 권장

<br>

### 직렬화 보조 스트림 주요 메서드
- ObjectOutputStream() 생성자
    - ObjectOutputStream(OutputStream out) : out을 이용해 ObjectOutputStream 객체를 생성
- writeObject()
    - writeObject(Object obj) : obj 를 직렬화해서 출력
- ObjectInputStream() 생성자
    - ObjectInputStream(InputStream in) : in을 이용해 ObjectInputStream 객체를 생성
- readObject()
    - readObject() : 직렬화된 데이터를 역직렬화해서 Object로 리턴

```java
private static File target = new File("...");

try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(target))) {
    ...
}

try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(target))) {
    ...
}
```

<br>

### Scanner vs BufferedReader
- char 형태의 데이터를 읽기 위한 클래스들
- Scanner : 자동 형변환을 지원하는 등 사용이 간편하지만 속도가 느림
- BufferedReader : 직접 스트림을 구성해야 하는 등 번거롭지만 속도가 빠름
