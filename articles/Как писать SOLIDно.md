## Как писать SOLIDно

![](./single-responsibility.png)

<br>&nbsp;&nbsp;&nbsp;&nbsp;В предыдущей статье [](LINK) я описал примерный путь и развитие событий для среднестатистического проекта
по качеству кода. Здесь хочу привести пример, чтобы ответить на вопрос зачем и как писать чище.

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
<br>В будущем эти классы могут постигнуть различные изменения, но все они потребуют больше изменений (изменения, изменений - лучше что-то вроде больше усилий на доработку), нежели, если бы был выделен интерфейс. Почему?
Для любого изменения, связанного с `CardService`
  -  если `AccountService` в библиотеке - придется править библиотеку или писать адаптер.
  Если бы был выделен интерфейс у `CardService` - можно было бы только передать в конструктор `AccountService` новую реализацию `CardService`.
  - если `AccountService` в текущем проекте - придется изменить и `AccountService`, соответственно его тесты
??Почему в случае с библиотекой AccountService менять не надо?
Отмечу еще, что:
- С интерфейсом API класс выглядит нагляднее. Со временем в классе может вырасти количество методов, они перемешаются (private до public), но
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
- С созданием интерфейса разработчик описывает основные свойства и может абстрактно представить ожидания от будущих реализаций (может ли??? может к этому надо стремиться?).
При этом, смотря на интерфейс, сосредотачиваешься на том, **как** могут быть использованы его реализации в будущем
?почему тут картинка, а не код?
<br>![](./interface.PNG)
- юнит-тесты проверяют как конкретная реализация выполняет логику для интерфейса (почему она это делает для интерфейса?). При написании тестов понятно, что тестируем, в голову не приходят такие вещи, как _a как тестировать private методы?_, потому что всегда **тестируется API** 
?так приватные методы все-таки тестировать или нет? а то в голову пришел вопрос как тестировать, а ответ на него - тестируй апи...
<br>![](./Test.PNG)
- Подавляющее большинство паттернов проектирования завязано на интерфейсы и абстрактные классы. Если Вы просто выделите интерфейс, кому-то
(вам) будет удобнее рефакторить код в будущем и, возможно, приводить к определенному паттерну проектирования.
Например, вы хотите сделать обертку вокруг `CardService`, которая имеет какое-то поле. Класс-обертка в этом случае потребует реализации конструктора оборачиваемого сервиса (5 раз перечитывала предложение, только после поняла),
что нам не нужно.
?А с интерфейсом-то как будет красиво выглядеть? надо показать
? зачем нам код ниже тут? было бы неплохо это в самом начале иметь
```
public class CardService {
    private final CardProperties cardProperties;
    
    public Card getCard(Long cardId) {
        ...
    }
}
```
![](./wrapper%20without%20interface%20constructor.PNG)

<br>С практикой, каждый разработчик осознает эти пункты, если постоянно задает себе вопросы про потенциальное переиспользование кода и его поддержку.
И так мы объяснили **принцип Барбары Лисков (The Liskov Substitution Principle)** - буква «L» в аббревиатуре SOLID, а требовалось лишь получить понимание плюсов выделения интерфейсов. 
? Все равно непонятно в чем смысл принципа, давай его здесь пропишем
<br>Перейдем к практике - IntelliJ Idea выделяет интерфейс за несколько кликов (?добавить описание как это делать), но конечно лучше начинайте писать с интерфейсов, чтобы стремиться к чистой сигнатуре метода.

---

<br>&nbsp;&nbsp;&nbsp;&nbsp;Рассмотрим еще один спорный момент в примере выше. Мы видим, что класс-сервис (какой? я уже забыла что там за класс был) меняет значение _amount_, а именно его _value_.
На практике также встречается, что при преобразовании данных от одного слоя к другому данные меняются, но всегда это делается по-разному, и люди не грешат
добавить конвертацию прямо в сервисе, это всего-то одно, почему бы и нет (??? что это значит вообще??? заменить это всего-то..).
<br>&nbsp;&nbsp;&nbsp;&nbsp;Стоит задать себе вопрос: _может и другие поля будут изменяться в будущем, одно мы уже конвертируем?_
Если ответственность разработчика пересилит (Чего пересилит? ты просто предлагал задавать себе вопрос, надо переформулировать!), то он ответит себе **да** и подумает, как сделать, чтобы было комфортнее в будущем работать с этим кодом, тестировать
конвертацию отдельно от остальной логики.
<br>&nbsp;&nbsp;&nbsp;&nbsp;В данном случае стоит выделить отдельную модель для слоя сервиса и отдельную для repository (история же была про конвертацию? почему репозиторий а не конвертер?).
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
Если не забывать про плюсы выделения интерфейса, описанные выше, можно понять, что здесь также будет удобно заимплементить интерфейс `java.util.function.Function` из Java 8 SDK, который
позволит быстро адаптироваться в будущем для конвертации множества объектов `AccountEntity`, путем применение этой функции через `java.util.stream.Stream #map`
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
Полученный класс `AccountConverter` легко переиспользовать и протестировать, где тесты помогут дать представление о данных входящего и выходего объектов,
соответственно можно быстро понять, какое поле `AccountEntity` превращается в какое у `Account`.
<br>&nbsp;&nbsp;&nbsp;&nbsp;С данным рефакторингом мы избавили класс `AccountService` от действительно лишней ответственности в конвертации данных.
Таким образом, мы пришли к букве "S" в аббривиатуре SOLID - принцип единственной ответственности (Single Responsibility). Опять же, мы открыли для себя
известный принцип, задавая себе вопросы, как сделать код лучше.
<br>&nbsp;&nbsp;&nbsp;&nbsp;Про данный принцип стоит заметить, что _единственная причина для изменений_ достаточно неопределенное определение. Таким образом
разработчики зачастую считают, что, если класс имеет простой интерфейс, например, возвращает `Account`, принимая один объект, то он имеет единственную ответственность.
На практике в вашем классе может происхоить преобразование данных в том числе или крайне сложный способ получения данных требует множества логики и строк кода.
Классически принято считать, что если интерфейс работает с разными типами сущностей, например, account и email, то это избыточность и его реализации нарушат принципы единственной ответственности.
Для того, чтобы реализации даже чистого интерфейса все же не разростались, стоит оспаривать свой написанный код и проводить декомпозицию составных частей класса.

<br>&nbsp;&nbsp;&nbsp;&nbsp;Методов проверки своего кода на качество существует много, но хочу предложить один существующий неявно,
но помогающий на code review в том числе.
Предположим, реализация интерфейса CardService будет иметь следующий вид:
```java
@RequiredArgsConstructor
public class DefaultCardService implements CardService {
    private final CardStorageService cardStorageService;

    @Override
    public Card getCard(Long cardId) {
        CardSearchRequest cardSearchRequest = createCardRequest(cardId);
        return cardStorageService.findCard(cardSearchRequest);
    }

    private CardSearchRequest createCardRequest(Long cardId) {
        CardSearchRequest cardSearchRequest = new CardSearchRequest();
        cardSearchRequest.setId(cardId * 100);
        return cardSearchRequest;
    }
}
```
В этом примере хочу обратить внимание на то, что внутри метода тестируемого создается объект, который не возвращается public методом класса.
Классический юнит-тест для этого класса будет написан так:
```
...
Long id = 200L;
Card expected = mock(Card.class);
when(cardStorageService.findCard(any(CardSearchRequest.class))).thenReturn(expected);

Card actual = cardService.getCard(id);

assertEquals(expected, actual);
```
<br>&nbsp;&nbsp;&nbsp;&nbsp;Минус данного теста, что мы ожидаем **любой** объект `CardSearchRequest`, переданным в метод нашей заглушки `CardStorageService`.
Но, например, если будут вызовы в цикле внутри метода `#getCard`, то уже наш стаб не подойдет - он даст одинаковое поведение для каждой итерации цикла.
Кроме циклов есть и другие случае, когда заглушка подводит.

<br>&nbsp;&nbsp;&nbsp;&nbsp;Чтобы имитировать конкретное поведение вида `when(cardStorageService.findCard(eq(cardSearchRequest)))` необходимо
сделать заглушку-имитацию созданного внутри объекта `CardSearchRequest`.
На практике создать такой stub может быть крайне сложно или даже невозможно (мнвыфвожество полей, зависимые сервисы).
Более того, делать это будет неправильно, так как в этом случае мы пишем заглушку, потому что мы знаем, как **сейчас работает код**.
В случае изменений придется долго править тест, чтобы подгонять поведение кода внутри тестируемого метода.
<br>&nbsp;&nbsp;&nbsp;&nbsp;Правильнее было бы, чтобы объект `CardSearchRequest` возвращался другим объектом (builder, provider),
в это случае мы могли бы легко имитировать это поведение, и код стал бы гибче для изменений. Стоит помнить,
что **создание объекта**, это тоже ответственность, которая должна быть протестирована **отдельно**.
<br>&nbsp;&nbsp;&nbsp;&nbsp;В данной статье я рассмотрел несколько примеров, которые покрывают несколько классических
моментов, которые можно диагностировать на code review, безусловно их множество, но предложу вопросы, которые стоит предъявлять качеству своего кода,
чтобы найти лучшие решения:
- Удобно ли будет написать классический юнит-тест на этот класс?
- Есть ли паралели с другим кодом, который я писал, можно ли выделить общее?
- Будет ли мне понятно, как работают реализации данного интерфейса через длительный промежуток времени?
- Сможет ли человек, не особо погружаясь в бизнес-логику понять, что делают данные классы?
