# Dirty Checking

### Dirty Checking
- Dirty 란 상태의 변화가 생긴 정도를 의미하여 Dirty Checking 은 상태 변경 검사를 의미함 -> JPA는 변경감지가 되었을 때 (Entity에서 변경이 일어난 것을 감지한 후) 데이터베이스에 반영
- JPA 에서는 트랜잭션이 끝나는 시점에 변화가 있는 모든 엔티티 객체를 데이터베이스에 자동으로 반영
- JPA 에서는 엔티티를 조회하면 해당 엔티티의 조회 상태를 스냅샷을 만듬
- 트랜잭션이 끝나는 시점에 이 스냅샷과 비교해서 변경이 있으면 Update query를 발생 -> Dirty Checking으로 생성되는 update쿼리는 기본적으로 모든 필드를 update
    - JPA는 전체 필드를 업데이트하는 방식을 기본값으로 함
    - 생성된 쿼리가 같아 부트 실행 시점에 미리 만들어 재사용 가능
    - 동일한 쿼리를 받으면 이전에 파싱된 쿼리를 재사용
    - 필드가 많아질 경우 전체필드 Update 쿼리가 부담이 됨 -> @DynamicUpdate로 변경 필드만 변경 가능
- Dirty Checking 대상은 영속성 컨텍스트가 관리하는 엔티티에만 적용
- 영속성 컨텍스트가 중간에서 쿼리들을 모았다가 한 번에 반영해주어 리소스 감소가 있음 -> 쓰기 지연
- 준영속, 비영속 상태의 엔티티는 Dirty Checking 대상에 포함되지 않음
    - Detech 된 엔티티 - 준영속
    - DB 에 반영되기 전 처음 생성된 엔티티 - 비영속
    - 값을 변경해도 데이터베이스에 반영되지 않음

<br>

#### Dirty Checking 예시 코드
```java
// Service
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookService {  
    @Transactional
    public void updateBookContents(String isbn, String contents){
        Book book = bookRepository.findByIsbn(isbn);
        book.updateContents(contents);
    }
}

// Entity
  @Entity
  @Getter
  @NoArgsConstructor
  @DynamicUpdate //변경된 필드만 대응
  public class Book {
      @Id
      @Column(name = "ISBN")
      private String isbn;
  
      @Column(name = "BOOK_NAME")
      private String bookName;
  
     ...
  
      @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
      private List<BookReview> bookReviewList = new ArrayList<>();
  
      @Builder
      public Book(String isbn, String bookName, String author, String publisher,
                  String kdc, String category, String keyword, String img, String contents) {
          this.isbn = isbn;
          this.bookName = bookName;
          ...
      }
  
      /**
       * udpate contetns
       *
       * @author hyunho
       * @since 2021/08/20
      **/
      public void updateContents(String contents){
          this.contents = contents;
      }
  }

```


<br>

<div style="text-align: right">22-07-17</div>

-------

## Reference
- https://velog.io/@hyunho058/Spring-Data-JPA-더티-체킹Dirty-Checking