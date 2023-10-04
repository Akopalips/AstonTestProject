Design and implement a RESTful API,

backing service and data model to create bank accounts and transfer money between them.
Interaction with API will be using HTTP requests.

• Accounts are created by supplying a name and four-digit PIN code. Account number is automatically created.
  дто аккаунта - имя и код 4 чара, номер акка(id) автоген
• Once account is created one can Deposit, Withdraw or Transfer money between accounts.
  после создание акка можно положить/снять/перевести между
• Any operation which deducts funds from the account needs to include the correct PIN code.
  действия списывания средств требуют проверочный код
• A specific call will fetch all the accounts: the name and their current balance.
  эндпоинт getAll
• APIs will use JSON payloads when applicable.
  dto и ответы в json
• Use in-memory database as a backing store.
  h2

!скачать спринг
!написать контроллер
  !endpoints:
    !создание акка
    !положить на счет
    !снять со счёта
    !перевести на другой счет
    !вывести всех
  !валидация
!бд
  !поднять бд
  !репозиторий для сервиса
  !миграция в бд
!сервис
  !маппер
  !написать сервис
    !создание акка
    !положить на счет
    !снять со счёта
    !перевести на другой счет
    !вывести всех
  !дебаг..
!проверить mvc
!dockerfile
!тесты
  !контроллер
    !создание акка
    !положить на счет
    !снять со счёта
    !перевести на другой счет
    !вывести всех
  !сервис
    !создание акка
    !положить на счет
    !снять со счёта
    !перевести на другой счет
    !вывести всех
а теперь нужно вспомнить про дробную часть..
и в пинкод сейчас можно ввести 4 пробела
пагинация
оптимизация
документация сервиса
документация свагера в контроллер