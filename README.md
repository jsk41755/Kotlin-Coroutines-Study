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

<details>
<summary> Coroutine Error Handling</summary>
<div markdown="1">

# Coroutine Error Handling

## How Coroutine Treat Exceptions

### 1. **예외 발생 시 동작**

- 코루틴 내에서 예외가 발생하면, 해당 예외는 **부모 코루틴**으로 전파됩니다. 예외가 처리되지 않으면 **애플리케이션이 충돌**하게 됩니다.
- 예외가 발생한 코루틴은 **즉시 중단**되며, 부모 코루틴이 해당 예외를 처리하지 않으면 부모 코루틴도 중단됩니다.

### 2. **예외 처리 방법**

- 예외를 처리하는 방법으로는 **`try-catch`** 블록을 사용하여 예외를 포착할 수 있습니다. 만약 자식 코루틴에서 발생한 예외를 처리하면, 부모 코루틴으로 전파되지 않고 안전하게 처리됩니다.

```kotlin
launch {
    try {
        delay(1000)
        throw Exception("오류 발생")
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
```

- 위 코드에서 자식 코루틴 내의 예외를 처리하면 부모 코루틴에 영향을 주지 않고 정상적으로 작업을 마칠 수 있습니다.

### 3. **부적절한 예외 처리**

- `try-catch`를 **`launch`** 함수 바깥에 사용하면 예외를 포착할 수 없습니다. 이는 `launch` 함수가 비동기로 실행되며, 호출 직후 바로 종료되기 때문에 **`try-catch`** 블록에서 예외를 잡을 수 없기 때문입니다.

```kotlin
try {
    launch {
        throw Exception("오류 발생")
    }
} catch (e: Exception) {
    println("예외 처리됨")
}
```

- 이 경우, 예외는 부모 코루틴으로 전파되며 애플리케이션이 충돌합니다.

### 4. **코루틴 예외 처리의 기본 원칙**

- **자식 코루틴**에서 발생한 예외는 **부모 코루틴으로 전파**되므로, 자식 코루틴 내에서 발생할 수 있는 예외를 **명시적으로 처리**하는 것이 좋습니다.
- 예를 들어, API 호출과 같은 중단 함수에서 발생할 수 있는 예외를 `try-catch`로 처리하여 부모 코루틴이 중단되지 않도록 해야 합니다.

## Catching Errors With CoroutineExceptionHandler

### 1. **`CoroutineExceptionHandler`의 개념**

- `CoroutineExceptionHandler`는 코루틴에서 발생한 **미처리된 예외**를 처리하는 **콜백**을 제공합니다.
- **코루틴 컨텍스트**의 일부로서, 예외가 발생했을 때 해당 예외를 처리할 수 있는 방법을 제공하며, 앱이 충돌하지 않도록 도와줍니다.

### 2. **사용 예시**

- **`try-catch`** 블록을 사용하지 않고 예외가 발생했을 때, 예외는 부모 코루틴으로 전파되며, 부모 코루틴이 취소됩니다.
- `CoroutineExceptionHandler`를 사용하면 **예외가 부모 코루틴으로 전파되기 전에 처리**할 수 있다.

```kotlin
val handler = CoroutineExceptionHandler { _, throwable ->
    throwable.printStackTrace()  // 예외 처리
}

launch(handler) {
    throw Exception("예외 발생")
}
```

### 3. **`CoroutineExceptionHandler`의 한계**

- **예외가 처리되더라도** 해당 코루틴은 **실패한 것으로 간주**됩니다. 즉, 예외가 발생한 자식 코루틴이 속한 **부모 코루틴** 및 해당 부모 코루틴 내의 **모든 자식 코루틴**도 취소됩니다.
- 예를 들어, 자식 코루틴에서 예외가 발생하면 부모 코루틴이 취소되고, 부모의 다른 자식 코루틴도 함께 취소됩니다.

### 4. **사용 시 주의사항**

- `CoroutineExceptionHandler`는 **글로벌 예외 처리** 또는 **로그 기록**을 위한 용도로 적합하지만, 일반적인 예외 처리를 위해 사용하는 것은 권장되지 않습니다.
- 예외 처리를 위해서는 **`try-catch` 블록**을 사용하는 것이 더 권장됩니다. 이는 각 코루틴 내에서 예외를 명시적으로 처리함으로써 부모 코루틴이 취소되는 것을 방지할 수 있기 때문입니다.

## SupervisorJob

### 1. **기본 코루틴 예외 처리**

- 기본적으로 코루틴에서 예외가 발생하면, **해당 코루틴 스코프 전체가 취소**됩니다. 예를 들어, 한 자식 코루틴에서 예외가 발생하면 같은 스코프 내의 다른 자식 코루틴도 함께 취소됩니다.

### 2. **`SupervisorJob`의 역할**

- `SupervisorJob`은 **각 자식 코루틴이 독립적으로 실행**되도록 하여, 한 자식 코루틴에서 예외가 발생해도 **다른 자식 코루틴에 영향을 주지 않도록** 합니다.
- `SupervisorJob`을 사용하면, 한 코루틴이 실패해도 나머지 코루틴은 계속해서 정상적으로 실행될 수 있습니다.

```kotlin
val supervisorScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

supervisorScope.launch {
    throw Exception("코루틴 1 오류 발생")
}

supervisorScope.launch {
    println("코루틴 2 실행 중")
}
```

위 코드에서 첫 번째 코루틴에서 예외가 발생하더라도, 두 번째 코루틴은 정상적으로 실행됩니다.

### 4. **안드로이드에서의 `SupervisorJob` 사용**

- **안드로이드의 `LifecycleScope`** 및 `ViewModelScope`는 기본적으로 `SupervisorJob`을 사용합니다. 이는 여러 독립적인 작업(예: 애니메이션, API 호출, 데이터베이스 쿼리 등)이 하나의 스코프 내에서 실행될 때, **하나의 작업이 실패해도 다른 작업에 영향을 주지 않도록** 하기 위함입니다.

### 5. **사용 시 주의사항**

- `launch` 또는 `withContext`와 함께 `SupervisorJob`을 직접 사용하는 것은 권장되지 않습니다. 이는 부모-자식 간의 구조적 동시성 관계가 깨질 수 있기 때문입니다. 대신, **사용자 정의 코루틴 스코프**에서만 `SupervisorJob`을 사용하는 것이 좋습니다.

## coroutineScope & supervisorScope

### 1. **`coroutineScope`**

- `coroutineScope`는 **모든 자식 코루틴이 완료될 때까지 기다리는 스코프**입니다.
- **구조적 동시성**을 보장하기 때문에, 자식 코루틴 중 하나에서 예외가 발생하면 **모든 자식 코루틴이 취소**됩니다. 즉, 한 코루틴이 실패하면 다른 자식 코루틴도 모두 취소됩니다.
- 이 방식은 **모든 작업이 성공해야 하는 경우**에 적합합니다. 예를 들어, 두 개의 API 호출이 상호 의존적일 때, 하나가 실패하면 다른 것도 취소되는 것이 바람직합니다.

### 2. **`supervisorScope`**

- `supervisorScope`는 **자식 코루틴이 독립적으로 동작**할 수 있게 하며, 한 자식 코루틴이 실패해도 다른 자식 코루틴에 영향을 주지 않습니다.
- 이는 여러 작업이 **서로 독립적**일 때 유용합니다. 예를 들어, 10개의 이미지를 압축하는 작업에서 하나의 이미지 압축이 실패해도 나머지 9개는 계속해서 압축 작업을 완료할 수 있습니다.

```kotlin
supervisorScope {
    launch {
        compressImage()  // 예외 발생 시 다른 코루틴에 영향 없음
    }
    launch {
        saveImage()  // 독립적으로 실행됨
    }
}
```

### 3. **사용 사례 비교**

- `coroutineScope`는 **모든 작업이 성공적으로 완료되어야 할 때** 사용됩니다. 예를 들어, 프로필 데이터를 가져올 때 두 개의 API 호출이 모두 성공해야 한다면, 하나의 호출이 실패하면 다른 호출도 취소되는 것이 이상적입니다.

```kotlin
coroutineScope {
    val metadata = async { getProfileMetadata() }
    val posts = async { getProfilePosts() }
    
    if (metadata.await() != null && posts.await() != null) {
        // 성공 시 처리
    }
}
```

- `supervisorScope`는 **각 작업이 독립적**이고, 하나의 실패가 다른 작업에 영향을 주지 않아야 할 때 사용됩니다. 예를 들어, 여러 이미지를 압축하는 경우 한 이미지의 압축이 실패해도 다른 이미지들이 계속 압축되어야 할 때 적합합니다.

### 4. **`launch`와 `async`의 차이**

- `launch`는 **결과를 반환하지 않는** 코루틴을 생성하며, 예외가 발생하면 즉시 부모 코루틴으로 전파됩니다.
- `async`는 **결과를 반환하는** 코루틴을 생성하며, `await`을 호출할 때까지 예외가 발생하지 않습니다. 즉, 예외가 발생하더라도 `await()`를 호출할 때까지 예외가 전파되지 않습니다.

</div>
</details>

<details>
<summary>Combining What You've Learnt So Far</summary>
<div markdown="1">

## **Converting a Callback to a Suspend Function**

### 1. **콜백을 `suspend` 함수로 변환**

- **콜백 기반 API**(예: 안드로이드의 GPS 위치 가져오기)를 **suspend 함수**로 변환하는 방법을 배웁니다.
- 예시로, `getCurrentLocation()`이라는 콜백 기반의 위치 요청 함수를 코루틴 기반의 `getLocation()`이라는 `suspend` 함수로 변환합니다.
- **`suspendCoroutine`** 또는 `suspendCancellableCoroutine`을 사용하여 **코루틴이 일시 중단**되도록 하고, 콜백이 호출되면 코루틴을 재개(resume)합니다.

### 2. **취소 처리**

- 코루틴이 취소되었을 때, 해당 콜백을 어떻게 중단시킬 수 있는지 설명합니다. 안드로이드의 경우, 대부분의 시스템 API는 `CancellationSignal`을 제공하여 이를 처리할 수 있습니다.
- `suspendCancellableCoroutine`을 사용하여 **취소 가능**한 코루틴을 만들고, 코루틴이 취소되면 `CancellationSignal`을 사용하여 요청을 취소합니다.

### 3. **에러 처리**

- 위치 권한이 없거나 다른 문제가 발생했을 때, `continuation.resumeWithException()`을 사용하여 코루틴에서 예외를 발생시키는 방법을 설명합니다.
- 예외가 발생하면 부모 코루틴으로 전파되며, 적절히 처리되지 않으면 코루틴이 중단됩니다.

### 4. **비동기 작업의 동작 방식**

- 비동기 작업이 콜백을 사용하여 즉시 반환하는 대신, 요청이 완료되었을 때 콜백을 호출합니다. 이를 **코루틴으로 변환**하여 **중단 지점**을 만들고, 콜백이 호출되면 코루틴이 다시 실행됩니다.

### 5. **주의 사항**

- **콜백이 한 번만 실행되는 경우**에만 `suspendCoroutine` 또는 `suspendCancellableCoroutine`을 사용할 수 있습니다. 만약 **여러 번 호출되는 콜백**을 처리해야 한다면, `Flow`를 사용하는 것이 더 적합합니다.

### 6. invokeOnCancellation

- **코루틴 취소 시점에 실행**: 코루틴이 취소될 때 **자동으로 호출**되어, 자원을 해제하거나 추가 작업을 실행할 수 있습니다.
- **취소 처리**: 외부 API나 비동기 작업이 있을 때, **취소 신호**를 보내거나, 비동기 작업을 중단할 때 유용합니다.
- 취소 가능 코루틴(`suspendCancellableCoroutine`)**과 함께 사용**: 특히, `suspendCancellableCoroutine`을 사용하는 경우, 비동기 작업이 중단될 수 있도록 이 메서드를 사용해 **콜백 취소**를 처리합니다.

```kotlin
suspend fun getLocation(): Location = suspendCancellableCoroutine { continuation ->
    val locationManager = ... // LocationManager 초기화
    val cancellationSignal = CancellationSignal()

    // 위치 요청 시작
    locationManager.getCurrentLocation(
        LocationManager.NETWORK_PROVIDER,
        cancellationSignal,
        mainExecutor,
        { location ->
            // 위치를 성공적으로 받으면 코루틴을 재개
            continuation.resume(location)
        }
    )

    // 코루틴이 취소될 때 취소 신호 전송
    continuation.invokeOnCancellation {
        cancellationSignal.cancel()  // 위치 요청을 취소
    }
}
```

### 동작 설명

1. `suspendCancellableCoroutine`을 사용하여 코루틴을 일시 중단합니다.
2. GPS 요청을 시작한 후, 위치가 받아지면 `continuation.resume()`*을 호출하여 코루틴을 다시 실행합니다.
3. 코루틴이 **취소**되면 `invokeOnCancellation`이 호출되고, 이 시점에 `CancellationSignal.cancel()`을 통해 **GPS 요청을 취소**합니다.

### 사용 시 주의사항

- `invokeOnCancellation`은 **코루틴이 취소될 때만 실행**됩니다. 코루틴이 정상적으로 완료되면 호출되지 않습니다.
- 취소된 상태에서도 **비동기 작업이 종료**되도록 안전하게 구현해야 합니다.

### 번외 : 백그라운드 위협에 대한 콜백을 실행하는 newSingleThreadExecutor 에서는 잘 작동하지 않고, Main Thread에서 수행되어야 하는 이유

백그라운드 스레드에서 실행되는 **`newSingleThreadExecutor`** 대신 **메인 스레드**에서 콜백을 실행해야 하는 이유는, 안드로이드 시스템에서 **UI 관련 작업** 및 일부 **시스템 API 호출**이 반드시 **메인 스레드**에서 이루어져야 하기 때문입니다. 이를 따르지 않으면 **비정상적인 동작**이나 **충돌**이 발생할 수 있습니다. 여기서 그 이유를 구체적으로 설명해 볼게요.

### 1. **안드로이드 UI 스레드 원칙**

- 안드로이드의 UI 스레드는 앱의 **UI와 상호작용**하는 모든 작업을 처리하는 스레드입니다. 따라서 **UI 업데이트** 또는 **UI와 관련된 콜백**은 메인 스레드에서 실행되어야 합니다.
- 많은 안드로이드 **시스템 API**는 메인 스레드에서 호출되어야 하며, 이를 백그라운드 스레드에서 호출하면 예기치 않은 오류가 발생할 수 있습니다. 예를 들어, 위치 서비스(Location Manager)나 **카메라 API** 등이 있습니다.

### 2. **`newSingleThreadExecutor`의 문제**

- `newSingleThreadExecutor`는 **단일 백그라운드 스레드**에서 작업을 처리하도록 설계된 **Executor**입니다. 이는 **멀티스레딩** 환경에서 유용하지만, 안드로이드의 **UI 작업**이나 **시스템 콜백**을 백그라운드 스레드에서 처리하려고 하면 **메인 스레드와의 충돌**이 발생할 수 있습니다.
- 특히 **안드로이드 시스템 API**에서 제공하는 콜백은 주로 **메인 스레드에서 호출**되도록 설계되어 있기 때문에, 이를 백그라운드 스레드에서 처리하면 동작이 제대로 이루어지지 않거나 충돌이 발생할 수 있습니다.

### 3. **GPS 및 시스템 서비스 콜백의 메인 스레드 요구**

- **GPS 위치 요청**과 같은 시스템 서비스는 **메인 스레드에서 실행**되어야 합니다. 위치 요청 API는 위치가 수신될 때 **메인 스레드에서 UI 업데이트**를 하거나, 시스템 리소스를 관리하는데, 백그라운드 스레드에서 이를 처리할 경우 이러한 동작이 **안정적으로 이루어지지 않을 수 있습니다**.
- **메인 스레드**는 이러한 API들이 요청을 올바르게 처리하고, 필요한 경우 **취소(cancellation)** 등의 작업도 안전하게 수행할 수 있는 환경을 제공합니다.

### 4. **주요 예시: `Main Executor` 사용**

백그라운드 스레드 대신 **메인 스레드**에서 안전하게 콜백을 실행하기 위해, 안드로이드에서 메인 스레드 실행기(`Main Executor`)를 사용해야 합니다.

```kotlin
val mainExecutor = ContextCompat.getMainExecutor(context)
locationManager.getCurrentLocation(
    LocationManager.NETWORK_PROVIDER,
    null,  // CancellationSignal
    mainExecutor,  // Main thread에서 콜백을 실행
    { location ->
        // 위치가 성공적으로 수신되었을 때 콜백 처리
        println("위치: ${location.latitude}, ${location.longitude}")
    }
)
```

### 5. **왜 `Main Executor`가 필요한가?**

- **안전한 스레드 동작**: 메인 스레드에서 실행되는 코드는 **UI 작업**과 **안드로이드 시스템과의 상호작용**이 안전하게 이루어지도록 보장됩니다.
- **예상치 않은 동작 방지**: 백그라운드 스레드에서 시스템 콜백을 처리하면 시스템 리소스 관리나 UI 업데이트에서 오류가 발생할 수 있습니다. 이러한 작업은 반드시 **메인 스레드**에서 처리되어야 안전하게 동작합니다.

### 결론

- `newSingleThreadExecutor`와 같은 백그라운드 스레드는 **UI 업데이트**나 **안드로이드 시스템 콜백**을 처리하는 데 적합하지 않습니다. 이러한 작업은 **메인 스레드**에서 이루어져야 하며, 이를 위해 `Main Executor`를 사용하는 것이 권장됩니다. 이는 시스템 안정성 및 예측 가능한 동작을 보장하는 데 필수적입니다.

</div>
</details>

<details>
<summary> Coroutine Synchronization</summary>
<div markdown="1">

## **When Do You Have to Think of Synchronization**

### 1. **경쟁 상태(Race Condition)**

- **경쟁 상태**란, 여러 코루틴(또는 스레드)이 **동시에 공유 자원**(예: 변수 `count`)에 접근하여 값을 읽고 쓰는 상황에서 발생하는 문제입니다.
- 예시로, `count` 변수를 0에서 시작하여 100,000번 증가시키려 하지만, 실제 실행 결과는 100,000에 도달하지 못하는 경우가 발생합니다. 이는 **다수의 코루틴이 동시에 값을 읽고, 증가시키고, 다시 쓰는 과정에서 충돌**이 발생하기 때문입니다.

### 2. **왜 발생하는가?**

- `count++` 연산은 단순해 보이지만, 실제로는 **세 단계**로 이루어집니다:여러 코루틴이 **동시에 이 세 단계를 실행**하면, 한 코루틴이 값을 증가시키는 도중 다른 코루틴이 같은 값을 읽고 동일한 결과를 기록할 수 있습니다. 이로 인해 **최종 값이 정확하지 않게** 됩니다.
    1. **현재 값 읽기**
    2. **값 증가**
    3. **증가된 값 쓰기**

### 3. **단일 스레드에서의 동작**

- 만약 **모든 작업을 메인 스레드**에서 실행하면, 코루틴들이 한 번에 하나씩만 실행되므로 경쟁 상태가 발생하지 않습니다. 하지만 이는 **UI가 멈추거나 성능 저하**를 일으킬 수 있기 때문에, 실제 애플리케이션에서는 비효율적입니다.

### 4. **동기화의 필요성**

- **여러 코루틴**이 **동시에 동일한 자원**에 접근하는 경우, 동기화(synchronization)가 필요합니다. 동기화를 통해 한 코루틴이 자원을 수정하는 동안 다른 코루틴이 접근하지 못하도록 해야 합니다.

## **synchronized and Mutex**

### 1. **`synchronized` 블록**

- **`synchronized`** 블록은 자바의 기본 동기화 메커니즘으로, **스레드 간 자원 접근을 제어**합니다.
- **공유된 락 객체**를 사용하여 여러 스레드가 같은 변수에 접근하지 않도록 **순차적으로 코드가 실행**되도록 보장합니다.
- 예시:
    
    ```kotlin
    val lock = Any()
    synchronized(lock) {
        count++
    }
    
    ```
    
- **동작 원리**: 스레드가 **동시에 같은 코드에 접근할 수 없도록** 하고, 한 스레드가 작업을 완료한 후에야 다른 스레드가 실행됩니다.
- **락 객체가 공유되지 않으면** 동기화가 제대로 이루어지지 않습니다. 각 코루틴이 서로 다른 락 객체를 사용하면 동기화되지 않아 **경쟁 상태**가 발생할 수 있습니다.

### 2. **`Mutex`** (Mutual Exclusion)

- `Mutex`는 코루틴에서 사용하는 동기화 메커니즘으로, 하나의 뮤텍스(key)를 통해 여러 코루틴이 **공유 자원에 순차적으로 접근**할 수 있도록 합니다.
- 예시:
    
    ```kotlin
    val mutex = Mutex()
    mutex.withLock {
        count++
    }
    
    ```
    
- **`withLock`** 함수는 **코루틴을 중단**시키며, 뮤텍스가 해제될 때까지 다른 코루틴이 자원에 접근하지 못하게 합니다. 중단된 코루틴은 뮤텍스가 해제되면 다시 실행됩니다.
- `withLock`은 **중단 함수**이므로, **코루틴의 동시성**을 관리하는 데 적합합니다.

### 3. **`synchronized` vs `Mutex` 비교**

- `synchronized`는 **일반 스레드**와 코루틴에서 모두 사용할 수 있지만, 코루틴에서는 비효율적일 수 있습니다.
- `Mutex`는 코루틴을 위한 동기화 도구로, 코루틴이 **중단되면서도 다른 코루틴이 실행될 수 있는 장점**을 제공합니다.
- **코루틴을 사용하는 경우**에는 `Mutex`를 사용하는 것이 더 적합합니다. `synchronized`는 자바에서 제공하는 스레드 기반 동기화 방식으로, **코루틴의 중단 상태**를 고려하지 않습니다.
- 또한, **Kotlin 멀티플랫폼 프로젝트**에서는 `synchronized`를 사용할 수 없고, `Mutex`만 사용할 수 있습니다.

### 4. **주의 사항**

- **데드락**: 잘못된 사용으로 **데드락**이 발생할 수 있습니다. 예를 들어, 두 코루틴이 서로 뮤텍스를 기다리면 **작업이 중단된 상태로 멈추는 문제**가 발생할 수 있습니다. 이를 방지하기 위해 `withLock`을 사용할 때 최소한의 코드만 동기화 블록에 넣는 것이 중요합니다.

## **Concurrent Lists and HashMaps**

### 1. **경쟁 상태와 동시성 문제**

- 경쟁 상태(Race Condition)는 **공유 데이터**에 여러 스레드 또는 코루틴이 동시에 접근하여 읽기 및 쓰기를 할 때 발생할 수 있습니다.
- **Mutable 데이터 구조**(예: **HashMap** 또는 **Mutable List**)도 이 문제를 겪습니다. 예를 들어, 여러 코루틴이 **HashMap**에 동시에 값을 읽고 쓰면, 데이터가 불일치하거나 손실될 수 있습니다.

### 2. **Concurrent HashMap**

- **Concurrent HashMap**은 **동시성**을 지원하는 데이터 구조로, 읽기와 쓰기 연산이 **동기화**되어 있습니다.
- 하지만, **읽기와 쓰기 작업 사이**는 동기화되지 않기 때문에, 읽기와 쓰기 사이에 다른 스레드가 값을 수정할 수 있는 **경쟁 상태**가 여전히 발생할 수 있습니다.
    
    ```kotlin
    val concurrentHashMap = ConcurrentHashMap<Int, Int>()
    // 동시성 문제 발생 가능
    val currentCount = concurrentHashMap[randomKey] ?: 0
    concurrentHashMap[randomKey] = currentCount + 1
    
    ```
    

### 3. **Mutex 사용**

- **Mutex**는 **동시성 문제**를 해결하는 더 안전한 방법입니다. **Mutex**를 사용하면, **읽기와 쓰기 작업을 모두 동기화**할 수 있어 경쟁 상태를 방지할 수 있습니다.
    
    ```kotlin
    val mutex = Mutex()
    mutex.withLock {
        val currentCount = hashMap[randomKey] ?: 0
        hashMap[randomKey] = currentCount + 1
    }
    ```
    
    `withLock`은 **코루틴을 중단**시키고, 뮤텍스가 해제될 때까지 다른 코루틴이 자원에 접근하지 못하게 합니
    

### 4. **Concurrent HashMap의 한계**

- **Concurrent HashMap**은 각 **읽기 또는 쓰기 연산**을 개별적으로 동기화하지만, **여러 연산을 연결**하는 경우에는 동기화가 되지 않습니다. 즉, **복합 연산**(읽기 후 쓰기 등)을 수행할 때는 여전히 **경쟁 상태**가 발생할 수 있습니다.
- 이러한 이유로, **Concurrent HashMap**만으로는 **완전한 동기화**를 보장할 수 없습니다.

### 5. **Mutex와의 비교**

- **Concurrent HashMap**은 단일 연산에 대해서만 동기화를 지원하며, **여러 연산을 결합한 작업**에서는 문제가 발생할 수 있습니다.
- 반면, **Mutex**를 사용하면 **여러 연산을 하나의 블록으로 묶어** 동기화할 수 있어, 읽기와 쓰기 사이의 경쟁 상태를 방지할 수 있습니다.

## **Single Thread Dispatcher**

### 1. **싱글 스레드 디스패처의 개념**

- **싱글 스레드 디스패처**는 **단일 스레드**에서 모든 작업을 실행하도록 제한합니다. 즉, 한 번에 하나의 작업만 실행되므로경쟁 상태(Race Condition)가 발생하지 않습니다.
- **동시성 문제**가 발생하지 않도록 **코드 블록을 순차적으로 실행**할 수 있기 때문에, **Mutex**나 **synchronized** 블록과 같은 동기화 기법이 필요 없습니다.

### 2. **사용 방법**

- `Dispatchers.IO.limitedParallelism(1)`을 사용하여 **단일 스레드**에서 코루틴을 실행할 수 있습니다. 이렇게 하면, 코루틴이 여러 스레드에서 동시에 실행되는 것을 방지하여 **동시성 문제**를 해결할 수 있습니다.
    
    ```kotlin
    val dispatcher = Dispatchers.IO.limitedParallelism(1)
    CoroutineScope(dispatcher).launch {
        // 동기화가 필요한 코드
        count++
    }
    
    ```
    

### 3. **장점**

- **간편한 동기화**: 단일 스레드에서 실행되므로, **경쟁 상태가 발생하지 않으며** 코드가 자연스럽게 동기화됩니다.
- **성능 최적화**: 코루틴이 일시 중단(`suspend`)될 때, 해당 스레드는 다른 코루틴을 처리할 수 있기 때문에 **단일 스레드로도 효율적인 처리**가 가능합니다.
- **병렬 처리와의 결합**: **API 호출** 등에서는 **다시 병렬 처리**로 전환할 수 있어, 필요한 부분에서만 싱글 스레드를 사용하고 나머지 작업은 병렬로 처리할 수 있습니다.

### 4. **주의 사항**

- **블로킹 코드**가 포함된 경우에는 주의가 필요합니다. 예를 들어, 비트맵을 압축하는 작업과 같은 **CPU 집약적인 작업**을 싱글 스레드에서 처리하면 성능이 저하될 수 있습니다. 이 경우, **멀티 스레드**로 전환하여 작업을 처리하는 것이 더 적합합니다.
    
    ```kotlin
    // 병렬 처리가 필요한 경우
    withContext(Dispatchers.Default) {
        // 블로킹 코드 처리
        compressBitmap()
    }
    
    ```
    

### 5. **싱글 스레드 디스패처 사용 시 주의**

- **디스패처 인스턴스 공유**: 각 코루틴이 동일한 **싱글 스레드 디스패처 인스턴스**를 사용해야만 제대로 동기화가 이루어집니다. 만약 각 코루틴이 **서로 다른 디스패처**를 사용하면 **동기화가 되지 않아** 예측하지 못한 동작이 발생할 수 있습니다.

### 6. **실전 적용**

- **싱글 스레드 디스패처**를 클래스에서 글로벌하게 정의하여 여러 코루틴이 동일한 디스패처를 사용할 수 있게 합니다. 이를 통해 **모든 코루틴이 같은 스레드에서 실행**되므로, **경쟁 상태**를 방지할 수 있습니다.

```kotlin
class MySynchronizedClass(
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO.limitedParallelism(1) + SupervisorJob())
) {
    fun incrementCount() {
        coroutineScope.launch {
            count++
        }
    }
}

```

### 요약

- **싱글 스레드 디스패처**는 모든 코루틴을 **단일 스레드**에서 실행하여 **경쟁 상태를 방지**하고, **간편하게 동기화** 문제를 해결할 수 있습니다.
- 단일 스레드에서 실행되므로 **동기화 메커니즘**이 필요 없지만, CPU 집약적인 작업에는 적합하지 않으므로 **멀티 스레드**로 전환이 필요할 수 있습니다

</div>
</details>

<details>
<summary>Flow Fundamentals</summary>
<div markdown="1">

## What is a Flow

### 1. **Flow의 개념**

- **Flow**는 **코루틴 기반**의 비동기 데이터 스트림입니다. 코루틴에서 단일 값을 반환하는 **`suspend` 함수**와 달리, **Flow**는 **여러 값을 순차적으로 반환**할 수 있습니다.
- **Flow**는 시간에 걸쳐 여러 값을 방출(emit)하며, 이 값을 수집(collect)하는 방식으로 동작합니다. 예를 들어, GPS 위치나 실시간 데이터를 관찰할 때 유용합니다.

### 2. **Flow 사용 예시**

- Flow는 `flow` 빌더를 사용하여 생성되며, 내부에서 값을 방출(emit)할 수 있습니다. Flow의 각 값은 **`emit()`** 함수를 통해 순차적으로 방출됩니다.
    
    ```kotlin
    fun flowDemo(): Flow<Int> = flow {
        emit(1)
        delay(2000)  // 2초 지연
        emit(2)
        delay(3000)  // 3초 지연
        emit(3)
    }
    ```
    
- `collect` 함수를 사용하여 Flow에서 방출된 값을 **수집**할 수 있습니다. **수집 과정**은 **코루틴 내에서 비동기적으로** 진행됩니다.
    
    ```kotlin
    CoroutineScope(Dispatchers.Main).launch {
        flowDemo().collect { value ->
            println("현재 값: $value")
        }
    }
    ```
    
- 위 코드에서 **1, 2, 3**이 순차적으로 방출되고, 2초와 3초의 지연 후에 각각의 값이 출력됩니다.

### 3. **Flow의 특징**

- Flow는 **코루틴 기반**이기 때문에 **`suspend` 함수**를 호출할 수 있으며, 비동기적인 작업을 쉽게 처리할 수 있습니다.
- Flow는 단일 값을 반환하는 것이 아니라, **여러 값을 순차적으로 방출**하기 때문에, 실시간 데이터 스트림이나 비동기 데이터 처리가 필요할 때 매우 유용합니다.

### 4. **Flow와 코루틴의 관계**

- Flow는 **코루틴의 모든 기능**을 상속받으며, 이전에 학습한 **취소 처리, 에러 처리, 동시성 처리** 등의 개념이 모두 적용됩니다.
- Flow를 **수집하는 동안**에는 **코루틴이 일시 중단**되며, 모든 값이 수집될 때까지 **수집 과정**이 계속됩니다.

### 5. **실전 활용**

- **실시간 데이터 관찰**: 예를 들어, 사용자가 입력하는 텍스트 필드를 **실시간으로 감지**하여 이메일 유효성을 확인하거나, GPS 위치를 실시간으로 업데이트할 때 유용합니다.
- Flow는 **반응형 프로그래밍**(Reactive Programming)의 기초가 되며, 값의 변화에 따라 자동으로 **반응하는** 시스템을 구축할 수 있습니다.

## The Structure of Every Launched Flow

### 1. **값 소스(Value Source)**

- **Flow**는 **값을 방출하는 소스**로 시작됩니다. 이 소스는 **여러 값을 시간에 걸쳐 방출**하며, 일반적인 함수가 하나의 값을 반환하는 것과 달리 Flow는 **여러 값을 순차적으로 방출**할 수 있습니다.

### 2. **Flow 실행과 `collect`**

- Flow는 **명시적으로 실행**되어야만 동작합니다. Flow를 정의하기만 하고 수집(collect)하지 않으면, Flow는 **아무 작업도 하지 않습니다**. 즉, **`collect`** 함수를 사용하여 Flow를 실행하고, 방출된 값을 수집해야 합니다.
    
    ```kotlin
    flowDemo().collect { value ->
        println("방출된 값: $value")
    }
    ```
    

### 3. **`launchIn`을 사용한 Flow 실행**

- Flow를 실행하는 또 다른 방법으로 `launchIn`을 사용할 수 있습니다. 이는 `collect`와 유사하지만, 코루틴 스코프에서 Flow를 실행할 때 더 간결하게 사용할 수 있습니다.
- `launchIn`은 `collect`와 달리 코루틴 스코프 내에서 Flow를 바로 실행할 수 있으며, **중단 함수**가 아닙니다. 이 방법은 **한 번 더 간결하게 Flow를 실행**할 수 있습니다.
    
    ```kotlin
    flowDemo()
        .onEach { value -> println("방출된 값: $value") }
        .launchIn(CoroutineScope(Dispatchers.Main))
    ```
    

### 4. **중간 연산자(Intermediate Operators)**

- Flow에서 **중간 연산자**는 값이 방출되는 과정에서 **값을 변환하거나 필터링하는 작업**을 수행합니다.
- 예시로 **`map`**, **`filter`**, **`take`**, **`buffer`** 등의 연산자가 있으며, Flow의 **데이터 스트림을 조작**할 수 있습니다.
    
    ```kotlin
    flowDemo()
        .filter { it > 1 }
        .map { it * 2 }
        .collect { value -> println("변환된 값: $value") }
    ```
    

### 5. **종료 연산자(Terminal Operators)**

- Flow는 **종료 연산자**가 호출되어야만 **실행**됩니다. `collect`와 `launchIn`이 대표적인 종료 연산자입니다.
- 종료 연산자 없이 Flow를 정의하면, **실제로 실행되지 않습니다**. 종료 연산자가 호출되어야 Flow가 **시작**되고, 그 결과를 사용할 수 있습니다.

### 6. **Flow의 전체 구조**

- **Flow의 구조**는 다음과 같습니다:Flow는 종료 연산자가 호출될 때까지 실제로 **실행되지 않으며**, 그 전까지는 정의만 된 상태입니다.
    - **값 소스(Value Source)**: 값이 방출되는 시작 지점.
    - **중간 연산자(Intermediate Operators)**: 값을 변환하거나 필터링하는 연산자.
    - **종료 연산자(Terminal Operators)**: Flow를 실행하는 최종 단계.

## SharedFlow

### 1. **SharedFlow란?**

- **SharedFlow**는 여러 코루틴에서 **공유되는 데이터 스트림**입니다. **cold flow**와 달리, **collector**가 없더라도 값을 방출할 수 있습니다.
- **cold flow**는 수집(collect)을 시작해야 값이 방출되지만, **SharedFlow**는 수집자가 없어도 **지속적으로 값을 방출**할 수 있습니다.

### 2. **SharedFlow 사용 예시**

- **SharedFlow**는 `MutableSharedFlow`로 생성되며, **코루틴 내부 어디서든** 값을 방출할 수 있습니다. 이를 통해 **공유된 데이터를 여러 코루틴에서 동시에 처리**할 수 있습니다.
    
    ```kotlin
    val sharedFlow = MutableSharedFlow<Int>()
    
    CoroutineScope(Dispatchers.Main).launch {
        sharedFlow.collect { value ->
            println("Collector 1: $value")
        }
    }
    
    CoroutineScope(Dispatchers.Main).launch {
        sharedFlow.collect { value ->
            println("Collector 2: $value")
        }
    }
    
    CoroutineScope(Dispatchers.Main).launch {
        repeat(10) {
            delay(500)
            sharedFlow.emit(it)
        }
    }
    ```
    
- 위 코드에서는 두 개의 **collector**가 **SharedFlow**를 수집하며, 500ms마다 방출된 값을 동시에 수집합니다.

### 3. **Hot Flow의 특성**

- **SharedFlow**는 **collector가 없더라도 값을 방출**할 수 있는 **hot flow**입니다. 이는 값이 방출되는 시점에 수집자가 없으면 **값이 손실**될 수 있음을 의미합니다.
- 예를 들어, **emit**이 5까지 방출된 후에 수집자가 등록되면, **이전 값들은 손실**되고 이후 값들만 수집됩니다.

### 4. **Replay Cache**

- **Replay Cache**는 새로운 **collector**가 생길 때 **이전 방출된 값들을 재전송**할 수 있도록 합니다. 예를 들어, `replay = 3`으로 설정하면 **마지막 3개의 값**이 캐시되어 새로운 **collector**에게 전달됩니다.
    
    ```kotlin
    val sharedFlow = MutableSharedFlow<Int>(replay = 3)
    ```
    
- **Replay Cache**를 통해 새로운 수집자가 생겨도 **이전 값들을 다시 전달**할 수 있습니다.

### 5. **Buffer 및 Overflow 처리**

- **SharedFlow**는 수집자들이 값을 처리하는 동안 **버퍼에 값을 저장**할 수 있습니다. 이를 통해 **느린 수집자**와 **빠른 수집자** 간의 처리 속도 차이를 해결할 수 있습니다.
- **Overflow 전략**을 통해 **버퍼가 가득 찼을 때** 어떻게 처리할지를 결정할 수 있습니다. 예를 들어, **가장 오래된 값**을 버리거나(`dropOldest`), **가장 새로운 값**을 버릴 수 있습니다(`dropLatest`).

### 6. **실전 활용 사례**

- **토스트 메시지**와 같은 **일회성 이벤트**에 적합합니다. 예를 들어, 뷰모델에서 **SharedFlow**를 통해 메시지를 방출하면, UI에서는 해당 이벤트를 수집하여 메시지를 표시할 수 있습니다.
- **위치 업데이트**와 같은 **지속적인 데이터 스트림**에도 유용합니다. **SharedFlow**를 사용하면 **여러 수집자**가 동시에 위치 데이터를 수신할 수 있습니다.

---

### 번외 extraBufferCapcity)

- `extraBufferCapacity`는 **SharedFlow**나 **StateFlow**에서 사용하는 **버퍼링 옵션** 중 하나로, 수집자(collector)들이 데이터를 처리하는 동안 **방출된 값들을 버퍼에 저장**할 수 있는 공간을 지정합니다. 이를 통해 **느린 수집자**가 데이터를 처리하는 동안, 빠르게 방출되는 값들을 **손실 없이 버퍼에 저장**할 수 있습니다.

### 1. **`extraBufferCapacity`의 역할**

- **빠르게 방출되는 값**과 **느리게 수집하는 수집자** 사이의 **속도 차이**를 해결하는 데 사용됩니다.
- 버퍼가 설정되지 않으면, 수집자가 데이터를 모두 처리할 때까지 **emit 함수가 중단**됩니다. 그러나 `extraBufferCapacity`를 설정하면, 방출된 값을 버퍼에 저장하고 수집자가 데이터를 처리할 수 있는 시간을 줍니다.

### 2. **사용 예시**

```kotlin
val sharedFlow = MutableSharedFlow<Int>(
    replay = 0,               // 이전 값은 저장하지 않음
    extraBufferCapacity = 5    // 버퍼 크기를 5로 설정
)

CoroutineScope(Dispatchers.Main).launch {
    sharedFlow.collect { value ->
        delay(1000)  // 느리게 데이터를 처리하는 수집자
        println("Collector: $value")
    }
}

CoroutineScope(Dispatchers.Main).launch {
    repeat(10) {
        sharedFlow.emit(it)  // 빠르게 값을 방출
        println("Emitted: $it")
    }
}
```

- 위 코드에서 `extraBufferCapacity = 5`로 설정했으므로, 수집자가 데이터를 **1초마다 처리**하는 동안 방출된 값들은 **버퍼에 저장**됩니다.
- 5개의 값은 버퍼에 저장되고, 그 이후 값은 수집자가 버퍼를 비울 때까지 **`emit` 함수가 중단**됩니다.

### 3. **`extraBufferCapacity`와 기본 동작**

- **기본적으로** `extraBufferCapacity`는 **0**으로 설정되어 있습니다. 즉, 수집자가 방출된 값을 처리하지 않으면 **버퍼 없이** `emit` 함수는 **중단**됩니다.
- 버퍼를 추가하면, `emit` 함수가 중단되지 않고 **버퍼에 데이터를 쌓아둘 수 있습니다**. 이를 통해 수집자가 느리게 데이터를 처리해도, 방출된 값이 **손실되지 않고 저장**됩니다.

### 4. **Overflow 처리**

- **버퍼가 가득 찼을 때** 발생하는 상황을 처리하기 위해 **버퍼 오버플로우 전략**을 설정할 수 있습니다. 예를 들어, **가장 오래된 값**을 삭제하거나(`dropOldest`), **가장 최근에 방출된 값**을 삭제하는(`dropLatest`) 방식으로 처리할 수 있습니다.

### 5. **실전 활용**

- **버퍼링**은 **실시간 데이터 스트림**이나 **대기 시간이 있는 작업**에서 유용합니다. 예를 들어, 네트워크 요청이나 데이터베이스 쿼리에서 **데이터 처리 속도가 느릴 때** 버퍼를 사용해 **손실 없이 데이터를 관리**할 수 있습니다.

## StateFlow

### 1. **StateFlow란?**

- **StateFlow**는 **상태 값을 관리**하고, 그 값이 변할 때마다 **자동으로 변경 사항을 감지**할 수 있는 **Flow**의 일종입니다.
- **초기 값**을 반드시 설정해야 하며, 이는 StateFlow가 **단일 상태 값을 보유**하는 데 중점을 두기 때문입니다. 예를 들어, 초기 값이 `0`이면, 이는 정수형 값을 관리하는 Flow가 됩니다.

### 2. **StateFlow와 SharedFlow의 차이점**

- **SharedFlow**는 **버퍼**와 **캐시 설정**이 가능하지만, **StateFlow**는 **오직 하나의 상태 값**만을 보유합니다.
- **SharedFlow**는 여러 값들을 방출하고 관리할 수 있지만, **StateFlow**는 **최신 상태 값을 보유**하며, 수집자가 새롭게 추가되면 **즉시 최신 상태 값**을 받습니다.

### 3. **StateFlow의 동작 방식**

- **수집자가 생기면** 즉시 **현재 상태 값**을 받게 됩니다. 이는 새로운 수집자가 추가되었을 때, 방출된 값이 없어도 즉시 상태를 확인할 수 있는 특징입니다.
- **StateFlow**는 항상 **가장 최근 값**을 유지하며, 수집자가 언제 추가되든 해당 **최신 값을 즉시 전달**합니다.

### 4. **StateFlow의 사용 사례**

- **UI 상태 관리**에 매우 적합합니다. 예를 들어, 로딩 상태(loading state)나 **텍스트 필드**의 값처럼 시간이 지남에 따라 변하는 상태를 관리할 때 사용됩니다.
- **ViewModel**에서 상태 값을 관리할 때, **StateFlow**를 사용하여 UI와 상호작용할 수 있습니다. UI는 StateFlow를 수집하여 변경 사항을 즉시 반영합니다.

### 5. **StateFlow 예시**

```kotlin
val isLoading = MutableStateFlow(false)

CoroutineScope(Dispatchers.Main).launch {
    isLoading.collect { value ->
        if (value) {
            showLoadingIndicator()
        } else {
            hideLoadingIndicator()
        }
    }
}

CoroutineScope(Dispatchers.IO).launch {
    isLoading.emit(true)  // 로딩 시작
    delay(3000)
    isLoading.emit(false)  // 로딩 완료
}
```

- 위 예시에서는 **로딩 상태**가 변경될 때마다 **UI에서 로딩 인디케이터**를 표시하거나 숨깁니다.

### 6. **UI 상태 관리에서의 사용 예시**

- **Compose**와 결합하여 **StateFlow**를 사용하면, **UI의 상태**를 손쉽게 관리할 수 있습니다. 예를 들어, 로딩 상태나 텍스트 필드 값이 변경될 때, 해당 값에 따라 UI가 자동으로 업데이트됩니다.
    
    ```kotlin
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    
    if (isLoading) {
        CircularProgressIndicator()
    } else {
        Text("Profile Loaded")
    }
    ```
    

### 7. **StateFlow의 동시성 처리**

- **StateFlow**는 스레드 안전(thread-safe)하게 설계되어 있어, 여러 코루틴에서 동시에 상태 값을 변경하더라도 경쟁 상태(race condition)가 발생하지 않습니다.
- 하지만 **UI 상태**와 같은 복합적인 데이터를 처리할 때는 **`copy()`** 메서드를 사용하여 **데이터를 업데이트**해야 합니다. **StateFlow**는 **새로운 인스턴스**가 만들어졌을 때만 상태 변경을 감지하고 방출합니다.

## **Making a cold Flow hot with stateIn()**

### 1. **Cold Flow와 Hot Flow의 차이**

- **Cold flow**는 수집자가 없으면 값을 방출하지 않습니다. 즉, 수집자가 생겨야만 데이터가 흘러가며, 각각의 수집자는 독립적인 데이터를 받습니다.
- 반면, **Hot flow**는 수집자가 없어도 값을 계속 방출하며, 새로운 수집자가 추가되면 **가장 최근 값**을 수집하게 됩니다.

### 2. **`stateIn` 연산자**

- `stateIn`은 **cold flow**를 **StateFlow**로 변환하여 **hot flow**처럼 동작하게 만듭니다. 이를 통해 Flow는 최신 상태 값을 유지하고, 새로운 수집자가 추가되면 **즉시 최신 값**을 받을 수 있습니다.
- **StateFlow**는 항상 **단일 상태 값**을 유지하며, 수집자가 새로운 값을 받기 전에 초기 값(initial value)을 설정해야 합니다.

### 3. **`stateIn` 사용 예시**

- Flow가 `stateIn`을 통해 **StateFlow**로 변환되면, 수집자는 언제든지 현재 상태 값을 쉽게 접근할 수 있습니다.
    
    ```kotlin
    val stateFlow = someFlow.stateIn(
        scope = CoroutineScope(Dispatchers.Main),
        started = SharingStarted.Eagerly,
        initialValue = 0
    )
    ```
    
- 위 코드에서는 `someFlow`라는 cold flow를 **StateFlow**로 변환하여, **Eagerly** 설정을 통해 **즉시 실행**되고 값을 캐시하게 만듭니다.

### 4. **상태 관리 및 캐싱**

- **StateFlow**는 **가장 최근의 값을 캐싱**하여 여러 수집자가 동일한 값을 수집할 수 있도록 합니다.
- **ViewModel**과 결합하여 **UI 상태를 관리**할 때 매우 유용합니다. 예를 들어, 사용자 위치 정보나 네트워크 상태와 같이 **지속적으로 변하는 데이터**를 관리할 수 있습니다.

### 5. **`SharingStarted` 옵션**

- `stateIn`에서 제공하는 **`SharingStarted`** 옵션을 통해 Flow가 언제 실행되고 언제 중단될지를 설정할 수 있습니다.
    - **Eagerly**: Flow가 즉시 실행되고, 수집자가 없어도 값을 방출합니다.
    - **Lazily**: 첫 번째 수집자가 생길 때까지 Flow의 실행을 지연시킵니다.
    - **WhileSubscribed**: 수집자가 있을 때만 Flow가 실행되고, 수집자가 없으면 중단됩니다.

### 6. **실제 예시: 사용자 위치 추적**

- 강의에서는 **사용자 위치 추적** 예시를 들어 설명합니다. 사용자의 위치가 변할 때마다 Flow를 통해 새로운 값을 방출하고, **StateFlow**를 사용해 최신 위치 정보를 여러 곳에서 참조할 수 있습니다.
- `stateIn`을 통해 사용자 위치 Flow를 **StateFlow**로 변환하여, 최신 위치를 캐시하고 필요한 곳에서 즉시 접근할 수 있습니다.

### 7. **실전 활용**

- `stateIn`은 **상태 관리**가 필요한 경우에 유용합니다. 특히, UI에서 **데이터의 최신 상태**를 유지하고, 여러 수집자가 동일한 데이터를 효율적으로 처리해야 할 때 적합합니다.

## **Making a cold Flow hot with shareIn()**

### 1. **Cold Flow와 Hot Flow의 차이**

- **Cold flow**는 수집자가 존재해야 값을 방출하는 방식입니다. 즉, **수집자가 없으면 아무 작업도 하지 않으며**, 수집자가 데이터를 요청할 때만 값을 방출합니다.
- **Hot flow**는 **수집자와 상관없이** 값을 방출하며, 수집자가 나중에 추가되더라도 **최신 값** 또는 **모든 방출된 값**을 받을 수 있습니다.

### 2. **`shareIn` 연산자**

- `shareIn`은 **cold flow**를 **hot flow**로 변환하는 연산자입니다. 이를 통해 Flow를 **여러 수집자에게 동시에 공유**할 수 있습니다.
- **코루틴 스코프**와 **방출을 시작하는 방식(sharing started)**, 그리고 **replay 값**(이전 값 재전송)을 선택할 수 있습니다.
    
    ```kotlin
    val sharedFlow = someFlow.shareIn(
        scope = CoroutineScope(Dispatchers.Main),
        started = SharingStarted.Eagerly,
        replay = 0
    )
    ```
    
- 위 코드에서 `shareIn`은 **수집자가 없어도 Flow를 방출**할 수 있게 하며, **Eagerly** 옵션을 통해 **즉시 방출을 시작**합니다.

### 3. **`shareIn`과 `stateIn`의 차이**

- `stateIn`은 Flow를 **StateFlow**로 변환하여 **단일 상태 값**만 관리합니다. 즉, 가장 최신 값만 유지하고, 수집자가 나중에 추가되면 **최신 값만 전달**합니다.
- `shareIn`은 모든 값을 방출하고, **여러 수집자에게 동일한 데이터를 공유**합니다. 수집자가 나중에 추가되더라도 **이전 값들을 모두 받을 수 있는 옵션**이 있습니다.

### 4. **실제 사용 사례**

- 예를 들어, 스마트워치와 휴대폰 간의 **메시지 시스템**을 구성할 때, **Heart Rate Update**나 **Distance Update** 같은 다양한 데이터를 **여러 ViewModel**에서 수집할 수 있습니다.
- 이때 **각각의 수집자**가 **모든 방출된 값**을 필요로 하기 때문에, `shareIn`을 사용하여 데이터 스트림을 **공유하고 관리**합니다.

### 5. **UI 상태 관리에서의 활용**

- UI에서는 **과거 값보다는 최신 값**에만 관심이 있기 때문에, 주로 **StateFlow**를 사용합니다. 예를 들어, 현재 위치 정보를 표시할 때는 **이전 값**이 아닌 **최신 위치**만 필요하므로 **StateFlow**가 더 적합합니다.

## Callback Flow

### 1. **`callbackFlow`란?**

- `callbackFlow`는 **콜백 기반의 API**를 **Flow**로 변환할 때 사용하는 **특수한 Flow 빌더**입니다. 이를 통해 **비동기 콜백**에서 발생하는 이벤트를 **Flow로 방출**할 수 있습니다.
- **콜백**은 일반적으로 비동기적으로 데이터를 제공하지만, **Flow**는 이를 **순차적으로 방출**하는 방식으로 처리할 수 있습니다.

### 2. **사용 사례: 위치 추적**

- 예시로, **Google Location Services**의 **사용자 위치 추적 API**를 **Flow**로 변환하여 **실시간으로 위치 데이터를 방출**합니다.
- **위치 콜백**은 지속적으로 새로운 위치를 제공하므로, `callbackFlow`를 사용하여 이 위치 데이터를 **Flow**의 **emission**으로 전환합니다.
    
    ```kotlin
    fun observeLocation(): Flow<Location> = callbackFlow {
        val locationRequest = LocationRequest.create().apply {
            interval = 1000L
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    
        val callback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult?) {
                result?.lastLocation?.let {
                    trySend(it)  // 콜백에서 새로운 위치를 Flow로 방출
                }
            }
        }
    
        locationClient.requestLocationUpdates(locationRequest, callback, Looper.getMainLooper())
    
        awaitClose {
            locationClient.removeLocationUpdates(callback)  // Flow 종료 시 콜백 제거
        }
    }
    ```
    

### 3. **`awaitClose`의 중요성**

- `awaitClose`는 **콜백 해제**를 처리하는 부분으로, **Flow가 종료되거나 취소될 때** 호출됩니다. 이를 통해 **콜백을 해제**하고, 불필요한 리소스 사용을 방지합니다.
- 예를 들어, 사용자가 화면을 벗어나거나 ViewModel이 종료될 때, Flow도 **자동으로 종료**되며 콜백이 해제됩니다.

### 4. **Flow의 종료 및 취소 처리**

- **콜백 기반 API**는 수동으로 콜백을 등록하고 해제해야 합니다. 그러나 `callbackFlow`를 사용하면 **Flow가 종료**되거나 **코루틴 스코프가 취소**될 때 자동으로 콜백을 해제할 수 있습니다.
- **코루틴의 생명 주기**에 맞춰 콜백이 관리되므로, 개발자가 별도로 콜백을 제거할 필요가 없습니다.

### 5. **실전 적용**

- **ViewModel**에서 `callbackFlow`를 사용해 위치 데이터를 처리하고, **LiveData** 또는 **StateFlow**로 변환하여 UI에 전달할 수 있습니다.
- **화면 전환이나 생명 주기 변화**에도 자동으로 콜백이 관리되므로, 리소스 누수나 잘못된 콜백 관리로 인한 문제를 방지할 수 있습니다.

---

### 번외 Callback)

- 콜백(callback)은 프로그래밍에서 특정 작업이 완료된 후에 **자동으로 호출되는 함수**를 의미합니다. 주로 비동기 작업(예: 네트워크 요청, 파일 읽기/쓰기, 타이머 등)에서 결과를 처리하거나 후속 작업을 진행할 때 사용됩니다. 콜백 함수는 특정 작업이 완료된 후 그 작업의 결과나 상태에 따라 실행됩니다.

### 콜백의 동작 원리

- **콜백 함수**는 미리 정의된 함수로, 특정 이벤트가 발생하거나 작업이 완료되면 **자동으로 호출**됩니다.
- 보통 콜백은 **비동기 함수**에 전달되며, 해당 함수가 작업을 끝낸 후 **콜백을 호출**하여 결과를 전달합니다.

### 콜백의 예시

**Kotlin의 콜백**:

- 안드로이드에서 위치 추적 API처럼, 콜백을 통해 위치 정보가 업데이트될 때마다 특정 함수를 호출하는 방식으로 사용됩니다.

```kotlin
val callback = object : LocationCallback() {
    override fun onLocationResult(result: LocationResult?) {
        result?.lastLocation?.let {
            // 위치 데이터를 처리하는 콜백 함수
            println("현재 위치: ${it.latitude}, ${it.longitude}")
        }
    }
}
```

### 콜백의 장점

- **비동기 작업을 처리**: 비동기 작업이 완료될 때까지 프로그램이 중단되지 않고 다른 작업을 계속 수행할 수 있습니다.
- **코드의 구조화**: 콜백을 사용하면 복잡한 비동기 작업의 결과 처리를 **명확하고 간결하게 구조화**할 수 있습니다.

### 콜백의 단점

- **콜백 헬(Callback Hell)**: 콜백 함수가 중첩되면, 코드가 복잡해져서 읽고 관리하기 어려워질 수 있습니다. 예를 들어, 여러 비동기 작업이 차례대로 실행되면, 콜백 함수가 중첩되는 현상이 발생합니다.
    
    ```jsx
    asyncOperation1(function(result1) {
        asyncOperation2(result1, function(result2) {
            asyncOperation3(result2, function(result3) {
                console.log(result3);
            });
        });
    });
    ```
    

### 콜백을 대체하는 다른 방식들

- **Coroutines** (Kotlin): **코루틴**은 콜백을 사용하지 않고도 비동기 작업을 순차적으로 작성할 수 있는 기능을 제공합니다. 이를 통해 비동기 작업을 마치 동기 작업처럼 표현할 수 있습니다.

</div>
</details>


<details>
<summary>Flows In Practice</summary>
<div markdown="1">

## Building a Timer Flow

### 1. **타이머 Flow 생성**

- 타이머를 구현하기 위해 **Flow**를 생성하고, **emissions per second** 값을 받아 **초당 특정 횟수**로 값을 방출하는 구조를 만들었습니다.
- **Flow 빌더**를 사용하여 **지속적인 루프**로 값을 방출하고, 방출 사이의 **시간 차이**를 Kotlin의 `Durati`으로 관리합니다.

### 예시 코드:

```kotlin
fun timerFlow(emissionsPerSecond: Int): Flow<Duration> = flow {
    var lastEmitTime = System.currentTimeMillis()
    emit(Duration.ZERO)  // 첫 번째 방출은 0으로 시작
    while (true) {
        delay((1000L / emissionsPerSecond).roundToLong())
        val currentTime = System.currentTimeMillis()
        val elapsedTime = currentTime - lastEmitTime
        emit(elapsedTime.milliseconds)
        lastEmitTime = currentTime
    }
}

```

- 지연(delay)을 통해 **지정된 초당 방출 횟수**로 값을 방출하며, 각 방출 사이의 **경과 시간**을 계산하여 **밀리초** 단위로 값을 방출합니다.

### 2. **실시간 타이머 구현**

- 타이머 Flow를 활용하여 **실시간 경과 시간**을 표시하는 **스톱워치**를 구현합니다.
- **`reduce` 연산자**를 사용하여 방출된 시간 차이를 **누적 합산**하여 **전체 경과 시간**을 계산합니다.
- **`map` 연산자**를 통해 경과 시간을 **시/분/초 형식의 문자열로 변환**하여 UI에 표시합니다.

### 예시 코드:

```kotlin
 val elapsedTimeFlow = timerFlow(10)
    .runningReduce { totalElapsedTime, newElapsedTime ->
        totalElapsedTime + newElapsedTime
    }
    .map { totalElapsedTime ->
	    totalElapsedTime.toComponents { hours, miniutes, seconds, nanoseconds ->
	        String.format(
	            "%02d:%02d:%02d",
	            hours,
	            miniutes,
	            seconds,
	            nanoseconds / (1_000_000L * 10L)
	        )
        }
    }
    .stateIn(
	    viewModelScope,
		  SharingStarted.WhileSubScribed(5000L),
		  "00:00:00:00"
		 )

```

- 이 코드를 통해 **경과 시간**을 누적하고, **시/분/초 형식**으로 변환하여 **UI에 표시**합니다.

### 3. **Flow 상태 관리**

- **StateFlow**로 변환하여 UI에서 **경과 시간**을 수집하고, **화면 회전과 같은 구성 변경**에도 타이머 상태를 유지합니다.
- *`sharingStarted = WhileSubscribed(5_000)`*를 설정하여, 구성 변경 시 **5초 동안 Flow를 유지**하고, 새로운 수집자가 생기면 Flow가 다시 중단 없이 이어집니다.

### 4. **진행률 표시**

- **진행률**을 표시하는 **Progress Bar**를 Flow로 구현합니다.
- **총 시간 대비 경과 시간**의 비율을 계산하여 **LinearProgressIndicator**로 UI에 표시합니다.

### 예시 코드:

```kotlin
val progress = timerFlow(100)
    .runningReduce { totalElapsedTime, newElapsedTime ->
        totalElapsedTime + newElapsedTime
    }
    .map { totalElapsedTime ->
        val totalMillis = totalElapsedTime.inWholeMilliseconds
        (totalMillis / 5_000f).coerceIn(0f, 1f)
    }
    .filter { progressFraction ->
		    progressFraction in (0f .. 1f)
		 }
		 .stateIn(
			 viewModelScope,
			 SharingStarted.WhileSubscribed(5000L),
			 0f
			)
	}
```

```kotlin
val progress by viewModel.progress.collectAsStateWithLifecycle()

LinearProgressIndicator(
	progress = { progress },
	modifier = Modifier
		.padding(16.dp)
		.fillMaxWidth()
)
```

## **Transforming Tracked Locations With zip() and combine()**

두 연산자는 각각 **두 개의 Flow를 결합**하지만, 그 방식에서 차이가 있습니다. 특히 **실시간 위치 추적** 앱에서 **위치 데이터**와 **타이머 데이터**를 결합하여 **평균 속도**를 계산하는 방법을 중점적으로 다룹니다.

### 1. **Flow 연산자: `zip`과 `combine`**

- **`zip`**: 두 Flow에서 **동시에 새로운 값**이 방출될 때, 그 값을 **쌍으로 묶어** 처리합니다. 두 Flow 중 하나가 더 빠른 속도로 값을 방출하더라도, **더 느린 Flow에 맞춰서** 동작합니다.
- **`combine`**: 두 Flow에서 **하나라도 새로운 값이 방출**되면, 그 값을 **즉시 결합**하여 처리합니다. 이는 **빈번한 값 방출**이 있는 경우, 두 Flow의 값이 항상 일치하지 않아도 처리할 수 있습니다.

### 2. **위치 데이터와 타이머 데이터를 결합한 예시**

- 강의에서는 **위치 데이터를 Flow**로 추적하고, **타이머 데이터를 Flow**로 함께 사용하여, 두 Flow를 결합하여 **평균 속도**를 계산하는 예시를 보여줍니다.
- **`zip` 연산자**를 사용하면 **타이머 값**과 **위치 값**이 동시에 갱신될 때, 두 값을 쌍으로 묶어 **경과 시간에 따른 위치 변화**를 추적할 수 있습니다.
    - 위 코드는 **위치 데이터**와 **타이머 데이터를 쌍**으로 묶어 **각 위치가 추적된 시점**을 기록합니다.

```kotlin
fun Context.locationTracking() {
    val observer = LocationObserver(this)

    timeAndEmit(3f)
        .runningReduce { totalElapsedTime, newElapsedTime ->
            totalElapsedTime + newElapsedTime
        }
        .zip(observer.observeLocation(1000L)) { totalDuration, location ->
            totalDuration to location
        }
        .onEach { (totalDuration, location) ->
            println("Location (${location.latitude}, ${location.longitude}) was tracked " +
                    "after ${totalDuration.inWholeMilliseconds} milliseconds.")
        }
        .runningFold(initial = emptyList<Pair<Duration, Location>>()) { locations, newLocation ->
            locations + newLocation
        }
        .map { allLocations ->
            allLocations.zipWithNext { (duration1, location1), (duration2, location2) ->
                val distance = location1.distanceTo(location2)
                val durationDifference = (duration2 - duration1).toDouble(DurationUnit.HOURS)

                if(durationDifference > 0.0) {
                    ((distance / 1000.0) / durationDifference)
                } else 0.0
            }.average()
        }
        .onEach { avgSpeed ->
            println("Average speed is $avgSpeed km/h")
        }
        .launchIn(GlobalScope)
}
```

### 3. **평균 속도 계산**

- 위치 데이터를 통해 **두 위치 사이의 거리**와 **경과 시간**을 계산하여 **평균 속도**를 구합니다. 이를 위해 **위치 간 거리 계산**과 **시간 차이**를 구해야 하며, **두 위치의 차이**를 계산한 후 **속도**를 계산할 수 있습니다.
    
    ```kotlin
    val distance = location1.distanceTo(location2)  // 두 위치 간 거리 계산
    val timeDifference = time2 - time1  // 시간 차이
    val speed = distance / timeDifference  // 속도 계산
    ```
    

### 4. **`combine` 연산자의 차이**

- **`combine`** 연산자를 사용하면, **두 Flow 중 하나라도 값이 갱신**될 때마다 그 값을 즉시 결합합니다. 하지만 이 경우 **두 Flow의 값 방출 주기**가 다르면 **불필요하게 많은 결합 작업**이 발생할 수 있습니다.
- 예를 들어, 타이머가 초당 세 번 값을 방출하고, 위치는 초당 한 번 값을 방출하면, **타이머 값이 불필요하게 많이 결합**됩니다.
    
    ```kotlin
    val combinedFlow = locationFlow.combine(timerFlow) { location, time ->
        Pair(location, time)
    }
    ```
    
    - `combine`을 사용하면 타이머가 더 자주 갱신될 때마다 **새로운 위치 없이 타이머 값만 계속 결합**되는 상황이 발생합니다.

### 5. **실전 예시: 평균 속도 계산**

- 사용자가 특정 경로를 이동할 때, **위치 데이터**와 **타이머 데이터**를 결합하여 **평균 속도**를 계산하는 작업을 수행합니다. `zip`을 통해 타이머와 위치를 결합하여 정확한 시점에 따른 위치 변화량을 추적할 수 있습니다.

## Combining UI States

**`combine`** 연산자를 사용하여 **UI 상태를 결합**하는 방법에 대해 설명합니다. 예시로 **회원가입 화면**에서 **이메일**과 **비밀번호 입력 필드**의 유효성을 실시간으로 검증하는 과정을 다루고 있습니다. 또한 **`debounce`** 연산자와 같은 추가적인 최적화 기법도 설명합니다.

### 주요 내용:

1. **UI 상태 결합**:
    - **이메일**과 **비밀번호** 입력 필드의 유효성을 검사하는 **회원가입 화면**을 구성합니다.
    - **`combine`** 연산자를 사용하여 **이메일**과 **비밀번호 상태 Flow**를 결합하고, 두 입력 값이 모두 유효할 때만 **회원가입 버튼**을 활성화합니다.
2. **기존의 비효율적인 방법**:
    - 강의에서는 이메일과 비밀번호가 입력될 때마다 매번 **입력 유효성을 수동으로 검사**하고 업데이트하는 방법이 비효율적이라고 설명합니다. 이는 입력 필드가 업데이트될 때마다 검증을 직접 호출해야 하기 때문입니다.
    - 대신 `combine`을 사용하여 상태를 자동으로 추적하고 검증하는 방식을 제안합니다.
3. **`combine`을 사용한 개선된 방식**:
    - **`combine`** 연산자를 사용하여 **이메일 Flow**와 **비밀번호 Flow**를 결합하고, 입력 필드 중 하나라도 변경되면 자동으로 유효성 검사를 실행합니다.
    
    예시 코드:
    
    ```kotlin
    val canRegisterFlow = combine(emailStateFlow, passwordStateFlow) { email, password ->
        val isValidEmail = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val isValidPassword = password.length > 9 && password.any { !it.isLetterOrDigit() }
        isValidEmail && isValidPassword
    }
    
    ```
    
    - 위 코드는 **이메일과 비밀번호가 모두 유효**할 때 **회원가입 버튼을 활성화**합니다. 이를 통해 두 필드 중 하나라도 변경되면 유효성 검사가 자동으로 수행됩니다.
4. **버튼 활성화**:
    - 결합된 Flow 결과인 **`canRegisterFlow`** 값을 **버튼의 활성화 상태**로 연결합니다.
    - 처음 앱이 실행되었을 때는 **버튼이 비활성화** 상태로 시작하며, **유효한 이메일과 비밀번호**가 입력되면 **버튼이 활성화**됩니다.
5. **`debounce` 연산자의 사용**:
    - **`debounce`** 연산자는 **입력 필드가 너무 자주 변경되는 경우** 불필요한 유효성 검사를 방지하기 위해 사용됩니다. 예를 들어, 사용자가 빠르게 입력할 때 **500밀리초 동안 입력이 멈출 때까지 기다린 후** 유효성 검사를 실행합니다.
    
    예시 코드:
    
    ```kotlin
    
    class FormViewModel : ViewModel() {
    
        private val _email = MutableStateFlow("")
        val email = _email.asStateFlow()
    
        private val _password = MutableStateFlow("")
        val password = _password.asStateFlow()
    
        val canRegister = email
            .debounce(500L)
            .combine(
                password,
            ) { email, password ->
                val isValidEmail = PatternsCompat.EMAIL_ADDRESS.matcher(email).matches()
                val isValidPassword = password.any { !it.isLetterOrDigit() } &&
                        password.length > 9
    
                isValidPassword && isValidEmail
            }.stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                false
            )
    
        fun onEmailChange(email: String) {
            _email.value = email
        }
    
        fun onPasswordChange(password: String) {
            _password.value = password
        }
    }
    ```
    
    - `debounce`는 주로 **API 호출 최적화**나 **서버 검색 요청**에 사용되며, **사용자가 입력을 멈춘 후** 일정 시간 동안 기다렸다가 작업을 수행하는 방식입니다. 이 강의에서는 로컬 유효성 검증이므로 `debounce`는 필요하지 않지만, 원격 서버 요청을 처리할 때 유용하다고 언급됩니다.
6. **실시간 검증과 UX 개선**:
    - 사용자는 **유효한 이메일**과 **비밀번호**를 입력할 때마다 즉시 **버튼의 활성화 상태가 변경**되는 것을 볼 수 있습니다. 예를 들어, 이메일 형식이 맞지 않으면 버튼이 비활성화되며, 다시 유효한 이메일 형식을 입력하면 버튼이 활성화됩니다.

### 결론:

- **`combine`** 연산자를 사용하여 여러 **UI 상태를 결합**하고, 각각의 상태 변화에 따라 **자동으로 검증 로직을 실행**하는 방식으로 UX를 개선할 수 있습니다.
- 또한 **`debounce`** 연산자는 입력 필드에서 불필요하게 자주 발생하는 유효성 검사를 줄여 **API 요청 등의 성능을 최적화**하는 데 사용할 수 있습니다

## Listening to a WebSocket Stram In a Flow

**WebSocket**을 사용하여 **실시간 메시지 스트림**을 처리하고, 이를 **Kotlin Flow**와 결합하여 데이터를 처리하는 방법을 설명합니다. WebSocket은 클라이언트와 서버 간에 지속적인 연결을 유지하며 **양방향 통신**을 가능하게 합니다. 특히, `callbackFlow`를 사용해 서버로부터 실시간으로 데이터를 받아와 **Flow**로 처리하는 방식을 다룹니다.

### 주요 내용

1. **WebSocket 개요**:
    - WebSocket은 일반적인 HTTP 요청과 달리, **지속적인 연결**을 유지하면서 **양방향 통신**을 가능하게 합니다. 서버와 클라이언트는 언제든지 메시지를 주고받을 수 있습니다.
    - 이 강의에서는 **에코 서버**를 사용하여, 클라이언트가 서버에 보낸 메시지를 그대로 돌려주는 방식으로 WebSocket 통신을 구현합니다.
2. **Kotlin에서 WebSocket 연결**:
    - **Ktor** 라이브러리의 **WebSocket 플러그인**을 사용하여 WebSocket을 연결합니다. 이를 통해 서버와 연결을 맺고, **실시간으로 데이터를 주고받을 수 있는 세션**을 생성합니다.
    - WebSocket 연결 후, **서버에서 전송된 메시지**를 실시간으로 받기 위해 **callbackFlow**를 사용합니다.
    
    ```kotlin
    class WebSocketClient(
        private val httpClient: HttpClient
    ) {
        private var session: WebSocketSession? = null
    
        suspend fun sendMessage(text: String) {
            session?.send(text)
        }
    
        fun listenToSocket(url: String): Flow<String> {
            return callbackFlow {
                session = httpClient.webSocketSession(
                    urlString = url
                )
    
                session?.let { session ->
                    session
                        .incoming
                        .consumeAsFlow()
                        .filterIsInstance<Frame.Text>()
                        .collect {
                            send(it.readText())
                        }
                } ?: run {
                    session?.close()
                    session = null
                    close()
                }
    
                awaitClose {
                    launch(NonCancellable) {
                        session?.close()
                        session = null
                    }
                }
            }
        }
    }
    ```
    
    - `callbackFlow`를 사용하여 WebSocket에서 받은 메시지를 Flow로 변환하고, **실시간으로 수집**할 수 있습니다.
    - **`send()`** 함수를 통해 서버로부터 받은 메시지를 Flow에 전달하며, **WebSocket 세션이 종료되면** 자동으로 Flow도 종료됩니다.
3. **Flow를 사용한 메시지 처리**:
    - WebSocket에서 받은 메시지를 `runningFold`를 사용하여 **누적된 메시지 리스트**로 변환합니다. 이를 통해, 서버로부터 받은 모든 메시지를 **리스트로 관리**하고, **UI에서 표시**할 수 있습니다.
    
    ```kotlin
    data class WebSocketLog(
        val formattedTime: String,
        val log: String
    )
    
    class WebSocketViewModel: ViewModel() {
    
        private val client = WebSocketClient(HttpClientFactory.create())
    
        val receivedLogs = client
            .listenToSocket("wss://echo.websocket.org/")
            .runningFold(initial = emptyList<WebSocketLog>()) { logs, newLog ->
                val formattedTime = DateTimeFormatter
                    .ofPattern("dd-MM-yyyy, hh:mm:ss")
                    .format(LocalDateTime.now())
    
                logs + WebSocketLog(
                    formattedTime = formattedTime,
                    log = newLog
                )
            }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000L),
                emptyList()
            )
    
        fun sendMessage(text: String) {
            viewModelScope.launch {
                client.sendMessage(text)
            }
        }
    }
    ```
    
    - 이 코드는 **실시간 메시지**를 누적하여 **메시지 리스트**로 변환하고, 이를 **UI 상태로 관리**합니다.
    - `runningFold`를 사용하여 새로운 메시지가 올 때마다 **기존 메시지 리스트에 추가**됩니다.
4. **UI에서의 WebSocket 메시지 표시**:
    - WebSocket에서 받은 메시지들을 **Jetpack Compose**를 사용해 **UI에 표시**합니다. 메시지는 **LazyColumn**을 통해 리스트로 나열되며, 새로운 메시지가 도착할 때마다 **UI가 업데이트**됩니다.
    
    ```kotlin
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun WebSocketUi(
        viewModel: WebSocketViewModel = viewModel(),
        modifier: Modifier = Modifier
    ) {
        val receivedLogs by viewModel.receivedLogs.collectAsStateWithLifecycle()
    
        LazyColumn(
            modifier = modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            stickyHeader {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var text by remember {
                        mutableStateOf("")
                    }
                    TextField(
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {
                        viewModel.sendMessage(text)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send"
                        )
                    }
                }
            }
            items(receivedLogs) { log ->
                Text(text = "${log.formattedTime}: ${log.log}")
            }
        }
    }
    ```
    
    - `collectAsStateWithLifecycle()`을 사용해 **ViewModel의 Flow 상태**를 UI에서 **실시간으로 수집**하고, 화면에 메시지를 표시합니다.
5. **메시지 전송**:
    - 사용자가 **텍스트 필드**에 입력한 메시지를 WebSocket을 통해 서버에 전송하고, 그 응답을 받아서 다시 리스트에 추가하는 기능을 구현합니다.
    - 버튼을 클릭하면 사용자가 입력한 메시지가 WebSocket을 통해 서버로 전송되고, **서버로부터 응답**이 오면 그 메시지가 UI에 표시됩니다.
    
    ```kotlin
    fun sendMessage(message: String) {
        viewModelScope.launch {
            webSocketClient.sendMessage(message)
        }
    }
    ```
    

### 요약

- **WebSocket**을 사용해 **실시간 통신**을 구현하며, **Ktor WebSocket 플러그인**을 통해 서버와의 지속적인 연결을 유지할 수 있습니다.
- `callbackFlow`를 사용해 **WebSocket에서 받은 메시지를 Flow로 처리**하고, 이를 **Jetpack Compose** UI와 결합하여 **실시간으로 메시지를 표시**하는 방식입니다.
- `runningFold`를 사용하여 서버로부터 받은 메시지를 누적하고, **상태를 관리**하며, UI는 이를 실시간으로 반영합니다

## **Handling Flow Errors & Retrying Failed Flows**

**Kotlin Flow**에서 **오류 처리**를 다루며, 특히 **네트워크 연결** 실패와 같은 상황에서 **재시도(retry)** 및 **오류 처리** 방법을 설명합니다. 실전에서는 **WebSocket**과 같은 네트워크 연결이 항상 성공하지 않으며, **연결 실패 시 앱이 충돌하지 않도록** 대비책을 마련하는 것이 중요합니다.

### 주요 내용

1. **기존 WebSocket 예제의 문제점**:
    - 이전에 작성된 WebSocket 클라이언트는 정상적인 경로만 처리했습니다(즉, 연결이 항상 성공하는 시나리오만 고려).
    - 그러나 **현실에서는 네트워크 오류**가 발생할 수 있으며, 예를 들어 **인터넷 연결이 끊어지거나** 서버를 찾을 수 없는 경우 앱이 **충돌**할 수 있습니다.
2. **오류 처리 방법**:
    - WebSocket 연결 실패 시 `UnresolvedAddressException`과 같은 예외가 발생할 수 있습니다.
    - 이러한 예외를 처리하지 않으면 앱이 **충돌**합니다. 예를 들어, **비행기 모드**에서 앱을 다시 시작하면 연결이 실패하고 앱이 충돌할 수 있습니다.
    - **`catch` 연산자**를 사용하여 Flow 내에서 발생한 예외를 **포착**하고, 이를 처리하는 방법을 설명합니다.
    
    ```kotlin
    listenToSocket()
        .catch { cause ->
            if (cause is UnresolvedAddressException) {
                println("Oops, no Internet!")
            }
        }
        .collect { message ->
            println("Received: $message")
        }
    ```
    
    - 이 코드를 통해 **인터넷 연결이 없을 때** 발생하는 예외를 잡아내고, 사용자에게 "인터넷이 끊겼다"는 메시지를 출력할 수 있습니다.
3. **재시도 로직 추가**:
    - 네트워크 문제는 **일시적인 문제**일 수 있기 때문에, **연결 실패 시 재시도**하는 것이 유용할 수 있습니다.
    - **`retry` 연산자**를 사용하여 **실패한 Flow**를 다시 시도할 수 있으며, 예외가 발생할 때마다 재시도를 수행할 수 있습니다.
    
    ```kotlin
    listenToSocket()
        .retry { cause ->
            cause is UnresolvedAddressException
        }
        .catch { cause ->
            println("No Internet after retrying.")
        }
        .collect { message ->
            println("Received: $message")
        }
    ```
    
    - **`retry` 블록**에서 **예외를 검사**하고, 예외가 특정 조건을 만족할 때 재시도합니다. 이 예에서는 **인터넷 연결 오류**가 발생했을 때만 재시도합니다.
4. **재시도 횟수 제한**:
    - 무한정 재시도하는 것은 좋지 않기 때문에, **재시도 횟수를 제한**하는 방법을 설명합니다.
    - **`retryWhen`** 연산자를 사용하면 **재시도 횟수**를 제어할 수 있으며, **특정 횟수 이상 실패**하면 재시도를 중단할 수 있습니다.
    
    ```kotlin
    listenToSocket()
        .retryWhen { cause, attempt ->
            cause is UnresolvedAddressException && attempt < 3
        }
        .catch { cause ->
            println("No Internet after 3 retries.")
        }
        .collect { message ->
            println("Received: $message")
        }
    ```
    
    - 이 코드는 **세 번까지 재시도**하며, 세 번 실패한 후에는 재시도를 멈추고 오류를 처리합니다.
5. **지연된 재시도 (Delay between retries)**:
    - 재시도 간의 **지연 시간**을 설정할 수 있습니다. 예를 들어, 각 재시도 사이에 **5초 간격**을 두고 다시 시도할 수 있습니다.
    - *`delay`*를 이용해 재시도 간격을 설정하여, 서버에 과부하를 주지 않도록 할 수 있습니다.
    
    ```kotlin
    listenToSocket()
        .retryWhen { cause, attempt ->
            if (cause is UnresolvedAddressException && attempt < 3) {
                delay(5000)  // 5초 기다림
                true
            } else {
                false
            }
        }
        .catch { cause ->
            println("No Internet after retries.")
        }
        .collect { message ->
            println("Received: $message")
        }
    ```
    
6. **지수적 백오프 (Exponential Backoff)**:
    - **지수적 백오프**는 각 재시도마다 대기 시간을 **두 배로 늘리는 방법**입니다. 이는 연결 실패 시 너무 빈번하게 재시도하는 것을 방지하고, **네트워크 복구 가능성**을 고려한 전략입니다.
    
    ```kotlin
    retryWhen { cause, attempt ->
        if (cause is UnresolvedAddressException && attempt < 3) {
            val delayTime = (2.0.pow(attempt) * 1000).toLong()  // 2^attempt 초 만큼 대기
            delay(delayTime)
            true
        } else {
            false
        }
    }
    ```
    
    - 첫 번째 재시도는 1초 후, 두 번째는 2초 후, 세 번째는 4초 후에 재시도를 시도하며 **대기 시간이 점점 길어집니다**.

### 전체 코드

```kotlin
class WebSocketViewModel: ViewModel() {

    private val client = WebSocketClient(HttpClientFactory.create())

    val receivedLogs = client
        .listenToSocket("wss://echo.websocket.org/")
        .retryWhen { cause, attempt ->
            delay(2f.pow(attempt.toInt()).roundToInt() * 2000L)
            cause is UnresolvedAddressException && attempt < 4
        }
        .catch { cause ->
            when(cause) {
                is UnresolvedAddressException -> {
                    println("Oops, no internet!")
                }
            }
        }
        .runningFold(initial = emptyList<WebSocketLog>()) { logs, newLog ->
            val formattedTime = DateTimeFormatter
                .ofPattern("dd-MM-yyyy, hh:mm:ss")
                .format(LocalDateTime.now())

            logs + WebSocketLog(
                formattedTime = formattedTime,
                log = newLog
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000L),
            emptyList()
        )

    fun sendMessage(text: String) {
        viewModelScope.launch {
            client.sendMessage(text)
        }
    }
}
```

## **flatMapConcat / flatMapMerge / flatMapLatest**

이 강의에서는 **Kotlin Flow**에서 사용되는 세 가지 **`flatMap` 연산자**인 **`flatMapConcat`**, **`flatMapMerge`**, 그리고 `flatMapLatest`에 대해 설명하고, 각 연산자의 차이점과 그에 따른 사용 사례를 다룹니다. 이들은 모두 **Flow** 내에서 **한 Flow를 다른 Flow로 변환**할 때 사용되지만, 각 연산자의 동작 방식이 다릅니다.

### 1. **`flatMapConcat`**

- **순차적 처리**: `flatMapConcat`은 **순차적으로** 데이터를 처리합니다. 즉, 한 Flow에서 값을 방출할 때, 그 값이 변환된 Flow가 모두 처리될 때까지 기다린 후 다음 값을 처리합니다.
- **한 번에 하나의 Flow만 처리**: 이전 Flow의 모든 데이터 방출이 완료될 때까지 대기한 후에 다음 Flow의 처리를 시작합니다.

### 예시:

```kotlin
flowOf(1, 2, 3)
    .flatMapConcat { value ->
        flow {
            emit("$value-A")
            delay(100)
            emit("$value-B")
        }
    }
```

- 결과는 순차적으로: `1-A`, `1-B`, `2-A`, `2-B`, `3-A`, `3-B` 순서로 방출됩니다.
- **순차 처리**가 필요한 상황에 적합합니다.

### 2. **`flatMapMerge`**

- **병렬 처리**: `flatMapMerge`는 **동시에 여러 Flow를 처리**할 수 있습니다. 각각의 Flow는 **독립적인 코루틴**에서 실행되며, 여러 Flow가 병렬로 처리됩니다.
- **성능 향상**: Flow를 병렬로 처리하므로 **성능을 극대화**할 수 있지만, 순차적인 순서는 보장되지 않습니다.

### 예시:

```kotlin
flowOf(1, 2, 3)
    .flatMapMerge { value ->
        flow {
            emit("$value-A")
            delay(100)
            emit("$value-B")
        }
    }
```

- 결과는: `1-A`, `2-A`, `3-A`, `1-B`, `2-B`, `3-B`로 병렬로 방출됩니다.
- 병렬 처리가 필요할 때 적합하며, 여러 작업을 동시에 처리하고 싶을 때 사용됩니다.

### 3. **`flatMapLatest`**

- **최신 값만 처리**: `flatMapLatest`는 새로운 값이 들어오면 **이전 값에 대한 처리를 중단**하고, 최신 값에 대한 Flow만을 처리합니다.
- **실시간 데이터 처리**: 주로 **UI 상태**를 처리할 때 유용하며, **가장 최신 데이터**만을 사용하는 상황에서 적합합니다.

### 예시:

```kotlin
flowOf(1, 2, 3)
    .flatMapLatest { value ->
        flow {
            emit("$value-A")
            delay(100)
            emit("$value-B")
        }
    }
```

- 결과는: `1-A`, `2-A`, `3-A`, `3-B`로 방출됩니다. 여기서 **1-B**와 **2-B**는 중단되고 처리되지 않습니다.
- UI 상태에서 **가장 최신 상태만** 반영하고 싶을 때 적합합니다.

### 비교 요약

| 연산자 | 동작 방식 | 특징 | 사용 사례 |
| --- | --- | --- | --- |
| **`flatMapConcat`** | **순차적 처리** | 하나의 Flow가 완료되면 다음 Flow 처리 | 순차적인 데이터 처리 필요 시 사용 |
| **`flatMapMerge`** | **병렬 처리** | 여러 Flow를 동시에 처리하고 순서는 보장되지 않음 | 병렬 작업 처리나 성능 최적화가 필요할 때 |
| **`flatMapLatest`** | **최신 값만 처리** | 이전 값 처리 중단, 최신 값만 처리 | 실시간 데이터나 UI 상태 최신화가 필요할 때 |

### 사용 시 고려 사항

- **`flatMapConcat`**: **순차적으로 처리**해야 하는 상황에서 사용합니다. 예를 들어, 데이터의 순서가 중요할 때 유용합니다.
- **`flatMapMerge`**: **병렬로 처리**할 수 있는 상황에서, 성능을 극대화하고 싶을 때 사용합니다. 여러 네트워크 요청이나 병렬로 처리할 수 있는 작업에서 유용합니다.
- **`flatMapLatest`**: UI와 같이 **최신 상태**만을 필요로 하는 경우에 사용되며, 이전 작업이 완료되지 않아도 **최신 값을 빠르게 처리**할 수 있습니다.

## **Handling Backpressure**

**Kotlin Flow**에서 **Backpressure**를 처리하는 방법에 대해 설명하고, 다양한 연산자를 사용하여 **생산자(Producer)**가 **소비자(Collector)**보다 빠르게 데이터를 방출할 때 발생하는 문제를 해결하는 방법을 다룹니다. **Backpressure**는 생산자가 데이터를 너무 빠르게 방출하여 소비자가 이를 처리할 시간이 부족할 때 발생합니다. 이를 해결하기 위해 **Buffer**, **Conflate**, 그리고 **CollectLatest** 연산자들이 사용됩니다.

### 주요 내용

1. **Backpressure란?**
    - **Backpressure**는 생산자가 소비자보다 더 빠르게 데이터를 방출할 때 발생하는 문제입니다.
    - 소비자는 생산자가 방출한 데이터를 순차적으로 처리하지만, 만약 소비자가 데이터를 처리하는 시간이 오래 걸리면, 생산자는 대기하게 됩니다.
    - 예를 들어, 생산자가 500밀리초 간격으로 데이터를 방출하고 소비자가 1초에 걸쳐 데이터를 처리하는 상황에서는, 데이터가 점점 밀리게 됩니다.
2. **Buffer 연산자**:
    - **Buffer**는 생산자가 빠르게 데이터를 방출하더라도, 데이터를 **버퍼(메모리)**에 저장해 소비자가 천천히 처리할 수 있도록 도와줍니다.
    - 버퍼에 저장된 데이터를 차례로 처리하며, 소비자는 데이터를 처리하는 동안에도 생산자는 계속 데이터를 방출할 수 있습니다.
    
    ```kotlin
    flowOf(1, 2, 3)
        .buffer()
        .collect { value ->
            delay(1000)  // 데이터를 처리하는 데 시간이 걸림
            println("Processed $value")
        }
    ```
    
    - 하지만 버퍼의 크기가 너무 커지면 메모리 부족 문제가 발생할 수 있습니다. 따라서 **버퍼 크기를 설정**하여 메모리 오버플로를 방지해야 합니다.
3. **Conflate 연산자**:
    - **Conflate**는 소비자가 데이터를 처리하는 동안 **이전 값을 건너뛰고** 최신 값만 처리합니다.
    - 즉, 중간 값들은 건너뛰며, 소비자가 이전 데이터를 처리하고 있는 동안 최신 데이터를 받을 수 있습니다.
    
    ```kotlin
    flowOf(1, 2, 3)
        .conflate()
        .collect { value ->
            delay(1000)
            println("Processed $value")
        }
    ```
    
    - 이 방식은 **일부 데이터의 손실을 허용**할 수 있는 경우 유용합니다. 예를 들어, UI 상태 업데이트에서는 **최신 상태**만 중요하기 때문에, 중간 값을 건너뛰는 것이 가능합니다.
4. **CollectLatest 연산자**:
    - **CollectLatest**는 소비자가 현재 처리 중인 작업을 **취소**하고, 최신 데이터로 작업을 다시 시작합니다.
    - 즉, 새로운 데이터가 들어오면 **이전 작업을 중단**하고 최신 데이터를 처리하는 방식입니다.
    
    ```kotlin
    flowOf(1, 2, 3)
        .collectLatest { value ->
            delay(1000)
            println("Processed $value")
        }
    
    ```
    
    - **CollectLatest**는 **UI 업데이트**와 같은 상황에서 유용합니다. 예를 들어, UI가 자주 업데이트될 때, 이전 상태는 더 이상 중요하지 않기 때문에 최신 상태만 반영하면 됩니다.

### 연산자 비교

| 연산자 | 설명 | 사용 예시 |
| --- | --- | --- |
| **Buffer** | 데이터를 **메모리에 저장**하여 천천히 처리할 수 있도록 함 | 모든 데이터를 저장하고 처리해야 할 때 |
| **Conflate** | 이전 데이터를 **건너뛰고**, 최신 데이터만 처리 | 최신 상태만 중요한 경우 (예: UI 상태) |
| **CollectLatest** | 이전 작업을 **취소**하고, 최신 데이터를 즉시 처리 | 이전 데이터를 중단하고 최신 데이터만 처리 |

---

### 추가) Conflate와 CollectLatest는 그럼 같은 기능이 아닌가요?

**Conflate**와 **CollectLatest**는 **유사한 목적**을 가지고 있지만, **동작 방식**에 차이가 있습니다. 두 연산자 모두 **이전 데이터를 무시하고 최신 데이터만 처리**하는 방식으로 **Backpressure** 문제를 해결하려는 공통점을 갖고 있지만, **어떻게 처리하느냐**에 있어 차이가 있습니다.

### 1. **Conflate**

- **Conflate**는 **중간 데이터를 건너뛰고 최신 데이터만 처리**합니다.
- **생산자(Producer)** 가 값을 방출할 때, 소비자가 처리 중인 경우 **다음 값**을 받을 때까지 기다리지 않고, 중간 값을 건너뛰고 **마지막 값**만 처리합니다.
- **이전 값을 취소하지 않고**, 그 값이 완료될 때까지 처리한 후 **최신 값**을 수집합니다. 즉, 중간에 값을 버리되, **현재 처리 중인 작업은 중단되지 않습니다**.

### 동작 방식:

```kotlin
flowOf(1, 2, 3)
    .conflate()
    .collect { value ->
        delay(1000)
        println("Processed $value")
    }
```

- 이 코드는 생산자가 빠르게 값을 방출할 때, 중간 값은 건너뛰고 **가장 최신 값만 처리**합니다.
- **처리 결과**는 "Processed 1", "Processed 3"처럼 중간 값 **2**는 **건너뛰지만**, 이미 시작된 **1**의 처리는 중단되지 않고 완료됩니다.

### 2. **CollectLatest**

- **CollectLatest**는 **최신 데이터만 처리**하는 동시에, **이전 작업을 취소**하는 특징이 있습니다.
- **새로운 값**이 방출될 때마다, **현재 진행 중인 작업을 중단**하고 **가장 최신 값**만 처리합니다.
- 즉, 이전 데이터에 대한 작업을 **즉시 취소**하고, 새로운 데이터에 대한 작업을 **재시작**합니다.

### 동작 방식:

```kotlin
flowOf(1, 2, 3)
    .collectLatest { value ->
        delay(1000)
        println("Processed $value")
    }
```

- 이 코드는 생산자가 빠르게 값을 방출할 때, **이전 값의 처리 도중** 새로운 값이 방출되면 **이전 값의 작업을 중단**하고 **새로운 값에 대한 작업을 시작**합니다.
- **처리 결과**는 "Processed 3"만 출력됩니다. 왜냐하면 **1**과 **2**는 각각의 처리 중에 **3**이 방출되면서 **작업이 취소**되었기 때문입니다.

### 차이점 요약

| **특징** | **Conflate** | **CollectLatest** |
| --- | --- | --- |
| **이전 값 처리** | 이전 값을 **완료할 때까지 처리**, 중간 값을 건너뜀 | **이전 값을 처리 중단**, 최신 값만 처리 |
| **작업 중단** | **현재 작업은 중단되지 않음**, 중간 값은 무시 | **현재 작업을 즉시 취소**하고, 최신 값으로 재시작 |
| **사용 사례** | 중간 데이터 손실을 허용하되, 이미 시작된 작업은 중단하지 않아야 할 때 | **UI 상태**나 **네트워크 호출**처럼 **최신 데이터만 중요한 경우** |

### 결론

- **Conflate**는 중간 값을 무시하면서 **현재 처리 중인 작업을 완료**하지만, **CollectLatest**는 **진행 중인 작업을 취소**하고 **최신 값으로 재시작**합니다.
- **CollectLatest**는 최신 데이터가 중요한 상황, 예를 들어 **실시간 UI 상태 관리**에서 자주 사용되고, **Conflate**는 **이전 데이터의 처리가 반드시 완료되어야 하는 경우**에 사용됩니다.

## 연산자

### coercein

**Kotlin**에서 제공하는 함수로, 주어진 값이 특정 **범위** 안에 있도록 값을 제한하는 역할을 합니다. 값이 범위 밖에 있으면, 범위의 **최소값 또는 최대값**으로 값을 제한합니다.

즉, **최소값보다 작으면 최소값을 반환**하고, **최대값보다 크면 최대값을 반환**하며, **범위 내에 있으면 원래 값을 반환**합니다.

```kotlin
fun coerceIn(minimumValue: T, maximumValue: T): T
```

### `coerceIn` 메서드의 동작

- 주어진 값을 `minimumValue`와 **`maximumValue`** 사이의 값으로 제한합니다.
- 만약 값이 **minimumValue**보다 작으면 minimumValue를 반환하고, 값이 **maximumValue**보다 크면 maximumValue를 반환합니다.

### 사용 예시

1. **숫자 값 제한**:
    
    ```kotlin
    val value = 150
    val coercedValue = value.coerceIn(0, 100)  // 150은 100보다 크므로 100으로 제한됨
    println(coercedValue)  // 출력: 100
    ```
    
    - `value`가 150일 때, 최소값 0과 최대값 100 사이로 제한하므로 **100**을 반환합니다.
2. **범위 내의 값일 때**:
    
    ```kotlin
    val value = 50
    val coercedValue = value.coerceIn(0, 100)  // 50은 범위 내이므로 그대로 반환됨
    println(coercedValue)  // 출력: 50
    ```
    
    - `value`가 50일 때, 이 값은 0과 100 사이에 있으므로 **값이 그대로 유지**됩니다.
3. **진행률 계산**:
    
    ```kotlin
    val progress = 1.2f  // 진행률이 1.0을 넘는 경우
    val coercedProgress = progress.coerceIn(0f, 1f)  // 1.0으로 제한됨
    println(coercedProgress)  // 출력: 1.0
    ```
    
    - `progress`가 1.2일 때, 0과 1 사이로 제한하므로 **1.0**으로 반환됩니다.

### `coerceIn`의 활용

1. **UI에서 진행률이나 스크롤 값 제한**:
    - 진행률(Progress) 계산 시 **0.0**에서 **1.0** 사이로 값을 제한할 때 많이 사용됩니다.
2. **입력 값 검증**:
    - 숫자 입력에서 사용자가 지정한 범위를 넘지 않도록 **입력값을 검증**하고 제한할 수 있습니다.
3. **게임에서 좌표나 속도 제한**:
    - 게임이나 애니메이션에서 물체의 위치나 속도를 특정 범위 안에 제한할 때 사용할 수 있습니다.
        
        ---
        

### runningReduce

- `runningReduce`는 **Kotlin Flow**의 **중간 연산자(Intermediate Operator)** 중 하나로, Flow에서 방출된 값들을 **누적하여 처리**하고, 각 중간 단계의 누적 결과를 새로운 값으로 방출하는 연산자입니다. 일반적인 `reduce`와 달리, **모든 중간 누적 결과**를 방출하기 때문에 **실시간으로 누적 상태를 추적**할 수 있습니다.

### `runningReduce`의 동작 방식

- **누적 결과를 계산**하면서, **Flow**가 방출할 때마다 중간 결과를 **계속 방출**합니다.
- 초기값 없이 첫 번째 값부터 누적이 시작되며, 두 번째 값부터 누적된 값을 계산하여 방출합니다.

### `runningReduce`의 시그니처

```kotlin
fun <S, T : S> Flow<T>.runningReduce(
    operation: suspend (accumulator: S, value: T) -> S
): Flow<S>
```

- `accumulator`는 누적된 값이고, `value`는 Flow에서 방출된 현재 값입니다.
- 각 방출된 값에 대해 **누적(accumulation) 연산**을 수행하고 그 결과를 방출합니다.

### 사용 예시

1. **숫자 합계 계산**:
    
    ```kotlin
    val flow = flowOf(1, 2, 3, 4)
    val sumFlow = flow.runningReduce { accumulator, value ->
        accumulator + value
    }
    
    // 방출 결과: 1, 3, 6, 10 (누적된 중간 값 방출)
    sumFlow.collect { println(it) }
    ```
    
    - 첫 번째 값 **1**을 그대로 방출하고, 이후에 **1+2=3**, **1+2+3=6**, **1+2+3+4=10**을 방출합니다.
2. **실시간 진행 상태 관리**:
예를 들어, 타이머나 작업의 진행 상태를 추적할 때, 누적된 시간을 계속해서 추적할 수 있습니다.
    
    ```kotlin
    val elapsedTimeFlow = timerFlow.runningReduce { accumulator, elapsedTime ->
        accumulator + elapsedTime  // 누적된 시간을 계산
    }
    ```
    
    - 여기서 `elapsedTimeFlow`는 각 방출된 시간을 누적하여 **경과 시간**을 계속해서 방출합니다.

### `runningReduce` vs `reduce`

- `reduce`는 **최종 누적 값**만을 반환하고 중간 결과는 방출하지 않습니다.
    
    ```kotlin
    kotlin
    코드 복사
    val flow = flowOf(1, 2, 3, 4)
    val sum = flow.reduce { accumulator, value ->
        accumulator + value
    }
    // 방출 결과: 10 (최종 값만 반환)
    ```
    
- `runningReduce`는 중간에 누적된 값도 **모든 단계에서 방출**하므로, 실시간으로 누적 상태를 추적하는 작업에 유용합니다.

### 사용 사례

1. **실시간 데이터의 누적 상태를 추적**: 예를 들어, 사용자의 위치 데이터를 수집하면서 **누적된 이동 거리**를 계산하거나, **타이머에서 경과 시간**을 누적하여 UI에 표시할 수 있습니다.
2. **누적된 합계**나 **통계 정보**를 실시간으로 업데이트할 때 유용합니다. 예를 들어, 점진적인 데이터 처리를 통해 **누적 평균**을 계산할 수 있습니다.

### runningFold vs scan

- `runningFold`와 **`scan`** 연산자는 모두 Kotlin Flow에서 **중간 값을 누적**하면서 **각 단계의 결과를 방출**하는 연산자입니다. `runningReduce`와 유사하지만, 두 연산자는 초기값을 제공하고 **누적 과정**에서 좀 더 다양한 사용 사례에 활용됩니다.

### 1. **`runningFold`**

- `runningFold`는 **누적 연산을 수행**하면서, Flow에서 방출된 각 값에 대해 **누적 결과를 중간마다 방출**하는 연산자입니다. **초기값**을 설정할 수 있다는 점에서 `runningReduce`와 차이가 있습니다.

### 사용 예시

```kotlin
val flow = flowOf(1, 2, 3, 4)
val foldedFlow = flow.runningFold(0) { accumulator, value ->
    accumulator + value
}

// 방출된 값: 0, 1, 3, 6, 10 (초기값 포함)
foldedFlow.collect { println(it) }
```

- 초기값 **0**을 설정하고, 그 값부터 시작하여 누적된 합계를 방출합니다.
    - **0** → **0 + 1 = 1** → **1 + 2 = 3** → **3 + 3 = 6** → **6 + 4 = 10**

### 동작 방식

- **초기값**을 설정하고, 그 값부터 시작하여 Flow에서 방출된 값을 누적하면서 **각 단계에서 누적된 중간 결과를 방출**합니다.
- `runningFold`는 `reduce`와 달리 **초기값을 지정할 수 있으며**, 결과를 **각 단계마다 방출**합니다.

### 사용 사례

- 누적 결과를 실시간으로 추적하거나, **초기값**이 중요한 계산에서 사용할 수 있습니다. 예를 들어, 타이머를 시작할 때 누적된 시간에 10초를 추가해야 할 경우 초기값으로 10을 설정할 수 있습니다.

---

### 2. **`scan`**

- `scan`은 `runningFold`와 거의 동일한 동작을 하지만, 이는 **Reactor**나 **RxJava**와 같은 **반응형 프로그래밍** 라이브러리에서 익숙한 이름입니다. **초기값**을 받아서 누적 연산을 수행하며, **각 중간 단계에서 누적된 값**을 방출합니다.

### 사용 예시

```kotlin
val flow = flowOf(1, 2, 3, 4)
val scannedFlow = flow.scan(0) { accumulator, value ->
    accumulator + value
}

// 방출된 값: 0, 1, 3, 6, 10 (초기값 포함)
scannedFlow.collect { println(it) }
```

- `scan`은 `runningFold`와 동일한 방식으로 동작하며, **초기값**부터 시작해서 각 방출된 값을 누적하고 중간마다 그 값을 방출합니다.

### `scan`의 동작

- **초기값**을 설정하고 Flow의 값들을 누적하면서 **중간 결과를 방출**합니다. `runningFold`와 기능적으로 동일하지만, `scan`은 **리액티브 프로그래밍**에서 사용되는 연산자 이름입니다.

---

### 3. **`runningFold` vs `scan`**

- **동작 방식**: 두 연산자는 **초기값**부터 시작해서 Flow에서 방출된 값을 누적하여 **중간 결과를 방출**하는 점에서 동일합니다.
- **차이점**: Kotlin에서는 `runningFold`가 더 일반적으로 사용되며, `scan`은 다른 리액티브 프로그래밍 라이브러리에서 자주 사용하는 이름입니다.
- **실제 사용**: Kotlin Flow에서는 `runningFold`를 더 자주 사용하지만, `scan`도 완전히 동일한 동작을 제공하며 **리액티브 프로그래밍**을 알고 있다면 자연스럽게 이해할 수 있습니다.

---

### 요약

| 연산자 | 특징 | 사용 예시 |
| --- | --- | --- |
| **`runningFold`** | 초기값을 받아서 각 단계마다 누적된 중간 결과를 방출하는 연산자. 초기값을 지정할 수 있음. | 초기값 0으로 누적 합계 계산: 0, 1, 3, 6, 10 |
| **`scan`** | `runningFold`와 동일하게 동작하며, 리액티브 프로그래밍에서 익숙한 이름. | 초기값을 받아 누적된 중간 값을 방출. (기능적으로 동일) |

두 연산자는 모두 **중간 값을 누적**하여 계산하며, **초기값**을 포함하여 실시간으로 누적된 중간 값을 계산하고 방출해야 하는 작업에서 유용하게 사용할 수 있습니다.

</div>
</details>
