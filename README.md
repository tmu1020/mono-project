# mono-project

SvelteKit（Frontend） + Quarkus（Backend） + PostgreSQL（DB）で構成された  
ユーザー管理Webアプリケーションです。

Dockerを用いてフロント・バックエンド・DBを統合し、  
ローカル環境で一貫した開発・実行が可能です。

---

## ディレクトリ構成

```
mono-project/
├─ mono-front/   # フロントエンド（SvelteKit）
├─ mono-back/    # バックエンド（Quarkus + PostgreSQL）
└─ docker-compose.yml
```

---

## システム構成

```
[ Browser ]
↓
[ Frontend (SvelteKit) ]
↓ fetch
[ Backend (Quarkus API) ]
↓
[ PostgreSQL ]
```

---

## 技術スタック

### Frontend
- SvelteKit
- TypeScript
- Fetch API

### Backend
- Quarkus
- Hibernate ORM (Panache)
- PostgreSQL
- Flyway

---

## 起動手順

### ①前提条件
- Docker Desktop がインストールされていること
- Docker Desktop が起動していること

### ②リポジトリをクローン
```bash
git clone <リポジトリURL>
cd mono-project
```

### ③フロントエンドの依存関係をインストール
```bash
cd mono-frontend
npm install
cd ..
```

### ④Dockerコンテナ起動
```bash
# プロジェクトルート(mono-project/)で実行
docker compose up --build
# (開発時)コード修正後
docker compose up
```

起動確認：
- Frontend
http://localhost:5173/users
- Backend
http://localhost:8080/
- Swagger UI
http://localhost:8080/q/swagger-ui

---

## 機能

- ユーザー検索（userId / userName）
- ソート（昇順 / 降順）
- ページネーション
- CRUD（作成 / 更新 / 削除）

---

## API仕様（抜粋）

### 一覧取得

```
GET /users?page=0&size=5&sort=userId,asc&userId=u&userName=山田
```

### 単体取得

```
GET /users/{id}
```

### 作成

```
POST /users
```

### 更新

```
PUT /users/{id}
```

### 削除

```
DELETE /users/{id}
```

---

## 設計ポイント

### 責務の分離
- Frontend：UI + API呼び出し
- Backend：ビジネスロジック + DB操作

### API設計
- ページング / ソート / 検索をクエリパラメータで統一

### DB管理
- Flywayでマイグレーションを自動適用
