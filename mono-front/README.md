# mono-front

SvelteKit を使用したユーザー管理フロントエンドアプリケーションです。  
Quarkus REST API（mono-back）と通信し、ユーザーのCRUD操作を行います。

---

## 技術スタック

- SvelteKit
- TypeScript
- Fetch API
- pnpm

---

## 機能

ユーザー管理機能を提供します。

- ユーザー検索
- ソート
- ページネーション
- ユーザー登録
- ユーザー更新
- ユーザー削除

---

## 起動方法

### ①前提条件
- バックエンド(mono-back)が起動していること
- Node.js（推奨: v18以上）がインストールされていること

### ②フロントエンドの起動
```bash
cd mono-front
pnpm install
pnpm dev
```

ブラウザで以下にアクセス
```
http://localhost:5173
```

---

## 画面一覧

### ユーザー検索画面

機能

- userId（部分一致検索）
- userName（部分一致検索）
- カラムソート（userId, userName）
  - 昇順 / 降順切り替え可能
- ページネーション（1ページあたり5件）

表示項目

|項目|
|---|
|ID|
|ユーザーID|
|ユーザー名|

---

### ユーザー詳細画面

以下の操作が可能です。

- 新規登録
- 更新
- 削除

---

## API連携

Backend API

```
http://localhost:8080
```

使用API

|Method|Endpoint|説明|
|---|---|---|
GET|/users|ユーザー一覧取得|
GET|/users/{id}|ユーザー単体取得|
POST|/users|ユーザー作成|
PUT|/users/{id}|ユーザー更新|
DELETE|/users/{id}|ユーザー削除|

---

## ディレクトリ構成（抜粋）

```
src
└ routes
  └ users
    ├ +page.svelte
    ├ +page.server.ts
    └ [id]
      ├ +page.svelte
      └ +page.server.ts
```

---

## 補足

本アプリケーションは SvelteKit の SPA 構成で実装されています。

画面遷移時にページリロードは行われず、Fetch API によりデータ取得を行います。
