# iTunes

### 실행 화면

https://github.com/HamBP/iTunes/assets/35232655/8963f30d-045b-4acd-a35b-5df11d204674

### 라이브러리

- Hilt - 의존성 주입
- Retrofit - 네트워크 통신
- OkHttp3 logging interceptor - 네트워크 통신시 로그 확인
- Glide - 이미지 처리
- Paging3 - 페이징 처리

### 아키텍처

- [앱 아키텍처 가이드](https://developer.android.com/topic/architecture?hl=ko)
- Domain 생략
- DataSource 생략

### 예외 처리

```kotlin
sealed class ItException(message: String) : RuntimeException(message)

data object NetworkException : ItException("네트워크 통신 과정에서 에러가 발생했어요.")
```

```kotlin
val ItException.messageId get(): @StringRes Int {
    return when(this) {
        is NetworkException -> R.string.network_exception
        is UnknownException -> R.string.unknown_exception
    }
}
```

```kotlin
fun Activity.showToast(@StringRes messageId: Int) {
    val message = resources.getString(messageId)
    showToast(message)
}
```

- 국제화 메시지에 대응하기 위해 strings.xml을 이용해 메시지를 가져온다.
- data 레이어에서 android에 대한 의존성을 없애기 위해 sealed class로 커스텀 예외를 정의하고, string id를 확장 프로퍼티 형태로 가져올 수 있다.

### 확장성

```kotlin
private val _keyword = MutableStateFlow("greenday")
val keyword: StateFlow<String> = _keyword.asStateFlow()

@OptIn(ExperimentalCoroutinesApi::class)
val trackFlow = keyword.flatMapLatest {
    Pager(PagingConfig(pageSize = 10)) {
        TrackPagingSource(trackRepository, it)
    }.flow
        .cachedIn(viewModelScope)
        .map { pagingData ->
            pagingData.map { it.toModel() }
        }
}
```

- 검색어 입력 기능이 추가되더라도 기존 코드는 거의 변경하지 않아도 된다.
