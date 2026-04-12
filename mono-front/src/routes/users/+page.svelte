<script lang="ts">
	import { goto } from '$app/navigation';

	export let data;

	$: users = data.users;
	$: total = data.total;
	$: page = data.page;

	let sortKey = data.sort as 'userId' | 'userName';
	let order = data.order as 'asc' | 'desc';

	let searchUserId = data.userId;
	let searchUserName = data.userName;

	const pageSize = 5;
	$: totalPages = Math.ceil(total / pageSize);

	function sort(key: typeof sortKey) {

		let newOrder: 'asc' | 'desc';

		if (sortKey === key) {
			newOrder = order === 'asc' ? 'desc' : 'asc';
		} else {
			newOrder = 'asc';
		}

		console.log('クリック時', { before: { sortKey, order }, after: { key, newOrder } });

		sortKey = key;
		order = newOrder;

		goto(
			`?page=1&sort=${key}&order=${newOrder}&userId=${searchUserId}&userName=${searchUserName}`,
			{ invalidateAll: true }
		);
	}

	function movePage(p: number) {
		goto(
			`?page=${p}&sort=${sortKey}&order=${order}&userId=${searchUserId}&userName=${searchUserName}`,
			{ invalidateAll: true }
		);
	}
</script>

<h1>ユーザー検索</h1>

<form method="GET" action="/users" style="margin-bottom: 16px;">
	<label>
		ユーザーID
		<input name="userId" bind:value={searchUserId} />
	</label>

	<label style="margin-left: 8px;">
		ユーザー名
		<input name="userName" bind:value={searchUserName} />
	</label>

	<input type="hidden" name="sort" value={sortKey} />
	<input type="hidden" name="order" value={order} />

	<button type="submit" style="margin-left: 8px;">検索</button>
</form>

<table border="1" cellpadding="8" style="border-collapse: collapse; width: 100%;">
	<thead style="background: #f5f5f5;">
		<tr>
			<th>
				<button on:click={() => sort('userId')}>
					ユーザーID
					{#if sortKey !== 'userId'}
						⇅
					{:else if order === 'asc'}
						▲
					{:else}
						▼
					{/if}
				</button>
			</th>

			<th>
				<button on:click={() => sort('userName')}>
					ユーザー名
					{#if sortKey !== 'userName'}
						⇅
					{:else if order === 'asc'}
						▲
					{:else}
						▼
					{/if}
				</button>
			</th>

			<th style="width: 100px;">詳細</th>
		</tr>
	</thead>

	<tbody>
		{#if users.length === 0}
			<tr>
				<td colspan="3" style="text-align: center;">
					データがありません
				</td>
			</tr>
		{:else}
			{#each users as user (user.id)}
				<tr>
					<td>{user.userId}</td>
					<td>{user.userName}</td>
					<td style="width: 100px;">
						<a href={`/users/${user.id}`} style="text-align: center; display: block;">詳細</a>
					</td>
				</tr>
			{/each}
		{/if}
	</tbody>
</table>

<div style="margin-top: 16px;">
	<button on:click={() => movePage(page - 1)} disabled={page === 1}>
		前へ
	</button>

	<strong style="margin-left: 12px; margin-right: 12px;">
		ページ : {page} / {totalPages}
	</strong> 

	<button on:click={() => movePage(page + 1)} disabled={page === totalPages}>
		次へ
	</button>
	
	<strong style="margin-left: 12px;">
		全件数 : {total}
	</strong> 
</div>

<div style="margin-top: 16px;">
	<a href="/users/new">
		<button>新規登録</button>
	</a>
</div>