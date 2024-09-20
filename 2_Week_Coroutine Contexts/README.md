# Coroutine Context

### What Is a Coroutine Context

1. 액티비티, 애플리케이션, 서비스 컨텍스트와 달리, Coroutine Context는 현재 실행 중인 코루틴의 상태를 나타낸다.
    1. 코루틴 컨텍스트의 요소
        1. 코루틴의 작업
        2. 이름
        3. 예외 처리기
        4. 디스패처 등
2.  **Coroutine Context**는 해시맵처럼 키-값 쌍으로 여러 요소를 포함한다.

예를 들어, 코루틴의 작업을 Job을 통해 접근할 수 있으며, 이름을 부여하거나 예외 처리기를 추가하는 것도 가능하다.

3. Dispatcher는 코루틴이 실행되는 스레드를 결정하는데 사용된다.

기본 디스패처는 _Dispatchers.Default_이며, _Dispatchers.Main_ 등으로 스레드를 지정할 수 있다.

4. Coroutine Context는 여러 요소를 조합할 수 있다. _+_ 연산자를 사용해 디스패처와 작업을 함꼐 설정하는 방식으로 컨텍스트를 구성할 수 있다.

```kotlin
CoroutineScope(Dispathcers.Main + CoroutineName("Cool Coroutine!")).launch {
	println(Dispatcher: ${coroutineContext[CoroutineDispatcher]}")
	println(Dispatcher: ${coroutineContext[CoroutineName]}")
}
```

5. **코루틴 스코프**는 단순히 코루틴 컨텍스트를 감싸는 래퍼로, 코루틴의 수명 주기를 관리하는 역할을 합니다.

### WithContext

1. 코루틴 컨텍스트는 언제든지 스레드를 변경할 수 있는 능력이 있으며, **withContext**를 사용하면 특정 스레드로 코드를 실행할 수 있다.
    
2. 각 디스패처는 특정 작업에 최적화되어 있다. 예를 들어, **IO 디스패처**는 네트워크 요청이나 파일 읽기와 같은 I/O 작업에 적합하고, **메인 디스패처**는 UI 업데이트 작업에 사용된다.
    

### IO Default Dispatcher

1. **IO 디스패처**는 I/O 작업(예: 네트워크 호출, 파일 읽기 등)에 최적화되어 있다. 이런 작업은 외부에서 응답을 기다려야 하므로 CPU 자원을 덜 사용하고, 여러 스레드에서 동시에 처리하는 것이 유f리하다.
2. **기본 디스패처**는 CPU를 많이 사용하는 작업에 적합합니다. 계산이나 복잡한 알고리즘을 처리할 때 더 적은 스레드를 사용하고, 각 CPU 코어에 맞춰 효율적으로 자원을 사용합니다.

두 디스패처의 차이점은 스레드 풀의 크기에서 나온다. IO 디스패처는 더 많은 스레드를 사용해 동시에 여러 작업을 처리할 수 있지만, 기본 디스패처는 CPU 자원을 최대한 활용하기 위해 코어당 하나의 스레드만 사용합니다. 예를 들어, 네트워크 요청 같은 작업은 IO 디스패처를 사용하는 것이 더 빠르지만, 복잡한 계산 작업은 기본 디스패처가 더 효율적입니다

### Main / Main Immediate Dispatcher

1. **메인 디스패처(Main Dispatcher)**: 메인 스레드에서 코루틴을 실행시키는 기본 방식입니다. 이 디스패처를 사용하면 작업이 대기열에 추가되어 메인 스레드의 다른 작업(예: UI 업데이트)이 완료된 후 실행됩니다. 즉, 코루틴이 스케줄링되어 대기 중인 다른 작업이 모두 끝나고 나면 실행됩니다.
    
2. **메인 Immediate 디스패처(Main Immediate Dispatcher)**: 메인 디스패처와 마찬가지로 메인 스레드에서 코루틴을 실행하지만, 차이점은 이름에서 알 수 있듯이 즉시 실행됩니다. 즉, 대기열에 넣지 않고 바로 실행되며, 코루틴이 이미 메인 스레드에서 실행 중이라면 즉시 코드를 실행합니다. 대기 시간 없이 바로 UI 업데이트가 필요할 때 유용합니다.
    

이렇게 immediate 는 순서 보장과 최적화에 유용하게 쓰일 수 있습니다. immediate 는 이미 해당 함수가 해당 스레드에 있다고 가정하고 추가적인 디스패치를 요구하지 않는 Dispatcher 이기 때문에 모든 환경에서 기본 값으로 존재하는 메인 스레드에 대해서만 가능합니다. 이런 이유로 Dispatchers.Main 이 아닌 다른 Dispatchers 에는 immediate 옵션이 존재하지 않습니다.

예를 들어, XML 기반의 UI에서는 메인 스레드에서만 UI를 변경할 수 있기 때문에 메인 디스패처를 꼭 사용해야 했습니다. 반면, Jetpack Compose 환경에서는 상태(state) 업데이트를 통해 UI가 갱신되기 때문에, 일부 상태 변경은 백그라운드 스레드에서도 안전하게 이루어질 수 있습니다.

Dispatchers.Main.immediate 는 viewModelScope 와 lifecycleScope 에 기본 값으로 사용되고 있습니다.

```kotlin
public val ViewModel.viewModelScope: CoroutineScope
    get() {
        val scope: CoroutineScope? = this.getTag(JOB_KEY)
        if (scope != null) {
            return scope
        }
        return setTagIfAbsent(
            JOB_KEY,
            CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        )
    }
    
public val Lifecycle.coroutineScope: LifecycleCoroutineScope
    get() {
        while (true) {
            val existing = mInternalScopeRef.get() as LifecycleCoroutineScopeImpl?
            if (existing != null) {
                return existing
            }
            val newScope = LifecycleCoroutineScopeImpl(
                this,
                SupervisorJob() + Dispatchers.Main.immediate
            )
            if (mInternalScopeRef.compareAndSet(null, newScope)) {
                newScope.register()
                return newScope
            }
        }
    }
```

안드로이드는 기본 스레드가 메인 스레드 하나밖에 없기 때문에 기본 값으로 모든 함수들은 메인 스레드에서 실행되기 때문입니다.

### Unconfined Dispatcher

이 디스패처는 일반적인 상황에서 자주 사용되지는 않지만, 미리 정의된 디스패처 중 하나로 존재하며 특정한 경우에 유용할 수 있다.

- **Unconfined Dispatcher**는 코루틴을 어느 스레드에서든 실행할 수 있으며, 실행 중이던 스레드를 그대로 이어받아 실행합니다. 즉, Unconfined 디스패처로 전환된 코루틴은 이전에 실행 중이던 스레드에서 계속 실행됩니다.
- 이 디스패처는 스레드에 구애받지 않고 실행되므로, 스레드 전환이 필요하지 않은 간단한 작업에 적합할 수 있지만, **예측하지 못한 동작**이 발생할 수 있어 실무에서는 자주 사용하지 않도록 권장됩니다. 예를 들어, 메인 스레드에서 실행되는 Unconfined 코루틴이 잘못된 블로킹 호출을 할 경우, 메인 스레드가 차단될 수 있습니다.
- 실제로 Unconfined Dispatcher의 **실제 사용 사례**는 거의 없으며, 예측 불가능한 동작 때문에 사용이 권장되지 않습니다. 하지만, **테스트 환경**에서는 Unconfined Dispatcher의 특별한 버전인 **Unconfined Test Dispatcher**가 유용할 수 있으며, 이는 테스트에서 코루틴의 실행 흐름을 제어할 수 있게 도와줍니다.
