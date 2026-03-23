<script lang="ts">
	export let data;
	export let form;
</script>

<h2>{data.user ? 'ユーザー詳細' : 'ユーザー新規登録'}</h2>

{#if form?.message}
	<p style="color:red;">{form.message}</p>
{/if}

<form method="POST">

	<div>
		<label>
			ユーザーID
			<input name="userId" value={data.user?.userId ?? ''} />
		</label>
	</div>

	<div>
		<label>
			ユーザー名
			<input name="userName" value={data.user?.userName ?? ''} />
		</label>
	</div>

	<div style="margin-top:20px">

		<button type="submit" formaction="?/save">
			保存
		</button>

		{#if data.user}
			<button
				type="submit"
				formaction="?/delete"
				style="margin-left:8px;"
				on:click={(e) => {
					if (!confirm('削除しますか？')) {
						e.preventDefault();
					}
				}}
			>
				削除
			</button>
		{/if}

		<a href="/users">
			<button type="button" style="margin-left:8px;">
				戻る
			</button>
		</a>

	</div>

</form>