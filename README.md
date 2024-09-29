# Kotlin-Coroutines-Study

<details>
<summary>What is a Coroutine</summary>
<div markdown="1">

## What is a Coroutine

### 스레드와 코루틴:

- **스레드(Thread):** 스레드는 여러 작업을 동시에 실행할 수 있도록 도와줍니다. 예를 들어, 밥과 치킨을 요리할 때, 밥이 다 될 때까지 기다린 후 치킨을 요리하는 대신 두 가지를 동시에 하면 전체 시간이 줄어듭니다. 이처럼 스레드는 작업을 병렬로 처리하여 시간을 절약할 수 있습니다.
- **멀티 스레딩(Multi-threading):** 프로그램에서 여러 독립적인 실행 흐름(스레드)을 가질 수 있습니다. 한 스레드가 결과를 기다리는 동안 다른 스레드에서 작업을 처리할 수 있어, CPU 리소스를 효율적으로 사용할 수 있습니다.

### 코루틴(Coroutine):

- **순차적 실행:** 코루틴은 기본적으로 스레드처럼 순차적으로 실행됩니다. 하지만 **중단 지점**에 도달하면 대기 상태가 됩니다. 예를 들어, 네트워크 응답을 기다리거나 파일 복사를 하는 경우가 있습니다.
- **중단 지점:** 코루틴이 중단 지점에 도달하면, **연속성(continuation)**이라는 메커니즘을 통해 현재 상태를 저장하고, 나중에 결과가 준비되면 다시 실행을 재개할 수 있습니다.
- **가벼움:** 코루틴은 스레드보다 훨씬 적은 리소스를 사용하기 때문에, 더 많은 코루틴을 생성하더라도 메모리 문제를 걱정할 필요가 없습니다.
- **콜백 불필요:** 기존의 비동기 작업에서 사용되는 콜백(callback) 대신, 코루틴은 필요한 시점에 실행을 일시 중지하고 나중에 재개하기 때문에 프로그램 흐름을 훨씬 간단하게 관리할 수 있습니다.
- **스레드 전환:** 코루틴은 작업을 수행하는 스레드를 쉽게 전환할 수 있습니다. 예를 들어, 네트워크 작업은 백그라운드 스레드에서 처리하고, 결과가 준비되면 UI 업데이트를 위해 메인(UI) 스레드로 전환할 수 있습니다.

### 차단(Blocking) vs 중단(Suspending):

- **중단(Suspending):** 코루틴은 자신의 실행을 중단하지만, 실행 중인 스레드는 다른 작업을 계속할 수 있습니다.
- **차단(Blocking):** 스레드 전체가 차단되어 해당 스레드에서 실행 중인 다른 코루틴들이 모두 멈추게 됩니다.

## Launching your first Coroutine

- **동시성과 병렬성**:
    - 동시성은 여러 작업을 한 번에 처리하는 방식으로, 병렬성과는 다르게 단일 CPU 코어에서 작업이 빠르게 전환되면서 동시에 처리되는 것처럼 보임.
    - 병렬성은 여러 CPU 코어를 사용할 때만 가능함.
- **지연 함수와 서스펜션**:
    - `delay` 함수는 서스펜드 함수로 코루틴을 일시 중지하고 일정 시간이 지난 후 다시 실행함.
    - 코루틴이 지연될 때 다른 코루틴은 계속 실행될 수 있으며, 네트워크 호출과 같은 비동기 작업에서 유용하게 사용됨.
- **구조화된 동시성(Structured Concurrency)**:
    - 코루틴의 부모-자식 관계를 통해 계층 구조를 가지며, 여러 코루틴을 함께 관리하고 취소하는 것이 쉬워짐.
    - 예시로 외부 코루틴이 실행되고, 내부 코루틴들이 순차적으로 실행되며 서로 독립적으로 동작함.
- **블로킹 코드와 서스펜딩 코드**:
    - 블로킹 코드(`Thread.sleep()`)와 서스펜딩 코드(`delay()`)의 차이를 설명.
    - 서스펜딩 코드는 코루틴만 일시 중지하지만, 블로킹 코드는 스레드 전체를 멈추게 함. UI 업데이트가 필요한 메인 스레드에서 블로킹 코드를 실행하면 앱이 멈추는 현상이 발생할 수 있음.

## Suspend Functions

- **실제 예제 소개**:
    - `Ktor` 네트워킹 라이브러리와 `Room` 데이터베이스를 활용한 예제를 통해, 보다 현실적인 코루틴 사용 사례를 다룸.
    - `Ktor`는 코루틴 기반으로 작동하며, 네트워킹과 데이터베이스 작업에 서스펜드 함수가 많이 사용됨.
- **서스펜드 함수 작성**:
    - `delay`와 같은 서스펜드 함수는 코루틴 안에서 실행되며, 서스펜드 함수 내에서도 다른 서스펜드 함수를 호출할 수 있음.
    - `fetchData`라는 서스펜드 함수 예제를 통해, API 호출로 얻은 데이터를 로컬 데이터베이스에 저장하는 과정을 설명. 이 과정은 비동기적으로 실행됨.
- **서스펜드 함수의 작동 방식**:
    - 네트워크 호출이나 데이터베이스 삽입과 같은 작업이 완료된 후에 다음 작업이 실행됨. 즉, 순차적으로 비동기 작업이 이루어짐.
    - 여러 서스펜드 함수를 하나로 묶어, 코루틴 스코프 내에서 호출할 수 있음.

## Coroutine Scopes

- **GlobalScope의 위험성**:
    - `GlobalScope`를 사용하면 코루틴이 애플리케이션의 전체 생명 주기에 연결되어 앱이 종료되기 전까지 코루틴이 취소되지 않음.
    - Android의 생명 주기 관리(액티비티, 프래그먼트, 뷰 모델)에서 GlobalScope를 사용하는 것은 비효율적이고 위험할 수 있음.
- **LifeCycleScope와 ViewModelScope**:
    - `LifeCycleScope`는 코루틴을 액티비티나 프래그먼트의 생명 주기와 연결하여, 화면 회전 등의 이유로 액티비티가 파괴되면 코루틴도 함께 취소됨.
    - `ViewModelScope`는 뷰모델의 생명 주기와 연결되어, 뷰모델이 종료될 때 코루틴도 자동으로 취소됨. 화면 회전 시에도 계속해서 네트워크 요청을 유지할 수 있어 자주 사용됨.
- **코루틴 스코프의 중요성**:
    - 각 Android 컴포넌트의 생명 주기에 맞춰 코루틴을 적절히 관리할 수 있도록, `GlobalScope` 대신 `LifeCycleScope`나 `ViewModelScope`를 사용하는 것이 권장됨.
- **Custom Coroutine Scope**:
    - 사용자가 직접 코루틴 스코프를 만들 수 있음. 예를 들어, 서비스와 같은 생명 주기를 가진 컴포넌트에서 스코프를 생성하고 서비스가 종료되면 코루틴도 취소할 수 있음.

## Jobs Deferreds

- **Job과 Coroutine**:
    - 코루틴은 `launch` 함수를 통해 실행되며, 이 함수는 `Job` 객체를 반환함. Job은 코루틴의 상태(실행 중, 취소됨, 완료됨)를 관리함.
    - Job을 통해 특정 코루틴을 취소하거나 상태를 확인할 수 있음. 예를 들어, `job.cancel()`로 특정 코루틴을 취소할 수 있고, `job.isActive`, `job.isCompleted` 등의 메서드로 상태를 확인할 수 있음.
- **Job 대기(Join)**:
    - `job.join()`은 해당 코루틴이 완료될 때까지 대기하는 서스펜딩 함수임. 여러 코루틴을 병렬로 실행하면서도, 각 코루틴이 끝날 때까지 기다릴 수 있음.
- **Deferred와 결과 반환**:
    - `launch` 대신 `async`를 사용하면 `Deferred` 객체를 반환받으며, 이는 결과를 지연 반환하는 코루틴임.
    
    ```kotlin
    val deferred: Deferred<String> = async {
        delay(1000L)  // 1초 동안 대기
        "결과 반환"   // 결과 반환
    }
    
    val result = deferred.await()  // 1초 후에 "결과 반환" 값을 얻음
    println(result)  // 출력: 결과 반환
    ```
    
    - `Deferred`는 결과를 포함한 `Job`의 일종으로, `await()` 함수를 통해 코루틴의 결과를 비동기적으로 받을 수 있음.
    - 예를 들어, `async`를 사용해 두 개의 네트워크 요청을 병렬로 실행하고, 각 요청이 끝난 후 결과를 받아 처리할 수 있음.
- **병렬 처리의 중요성**:
    - 여러 코루틴을 병렬로 실행하면 전체 시간이 단축됨. 예시로, 두 개의 작업이 각각 2초와 3초가 걸리더라도, 병렬로 처리하면 5초가 아닌 3초 안에 완료됨.
    - `async` 블록 내에서 즉시 `await()`를 호출하면, 병렬 처리가 아닌 순차적으로 실행되어 비효율적임.

## Coroutines in Compose

- **Compose에서 코루틴 사용**:
    - Jetpack Compose는 코루틴 기반으로 설계되어 있으며, UI 관련 작업에 코루틴을 많이 활용함.
    - 예시로 간단한 카운터 앱을 통해, 코루틴이 Compose에서 어떻게 사용되는지 설명함.
- **LaunchedEffect**:
    - `LaunchedEffect`는 컴포지션이 시작될 때 코루틴을 실행하고, 특정 상태가 변경되면 코루틴을 재실행하는 효과 핸들러임.
    - 상태(key)가 변경되면 코루틴이 취소되고 새롭게 시작되며, UI가 변경될 때 오래된 상태에 의존하지 않도록 도와줌.
- **ProducedState**:
    - `ProducedState`는 코루틴 컨텍스트에서 상태를 생성하는데 사용되며, 서스펜드 함수와 함께 상태를 업데이트할 수 있음.
        - **코루틴 기반 상태 생성**:
        `ProducedState`는 코루틴 컨텍스트 안에서 비동기 작업을 수행하여 UI에서 사용할 상태를 생성합니다. `ProducedState`는 **서스펜드 함수**를 호출할 수 있으므로, 네트워크 호출이나 시간 지연 같은 비동기 작업을 처리할 때 유용함.
        - **상태의 지속성**:
        `ProducedState`로 생성된 상태는 해당 컴포저블이 **컴포지션**에 있을 때만 유효하며, 컴포저블이 사라지면 코루틴과 상태도 함께 소멸합니다. 이는 UI의 생명 주기와 상태의 생명 주기를 맞추는 데 유용함.
        - **사용법**:
        `ProducedState`는 초기 상태 값을 제공하고, 코루틴을 사용하여 상태를 갱신합니다. 코루틴 안에서 비동기 작업이 완료될 때마다 상태를 업데이트하고, UI는 이 상태 변화를 관찰하여 자동으로 다시 그려짐.
        
        ```kotlin
        @Composable
        fun CounterScreen() {
            val counter by produceState(initialValue = 0) {
                while (true) {
                    delay(1000L)  // 1초 대기
                    value += 1    // 상태 값 증가
                }
            }
        
            Text(text = "Counter: $counter")
        }
        ```
        
        - 예를 들어, 사용자가 프로필 정보를 로딩하는 동안 화면에 상태를 실시간으로 업데이트하거나, 데이터가 준비될 때까지 대기할 수 있습니다.
- **Snackbar 예시**:
    - `Snackbar`는 서스펜딩 함수(`showSnackbar`)를 사용하여 코루틴이 실행 중인 동안 일시 중단됨. 이를 통해 UI가 반응하는 동안 상태를 관리할 수 있음.
    - `rememberCoroutineScope`를 사용하여 특정 컴포저블의 생명 주기와 연계된 코루틴 스코프를 만들 수 있음.

 </div>
</details>

<details>
<summary>Coroutine Context</summary>
<div markdown="1">

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

### Unconfined Dispatcher

이 디스패처는 일반적인 상황에서 자주 사용되지는 않지만, 미리 정의된 디스패처 중 하나로 존재하며 특정한 경우에 유용할 수 있다.

- **Unconfined Dispatcher**는 코루틴을 어느 스레드에서든 실행할 수 있으며, 실행 중이던 스레드를 그대로 이어받아 실행합니다. 즉, Unconfined 디스패처로 전환된 코루틴은 이전에 실행 중이던 스레드에서 계속 실행됩니다.

- 이 디스패처는 스레드에 구애받지 않고 실행되므로, 스레드 전환이 필요하지 않은 간단한 작업에 적합할 수 있지만, **예측하지 못한 동작**이 발생할 수 있어 실무에서는 자주 사용하지 않도록 권장됩니다. 예를 들어, 메인 스레드에서 실행되는 Unconfined 코루틴이 잘못된 블로킹 호출을 할 경우, 메인 스레드가 차단될 수 있습니다.

- 실제로 Unconfined Dispatcher의 **실제 사용 사례**는 거의 없으며, 예측 불가능한 동작 때문에 사용이 권장되지 않습니다. 하지만, **테스트 환경**에서는 Unconfined Dispatcher의 특별한 버전인 **Unconfined Test Dispatcher**가 유용할 수 있으며, 이는 테스트에서 코루틴의 실행 흐름을 제어할 수 있게 도와줍니다.

</div>
</details>

<details>
<summary> Coroutine Cancellation</summary>
<div markdown="1">

## Why Cancellation Seems Simple, But is hard

### 1. **취소의 기본 개념**

- 코루틴을 취소하면 실행 중인 작업이 중단됩니다. 특정 코루틴 스코프 내에서 모든 코루틴이 취소될 수 있으며, **부모 작업**이 취소되면 **자식 작업**도 함께 취소됩니다. 이는 비교적 직관적으로 이해할 수 있습니다.

### 2. **취소의 협력적 특성**

- 코루틴의 취소는 **자동으로 이루어지지 않고**, 중단 함수(suspending function)가 취소 여부를 확인하고 취소를 수락해야만 작동합니다. 즉, 취소가 발생해도 중단 함수 내에서 **취소 체크포인트**가 있어야 취소가 반영됩니다.
- 예를 들어, 이미지 압축 작업을 중단시키려면 각 중단 함수 호출 사이에 취소 여부를 확인하는 코드(예: `isActive` 또는 `ensureActive`)가 필요합니다. 이를 통해 코루틴이 취소되었는지 확인하고, 취소된 경우에는 더 이상 작업을 계속하지 않고 종료됩니다.

### 3. **취소 처리 시의 문제점**

- 코루틴 취소가 제대로 지원되지 않으면, 취소 요청을 받아도 코루틴이 계속 실행될 수 있습니다. 예를 들어, 파일을 읽거나 이미지를 압축하는 작업에서 취소 체크포인트가 없으면, 코루틴이 취소되었더라도 해당 작업이 끝날 때까지 계속됩니다.
- **취소 체크포인트**는 중단 함수 사이에 위치해야 하며, 이를 통해 취소 여부를 주기적으로 확인해야 합니다.

### 4. **실제 안드로이드 환경에서의 취소**

- 안드로이드 환경에서는 **생명 주기(Lifecycle)**에 따라 코루틴이 자동으로 취소될 수 있습니다. 예를 들어, **ViewModel 스코프**에서 실행 중인 코루틴은 사용자가 화면을 뒤로 이동하거나 액티비티가 종료되면 자동으로 취소됩니다. 이런 상황에서 취소를 제대로 지원하지 않으면, **메모리 누수**나 **성능 문제**, 심지어 **앱 크래시**가 발생할 수 있습니다.

## The Consequences of Cancellation

- **취소의 기본 구조**:
    - 코루틴 내에서 취소된 **작업(job)**은 더 이상 실행되지 않지만, 동일한 부모 작업 내의 **다른 형제 작업**은 계속 실행됩니다.
    - 반면, **부모 작업**이 취소되면 모든 자식 작업도 함께 취소됩니다. 이는 **구조적 동시성(Structured Concurrency)**의 원칙에 따른 것으로, 부모 작업의 취소는 하위 모든 작업에 영향을 미칩니다.
- **코루틴 스코프 취소**:
    - **코루틴 스코프**가 취소되면 해당 스코프 내에서 실행 중이던 모든 코루틴과 작업이 취소됩니다.
    - 한 번 취소된 코루틴 스코프는 더 이상 재사용할 수 없으며, 새로운 코루틴을 시작할 수 없습니다. 만약 스코프 자체는 유지하면서 자식 작업들만 취소하고 싶다면, `coroutineContext.cancelChildren()`을 사용할 수 있습니다.
- **취소의 내부 작동**:
    - 코루틴이 취소될 때, **취소 예외(CancellationException)**가 발생하며, 코루틴은 더 이상 실행되지 않습니다. 예를 들어, `ensureActive()` 함수는 코루틴이 여전히 활성 상태인지 확인하고, 취소된 경우 예외를 발생시킵니다.
    - 코루틴은 상태 머신처럼 동작하여, 실행 상태(Active)에서 **취소 중 상태(Cancelling)**로 전환되고, 마지막으로 **취소된 상태(Cancelled)**로 이동합니다. 이 과정에서 자원을 정리하는 등의 작업이 필요할 수 있습니다.
- **취소 예외의 특수성**:
    - **CancellationException**은 다른 예외와 달리 오류로 간주되지 않으며, 코루틴이 취소된 후에도 남은 작업을 처리할 수 있는 시간을 허용합니다. 예를 들어, 파일을 열고 있던 스트림을 닫는 등의 정리 작업을 할 수 있습니다.

## Cancellation Trap 1 Try-Catch

1. **취소 예외의 처리 문제**:
    - 코루틴에서 취소가 발생하면 `CancellationException`이 발생하고, 이는 코루틴의 모든 중단점(suspension point)에서 던져집니다.
    - 만약 `try-catch` 블록에서 모든 예외를 포괄적으로 처리하면, `CancellationException`도 함께 잡히게 됩니다. 이로 인해 코루틴이 취소되었음에도 불구하고 취소 상태를 부모 작업이 인식하지 못하게 되어, **무한 반복**이 발생할 수 있습니다.
2. **취소 예외를 삼키는 문제**:
    - 예를 들어, API 요청을 반복하는 코루틴에서 `try-catch`로 모든 예외를 처리한 경우, `CancellationException`이 발생하더라도 코루틴이 계속해서 실행될 수 있습니다. 이는 부모 작업이 취소된 상태를 알 수 없기 때문입니다.
    - 이로 인해, 무한 반복 또는 불필요한 리소스 낭비가 발생할 수 있습니다.
3. **해결 방법**:
    - **특정 예외만 처리**: 모든 예외를 처리하는 대신, 특정 예외(예: 네트워크 오류)만을 처리하여 `CancellationException`을 부모 작업으로 전달할 수 있습니다.
    - **취소 예외 재발생**: `catch` 블록에서 `CancellationException`을 확인하고, 취소된 경우에는 이 예외를 다시 던져서 부모 작업이 취소를 인식하도록 합니다.
    - **`ensureActive` 사용**: `ensureActive()`를 사용해 코루틴이 여전히 활성 상태인지 확인하고, 취소된 경우 `CancellationException`을 발생시킵니다.

## Cancellation Trap 2 Transaction like Behavior

### 문제)

코드에서 원격 API 호출로 주문을 생성하고, 그 주문의 추적 번호를 로컬 데이터베이스에 저장하는 과정이 있다. 여기서 발생할 수 있는 문제는 다음과 같다.

1. **원격 API 호출 성공 후 코루틴 취소**: 주문이 원격 API에 성공적으로 전달되었지만, 로컬 데이터베이스에 추적 번호를 저장하기 전에 코루틴이 취소될 수 있습니다. 이렇게 되면 원격 서버에는 주문 정보가 있지만, 클라이언트(앱)에는 추적 번호가 저장되지 않아 데이터 불일치가 발생합니다.
2. **트랜잭션과 유사한 동작**: 데이터베이스 트랜잭션처럼, 모든 작업이 성공적으로 완료되었을 때만 데이터베이스에 반영되도록 하는 것이 이상적입니다. 하지만 코루틴이 중간에 취소되면, 일부 작업이 완료되지 않아 일관성이 깨질 수 있습니다.

---

### 해결책)

- **withContext(NonCancellable)**: 특정 코드 블록을 **취소 불가능**하게 만들어, 그 코드가 반드시 실행되도록 보장합니다. 예를 들어, 주문이 성공적으로 생성된 후, 추적 번호를 저장하는 코드가 반드시 실행되도록 합니다. 하지만 이 방법은 위험할 수 있으며, 무한 루프나 대규모 작업이 취소되지 않고 계속 실행될 위험이 있습니다.
- **애플리케이션 전역의 코루틴 스코프 생성**: **글로벌 스코프(Global Scope)** 대신, 애플리케이션의 수명과 연동된 전역 코루틴 스코프를 생성하여 사용할 수 있습니다. 이렇게 하면 뷰모델 스코프나 생명 주기 스코프와는 독립적으로 동작하며, 특정 작업이 취소되지 않고 실행되도록 할 수 있습니다. 이를 통해 주문이 성공적으로 완료된 후 추적 번호를 저장하는 작업이 반드시 완료되도록 보장할 수 있습니다.
- **Supervisor Job**: 여러 코루틴이 동일한 스코프에서 실행될 때, 하나의 코루틴이 실패하더라도 다른 코루틴에 영향을 미치지 않도록 하기 위해 **Supervisor Job**을 사용합니다.

## Cancellation Trap 3 Try-Finally

### 문제 설명

코드에서는 파일에 데이터를 쓰고, 코루틴이 취소될 경우 **임시 파일을 삭제**하는 과정이 포함되어 있습니다. 주요 과정은 다음과 같습니다:

1. **파일에 기록**: 파일에 데이터베이스 레코드를 작성하는 함수가 있습니다. 이 함수는 5줄을 파일에 쓰는 작업을 시뮬레이션하며, 중간에 지연(delay)을 추가하여 코루틴이 취소될 가능성을 보여줍니다.
2. **취소 시 파일 삭제**: 코루틴이 취소되면, 파일이 완전히 기록되지 않기 때문에 **임시 파일을 삭제**해야 합니다. 이를 위해 `finally` 블록에서 파일 삭제 작업을 수행합니다.
3. **문제 발생**: 코루틴이 취소될 경우, `finally` 블록은 실행되지만, 코루틴이 이미 **취소 상태**일 때는 **서스펜드 함수(suspend function)**가 호출되지 않습니다. 예를 들어, 파일 삭제 작업이 서스펜드 함수 내에서 이루어지면, 코루틴이 취소된 상태에서는 이 작업이 건너뛰어져 파일이 삭제되지 않습니다.

---

### 해결책

- **`withContext(NonCancellable)`**: 서스펜드 함수를 사용하는 코드가 **취소되지 않도록** `finally` 블록 안에서 `withContext(NonCancellable)`를 사용해야 합니다. 이를 통해 코루틴이 취소된 상태에서도 `finally` 블록 내의 모든 코드가 정상적으로 실행됩니다.
    - 예를 들어, 파일을 삭제하는 함수가 서스펜드 함수라면, `withContext(NonCancellable)`로 감싸주면 코루틴이 취소된 상태에서도 해당 파일이 안전하게 삭제됩니다.

---

### 핵심 요약

코루틴이 취소될 경우, 서스펜드 함수는 더 이상 호출되지 않지만, `finally` 블록에서 리소스를 정리해야 할 경우 `withContext(NonCancellable)`를 사용하여 **취소 불가** 상태에서 반드시 리소스 정리 작업을 완료하도록 해야 합니다

## EnsureActive vs yield

### 1. **ensureActive**

- `ensureActive`는 **취소 여부를 확인**하는 함수로, 코루틴이 취소된 경우 `CancellationException`을 발생시킵니다.
- **중단 함수**가 아니므로, **스레드 전환**이 발생하지 않습니다. 단순히 코루틴이 취소되었는지 확인하고, 취소된 경우 부모 스코프에 이를 알리는 역할을 합니다.

### 2. **yield**

- `yield`는 **중단 함수**이므로, 실행 중인 코루틴이 중단되고, 다른 코루틴이 실행될 수 있는 기회를 줍니다.
- `yield` 역시 코루틴이 취소되었는지 확인하고, 취소된 경우 **취소 예외**를 던집니다.
- 주요 차이점은 **스레드 전환**이 가능하다는 점입니다. 코루틴이 `yield`를 만나면 중단 상태에 들어가고, 다른 코루틴이 실행될 수 있는 기회를 갖습니다.

### 차이점 요약:

- `ensureActive`는 코루틴의 취소를 확인하지만, 중단 상태로 들어가지 않기 때문에 스레드 전환이 일어나지 않습니다.
- `yield`는 중단 상태로 들어가서 다른 코루틴이 실행될 수 있는 기회를 주며, **동시성 프로그래밍**에서 더 효율적으로 동작할 수 있습니다.

결론적으로, `ensureActive`는 빠른 실행을 원할 때 사용하고, `yield`는 **다른 코루틴에게 실행 기회를 제공**하면서 **동시성 작업을 최적화**할 때 유용합니다

</div>
</details>
