## Как писать SOLIDно

![](./single-responsibility.png)

<br>&nbsp;&nbsp;&nbsp;&nbsp;В предыдущей статье [](LINK) я описал примерный путь и развитие событий для средне-статистического проекта
по качеству кода. Здесь хочу попробовать привести пример, чтобы ответить на вопрос зачем и как все же писать чище.

В классическом Java-проекте есть классы-сервисы, которые написаны примерно таким образом, но имеют в среднем больше строк.
```
@RequiredArgsConstructor
public class AccountService {

    private final CardService cardService; //class
    private final AccountRepository accountRepository;
    private final AccountProperties accountProperties;

    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        Account account = accountRepository.find(accountRequest.getId());
        Card card = cardService.getCard(cardId);
        account.setCard(card);
        return doAccountMagic(account);
    }

    /** some logic **/
    private Account doAccountMagic(Account account) {
        Amount specificAmount = account.getAmount();
        specificAmount.setValue(
                specificAmount.getValue() * accountProperties.getMagicBusinessValue()
        );
        account.setAmount(specificAmount);
        return account;
    }

    public Account getAccount(AccountRequest accountRequest) {
        ...
    }
}
``` 

Обращу внимание, что `CardService` и `AccountService` являются классами, без выделенного интерфейса.
Разработчики смержили код, ведь необходимости в интерфейсе явной не было. Мы догадываемся, что это классы какого-то REST API
сервиса, но также это могут быть классы библиотеки, немного в другом виде или классы в монолитном модуле.
В будущем эти классы могут постигнуть различные изменения, но все они потребуют больше изменений, нежели, если бы был выделен интерфейс.
- Для любого изменения, связанного с `CardService`
  - `AccountService` в библиотеке - придется править библиотеку или писать адаптер.
  Если бы был выделен интерфейс CardService - можно было бы изменить реализацию, передав в конструктор.
  - `AccountService` в текущем проекте. В добавок придется изменить и `AccountService`, соответственно его тесты
- С интерфейсом API класса выглядит нагляднее. Методы в классы со временем могут быть намешены (private до public), но
при наличии интерфейса API выделено `@Override` аннотациями. Более того, сразу захочется убрать private методы вниз, как неконсистентные:
```
    @Override
    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        ...
    }

    /** some logic **/
    private Account doAccountMagic(Account account) {
        ...
    }
    
    @Override
    public Account getAccount(AccountRequest accountRequest) {
        ...
    }
```
- С созданием интерфейса разработчик описывает основные свойства и может абстрактно представить ожидания от будущих реализаций.
Смотря на интерфейс, внимание сосредоточено на то, **как** могут быть использованы реализации в будущем ![](./interface.PNG)
- В юнит-тестах тестируется как реализация выполняет логику для интерфейса. При написании тестов нет сомнений, что тестируем
и не приходят в голову такие вещи, как _a как тестировать private методы?_, потому что всегда **тестируется API** ![](./Test.PNG)
- Подавляющее большинство паттернов программирования завязано на интерфейсы и абстрактные классы. Если Вы просто выделите интерфейс, кому-то
(вам) будет удобнее рефакторить это в будущем и, возможно, подводить под определенный паттерн программирования.
Например, вы хотите сделать обертку вокруг `CardService`, который имеет какое-то поле. Класс-обертка в этом случае потребует реализации конструктора оборачиваемого сервиса,
что нам не нужно.
```
public class CardService {
    private final CardProperties cardProperties;
    
    public Card getCard(Long cardId) {
        ...
    }
}
```
![](./wrapper%20without%20interface%20constructor.PNG)

<br>&nbsp;&nbsp;&nbsp;&nbsp;С практикой, каждый разработчик приходит к этим моментам, если задавать себе вопросы про потенциальное переиспользование своего кода и его поддержку.
Сейчас же мы пришли к **принципу Барбары Лисков (The Liskov Substitution Principle)**, буква «L» в аббревиатуре SOLID, и требовалось лишь получить опыт о плюсах интерфейса.
Говоря о практике, IntelliJ Idea выделяет интерфейс за несколько кликов, но конечно рекомендуется начинать с интерфейсов, чтобы стремиться к чистой сигнатуре метода.
---
<br>&nbsp;&nbsp;&nbsp;&nbsp;Рассмотрим еще спорный момент в примере выше. Мы видимо, что классы-сервис меняет значение _amount_, а именно его _value_.
На практике также встречается, что при преобразовании данных от одного слоя к другому данные меняются, но всегда это делается по разному, и люди не грешает
добавить конвертацию в сервисе, это всего-то одно, почему бы и нет.
<br>&nbsp;&nbsp;&nbsp;&nbsp;Стоит задавать себе вопрос: _а могут ли другие поля измениться в будущем, одно ведь мы уже преобразуем?_
Если ответственность разработчика пересилит, то он ответит себе **да** и подумает, как сделать, чтобы было комфортнее в будущем работать с этим кодом, а именно хотелось бы тестировать
это отдельно от остальной логики.
<br>&nbsp;&nbsp;&nbsp;&nbsp;В данном случае стоит выделить отдельную модель для слоя сервиса и отдельную для repository.
Пакеты классов будут выглядить примерно таким образом:
```
package ru.example.repository.domain;

public class AccountEntity {
...
}
```

```
package ru.example.service.domain;

public class Account {
...
}
```
```
public class AccountService {

    private final CardService cardService;
    private final AccountRepository accountRepository;
    private final AmountConverter amountConverter;

    public Account getAccount(AccountRequest accountRequest, Long cardId) {
        AccountEntity account = accountRepository.find(accountRequest.getId());
        Card card = cardService.getCard(cardId);
        account.setCard(card);
        return amountConverter.apply(account);
    }
...
```
Не забывая, про выделение интерфейса, можно взять интерфейс `java.util.function.Function` из Java 8 SDK, который
позволит быстро адаптироваться в будущем для конвертации множества объектов `AccountEntity`, путем применение этой функци через `java.util.stream.Stream #map`
```
public class AccountConverter implements Function<AccountEntity, Account> {
    
    private final AccountProperties accountProperties;
    
    @Override
    public Account apply(AccountEntity accountEntity) {
        AmountEntity specificAmount = accountEntity.getAmount();
        specificAmount.setValue(
                specificAmount.getValue() * accountProperties.getMagicBusinessValue()
        );
        account.setAmount(specificAmount);
        return account;
    }
}
```
Класс `AccountConverter` легко переиспользовать и протестировать, где тесты помогут дать представление о данных входящего и выходего объектов,
соответственно можно быстро понять, какое поле `AccountEntity` превращается в какое у `Account`.
<br>&nbsp;&nbsp;&nbsp;&nbsp;С данным рефакторингом мы избавили класс `AccountService` от действительно лишней ответственности в конвертации данных.
Таким образом, мы пришли к букве "S" в аббривиатуре SOLID - принцип единственной ответственности (Single Responsibility). Опять же, мы открыли для себя
известный принцип, задавая себе вопросы, как сделать код лучше.
 <br>&nbsp;&nbsp;&nbsp;&nbsp;Про данный принцип стоит заметить, что даже огромный класс с множеством строк можно назвать классом с единственной ответственностью,
 особенно, если он имеет простой интерфейс, например, просто возвращает `Account`. В классических примерах принято считать, что если интерфейс работает
 с разными типами сущностей, например, account и email, то это избыточность и его реализации нарушат принципы единственной ответственности.
 Чтобы реализации даже чистого интерфейса все же не разростались, стоит задавать себе вопросы и проводить декомпозицию составных частей класса.
 //todo здесь рассказываю, рекомендацию, что если создается новый объект, который не выходит из public метода, то протестировать будет сложнее и требуется декомпозиция.