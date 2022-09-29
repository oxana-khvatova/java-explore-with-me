# Explore With Me

Explore With Me — это афиша, где можно предложить какое-либо событие от 
выставки до похода в кино и набрать компанию для участия в нём.
В приложении реализован сбор статистики по просмотрам события, чтобы можно
было выбрать самые популярные.


# Features

* События, которые можно создавать, изменять, удалять и запрашивать согласно критериям пользователя (дате, категории, стоимости и т.д.).
* Пользователи разделены на администраторов и обычных, с разными уровнями доступа.
* Можно создавать запросы на участие в событиях и ждать их одобрения.
* Категории событий позволяют фомировать подборку событий нужного типа.
* Подборки событий — это готовые подборки основанные на предпочтениях пользователя.
* Поддерживаются комментарии к событиям и их модерация.
* Собирается статистика по просмотрам событий.

# Архитектура

Приложение состоит из 2 сервисов:

1. основной сервис (Размещение событий, запросов, подборок событий и пр.)
2. сервис сбора статистики по просмотрам

# Инструкция по запуску 

```Bash
mvn install
docker-compose up
```

# API

- [API основного сервиса](https://github.com/oxana-khvatova/java-explore-with-me/blob/main/ewm-main-service-spec.json)
- [API stats-сервиса](https://github.com/oxana-khvatova/java-explore-with-me/blob/main/ewm-stats-service-spec.json)







