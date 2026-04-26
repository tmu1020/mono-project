# mono-project

SvelteKit（Frontend） + Quarkus（Backend） + PostgreSQL（DB）で構成された  
ユーザー管理Webアプリケーションです。

バックエンドとDBはDockerで起動し、
フロントエンドはローカル環境で起動します。

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
- pnpm

### Backend
- Java 21
- Quarkus
- Hibernate ORM (Panache)
- PostgreSQL
- Flyway

---

## 起動手順

### ①前提条件
- Docker Desktop がインストールされていること
- Docker Desktop が起動していること
- Node.js（推奨: v18以上）がインストールされていること

### ②リポジトリをクローン
```bash
git clone https://github.com/tmu1020/mono-project.git
cd mono-project
```
### ③Dockerコンテナ(DB, バックエンド)の起動
```bash
# プロジェクトルート(mono-project/)で実行
docker compose up --build
```

### ④フロントエンドの起動
```bash
cd mono-front
pnpm install
pnpm dev
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
- Backend ：ビジネスロジック + DB操作

### API設計
- ページング / ソート / 検索をクエリパラメータで統一

### DB管理
- Flywayでマイグレーションを自動適用

## 補足
- 初期データはマイグレーションによって追加される