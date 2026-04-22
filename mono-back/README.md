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

## 起動手順
```bash
# プロジェクトルート(mono-project/)で実行
docker compose up --build
```

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
userId|ユーザーID検索(部分一致)|
userName|ユーザー名検索(部分一致)|

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

利用可能なfield:
- userId
- userName

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

|フィールド|説明|
|---|---|
total|総件数|
page|現在ページ|
size|ページサイズ|
data|ユーザー一覧|

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

|column|type|constraints|説明|
|---|---|---|---|
|id|SERIAL|PRIMARY KEY|内部ID|
|user_id|VARCHAR(50)|NOT NULL, UNIQUE|ユーザーID（業務キー）|
|user_name|VARCHAR(100)|NOT NULL|ユーザー名|
|created_at|TIMESTAMP|NOT NULL|作成日時|
|updated_at|TIMESTAMP|NOT NULL|更新日時|

### 補足
- user_id は業務上の一意キーとして扱います
- created_at / updated_at はアプリケーション側で自動設定されます

---

## 今後の拡張案

- APIテスト追加
- JWT認証
- 論理削除
- CI/CD

## 補足
- 初期データはマイグレーションによって追加されます