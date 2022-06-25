Порядок элементов в запросе является произвольным (не важно, используем коллекцию)

Гарантируется:
- во входных данных нет циклических зависимостей
- поле updateDate монотонно возрастает
- при проверке передаваемое время кратно секундам (надо использовать формат без миллисекунд)


### JSON
- Импорт
- Парсинг
- Batch save to DB 

### Валидация
1. id
- [service] - в одном запросе не может быть двух элементов с одинаковым id
- [given/non generated, skipped if first option is met] - uuid товара или категории является уникальным среди товаров и категорий

2. type
- [service] - родителем товара или категории может быть только категория
- [service] - Изменение типа элемента с товара на категорию или с категории на товар не допускается

3. price
- [hibernate] - у категорий поле price должно содержать null
- [hibernate] - цена товара не может быть null и должна быть больше либо равна нулю

4. other
- [hibernate] - название элемента не может быть null
- [hibernate] - дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400 

6. update rules
- [hibernate - use required and default where null is allowed] - при обновлении товара/категории обновленными считаются все их параметры
- [custom repository.save()] - при обновлении параметров элемента обязательно обновляется поле date в соответствии с временем обновления

7. additional - parentId
- [service] - сущность с id == parentId существует (в json или в базе) 
- принадлежность к категории определяется полем parentId
- товар или категория могут не иметь родителя

8. updateDate
- [hibernate] дата должна обрабатываться согласно ISO 8601 (такой придерживается OpenAPI). Если дата не удовлетворяет данному формату, необходимо отвечать 400.


### Сохранение в БД
1. save()
- [save()] - Импортирует новые товары и/или категории
- [save()] - Товары/категории импортированные повторно обновляют текущие