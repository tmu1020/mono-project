# mono-back

Quarkus を使用したユーザー管理 REST API です。  
PostgreSQL を使用してユーザー情報を管理します。

---

## 技術スタック

- Java 21
- Quarkus
- Hibernate ORM (Panache)
- Flyway
- PostgreSQL
- Docker
- Bean Validation

---

## アーキテクチャ

本プロジェクトでは以下のレイヤー構成を採用しています。

```
Resource (REST API)
↓
Service (Business Logic)
↓
Entity / Repository (DB Access)
```

### 設計方針

- EntityとDTOを分離
- Service層でトランザクション管理
- FlywayでDBマイグレーション管理
- ExceptionMapperによるエラーハンドリング統一

---

## API一覧

|Method|Endpoint|説明|
|---|---|---|
GET|/users|ユーザー一覧|
GET|/users/{id}|ユーザー取得|
POST|/users|ユーザー作成|
PUT|/users/{id}|ユーザー更新|
DELETE|/users/{id}|ユーザー削除|

---

## 検索

以下のクエリパラメータで検索可能です。

|パラメータ|説明|
|---|---|
userId|ユーザーID部分一致検索|
userName|ユーザー名部分一致検索|

例

```
GET /users?page=0&size=5&userId=u001
```

---

## ソート

ソートは `sort` パラメータで指定します。

```
sort=<field>,<order>
```

例

```
GET /users?page=0&size=5&sort=userId,asc
```

|order|説明|
|---|---|
asc|昇順|
desc|降順|

---

## レスポンス例

```
GET /users?page=0&size=5
```

```json
{
  "total": 10,
  "page": 0,
  "size": 5,
  "data": [
    {
      "id": 1,
      "userId": "u001",
      "userName": "山田太郎"
    }
  ]
}
```

## エラーハンドリング

|HTTP|Exception|
|---|---|
400|ValidationException|
404|NotFoundException|
409|ConflictException|

例

```json
    {
      "code": "NOT_FOUND",
      "message": "ユーザーが見つかりませんでした"
    }
```

---

## セットアップ

必要ソフト

- Java 21
- Maven
- Docker

---

## 起動手順

### 1. 前提

- Dockerが起動していること
- Java 21 がインストールされていること

### 2. DB、Backend起動

```bash
cd mono-back
docker compose up -d
mvn quarkus:dev
```

PostgreSQL 接続情報

```
host : localhost
port : 5432
db   : mono
user : mono
pass : mono
```

---

### 2. 動作確認

- API
```
http://localhost:8080
```

- Swagger UI
```
http://localhost:8080/q/swagger-ui
```

---

## DBマイグレーション

Flyway を使用しています。

マイグレーションファイル

```
    src/main/resources/db/migration
```

例

```
    V1__create_users.sql
```

アプリ起動時に自動適用されます。

---

## Entity

users テーブル

|column|type|
|---|---|
id|SERIAL|
user_id|VARCHAR(50)|
user_name|VARCHAR(100)|
created_at|TIMESTAMP|
updated_at|TIMESTAMP|

---

## 今後の拡張案

- APIテスト追加
- JWT認証
- 論理削除
- CI/CD