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

С практикой, каждый разработчик приходит к этим моментам, если задавать себе вопросы про потенциальное переиспользование своего кода и его поддержку.
Сейчас же мы пришли к **принципу Барбары Лисков (The Liskov Substitution Principle)**, буква «L» в аббревиатуре SOLID, и требовалось лишь получить опыт о плюсах интерфейса.
Говоря о практике, IntelliJ Idea выделяет интерфейс за несколько кликов, но конечно рекомендуется начинать с интерфейсов, чтобы стремиться к чистой сигнатуре метода.