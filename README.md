# mono-project

SvelteKit（Frontend） + Quarkus（Backend）で構成された  
ユーザー管理アプリケーションです。

---

## 構成

```
mono-project/
├─ mono-front/   # フロントエンド（SvelteKit）
└─ mono-back/    # バックエンド（Quarkus + PostgreSQL）
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

## 起動手順（最短）

### ① DB起動

```bash
cd mono-back
docker compose up -d
```

### ② Backend起動

```bash
cd mono-back
mvn quarkus:dev
```

起動確認：
http://localhost:8080/q/swagger-ui

### ③ Frontend起動

```bash
cd mono-front
pnpm install
pnpm dev
```

起動確認：
http://localhost:5173/users

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

- DTOとEntityの分離
- Service層でトランザクション管理
- 例外を統一（BusinessException）
- ページング / ソート / 検索対応
- FlywayによるDB管理

---

## 補足

- DBはDockerで起動
- CORSは全許可（開発用）
- バリデーションはBean Validationで実施